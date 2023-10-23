<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="字典名称" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="请输入字典名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="字典编码" prop="dictCode">
        <el-input
          v-model="queryParams.dictCode"
          placeholder="请输入字典编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入描述"
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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-refresh-right"
          size="mini"
          @click="handleSync"
        >同步系统数据字典</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dictList" @selection-change="handleSelectionChange" v-adaptive height="100">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="字典名称" align="center" prop="dictName">
        <template v-slot="scope">
          <el-link type="primary" :underline="false" @click="showDictItem(scope.row.id)">{{ scope.row.dictName }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="字典编码" align="center" prop="dictCode" />
      <el-table-column label="描述" align="center" prop="description" />
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
    
    <!-- 添加或修改报表字典对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :id="id" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 字典列表 -->
    <el-drawer
      title="字典列表"
      :visible.sync="openDictItem"
      direction="rtl"
      size="45%"
      :before-close="handleClose">
      <item-list :key="id" :dictId="id" />
    </el-drawer>

    <!--系统数据字典-->
    <el-dialog title="系统数据字典" :visible.sync="openSysDict" width="900px" v-dialog-drag append-to-body>
      <el-form :model="querySysDictParams" ref="querySysForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input
            v-model="querySysDictParams.dictName"
            placeholder="请输入字典名称"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input
            v-model="querySysDictParams.dictType"
            placeholder="请输入字典类型"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loadingSys" :data="sysDictList" :maxHeight="600" @selection-change="handleSysDictSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="字典名称" align="center" prop="dictName" :show-overflow-tooltip="true" />
        <el-table-column label="字典类型" align="center" prop="dictType" :show-overflow-tooltip="true"/>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="totalSys>0"
        :total="totalSys"
        :page.sync="querySysDictParams.pageNum"
        :limit.sync="querySysDictParams.pageSize"
        @pagination="getSysDictList"
      />
      <div slot="footer" class="dialog-footer" style="text-align: right;">
        <el-button type="primary" @click="syncSysDict" >确 定</el-button>
        <el-button @click="cancelSyscDict">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDict, delDict, syncDict } from "@/api/jimureport/dict"
import { listType } from "@/api/system/dict/type"
import Detail from "./detail"
import ItemList from "./dictitem/index"

export default {
  name: "Dict",
  components: {
    "detail": Detail,
    "item-list": ItemList
  },
  dicts: ['sys_normal_disable'],
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
      // 报表字典表格数据
      dictList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictName: null,
        dictCode: null,
        description: null,
        type: null,
        tenantId: null
      },
      id: 0,
      disabled: false,
      openDictItem: false,
      openSysDict: false,
      totalSys: 0,
      loadingSys: false, 
      sysDictList: [],
      sysDictRows: [],
      querySysDictParams: {
        pageNum: 1,
        pageSize: 30,
        dictName: null,
        dictType: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listDict(this.queryParams).then(response => {
        this.dictList = response.rows;
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
      this.title = "添加报表字典";
      this.id = 0;
      this.disabled = false;
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.id = row != null ? row.id : this.ids[0];
      if(this.disabled) {
        this.title = "查看报表字典";
      } else {
        this.title = "修改报表字典";
      }
      this.open = true;
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delDict(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
    handleSync() {
      this.openSysDict = true
      this.getSysDictList()
    },
    getSysDictList() {
      listType(this.querySysDictParams).then(res => {
        this.sysDictList = res.rows
        this.totalSys = res.total;
        this.loadingSys = false;
      })
    },
    handleSysDictSelectionChange(selection) {
      this.sysDictRows = selection
    },
    syncSysDict() {
      if(!this.sysDictRows || this.sysDictRows.length == 0) {
        this.$modal.msgError("请选择要进行同步的数据字典！");
        return false
      }
      syncDict(this.sysDictRows).then(res => {
        this.$modal.msgSuccess("同步成功");
        this.cancelSyscDict();
        this.getList();
      })
    },
    cancelSyscDict() {
      this.resetForm("querySysForm");
      this.sysDictList = []
      this.sysDictRows = []
      this.openSysDict = false
    },
    showDictItem(id) {
      this.id = id
      this.openDictItem = true
    },
    handleClose() {
      this.openDictItem = false
      this.id = 0
    }
  }
};
</script>
