<template>
  <div>
    <el-tabs type="border-card" value="info">
      <el-tab-pane label="基本信息" name="info" lazy>
        <FlowDemo :key="key" :uid="bizUid" :pageCtl="pageCtl" :taskId="taskId" :procDef="procDef" @closeWindow="closeWindow" v-if="flowType == 'leave' && loadBizDetail" />
        <span v-else>无相关业务</span>
      </el-tab-pane>
      <el-tab-pane label="流程记录" name="record" lazy style="height: 700px; overflow: auto;" v-if="procInstId">
        <flow-record :key="bizUid" :bizUid="bizUid" />
      </el-tab-pane>
      <el-tab-pane label="转办记录" name="assign" lazy v-if="false">
      </el-tab-pane>
      <el-tab-pane label="流程监控" name="monitor" lazy style="height: 700px;">
        <flow-diagram :key="deployId" :deployId="deployId" :procInstId="procInstId"/>
      </el-tab-pane>
    </el-tabs>
    <el-row v-if="pageCtl == 0">
      <el-col :span="24">
        <div class="dialog-footer">
          <el-button type="info" @click="closeWindow">返 回</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { flowDefByType } from "@/api/flowable/process"
import FlowRecord from '@/views/flowable/task/record'
import FlowDiagram from '@/views/flowable/task/diagram'
import FlowDemo from '@/views/flowable/demo/flowdemo'


export default {
  name: 'FlowDetailPage',
  components: { FlowRecord, FlowDiagram, FlowDemo },
  props: {
    flowType: {
      type: String,
      required: true
    },
    bizUid: {
      type: String,
      required: false,
      default: null
    },
    procInstId: {
      type: String,
      required: false,
      default: null
    },
    procDeployId: {
      type: String,
      required: false,
      default: null
    },
    taskId: {
      type: String,
      required: false,
      default: null
    },
    // 页面操作控制，0: 只读，1: 编辑， 2: 待办
    pageCtl: {
      type: Number,
      required: false,
      default: 0
    }
  },
  data() {
    return {
      key: '',
      procDef: {}, // 流程定义,
      deployId: null, // 流程部署Id
    }
  },
  computed: {
    loadBizDetail() {
      return this.bizUid ? true : this.procDef.flowDefId ? true : false
    },
  },
  async created() {
    await this.initPage()
  },
  methods: {
    async initPage() {
      this.key = this.nanoid()
      if(this.bizUid) {
        this.deployId = this.procDeployId
      } else {
        await flowDefByType(this.flowType).then(res => {
          this.procDef = res.data
          this.deployId = this.procDef.flowDeployId
        })
      }
    },
    closeWindow() {
      this.$emit('closeWindow');
    }
  }
}
</script>

<style lang="scss" scoped>
.el-row {
  margin-top: 20px;
  .dialog-footer {
    text-align: center;
  }
}
</style>