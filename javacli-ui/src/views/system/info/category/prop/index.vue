<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="属性名称" prop="propName">
        <el-input
          v-model="queryParams.propName"
          placeholder="请输入属性名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性类型" prop="propType">
        <el-select v-model="queryParams.propType" clearable filterable placeholder="请选择" >
          <el-option
            v-for="(dict, index) in propTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value">
          </el-option>
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate(null, false)"
          v-hasPermi="['system:info:edit']"
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
          v-hasPermi="['system:info:remove']"
        >删除</el-button>
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

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange" v-adaptive height="100" :max-height="650">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="栏目" align="center" prop="categoryName" />
      <el-table-column label="属性名称" align="center" prop="propName" />
      <el-table-column label="属性类型" align="center" prop="propType">
        <template v-slot="scope">
          {{ selectDictLabels(propTypeOptions, scope.row.propType) }}
        </template>
      </el-table-column>
      <el-table-column label="属性类型值" align="center" prop="propTypeVal">
        <template v-slot="scope">
          {{ selectDictLabels(dictTypeOptions, scope.row.propTypeVal) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template v-slot="scope">
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['system:info:edit']"
          >修改</el-button>
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
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    
    <!-- 添加或修改信息栏目属性对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :propId="propId" :categoryId="categoryId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";
import { listProp, delProp, getPropType } from "@/api/system/infocategory";
import Detail from "./detail";

export default {
  name: "Info",
  components: {
    "detail": Detail
  },
  props: {
    categoryId: {
      type: Number,
      required: true,
      default: 0
    },
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
      // 信息栏目属性表格数据
      infoList: [],
      // 属性类型
      propTypeOptions: [],
      // 字典类型
      dictTypeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        params: {},
        categoryId: null,
        propName: null,
        propType: null,
      },
      propId: 0,
      disabled: false
    };
  },
  created() {
    this.loadSelectOption();
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      this.queryParams.categoryId = this.categoryId
      listProp(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    loadSelectOption() {
      this.propTypeOptions = getPropType();
      getDictOptionselect().then(response => {
        this.dictTypeOptions = response.data.map(d => {
          return { label: d.dictName , value: d.dictType }
        });
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
      this.ids = selection.map(item => item.propId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加信息栏目属性";
      this.propId = 0;
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.propId = row != null ? row.propId : this.ids[0];
      if(this.disabled) {
        this.title = "查看信息栏目属性";
      } else {
        this.title = "修改信息栏目属性";
      }
      this.open = true;
    },
    handleDelete(row) {
      const propIds = row.propId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delProp(propIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `信息栏目属性_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
  }
};
</script>
