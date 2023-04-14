<template>
  <div>
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
  </div>
</template>

<script>
import { deptTree4UserSel } from '@/api/system/user'

export default {
	name: 'SysDeptUserSelect',
  props: {
    // 部门层级 0: 全部 1: 部门及下级 2: 本部门 3: 部门及上级
    level: {
      type: Number,
      required: false,
      default: 0
    },
    // 部门树节点点击回调方法
    deptSelected: {
      type: Function,
      required: false,
      default: null
    },
    // 是否部门树加载后触发点击事件
    clickOnload: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  data() {
    return {
      loginDeptId: null,
      deptName: '',
      deptOptions: [],
      defaultProps: []
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
            if(this.clickOnload) {
              this.selDeptTreeNode(this.deptOptions[0].id, this.deptOptions[0])
            }
          } 
        }
        setTimeout(() => {
          if(this.level !== 0) {
            this.$refs.deptTree.setCurrentKey(this.loginDeptId)
            if(this.clickOnload) {
              this.selDeptTreeNode(this.loginDeptId, this.$refs.deptTree.getCurrentNode())
            }
          }
        }, 0)
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick(node) {
      let deptId  = node.id
      if(this.level == 2 || (this.level == 1 && this.loginDeptIsChild(node))) {
        deptId = this.loginDeptId
      } 
      this.selDeptTreeNode(deptId, this.getTreeNodeData(deptId))
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
    selDeptTreeNode(deptId, node) {
      if(this.deptSelected && (typeof this.deptSelected) == 'function') {
        this.deptSelected(deptId, node) // 参数由业务来定，this.deptSelected(deptId)、this.deptSelected(node)、this.deptSelected(deptId, node)
      } 
    },
    getTreeNodeData(nodeId) {
      return this.$refs.deptTree.getNode(nodeId).data;
    },
  },
  watch: {
    deptName(val) {
      this.$refs.deptTree.filter(val);
    }
  }

}
</script>