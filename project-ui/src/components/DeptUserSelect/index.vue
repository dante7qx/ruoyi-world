<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="5">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="按部门名称过滤"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px;width: 90%;"
            />
        </div>
        <el-tree
          node-key="id"
          highlight-current
          :data="deptOptions"
          :props="defaultProps"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          
          ref="deptTree"
          default-expand-all
          @node-click="handleNodeClick"
        />
      </el-col>
      <el-col :span="19">
        <div style="margin-bottom: 10px;">
          <label>已选用户：</label>
          <el-tag
            v-for="user in selUserRow"
            :key="user.userId"
            closable
            :disable-transitions="false"
            @close="removeSelUser(user)"
            style="margin-right: 5px;">
            {{ user.nickName }}
          </el-tag>
        </div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="用户名" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row>
          <el-table @row-click="clickUserRow" ref="userTable" :data="userList" @selection-change="handleSelectionChange" height="350px" max-height="380px">
            <el-table-column type="selection" width="55" v-if="multi"></el-table-column>
            <el-table-column type="index" label="序号" width="50" align="center" />
            <el-table-column label="用户名" prop="nickName" :show-overflow-tooltip="true" />
            <el-table-column label="所属部门" prop="dept.deptName" :show-overflow-tooltip="true" />
            <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" />
            <el-table-column label="手机" prop="phonenumber" :show-overflow-tooltip="true" />
            <el-table-column label="创建时间" align="center" prop="createTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getUserList"
          />
        </el-row>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row>
      <el-col :span="24">
        <div class="dialog-footer" style="text-align: right;">
          <el-button type="primary" @click="confirm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
import { deptTree4UserSel, listDeptUser } from '@/api/system/user'

export default {
	name: 'SysDeptUserSelect',
  props: {
    // 部门层级 0: 全部 1: 部门及下级 2: 本部门 4: 部门及上级
    level: {
      type: Number,
      required: false,
      default: 0
    },
    // 用户多选
    multi: {
      type: Boolean,
      required: false,
      default: true
    },
    // 用户选择完成回调方法
    userSelFinish: {
      type: Function,
      required: false,
      default: function () {}
    },
    // 取消用户选择回调方法
    userSelCancel: {
      type: Function,
      required: true,
      default: function () {}
    },
  },
  data() {
    return {
      loginDeptId: null,
      // 部门
      deptName: '',
      deptOptions: [],
      defaultProps: [],
      // 用户
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        level: null,
        deptId: null,
        userName: null,
        phonenumber: null,
        params: {}
      },
      userList: [],
      // 选择后用户
      selUserRow: []
    }
  },
  created() {
    this.loadDept()
  },
  methods: {
    loadDept() {
      const that = this
      deptTree4UserSel(this.level).then(res => {
        this.loginDeptId = res.data.loginDeptId
        this.deptOptions = res.data.tree
        if(this.deptOptions.length > 0) {
          if(this.level == 0) {
            this.queryParams.params["deptId"] = this.deptOptions[0].id
          } else {
            this.queryParams.params["deptId"] = this.loginDeptId
          }
        }
        this.getUserList()
        setTimeout(() => {
          if(this.level !== 0) {
            this.$refs.deptTree.setCurrentKey(this.loginDeptId)
          }
        }, 0)
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick(node) {
      this.queryParams.params["deptId"] = node.id
      if(this.level == 2 || (this.level == 1 && this.loginDeptIsChild(node))) {
        this.queryParams.params["deptId"] = this.loginDeptId
      } 
      this.handleQuery()
    },
    loginDeptIsChild(node) {
      const children = node.children
      if(children == null) return false
      for (let d of children) {
        if(d.id == this.loginDeptId) {
          return true
        } else {
          return this.loginDeptIsChild(d)
        }
      }
    },
    getUserList() {
      this.queryParams.params["level"] = this.level
      listDeptUser(this.queryParams).then(res => {
        this.userList = res.rows;
        this.total = res.total;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getUserList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    clickUserRow(row) {
      // 单选
      if(!this.multi && this.selUserRow.length == 0) {
        this.selUserRow.push(row)
      }
    },
    handleSelectionChange(selection) {
      // 多选
      if(selection.length == 0) {
        return;
      }
      for(let i = 0, len = selection.length; i < len; i++) {
        const user = selection[i]
        const index = this.selUserRow.findIndex(u => u.userId == user.userId);
        if(index < 0) {
          this.selUserRow.push(user)
        }
      }
    },
    removeSelUser(user) {
      const index = this.selUserRow.findIndex(u => u.userId == user.userId);
      this.selUserRow.splice(index, 1)
    },
    confirm() {
      if (this.selUserRow.length == 0) {
        this.$modal.msgError("请选择用户！");
        return;
      }
      this.userSelCancel()
      this.userSelFinish(this.selUserRow)
    },
    cancel() {
      this.userSelCancel()
    }
  },
  watch: {
    deptName(val) {
      this.$refs.deptTree.filter(val);
    }
  }

}
</script>