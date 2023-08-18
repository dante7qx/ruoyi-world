<template>
  <div class="app-container">
    <splitpanes vertical>
      <pane size="20" min-size="20">
        <sys-dept-tree :key="deptLevel" :level="deptLevel" :maxHeight="700" :deptSelected="getListByDept"/>
      </pane>
      <pane>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="业务名称" prop="demoName">
            <el-input
              v-model="queryParams.demoName"
              placeholder="请输入业务名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="业务时间" prop="demoTime">
            <el-date-picker clearable
              v-model="queryParams.demoTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择业务时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            <el-button type="success" icon="el-icon-zoom-in" size="mini" @click="openCustAdvSearch">高级查询</el-button>
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
              @click="handleUpdate(null, false)"
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
              v-show="false"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="demoList" @selection-change="handleSelectionChange" v-adaptive height="100">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column type="index" label="序号" width="50" align="center" />
          <el-table-column label="业务主键ID" align="center" prop="demoId" />
          <el-table-column label="业务名称" align="center" prop="demoName" />
          <el-table-column label="业务时间" align="center" prop="demoTime" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.demoTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="业务图片" align="center" prop="demoImage" width="100">
            <template v-slot="scope">
              <image-preview :src="scope.row.demoImage" :width="50" :height="50"/>
            </template>
          </el-table-column>
          <el-table-column label="业务附件" align="center" prop="attachment" />
          <el-table-column label="业务内容" align="center" prop="demoContent" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleUpdate(scope.row, true)"
                v-hasPermi="['biz:demo:query']"
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
      </pane>
    </splitpanes>
    
    <!-- 添加或修改业务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :demoId="demoId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
    
    <!--自定义高级查询组件 -->
    <cust-adv-search v-if="showCustAdvSearch" :tableName="'t_demo'" :tableAlias="'t'" :searchFunc="customSearch"/>

  </div>
</template>

<script>
import { Splitpanes, Pane } from 'splitpanes'
import 'splitpanes/dist/splitpanes.css'
import { listDemo, delDemo } from "@/api/biz/demo"
import Detail from "./detail"

export default {
  name: "Demo",
  components: {
    Splitpanes, Pane,
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        params: {},
        demoName: null,
        demoTime: null,
        demoImage: null,
        attachment: null,
        demoContent: null,
      },
      demoId: 0,
      disabled: false,
      // 当前登录部门及下级部门
      deptLevel: 1,
      showCustAdvSearch: false	// 打开自定义查询
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
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
      this.queryParams.params = {};
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
      this.demoId = row != null ? row.demoId : this.ids[0];
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
    },
    /** 部门树点击回调函数 */
    getListByDept(deptId, deptNode) {
      this.queryParams.pageNum = 1;
      this.queryParams.deptId = deptId;
      this.getList();
    },
    openCustAdvSearch() {
      this.showCustAdvSearch = false;
      setTimeout(() => { this.showCustAdvSearch = true; }, 0)
    },
    customSearch(key, params) {
      this.queryParams.pageNum = 1;
      this.queryParams.params[key] = params;
      this.getList();
    }
  }
};
</script>
