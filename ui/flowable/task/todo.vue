<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所在部门" prop="deptIds">
        <treeselect v-model="queryParams.deptIds" 
          :options="deptOptions" 
          empty-text="未找到部门信息" 
          :show-count="true"
					:multiple="true" 
          :flat="true" 
          :normalizer="normalizerDept" 
          search-nested placeholder="请选择" 
          style="width: 220px;"/>
      </el-form-item>
      <el-form-item label="发起人" prop="starterUserName">
        <el-input
          v-model="queryParams.starterUserName"
          placeholder="请输入发起人"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 130px;"
        />
      </el-form-item>
      <el-form-item label="流程类型" prop="flowType">
        <el-select v-model="queryParams.flowType" placeholder="请选择流程类型" clearable style="width: 140px;">
          <el-option
            v-for="dict in dict.type.sys_process_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发起时间">
        <el-date-picker
          v-model="daterangeTime"
          style="width: 220px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="上一级审批结果" prop="flowResult" label-width="110px">
        <el-select v-model="queryParams.flowResult" placeholder="请选择" clearable style="width: 120px">
          <el-option
            v-for="dict in dict.type.sys_process_result"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value, 10)"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="flowList">
      <el-table-column label="序号" align="center" prop="flowNum" width="80"/>
      <el-table-column label="流程类型" align="center" prop="flowType">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_process_type" :value="scope.row.flowType"/>
        </template>
      </el-table-column>
      <el-table-column label="所在部门" align="center" prop="userDept" />
      <el-table-column label="发起人" align="center" prop="userName" />
      <el-table-column label="当前节点" align="center" prop="taskDefName" />
      <el-table-column label="发起时间" align="center" prop="commitTime" />
      
      <el-table-column label="上一级审批结果" align="center" prop="flowResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_process_result" :value="scope.row.flowResult"/>
        </template>
      </el-table-column>
      <el-table-column label="流程描述" align="center" prop="bizDesc">
        <template v-slot="scope">
          <long-table-col :str="scope.row.bizDesc" :len="10"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleProcess(scope.row, 0)"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleProcess(scope.row, 2)"
          >处理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-user-solid"
            @click="handleAssign(scope.row)"
            v-show="false"
          >转办</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            v-if="false"
            @click="handleExport(scope.row)"
          >导出</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.firstTask" 
            @click="handleDelete(scope.row)"
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

    <!-- 审批详情 -->
    <el-dialog :title="title" :visible.sync="open" :width="dialogWidth" v-dialog-drag append-to-body>
      <flow-detail v-if="open" :key="key" :flowType="flowType" :bizUid="row.bizUid" :procInstId="row.procInstId" :procDeployId="row.procDeployId" :taskId="row.taskId" :pageCtl="pageCtl"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 任务转办详情对话框 -->
    <el-dialog title="任务转办" :visible.sync="openAssign" width="600px" v-dialog-drag append-to-body>
      <flow-assign v-if="openAssign" :taskId="assignTaskId"  @closeWindow="closeAssignWin" />
    </el-dialog>

  </div>
</template>

<script>
import { allDeptTreeSelect }  from "@/api/system/user";
import { todoList, delFlow } from "@/api/flowable/process";
import FlowDetail from "@/views/flowable/task/flowdetail";
import FlowAssign from '@/views/flowable/task/assign'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "FlowTodoPage",
  components: { FlowDetail, FlowAssign, Treeselect },
  dicts: ['sys_process_category', 'sys_process_type', 'sys_process_result'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 我的任务列表数据
      flowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dialogWidth: "0px",
      // 审批时间时间范围
      daterangeTime: [],
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptIds: [],
        starterUserName: null,
        flowType: null,
        startTime: null,
        endTime: null,
        flowResult: null,
      },
      key: '',
      pageCtl: 0,
      flowType: null,
      row: {},
      // 任务转办
      openAssign: false,
      assignTaskId:  []
    };
  },
  created() {
    this.getList();
    this.loadDeptTree();
  },
  mounted() {
    this.$nextTick(() => {
      this.dialogWidth = (window.innerWidth - 200 - 400) + "px";
    })
  },
  methods: {
    getList() {
      this.loading = true;
      if (null != this.daterangeTime && '' != this.daterangeTime) {
        this.queryParams.startTime = this.daterangeTime[0] + ' 00:00:00';
        this.queryParams.endTime = this.daterangeTime[1] + ' 23:59:59';
      }
      todoList(this.queryParams).then(response => {
        this.flowList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    loadDeptTree() {
      allDeptTreeSelect().then(res => {
				this.deptOptions = res.data;
			});
    },
    normalizerDept(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children && node.children.length > 0 ? node.children : 0,
        isDefaultExpanded: true
      }
    },
    handleProcess(row, pageCtl) {
      this.open = true;
      this.key = row.bizUid + '_' + pageCtl
      this.row = row
      this.title = "审批详情";
      this.pageCtl = pageCtl
      this.flowType = row.flowType
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.daterangeTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 删除流程（对于撤销的或退回到开始任务的，可以删除）
    handleDelete(row) {
      this.$modal.confirm('是否确认删除？').then(function() {
          return delFlow([row]);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.getList();
    },
    handleAssign(row) {
      this.openAssign = true;
      this.assignTaskId = row.taskId;
    },
    closeAssignWin() {
      this.openAssign = false;
      this.assignTaskId = [];
      this.getList();
    }
  }
};
</script>