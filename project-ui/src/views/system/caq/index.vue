<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入模板名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据库表名" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入数据库表名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="caqList" v-adaptive height="100">
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="模板名称" align="center" prop="templateName" />
      <el-table-column label="数据库表名" align="center" prop="tableName" />
      <el-table-column label="数据库表别名" align="center" prop="tableAlias" />
      <el-table-column label="数据库表主键列名" align="center" prop="pkCol" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['system:caq:query']"
          >查看</el-button>
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
    
    <!-- 添加或修改自定义高级查询模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :queryId="queryId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listCaq, delCaq } from "@/api/system/caq"
import Detail from "./detail"

export default {
  name: "Caq",
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
      // 自定义高级查询模板表格数据
      caqList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateName: null,
        tableName: null,
        tableAlias: null,
      },
      queryId: 0,
      disabled: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listCaq(this.queryParams).then(response => {
        this.caqList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.queryId = row != null ? row.queryId : this.ids[0];
      if(this.disabled) {
        this.title = "查看自定义高级查询模板条件";
      } else {
        this.title = "修改自定义高级查询模板条件";
      }
      this.open = true;
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    }
  }
};
</script>
