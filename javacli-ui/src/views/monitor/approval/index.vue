<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="业务模块" prop="bizModel">
        <el-select v-model="queryParams.bizModel" placeholder="请选择业务模块" clearable>
          <el-option
            v-for="dict in dict.type.sys_biz_model"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作类型" prop="operateType">
        <el-select v-model="queryParams.operateType" placeholder="请选择操作类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_approval_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作人" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入操作人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker
          v-model="daterangeOperateTime"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="approvallogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="业务模块" align="center" prop="bizModel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_biz_model" :value="scope.row.bizModel"/>
        </template>
      </el-table-column>
      <el-table-column label="审批意见" align="center" prop="comment" />
      <el-table-column label="操作类型" align="center" prop="operateType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_approval_type" :value="scope.row.operateType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="操作时间" align="center" prop="operateTime" width="180" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listApprovallog } from "@/api/monitor/approvallog"

export default {
  name: "Approvallog",
  dicts: ['sys_approval_type', 'sys_biz_model'],
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
      // 审批日志表格数据
      approvallogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 操作时间时间范围
      daterangeOperateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bizModel: null,
        bizId: null,
        comment: null,
        operateType: null,
        operator: null,
        operateTime: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOperateTime && '' != this.daterangeOperateTime) {
        this.queryParams.params["beginOperateTime"] = this.daterangeOperateTime[0] + " 00:00:00";
        this.queryParams.params["endOperateTime"] = this.daterangeOperateTime[1] + " 23:59:59";
      }
      listApprovallog(this.queryParams).then(response => {
        this.approvallogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.daterangeOperateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    }
  }
};
</script>
