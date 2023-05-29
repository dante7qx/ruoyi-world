<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否是模板" prop="template">
        <el-select v-model="queryParams.template" placeholder="请选择" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否删除" prop="delFlag">
        <el-select v-model="queryParams.delFlag" placeholder="请选择">
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate(null, false)"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-document-add"
          size="mini"
          :disabled="multiple"
          v-if="showMenuSetup"
          @click="handleMenuSetUp"
        >菜单设置</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-document-remove"
          size="mini"
          :disabled="multiple"
          @click="handleCancelMenuSetUp"
        >取消菜单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jimureportList" @selection-change="handleSelectionChange" v-adaptive height="100">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" fixed/>
      <el-table-column label="主键" align="center" prop="id" width="165" fixed v-if="false"/>
      <el-table-column label="编码" align="center" prop="code" width="130" fixed/>
      <el-table-column label="名称" align="center" prop="name" width="140" fixed/>
      <el-table-column label="浏览次数" align="center" prop="viewCount" fixed/>
      <el-table-column label="设置菜单" align="center" prop="menuId" fixed>
        <template v-slot="scope">
          <span v-text="scope.row.menuId ? '已设置' : '未设置'"></span>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="note">
        <template slot-scope="scope">
          <long-table-col :str="scope.row.note"/>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="json字符串" align="center" prop="jsonStr" width="100">
        <template slot-scope="scope">
          <long-table-col :str="scope.row.jsonStr"/>
        </template>
      </el-table-column>
      <el-table-column label="请求地址" align="center" prop="apiUrl" />
      <el-table-column label="缩略图" align="center" prop="thumb" />
      <el-table-column label="请求方法" align="center" prop="apiMethod" />
      <el-table-column label="请求编码" align="center" prop="apiCode" />
      <el-table-column label="模板" align="center" prop="template">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.template"/>
        </template>
      </el-table-column>
      <el-table-column label="css增强" align="center" prop="cssStr">
        <template slot-scope="scope">
          <long-table-col :str="scope.row.cssStr"/>
        </template>
      </el-table-column>
      <el-table-column label="js增强" align="center" prop="jsStr">
        <template slot-scope="scope">
          <long-table-col :str="scope.row.jsStr"/>
        </template>
      </el-table-column>
      <el-table-column label="多租户" align="center" prop="tenantId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  fixed="right">
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
            v-if="scope.row.delFlag == '1'"
          >彻底删除</el-button>
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
    
    <!-- 添加或修改报表列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail v-if="open" :id="id" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 设置菜单 -->
    <el-dialog title="设置菜单" :visible.sync="openMenu" width="700px" v-dialog-drag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" v-if="openMenu">
        <el-form-item label="上级菜单" prop="parentMenuId">
          <el-select v-model="form.parentMenuId" filterable remote placeholder="请输入菜单名称" :remote-method="searchMenu" style="width: 100%">
            <el-option
              v-for="item in menuOptions"
              :key="item.menuId"
              :label="item.menuName"
              :value="item.menuId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right;">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJimureport, delJimureport, listMenu, setupJimureportMenu, cancelJimureportMenu } from "@/api/jimureport/report"
import Detail from "./detail"

export default {
  name: "Jimureport",
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
      // 报表列表表格数据
      jimureportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        template: null,
        delFlag: '0',
      },
      id: 0,
      disabled: false,
      // 设置菜单
      showMenuSetup: false,
      openMenu: false,
      form: {},
      rules: { 
        parentMenuId: [{ required: true, message: '请选择上级菜单', trigger: ['blur','change']}]
      },
      menuOptions: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      this.showMenuSetup = this.queryParams.delFlag == 0;
      listJimureport(this.queryParams).then(response => {
        this.jimureportList = response.rows;
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
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.id = row != null ? row.id : this.ids[0];
      if(this.disabled) {
        this.title = "查看报表";
      } else {
        this.title = "修改报表";
      }
      this.open = true;
    },
    handleDelete(row) {
      const ids = row.id;
      this.$modal.confirm('是否确认彻底删除？').then(function() {
        return delJimureport(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleMenuSetUp() {
      this.openMenu = true;
    },
    searchMenu(query) {
      if (query !== '') {
        listMenu(query).then(res => {
          this.menuOptions = res.data 
        })
      }
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const ids = this.ids;
          this.form.ids = ids
          setupJimureportMenu(this.form).then(res => {
            this.cancel();
            this.getList();
            this.$modal.msgSuccess("菜单设置成功");
          })
        }
      })
    },
    cancel() {
      this.openMenu = false;
      this.form = {};
    },
    handleCancelMenuSetUp() {
      const ids = this.ids;
      if(ids && ids.length > 0) {
        cancelJimureportMenu(ids).then(res => {
          this.getList();
          this.$modal.msgSuccess("菜单取消成功");
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
