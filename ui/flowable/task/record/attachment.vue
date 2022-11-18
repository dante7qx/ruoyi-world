<template>
  <div class="app-container" style="height: 600px; overflow-y: auto;">
    <el-timeline v-if="recordList.length > 0">
      <el-timeline-item 
        v-for="record in recordList" :key="record.id"
        :timestamp="record.fullFlowRecord"
        icon="el-icon-link"
        size="large" 
        type="primary" 
        placement="top">
        <file-upload v-model="record.url" :bizModel="'flow'" :disabled="true"/>
      </el-timeline-item>
    </el-timeline>
    <span v-else>无附件记录</span>
  </div>
</template>

<script>
import { getAttachmentList } from "@/api/flowable/flowlist"

export default {
  name: "FlowAttachment",
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
      getAttachmentList(this.procInsId).then(res => {
        this.recordList = res.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>