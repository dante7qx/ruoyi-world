<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="栏目名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入栏目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="disabled">
        <el-select v-model="queryParams.disabled" placeholder="请选择" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:info:export']"
          v-show="false"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-if="refreshTable" 
      v-loading="loading" 
      :data="infoList" 
      row-key="categoryId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      :max-height="780">
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="栏目名称" header-align="center" align="left" prop="categoryName" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" width="90" />
      <el-table-column label="状态" align="center" prop="disabled" width="80">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.disabled ? '1' : '0'"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-help"
            @click="handleProp(scope.row.categoryId)"
            v-hasPermi="['system:info:add']"
          >栏目属性</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:info:add']"
            v-if="!scope.row.disabled"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['system:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['system:info:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:info:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 添加或修改信息栏目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :categoryId="categoryId" :parentId="parentId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 栏目属性对话框 -->
    <el-dialog title="栏目属性" :visible.sync="openProp" width="1000px" v-dialog-drag append-to-body>
      <category-prop v-if="openProp" :categoryId="categoryId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listCategory, delCategory } from "@/api/system/infocategory"
import Detail from "./detail"
import CategoryProp from "./prop/index"

export default {
  name: "InfoCategory",
  components: {
    "detail": Detail,
    "category-prop": CategoryProp
  },
  dicts: ['sys_normal_disable'],
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
      // 信息栏目表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openProp: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        params: {},
        categoryName: null,
        disabled: null,
      },
      categoryId: 0,
      parentId: 0,
      disabled: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.infoList = this.handleTree(response.data, "categoryId");
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
    handleProp(categoryId) {
      this.categoryId = categoryId;
      this.openProp = true;
    },
    handleAdd(row) {
      this.open = true;
      this.title = "添加信息栏目";
      if(row) {
        this.parentId = row.categoryId;
      }
      this.categoryId = 0;
      this.disabled = false;
    },
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.categoryId = row != null ? row.categoryId : this.ids[0];
      if(this.disabled) {
        this.title = "查看信息栏目";
      } else {
        this.title = "修改信息栏目";
      }
      this.open = true;
    },
    handleDelete(row) {
      const categoryIds = row.categoryId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delCategory(categoryIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `信息栏目_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
  }
};
</script>
