<template>
  <div>
    <el-tabs type="border-card" value="info">
      <el-tab-pane label="基本信息" name="info" lazy>
        <biz-detail :key="key" :bizId="parseInt(flowData.bizId, 10)" :bizModel="flowData.bizModel" :disabled="!commitable" @closeWindow="closeWindow"/>
        <flow-approval :key="key" :hasAttach="flowData.hasApprovalAttach" :taskId="flowData.taskId"  @closeWindow="closeWindow" v-if="approvable"/>
      </el-tab-pane>
      <el-tab-pane label="流程记录" name="record" lazy>
        <flow-hist-record :key="key" :procInsId="flowData.procInsId" />
      </el-tab-pane>
      <el-tab-pane label="附件记录" name="attachment" lazy v-if="flowData.hasApprovalAttach">
        <flow-attachment :key="key" :procInsId="flowData.procInsId" />
      </el-tab-pane>
      <el-tab-pane label="转办记录" name="assign" lazy>
        <flow-assign-record :key="key" :procInsId="flowData.procInsId" />
      </el-tab-pane>
      <el-tab-pane label="流程监控" name="monitor" lazy>
        <flow-diagram :xmlData="xmlData" :taskData="taskList" />
      </el-tab-pane>
    </el-tabs>
    <el-row v-if="disabled">
      <el-col :span="24">
        <div class="dialog-footer">
          <el-button type="info" @click="closeWindow">返 回</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Parser from '@/components/Process/parser/Parser'
import FlowDiagram from '@/views/flowable/task/diagram'
import {readXml, beginUserTask} from "@/api/flowable/definition"
import { getFlowViewer } from "@/api/flowable/flowlist"
import BizDetail from '@/views/flowable/task/record/bizdetail'
import FlowApproval from '@/views/flowable/task/record/approval'
import FlowHistRecord from '@/views/flowable/task/record/recordhist'
import FlowAttachment from '@/views/flowable/task/record/attachment'
import FlowAssignRecord from '@/views/flowable/task/assign/record'

export default {
  name: "FlowRecordDetailPage",
  components: {
    Parser,
    BizDetail,
    FlowApproval,
    FlowHistRecord,
    FlowAttachment,
    FlowAssignRecord,
    FlowDiagram
  },
  props: {
    flowData: {
      type: Object,
      required: true
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      key: '',
      // 起始流程节点
      firstNode: false,
      // 可提交
      commitable: false,
      // 可审批
      approvable: true,
      // 流程图
      xmlData: "",
      taskList: [],
    };
  },
  created() {
    this.key = this.nanoid();
    this.operControl();
    this.loadFlowDiagram(this.flowData.deployId, this.flowData.procInsId);
    
  },
  methods: {
    // 按钮控制
    async operControl() {
      if(this.disabled) {
        this.approvable = false
        this.commitable = false
      } else {
        await beginUserTask(this.flowData.procDefId).then(res => {
          if(res.msg == this.flowData.taskDefKey) {
            this.firstNode = true
          }
          if(this.firstNode) {
            this.approvable = false
            this.commitable = true
          } else {
            this.approvable = !this.disabled
          }
        })
      }
    },
    // 加载流程图
    loadFlowDiagram(deployId, procInsId) {
      readXml(deployId).then(res => {
        this.xmlData = res.data
      })
      getFlowViewer(procInsId).then(res => {
        this.taskList = res.data
      })
    },
    closeWindow() {
      this.$emit('closeWindow');
    }
  }
};
</script>

<style lang="scss" scoped>
.el-row {
  margin-top: 20px;
  .dialog-footer {
    text-align: center;
  }
}
</style>
