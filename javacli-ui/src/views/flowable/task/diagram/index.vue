<template>
  <div>
    <el-card v-if="taskData.length > 0" class="box-card" style="height: 660px;">
      <div slot="header" class="clearfix">
        <span class="el-icon-picture-outline">流程图</span>
      </div>
      <flow-view :xmlData="xmlData" :taskList="taskData"/>
    </el-card>
    <flow-view v-else :xmlData="xmlData" :taskList="taskData"/>
  </div>
</template>

<script>
import { readXml } from "@/api/flowable/definition"
import { getFlowViewer } from "@/api/flowable/process"
import FlowView from './view'

export default {
  name: "FlowDiagram",
  components: {
    FlowView
  },
  props: {
    deployId: {
      type: String,
      default: ''
    },
    procInstId: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      xmlData: '',
      taskData: []
    };
  },
  created() {
    // 加载流程图
    readXml(this.deployId).then(res => {
      this.xmlData = res.data
      if(this.procInstId) {
        getFlowViewer(this.procInstId).then(res => {
          this.taskData = res.data
        })
      }
    })
  }
};
</script>

<style lang="scss" scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
.box-card {
  width: 100%;
  height: 620px;
  margin-bottom: 20px;
}
</style>
