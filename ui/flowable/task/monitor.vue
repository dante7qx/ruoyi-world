<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="所在部门" prop="deptIds">
            <treeselect v-model="queryParams.deptIds" 
              :options="deptOptions" 
              empty-text="未找到部门信息" 
              :show-count="true"
              :multiple="true" 
              :flat="true" 
              :normalizer="normalizerDept" 
              search-nested placeholder="请选择" 
              style="width: 240px;"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="发起人" prop="starterUserName" label-width="110px">
            <el-input
              v-model="queryParams.starterUserName"
              placeholder="请输入发起人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="流程类型" prop="flowType">
            <el-select v-model="queryParams.flowType" placeholder="请选择流程类型" clearable>
              <el-option
                v-for="dict in dict.type.sys_process_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="流程状态" prop="flowStatus">
            <el-select v-model="queryParams.flowStatus" placeholder="请选择流程状态" clearable>
              <el-option
                v-for="dict in dict.type.sys_process_status"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value, 10)"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="发起时间">
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
        </el-col>
        <el-col :span="6">
          <el-form-item label="上一级审批结果" prop="flowResult" label-width="110px">
            <el-select v-model="queryParams.flowResult" placeholder="请选择" clearable>
              <el-option
                v-for="dict in dict.type.sys_process_result"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value, 10)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasRole="['admin']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="flowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="flowNum" width="80"/>
      <el-table-column label="流程类型" align="center" prop="flowType" width="90">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_process_type" :value="scope.row.flowType"/>
        </template>
      </el-table-column>
      <el-table-column label="所在部门" align="center" prop="userDept" />
      <el-table-column label="发起人" align="center" prop="userName" />
      <el-table-column label="当前节点" align="center" prop="taskDefName" />
      <el-table-column label="发起时间" align="center" prop="commitTime" />
      <el-table-column label="流程状态" align="center" prop="flowStatus" width="80">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_process_status" :value="scope.row.flowStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上一级审批结果" align="center" prop="flowResult" width="120">
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
            icon="el-icon-download"
            v-if="false"
            @click="handleExport(scope.row)"
          >导出</el-button>
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
      <flow-detail v-if="open" :key="key" :flowType="flowType" :bizUid="row.bizUid" :procInstId="row.procInstId" :procDeployId="row.procDeployId" :pageCtl="pageCtl"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { allDeptTreeSelect }  from "@/api/system/user"
import { monitorList, delFlow } from "@/api/flowable/process";
import FlowDetail from "@/views/flowable/task/flowdetail";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "FlowMonitorPage",
  components: { FlowDetail, Treeselect },
  dicts: ['sys_process_category', 'sys_process_type', 'sys_process_status', 'sys_process_result'],
  data() {
    return {
       // 非多个禁用
       multiple: true,
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
        flowStatus: null,
        flowResult: null,
      },
      key: '',
      pageCtl: 0,
      flowType: null,
      row: {},
      delRows: []
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
      monitorList(this.queryParams).then(response => {
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
    handleSelectionChange(selection) {
      this.delRows = selection
      this.multiple = !selection.length
    },
    handleDelete() {
      const rows = this.delRows
      this.$modal.confirm('是否确认删除？').then(function() {
          return delFlow(rows);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.getList();
    }
  }
};
</script>