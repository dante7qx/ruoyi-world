<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="有效期" prop="termOfValidity">
        <el-select v-model="queryParams.termOfValidity" placeholder="请选择有效期" clearable>
          <el-option
            v-for="dict in termOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
        
      </el-form-item>
      <el-form-item label="是否过期" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择是否过期" clearable>
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shareList" @selection-change="handleSelectionChange" v-adaptive height="100">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="报表名称" align="center" prop="reportName" />
      <el-table-column label="预览地址" align="center" prop="previewUrl" width="480">
        <template v-slot="scope">{{ baseUrl + scope.row.previewUrl }}</template>
      </el-table-column>
      <el-table-column label="密码锁" align="center" prop="previewLock" width="120">
        <template v-slot="scope">
          <el-button
            class="view"
            type="text"
            @click.native.prevent="view(scope.row)"
            ><i class="el-icon-view"></i></el-button
          >{{ scope.row.previewLock_ ?  scope.row.previewLock_ : '****'}}
        </template>
      </el-table-column>
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="155" />
      <el-table-column label="有效期" align="center" prop="termOfValidity" width="90">
        <template v-slot="scope"> {{ selectDictLabel(termOptions, scope.row.termOfValidity) }}</template>
      </el-table-column>
      <el-table-column label="是否过期" align="center" prop="status" width="90">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-if="false"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
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
    
    <!-- 添加或修改报表预览对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :id="id" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listShare, delShare } from "@/api/jimureport/share"
import Detail from "./detail"

export default {
  name: "Share",
  components: {
    "detail": Detail
  },
  dicts: ['sys_yes_no'],
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
      // 报表预览表格数据
      shareList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        previewLockStatus: null,
        termOfValidity: null,
        status: null
      },
      id: 0,
      disabled: false,
      baseUrl: window.location.protocol+"//"+window.location.host + process.env.VUE_APP_BASE_API,
      termOptions: [{
          value: '0',
          label: '永久有效'
        }, {
          value: '1',
          label: '1天'
        }, {
          value: '2',
          label: '7天'
      }]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listShare(this.queryParams).then(response => {
        this.shareList = response.rows;
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
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加报表预览";
      this.id = 0;
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.id = row != null ? row.id : this.ids[0];
      if(this.disabled) {
        this.title = "查看报表预览";
      } else {
        this.title = "修改报表预览";
      }
      this.open = true;
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delShare(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jmrep/share/export', {
        ...this.queryParams
      }, `share_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
    view(row) {
      if(row.previewLock_) {
        row.previewLock_ = ''
        this.$set(row, 'previewLock_', '')
      } else {
        this.$set(row, 'previewLock_', row.previewLock)
      }
    }
  }
};
</script>

<style scoped>
.view {
  position: absolute;
  top: 7px;
  left: 90px;
}
</style>