<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="业务名称" prop="demoName">
        <el-input
          v-model="queryParams.demoName"
          placeholder="请输入业务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务时间">
        <el-date-picker
          v-model="daterangeDemoTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="角色名" prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="请输入角色名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位编码" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="请输入岗位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="success" icon="el-icon-zoom-in" size="mini" @click="openCustAdvSearch">高级查询</el-button>
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
          v-hasPermi="['biz:demo:add']"
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
          v-hasPermi="['biz:demo:edit']"
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
          v-hasPermi="['biz:demo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['biz:demo:import']"
          v-show="true"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:demo:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="batchAdd"
          v-hasPermi="['biz:demo:add']"
        >批量新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="clearData"
          v-hasPermi="['biz:demo:remove']"
        >清空数据</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="demoList" @selection-change="handleSelectionChange" v-adaptive height="100">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="业务名称" align="center" prop="demoName">
        <template v-slot="scope">
          <long-table-col :str="scope.row.demoName" :len="5"/>
        </template>
      </el-table-column>
      <el-table-column label="业务时间" align="center" prop="demoTime" width="110">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.demoTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务图片" align="center" prop="demoImage" width="180">
        <template v-slot="scope">
          <image-preview :src="scope.row.demoImage" :width="50" :height="50"/>
          <label class="unpack" @click="downloadZip(scope.row)">打包下载</label>
        </template>
      </el-table-column>
      <el-table-column label="角色名" align="center" prop="roleName" />
      <el-table-column label="岗位编码" align="center" prop="postCode" />
      <el-table-column label="创建人" align="center" prop="createBy" width="100"></el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" width="100"></el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['biz:demo:edit']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['biz:demo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:demo:remove']"
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
    
    <!-- 添加或修改业务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" v-dialog-drag append-to-body>
      <detail v-if="open" :demoId="demoId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog title="业务导入" :visible.sync="upload.open" width="400px" v-dialog-drag append-to-body>
      <el-upload
        ref="uploadImport"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!--自定义高级查询组件 -->
    <cust-adv-search v-if="showCustAdvSearch" :tableName="'t_demo'" :tableAlias="'t'" :searchFunc="customSearch"/>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth"
import { listDemo, delDemo, addBatchDemo, clearDemoData } from "@/api/biz/demo"
import Detail from "./detail"

export default {
  name: "Demo",
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
      // 业务表格数据
      demoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeDemoTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        demoName: null,
        demoTime: null,
        demoImage: null,
        attachment: null,
        roleName: null,
        postCode: null,
        params: {}
      },
      // 导入参数
      upload: {
        open: false,
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/biz/demo/importData"
      },
      demoId: 0,
      disabled: false,
      showCustAdvSearch: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      if (null != this.daterangeDemoTime && '' != this.daterangeDemoTime) {
        this.queryParams.params["beginDemoTime"] = this.daterangeDemoTime[0];
        this.queryParams.params["endDemoTime"] = this.daterangeDemoTime[1];
      }
      listDemo(this.queryParams).then(response => {
        this.demoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.queryParams.params = {};
      this.daterangeDemoTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.demoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加业务";
      this.demoId = 0;
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.demoId = row != null ? row.demoId : this.ids[0];
      if(this.disabled) {
        this.title = "查看业务";
      } else {
        this.title = "修改业务";
      }
      this.open = true;
    },
    handleDelete(row) {
      const demoIds = row.demoId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delDemo(demoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.open = true;
    },
    importTemplate() {
      this.download('biz/demo/importTemplate', {
      }, `业务模板.xlsx`)
    },
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.uploadImport.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    submitFileForm() {
      this.$refs.uploadImport.submit();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/demo/export', {
        ...this.queryParams
      }, `业务_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
    batchAdd() {
      this.$modal.loading('数据处理中...');
      addBatchDemo().then(res => {
        this.$modal.msgSuccess("新增成功");
        this.$modal.closeLoading();
        this.getList();
      })
    },
    clearData() {
      clearDemoData().then(res => {
        this.$modal.msgSuccess("数据已清空");
        this.getList();
      })
    },
    downloadZip(row) {
      this.$download.resource2zip({ resource: row.demoImage, fileName: "示例图片.zip"})
    },
    openCustAdvSearch() {
      this.showCustAdvSearch = false;
      setTimeout(() => { this.showCustAdvSearch = true; }, 0)
    },
    customSearch(key, params) {
      this.queryParams.pageNum = 1;
      this.queryParams.params[key] = params;
      this.getList();
    }
  }
};
</script>

<style scoped>
.unpack {
  position: absolute; 
  top: 28px;
  margin-left: 5px;  
  cursor: pointer;
}
</style>