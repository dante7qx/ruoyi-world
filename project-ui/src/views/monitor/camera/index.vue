<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="监控名称" prop="monitorName">
        <el-input
          v-model="queryParams.monitorName"
          placeholder="请输入监控名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['monitor:camera:add']"
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
          v-hasPermi="['monitor:camera:export']"
          v-show="false"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <span style="color: red;">1、在系统设置中，设置监控服务器地址。2、添加监控后，请点击播放按钮，获取解析地址。3、视频无法播放且监控服务器正常的情况下，也请点击播放按钮。</span>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-if="refreshTable" 
      v-loading="loading" 
      :data="cameraList" 
      row-key="monitorId" 
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      v-adaptive height="100">
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="监控名称" header-align="center" align="left" prop="monitorName" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" width="80" />
      <el-table-column label="RTSP地址" align="center" prop="rtspUrl" />
      <el-table-column label="播放地址" align="center">
        <el-table-column label="URI" align="center" prop="playUri" width="370"></el-table-column>
        <el-table-column label="播放" align="center" width="60">
          <template slot-scope="scope">
            <i class="el-icon-video-play" style="cursor: pointer;" @click="parseRTSP(scope.row)"/>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" width="160">
        <template slot-scope="scope">
          <long-table-col :str="scope.row.remark"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['monitor:camera:add']"
            v-if="scope.row.parentId == 0"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['monitor:camera:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['monitor:camera:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['monitor:camera:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 添加或修改视频监控对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :monitorId="monitorId" :parentId="parentId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>
  </div>
</template>

<script>
import { listCamera, delCamera, playCamera, updateCamera } from "@/api/monitor/camera"
import Detail from "./detail"

export default {
  name: "Camera",
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
      // 视频监控表格数据
      cameraList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        monitorName: null
      },
      monitorId: 0,
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
      listCamera(this.queryParams).then(response => {
        const result = response.data
        this.cameraList = this.handleTree(result.data, "monitorId");
        this.loading = false;
      });
    },
    handleQuery() {
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleAdd(row) {
      this.open = true;
      this.title = "添加视频监控";
      this.monitorId = 0;
      if(row) {
        this.parentId = row.monitorId;
      }
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.monitorId = row != null ? row.monitorId : this.ids[0];
      if(this.disabled) {
        this.title = "查看视频监控";
      } else {
        this.title = "修改视频监控";
      }
      this.open = true;
    },
    handleDelete(row) {
      const monitorIds = row.monitorId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delCamera(monitorIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('monitor/camera/export', {
        ...this.queryParams
      }, `camera_${new Date().getTime()}.xlsx`)
    },
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    parseRTSP(row) {
      if(row.rtspUrl) {
        playCamera(row).then(res => {
          row.playUri = res.data.path
          updateCamera(row).then(res => {})
        })
      }
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    }
  }
};
</script>
