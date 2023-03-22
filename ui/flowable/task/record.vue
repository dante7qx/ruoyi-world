<template>
  <div class="app-container">
    <el-timeline v-if="recordList.length > 0">
      <el-timeline-item 
        v-for="(record, index) in recordList" :key="index"
        :timestamp="(index + 1) + '. ' + record.taskDefName" 
        :color="`${record.endTime != null ? (record.flowResult != null && !record.flowResult ? '#ff8482' : '#11ce65') : '#1990ff'}`" 
        icon="el-icon-video-play"
        size="large" 
        type="primary" 
        placement="top">
        <el-card class="record-card">
          <span v-if="record.userId">处理人：<span v-html="record.userName"></span>，</span>
          <span v-if="record.userId">所在部门： <span v-html="record.userDept"></span>，</span>
          <span v-if="record.groupId && !record.userId">处理组：<span v-html="record.groupName"></span>，</span>
          处理时间：<span v-html="record.startTime"></span><label> ~ </label><span v-html="record.endTime"></span>
          <el-link :underline="false" style="float: right;" v-if="record.flowResult == null"></el-link>
          <el-link type="success" :underline="false" style="float: right;" v-else-if="record.flowResult">通过</el-link>
          <el-link type="danger" :underline="false" style="float: right;" v-else>退回</el-link>
          <el-link type="primary" :underline="false" style="float: right;" v-if="record.endTime == null">待处理</el-link>
          <span v-if="record.comment"> <br/>审批意见： <span v-html="record.comment"></span></span>
          <span v-if="record.attachment"><br/>审批附件： <file-upload v-model="record.attachment" :bizModel="'SysFlowTask'" :disabled="true"/></span>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <span v-else>无审批记录</span>
  </div>
</template>

<script>

import { listRecord } from "@/api/flowable/process"

export default {
  name: 'FlowRecordPage',
  props: {
    bizUid: {
      type: String,
      required: true,
      default: ''
    }
  },
  data() {
    return {
      recordList: []
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      listRecord(this.bizUid).then(res => {
        if(res.data) {
          this.recordList = res.data
        }
      })
    }
  }
}
</script>