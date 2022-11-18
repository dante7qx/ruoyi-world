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
import FlowView from './view'

export default {
  name: "FlowDiagram",
  components: {
    FlowView
  },
  props: {
    xmlData: {
      type: String,
      default: ''
    },
    taskData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {};
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
