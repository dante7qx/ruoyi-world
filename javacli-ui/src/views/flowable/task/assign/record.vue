<template>
  <div class="app-container" style="height: 600px; overflow-y: auto;">
    <el-timeline v-if="recordList.length > 0">
      <el-timeline-item 
        v-for="record in recordList" :key="record.id"
        :timestamp="record.createTime"
        icon="el-icon-right"
        size="large" 
        type="success" 
        placement="top">
        <span>{{ record.fullFlowRecord }}</span>
        <br/>
        <span>转办说明：{{ record.comment }} </span>
      </el-timeline-item>
    </el-timeline>
    <span v-else>无转办记录</span>
  </div>
</template>

<script>
import { listAssign } from "@/api/flowable/process"

export default {
  name: "FlowAssignRecord",
  props: {
    procInsId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      recordList: [],
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      listAssign(this.procInsId).then(res => {
        this.recordList = res.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>