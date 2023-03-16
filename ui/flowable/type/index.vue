<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程分类" prop="flowCategory">
        <el-select v-model="queryParams.flowCategory" placeholder="请选择流程分类" clearable>
          <el-option
            v-for="dict in dict.type.sys_process_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流程类型" prop="typeName">
        <el-select v-model="queryParams.typeName" placeholder="请选择流程类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_process_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流程组" prop="flowGroupId">
        <el-select v-model="queryParams.flowGroupId" placeholder="请选择流程组" clearable>
          <el-option
            v-for="group in groupList"
            :key="group.groupId"
            :label="group.groupName"
            :value="group.groupId"
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
          v-hasPermi="['flowable:type:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate(null, true)"
          v-hasPermi="['flowable:type:edit']"
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
          v-hasPermi="['flowable:type:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['flowable:type:export']"
          v-show="false"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="流程类型" align="center" prop="typeName">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_process_type" :value="scope.row.typeName"/>
        </template>
      </el-table-column>
      <el-table-column label="流程分类" align="center" prop="flowCategory">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.flowCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="流程组" align="center" prop="flowGroupName" />
      <el-table-column label="流程名称" align="center" prop="flowDefName" />
      <el-table-column label="流程定义Key" align="center" prop="flowDefKey" />
      <el-table-column label="权重" align="center" prop="orderNum" width="60"/>
      <el-table-column label="备注" align="center" prop="remark" >
        <template v-slot="scope">
          <long-table-col :str="scope.row.remark" :len="10"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['flowable:type:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['flowable:type:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['flowable:type:remove']"
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
    
    <!-- 添加或修改流程类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail :key="key" :typeId="typeId" :disabled="disabled" :groups="groupList" @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listType, delType } from "@/api/flowable/type"
import { getGroups } from "@/api/flowable/group"
import Detail from "./detail"

export default {
  name: "FlowType",
  components: {
    "detail": Detail
  },
  dicts: ['sys_process_category', 'sys_process_type'],
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
      // 流程类型表格数据
      typeList: [],
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        typeName: null,
        flowCategory: null,
        flowGroupId: null,
        flowDefKey: null,
        flowDeployId: null,
        orderNum: null,
      },
      typeId: 0,
      disabled: false,
      key: ''
    };
  },
  created() {
    this.getGroupList();
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listType(this.queryParams).then(response => {
        this.typeList = response.rows;
        console.log(this.typeList);
        this.total = response.total;
        this.loading = false;
      });
    },
    getGroupList() {
      getGroups().then(response => {
        this.groupList = response.data;
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.typeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加流程类型";
      this.typeId = 0;
      this.disabled = false;
      this.key = this.nanoid();
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.typeId = row != null ? row.typeId : this.ids[0];
      if(this.disabled) {
        this.title = "查看流程类型";
      } else {
        this.title = "修改流程类型";
      }
      this.open = true;
      this.key = this.nanoid();
    },
    handleDelete(row) {
      const typeIds = row.typeId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delType(typeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/type/export', {
        ...this.queryParams
      }, `type_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    }
  }
};
</script>
