<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="daterangeStartTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="daterangeEndTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="请假原因" prop="leaveReason">
        <el-input
          v-model="queryParams.leaveReason"
          placeholder="请输入请假原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-show="false"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="demoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假原因" align="center" prop="leaveReason" />
      <el-table-column label="状态" align="center" prop="flowMonitor.status">
        <template slot-scope="scope">
          <span v-if="scope.row.flowMonitor == null" v-html="'申请'"></span>
          <span v-else>{{ scope.row.flowMonitor.status }}{{  scope.row.flowMonitor.finished ? scope.row.flowMonitor.passed ? ' - 通过' : ' - 驳回' : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-if="scope.row.flowMonitor == null || scope.row.flowMonitor.commited == true"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="scope.row.flowMonitor == null || scope.row.flowMonitor.commited == true"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    
    <!-- 添加或修改业务流程示例对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail :key="key" :demoId="demoId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listDemo, delDemo } from "@/api/flowable/demo"
import Detail from "./detail"

export default {
  name: "Demo",
  components: {
    "detail": Detail
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 业务流程示例表格数据
      demoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeStartTime: [],
      // 更新时间时间范围
      daterangeEndTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        leaveUserId: null,
        uid: null,
        startTime: null,
        endTime: null,
        leaveReason: null,
      },
      demoId: 0,
      disabled: false,
      key: ''
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeStartTime && '' != this.daterangeStartTime) {
        this.queryParams.params["beginStartTime"] = this.daterangeStartTime[0] + " 00:00:00";
        this.queryParams.params["endStartTime"] = this.daterangeStartTime[1] + " 23:59:59";
      }
      if (null != this.daterangeEndTime && '' != this.daterangeEndTime) {
        this.queryParams.params["beginEndTime"] = this.daterangeEndTime[0] + " 00:00:00";
        this.queryParams.params["endEndTime"] = this.daterangeEndTime[1] + " 23:59:59";
      }
      listDemo(this.queryParams).then(response => {
        this.demoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.daterangeStartTime = [];
      this.daterangeEndTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.demoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加业务流程示例";
      this.demoId = 0;
      this.disabled = false;
      this.key = this.nanoid();
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.demoId = row.demoId;
      if(this.disabled) {
        this.title = "查看业务流程示例";
      } else {
        this.title = "修改业务流程示例";
      }
      this.open = true;
      this.key = this.nanoid();
    },
    handleDelete(row) {
      const demoIds = row.demoId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delDemo(demoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/demo/export', {
        ...this.queryParams
      }, `demo_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    }
  }
};
</script>
