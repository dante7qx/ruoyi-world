<template>
	<div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
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
        <div :style="{ height: deptTreeHeight + 'px', overflow: 'auto'}">
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

      </el-col>
      <el-col :span="20" class="aclDiv">
        <el-transfer 
          ref="aclTransfer"
          v-loading="loading"
          filterable
          v-model="aclReportList"
          :data="jimureportList"
          :titles="['未设置', '已设置']"
          :button-texts="['移除', '添加']"
          @change="aclChange">
        </el-transfer>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
import { deptTree4UserSel } from '@/api/system/user'
import { listJimureport, setupDeptAcl, removeDeptAcl } from "@/api/jimureport/report"

export default {
	name: 'Jmpermission',
  dicts: ['sys_yes_no'],
  data() {
		return {
      level: 0,
      maxHeight: 0,
      deptName: '',
      deptOptions: [],
      defaultProps: [],
      curDeptId: 0,
      // 报表列表
      loading: true,
      jimureportList: [],
      aclReportList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 1000,
        code: null,
        name: null,
        template: null,
        delFlag: '0',
        deptId: null
      },
    }
  },
  computed: {
    deptTreeHeight() {
      return this.maxHeight > 0 ? this.maxHeight : `${document.documentElement.clientHeight}` - 176;
    }
  },
  created() {
    this.loadDept()
  },
  methods: {
    loadDept() {
      const that = this
      deptTree4UserSel(this.level).then(res => {
        this.deptOptions = res.data.tree
        if(this.deptOptions.length > 0) {
          this.selDeptTreeNode(this.deptOptions[0].id, this.deptOptions[0])
        }
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick(node) {
      let deptId  = node.id
      this.selDeptTreeNode(deptId, this.getTreeNodeData(deptId))
    },
    selDeptTreeNode(deptId, node) {
      this.curDeptId = deptId
      this.getList(deptId)
      this.clearTransferCheck()
    },
    getTreeNodeData(nodeId) {
      return this.$refs.deptTree.getNode(nodeId).data;
    },
    clearTransferCheck() {
      this.$refs.aclTransfer.$children["0"].checked = [];
      this.$refs.aclTransfer.$children["3"].checked = [];
    },
    getList() {
      this.loading = true;
      this.queryParams.deptId = this.curDeptId
      listJimureport(this.queryParams).then(response => {
        let dataList = response.rows;
        this.loading = false;
        this.jimureportList = dataList.map(item => Object.create({key: item.id, label: item.name + (item.template == 1 ? '（模板）' : '')}))
        this.aclReportList = dataList.filter(item => item.setupAcl === 1).map(item => item.id)
      });
    },
    aclChange(curVal, direction, changeArr) {
      if(direction == 'right') {
        setupDeptAcl(this.queryParams.deptId, changeArr).then(res => {
          this.$modal.msgSuccess("权限添加成功！");
          this.getList()
        })
      } else if(direction == 'left') {
        removeDeptAcl(this.queryParams.deptId, changeArr).then(res => {
          this.$modal.msgSuccess("权限移除成功！");
          this.getList()
        })
      }
      
    }
  },
  watch: {
    deptName(val) {
      this.$refs.deptTree.filter(val);
    }
  }
}
</script>
<style scoped>
   .aclDiv >>> .el-transfer-panel {
     width: calc(50% - 125px);
     height: calc(100vh - 130px);
   }

   .aclDiv >>> .el-transfer-panel__list.is-filterable{
      height: calc(100vh - 140px);
   }
</style>