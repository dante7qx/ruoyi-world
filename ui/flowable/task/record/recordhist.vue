<template>
  <div class="app-container" style="height: 600px; overflow-y: auto;">
    <el-timeline v-if="recordList.length > 0">
      <el-timeline-item 
        v-for="record in recordList" :key="record.seqNum"
        :timestamp="`${record.fromTask}${record.toTask != null ? ' —> ' + record.toTask : ''} 【 ${record.startDate} ~ ${record.endDate != null ? record.endDate : ''} 】`" 
        :color="`${record.endDate != null ? '#11ce65' : 'red'}`" 
        icon="el-icon-video-play"
        size="large" 
        type="primary" 
        placement="top">
        <el-card class="record-card">
          <span v-html="`${record.fullFlowRecord}`"></span>
          <file-upload v-model="record.attachUrl" :bizModel="'flow'" :disabled="true"/>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <span v-else>无审批记录</span>
  </div>
</template>

<script>
import { getRecordList } from "@/api/flowable/flowlist"

export default {
  name: "FlowRecordHistPage",
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
      getRecordList(this.procInsId).then(res => {
        this.recordList = res.data
      })
      if(this.recordList.length === 0) {
        const loading = this.$loading({
					lock: true,
					text: '处理中...',
					spinner: 'el-icon-loading',
					background: 'rgba(55,55,55,0.4)',
					target: null
				});
				this.loopRecordTab(loading, 200)
      }
    },
    loopRecordTab(loading, delay) {
			const that = this;
			setTimeout(function() {
				if(that.recordList.length > 0) {
					loading.close();
					return;
				}
      	that.loopRecordTab(loading, delay);
  		}, delay);
		}
  }
}
</script>

<style lang="scss" scoped>
.box-card {
  width: 100%;
  height: 620px;
  margin-bottom: 20px;
}

.record-card {
  height: auto;
}
</style>