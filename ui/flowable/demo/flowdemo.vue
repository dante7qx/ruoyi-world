<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="申请人">
            <span v-html="applUser"></span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker clearable
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择开始时间"
              :disabled="disabled"
              style="width: 100%;">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker clearable
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择结束时间"
              :disabled="disabled"
              style="width: 100%;">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="申请原因" prop="applReason">
            <el-input v-model="form.applReason" placeholder="请输入申请原因" maxlength="50" show-word-limit :disabled="disabled"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="补充材料" prop="applAttachment">
            <file-upload v-model="form.applAttachment" :bizModel="'SysFlowTask'" :disabled="disabled"/>
          </el-form-item>
        </el-col>
        <!-- 流程相关 -->
        <el-col :span="24" v-if="showApprovalUser">
          <el-form-item label="审批人" prop="approvalUserId">
            <el-select v-model="form.approvalUserId" clearable :multiple="approver && approver.multi" placeholder="请选择审批人" style="width: 100%">
              <el-option
                v-for="user in approver.users"
                :key="user.userId"
                :label="`${user.nickName} -  ${user.deptName}`"
                :value="user.userId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="showApproval">
          <el-form-item label="审批意见" prop="approvalComment">
            <el-autocomplete v-model="form.approvalComment" placeholder="请输入审批意见" 
              :fetch-suggestions="queryComment" 
              show-word-limit 
              :maxlength="200" 
              @input="changeApprovalComment($event)"
              style="width: 100%"></el-autocomplete>
          </el-form-item>
        </el-col>
        <!-- 流程相关 -->
      </el-row>
	  </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;" v-if="!disabled">
      <el-button type="primary" @click="submitForm">提 交</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
    <div slot="footer" class="dialog-footer" style="text-align: right;" v-if="showApproval">
      <el-button type="success" @click="handleApproval(true)">通 过</el-button>
      <el-button type="danger" @click="handleApproval(false)" v-if="showRejectBtn">退 回</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getFlowdemoByUid, commitFlowdemo } from "@/api/flowable/demo/flowdemo";
import { flowApprover, approvalComment, convertApprovalUserId, approvalFlow } from "@/api/flowable/process";

export default {
  name: "FlowdemoDetail",
  props: {
    uid: {
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
      required: true,
      default: 0
    },
    // 流程定义对象
    procDef: {
      type: Object,
      required: false,
      default: {}
    }
  },
  dicts: ['sys_process_type'],
  data() {
    var checkLeaveTime = (rule, value, callback) => {
      if (!value) {
        return callback();
      }
      if(!this.form.startTime) {
        callback();
      } else if (this.form.startTime > this.form.endTime) {
        return callback(new Error('开始时间不能大于结束时间'));
      } else {
        return callback();
      }
    };
    return {
      userTask: {
        BMLDSP: 'BMLDSP',  // 部门领导审批
        ZYBA: 'ZYBA', // 专员备案
        YGXJ: 'YGXJ' // 员工销假
      },
      form: {
        trace: {}
      },
      rules: {
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "change" },
          { validator: checkLeaveTime, trigger: 'change' }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "change" },
          { validator: checkLeaveTime, trigger: 'change' }
        ],
        applReason: [
          { required: true, message: "申请原因不能为空", trigger: "blur" }
        ],
      },
      approver: {}, // 审批候选对象
    };
  },
  computed: {
    applUser() {
      return this.uid ? this.form.userName : this.$store.state.user.nickName;
    },
    disabled() {
      if(this.pageCtl == 0) {
        return true
      } else if(this.pageCtl == 2) {
        return this.form.trace && !this.form.trace.firstTask
      } else {
        return false
      }
    },
    showApproval() {
      return this.pageCtl == 2 && (this.form.trace && !this.form.trace.firstTask) ? true : false
    },
    showApprovalUser() {
      if(this.pageCtl == 1 && this.approver.users && this.approver.users.length > 1) {
        return true
      } else if(this.pageCtl == 2) {
        // 部门领导审批，请假时间小于3天，不需要选择候选人
        if(this.userTask.BMLDSP == this.form.trace.taskDefId && this.form.days < 3) {
          return false
        } else {
          return this.approver.users && this.approver.users.length > 1
        }
      } else {
        return false
      }
    },
    showRejectBtn() {
      return this.userTask.ZYBA != this.form.trace.taskDefId &&this.userTask.YGXJ != this.form.trace.taskDefId
    }
    
  },
  created() {
    this.initPage()
  },
  methods: {
    initPage() {
      if(this.uid) {
        this.loadForm()
      } else {
        this.loadApprover(this.procDef.flowDefId, null)
        this.form.trace['procDefKey'] = this.procDef.flowDefKey
        this.form.trace['flowType'] = this.procDef.typeName
      }
    },
    loadApprover(flowDefId, taskDefId, startUserId) {
      flowApprover(flowDefId, taskDefId, startUserId).then(res => {
          this.approver = res.data
          this.form.trace['multi'] = this.approver.multi
          if(this.approver.users && this.approver.users.length == 1) {
            this.form.approvalUserId = this.approver.users[0].userId
          } 
        })
    },
    loadForm() {
      this.reset();
      getFlowdemoByUid(this.uid).then(response => {
        this.form = response.data;
        if(this.pageCtl == 2) {
          this.form.trace.taskId = this.taskId
        }
        if(this.userTask.BMLDSP == this.form.trace.taskDefId && this.form.days < 3) {
          this.form.approvalUserId = this.form.trace.commitUserId
        } else {
          this.loadApprover(this.form.trace.procDefId, this.form.trace.taskDefId, this.form.trace.commitUserId)
        }
        
      });
    },
    reset() {
      this.form = {
        demoId: null,
        userId: null,
        uid: null,
        startTime: null,
        endTime: null,
        applReason: null,
        applAttachment: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        trace: {},
        approvalUserId: null,
        approvalComment: null
      };
      this.resetForm("form");
    },
    // 动态校验审批人
    requiredApprovalUserId(agree) {
      if(this.approver.users && this.approver.users.length > 1) {
        if(agree) {
          const approvalUserId = [
            { required: true, message: "审批人不能为空", trigger: ['change','blur'] }
          ]
          this.rules = { ...this.rules, approvalUserId }
        } else {
          this.$refs['form'].clearValidate("approvalUserId")
          this.rules = { ...this.rules, approvalUserId: [] }
          this.form.approvalUserId = null
        }
      }
    },
    submitForm() {
      this.requiredApprovalUserId(true)
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.trace['approvalUserId'] = convertApprovalUserId(this.form.approvalUserId)
          if(this.showApprovalUser && (!this.form.approvalUserId || this.form.approvalUserId.length == 0)) {
            return false
          }
          const loading = this.$loading({
            lock: true,
            text: '处理中...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          commitFlowdemo(this.form).then(response => {
            loading.close()
            this.$modal.msgSuccess("提交成功");
            this.cancel();
          });
        }
      });
    },
    fillApprovalComment(agree) {
      if(agree && (!this.form.approvalComment || this.form.approvalComment == '退回')) {
        this.form.approvalComment = '同意'
      } else if(!agree && (!this.form.approvalComment || this.form.approvalComment == '同意')) {
        this.form.approvalComment = '退回'
      }
      this.$forceUpdate()
    },
    needAddAttachment() {
      return false
    },
    handleApproval(agree) {
      this.fillApprovalComment(agree)
      this.requiredApprovalUserId(agree)
      this.$refs["form"].validate(valid => {
        if(valid) {
          let data = { 
            flowType: this.form.trace.flowType,
            bizUid: this.form.uid,
            taskId: this.form.trace.taskId,
            taskDefId: this.form.trace.taskDefId,
            multi: this.form.trace.multi,
            approvalUserId: agree ? convertApprovalUserId(this.form.approvalUserId) : null,
            comment: this.form.approvalComment,
            attachment: this.needAddAttachment() ?  this.form.applAttachment : null,
            agree: agree
          }
          if(!agree) {
            const that = this
            this.$modal.confirm('您确定要退回吗？').then(function() {
              return that.approval(data);
            }).then(() => {
              this.$modal.msgSuccess("处理成功！");
            }).catch(() => {});
          } else {
            if(this.showApprovalUser && (!data.approvalUserId || data.approvalUserId.length == 0)) {
              return false
            }
            this.approval(data)
            this.$modal.msgSuccess("处理成功！");
          }
        }
      })
    },
    approval(data) {
      const loading = this.$loading({
        lock: true,
        text: '处理中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      approvalFlow(data).then(res => {
        loading.close()
        this.cancel()
      }) 
    },
    cancel() {
      this.reset();
      this.$emit('closeWindow');
    },
    queryComment(queryString, cb) {
        var data = approvalComment()
        var results = queryString ? data.filter(this.createFilter(queryString)) : data;
        cb(results);
    },
    createFilter(queryString) {
      return (data) => {
        return (data.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    changeApprovalComment(e) {
      this.$forceUpdate()
    },
  }
};
</script>
