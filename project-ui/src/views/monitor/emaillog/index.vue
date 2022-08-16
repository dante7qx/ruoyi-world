<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="邮件主题" prop="subject">
        <el-input
          v-model="queryParams.subject"
          placeholder="请输入邮件主题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送时间">
        <el-date-picker
          v-model="daterangeSendDate"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:emaillog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="emaillogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" width="50" align="center" />
      <el-table-column label="接收人" align="center" prop="sendTo" />
      <el-table-column label="抄送人" align="center" prop="sendCc" />
      <el-table-column label="邮件主题" align="center" prop="subject" />
      <el-table-column label="邮件内容" align="center" prop="content">
        <template slot-scope="scope">
          <el-popover placement="top-start" width="300" trigger="hover" v-if="scope.row.content && scope.row.content.length > 20">
            <div style="padding: 10px; height: 400px; overflow: auto;">
              {{ scope.row.content }}
            </div>
            <span slot="reference">{{scope.row.content.substring(0, 20) + '...' }}</span>
          </el-popover>
          <span v-else>{{ scope.row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发送时间" align="center" prop="sendDate" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['system:emaillog:edit']"
          >详情</el-button>
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
    
    <!-- 添加或修改邮件日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <detail :key="nanoid()" :emailId="emailId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listEmaillog, delEmaillog } from "@/api/system/emaillog"
import Detail from "./detail"

export default {
  name: "Emaillog",
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
      // 邮件日志表格数据
      emaillogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeSendDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sendTo: null,
        sendCc: null,
        sendBcc: null,
        subject: null,
        content: null,
        sendDate: null,
      },
      emailId: 0,
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
      if (null != this.daterangeSendDate && '' != this.daterangeSendDate) {
        this.queryParams.params["beginSendDate"] = this.daterangeSendDate[0];
        this.queryParams.params["endSendDate"] = this.daterangeSendDate[1];
      }
      listEmaillog(this.queryParams).then(response => {
        this.emaillogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.daterangeSendDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.emailId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加邮件日志";
      this.emailId = 0;
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.emailId = row.emailId;
      if(this.disabled) {
        this.title = "查看邮件日志";
      } else {
        this.title = "修改邮件日志";
      }
      this.open = true;
    },
    handleDelete(row) {
      const emailIds = row.emailId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delEmaillog(emailIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/emaillog/export', {
        ...this.queryParams
      }, `emaillog_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    }
  }
};
</script>
