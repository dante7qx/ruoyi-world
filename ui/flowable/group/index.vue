<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="审批组名称" prop="groupName">
        <el-input
          v-model="queryParams.groupName"
          placeholder="请输入审批组名称"
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
          v-hasPermi="['flowable:group:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['flowable:group:edit']"
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
          v-hasPermi="['flowable:group:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['flowable:group:export']"
          v-show="false"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="groupList" 
      row-key="rowKey"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="审批组名称" align="center" prop="groupName" />
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          <long-table-col :str="scope.row.remark" :len="15"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" width="100"></el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['flowable:group:query']"
            v-if="!scope.row.groupUser"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['flowable:group:edit']"
            v-if="!scope.row.groupUser"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['flowable:group:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-user"
            @click="handleSelUser(scope.row)"
            v-hasPermi="['flowable:group:edit']"
            v-if="!scope.row.groupUser"
          >分配用户</el-button>
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
    
    <!-- 添加或修改流程审批组对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" v-dialog-drag append-to-body>
      <detail :key="key" :groupId="groupId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 选择用户对话框 -->
    <el-dialog title="选择用户" :visible.sync="openSelUser" width="900px" v-dialog-drag append-to-body>
      <el-form :model="queryUserParams" ref="queryUserForm" size="small" :inline="true">
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryUserParams.userName"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleUserQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phonenumber">
        <el-input
          v-model="queryUserParams.phonenumber"
          placeholder="请输入手机号码"
          clearable
          @keyup.enter.native="handleUserQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetUserQuery">重置</el-button>
      </el-form-item>
    </el-form>
      <el-table @row-click="clickRow" ref="userTable" :data="userList" @selection-change="handleUserSelectionChange" height="260px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="用户名称" prop="userName" :show-overflow-tooltip="true" />
        <el-table-column label="用户昵称" prop="nickName" :show-overflow-tooltip="true" />
        <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" />
        <el-table-column label="手机" prop="phonenumber" :show-overflow-tooltip="true" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="totalUser>0"
        :total="totalUser"
        :page.sync="queryUserParams.pageNum"
        :limit.sync="queryUserParams.pageSize"
        @pagination="getUserList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirm">确 定</el-button>
        <el-button @click="openSelUser = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGroup, delGroup, allocate, delGroupUser } from "@/api/flowable/group"
import { listUser } from "@/api/system/user"
import Detail from "./detail"

export default {
  name: "Group",
  components: {
    "detail": Detail
  },
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selRows: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程审批组表格数据
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupName: null,
      },
      groupId: 0,
      disabled: false,
      key: '',
      openSelUser: false,
      selGroupId: null,
      userIds: [],
      userList: [],
      totalUser: 0,
      queryUserParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined
      }
    };
  },
  created() {
    this.getList();
    this.getUserList();
  },
  methods: {
    getList() {
      this.loading = true;
      listGroup(this.queryParams).then(response => {
        this.groupList = response.rows;
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
      this.ids = selection.map(item => item.groupId)
      this.selRows = selection
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加流程审批组";
      this.groupId = 0;
      this.disabled = false;
      this.key = this.nanoid();
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.groupId = row.groupId;
      if(this.disabled) {
        this.title = "查看流程审批组";
      } else {
        this.title = "修改流程审批组";
      }
      this.open = true;
      this.key = this.nanoid();
    },
    handleDelete(row) {
      if(this.selRows.length == 0) {
        this.$modal.confirm('是否确认删除？').then(function() {
            if(row.groupUser) {
              return delGroupUser(row.groupId);
            } else {
              return delGroup(row.groupId);
            }
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {});
        
      } else {
        const groupIds = this.selRows.filter(item => !item.groupUser).map(item => item.groupId)
        const groupUserIds = this.selRows.filter(item => item.groupUser).map(item => item.groupId)
        this.$modal.confirm('是否确认删除？').then(function() {
            if(groupIds.length > 0) {
              return delGroup(groupIds);
            } else if(groupUserIds.length > 0) {
              return delGroupUser(groupUserIds);
            }
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {});
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/group/export', {
        ...this.queryParams
      }, `group_${new Date().getTime()}.xlsx`)
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
    handleSelUser(row) {
      this.openSelUser = true;
      this.$refs.userTable.clearSelection();
      this.selGroupId = row.groupId;
    },
    getUserList() {
      listUser(this.queryUserParams).then(res => {
        this.userList = res.rows;
        this.totalUser = res.total;
      })
    },
    handleUserQuery() {
      this.queryUserParams.pageNum = 1;
      this.getUserList();
    },
    resetUserQuery() {
      this.resetForm("queryUserForm");
      this.handleUserQuery();
    },
    handleUserSelectionChange(selection) {
      this.userIds = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    clickRow(row) {
      this.$refs.userTable.toggleRowSelection(row);
    },
    confirm() {
      const userIds = this.userIds.join(",");
      if (userIds == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      allocate({ groupId: this.selGroupId, userIds: this.userIds }).then(res => {
        this.$modal.msgSuccess("用户分配成功！");
        this.openSelUser = false;
        this.getList();
      })
    }
  }
};
</script>
