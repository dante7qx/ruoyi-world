<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="业务名称" prop="demoName">
        <el-input
          v-model="queryParams.demoName"
          placeholder="请输入业务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务时间">
        <el-date-picker
          v-model="daterangeDemoTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['biz:demo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:demo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['biz:demo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:demo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="demoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="业务名称" align="center" prop="demoName" />
      <el-table-column label="业务时间" align="center" prop="demoTime" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.demoTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务图片" align="center" prop="demoImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.demoImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" width="100"></el-table-column>
      <el-table-column label="业务时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" width="100"></el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['biz:demo:edit']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['biz:demo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:demo:remove']"
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
    
    <!-- 添加或修改业务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <detail :key="nanoid()" :demoId="demoId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listDemo, delDemo } from "@/api/biz/demo"
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
      // 业务表格数据
      demoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeDemoTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        demoName: null,
        demoTime: null,
        demoImage: null,
        attachment: null,
      },
      demoId: 0,
      disabled: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDemoTime && '' != this.daterangeDemoTime) {
        this.queryParams.params["beginDemoTime"] = this.daterangeDemoTime[0];
        this.queryParams.params["endDemoTime"] = this.daterangeDemoTime[1];
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
      this.daterangeDemoTime = [];
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
      this.title = "添加业务";
      this.demoId = 0;
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.demoId = row.demoId;
      if(this.disabled) {
        this.title = "查看业务";
      } else {
        this.title = "修改业务";
      }
      this.open = true;
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
      this.download('biz/demo/export', {
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
