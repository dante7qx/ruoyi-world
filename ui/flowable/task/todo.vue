<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程分类" prop="flowCategory">
        <el-select v-model="queryParams.flowCategory" placeholder="请选择" clearable>
          <el-option
            v-for="dict in dict.type.sys_process_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流程名称" prop="flowName">
        <el-input
          v-model="queryParams.flowName"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收时间">
        <el-date-picker
          v-model="daterangeTime"
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
          icon="el-icon-user-solid"
          size="mini"
          :disabled="multiple"
          @click="handleAssign"
        >转办
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasRole="['admin']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="todoList" border @selection-change="handleSelectionChange" @row-click="handleProcess">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="流程分类" align="center" prop="category">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_process_category" :value="scope.row.category"/>
        </template>
      </el-table-column>
      <el-table-column label="流程名称" align="center" prop="procDefName"/>
      <el-table-column label="任务节点" align="center" prop="taskName"/>
      <el-table-column label="流程版本" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">v{{scope.row.procDefVersion}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="流程发起人" align="center">
        <template slot-scope="scope">
          <label>{{scope.row.startUserName}} <el-tag type="info" size="mini">{{scope.row.startDeptName}}</el-tag></label>
        </template>
      </el-table-column>
      <el-table-column label="接收时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleProcess(scope.row)"
          >处理
          </el-button>
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

     <!-- 审批记录详情对话框 -->
     <el-dialog :title="title" :visible.sync="open" width="1000px" v-dialog-drag append-to-body>
      <approval-record :key="key" :flowData="flowData" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 任务转办详情对话框 -->
    <el-dialog title="任务转办" :visible.sync="openAssign" width="600px" v-dialog-drag append-to-body>
      <flow-assign :key="assignKey" :taskId="assignTaskId"  @closeWindow="closeAssignWin" />
    </el-dialog>
  </div>
</template>

<script>
import { todoList, delProcess } from "@/api/flowable/flowlist"
import ApprovalRecord from "@/views/flowable/task/record/index"
import FlowAssign from '@/views/flowable/task/assign'

export default {
  name: "FlowTodoPage",
  components: {
    ApprovalRecord,
    FlowAssign
  },
  dicts: ['sys_process_category'],
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
      // 流程待办任务表格数据
      todoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 接受时间时间范围
      daterangeTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        flowName: null,
        flowCategory: null,
        startTime: null,
        endTime: null
      },
      key: '',
      flowData: null,
      disabled: false,
      // 任务转办
      assignKey: '',
      openAssign: false,
      assignTaskId: ['22', '33']
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      if (null != this.daterangeTime && '' != this.daterangeTime) {
        this.queryParams.startTime = this.daterangeTime[0] + ' 00:00:00';
        this.queryParams.endTime = this.daterangeTime[1] + ' 23:59:59';
      }
      todoList(this.queryParams).then(response => {
        this.todoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 打开处理页面
    handleProcess(row) {
      this.open = true;
      this.title = "审批详情";
      this.key = this.nanoid();
      this.flowData = row;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.procInsId)
      this.assignTaskId = selection.map(item => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.procInsId || this.ids;
      this.$confirm('是否确认删除所选流程吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delProcess(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
    handleAssign() {
      this.openAssign = true
      this.assignKey = this.nanoid();
    },
    closeAssignWin() {
      this.openAssign = false;
      this.handleQuery();
    }
    
  }
};
</script>

