<template>
  <div class="app-container">
    <el-form ref="taskForm" :model="taskForm" :rules="taskRules" label-width="100px">
      <el-form-item label="审批意见" prop="comment">
        <el-input type="textarea" v-model="taskForm.comment" placeholder="请输入审批意见" resize="none" :autosize="{ minRows: 4, maxRows: 6}" maxlength="100" show-word-limit />
      </el-form-item>
      <el-form-item label="审批附件" prop="attachment" v-if="hasAttach">
        <file-upload v-model="taskForm.attachment" :bizModel="'flow'" />
      </el-form-item>
    </el-form>

    <div class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="approval(true)">同 意</el-button>
      <el-button type="danger" @click="approval(false)">驳 回</el-button>
      <el-button @click="closeWindow">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { approval } from "@/api/flowable/flowlist"

export default {
  name: "FlowApproval",
  props: {
    taskId: {
      type: String,
      required: true
    },
    hasAttach: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      taskForm: {
        taskId: null,
        comment: null,
        attachment: null
      }, 
      taskRules: {
        comment: [
          { required: true, message: "审批意见不能为空", trigger: "blur" }
        ],
        attachment: [
          { required: true, message: "审批附件不能为空", trigger: "blur" }
        ],
      },
    }
  },
  methods: {
    approval(agree) {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          this.taskForm.taskId = this.taskId;
          this.taskForm.agree = agree;
          approval(this.taskForm).then(res => {
            this.$modal.msgSuccess("审批成功！");
            this.closeWindow();
          })
        }
      });
    },
    closeWindow() {
      this.$emit('closeWindow');
    }
  }
}
</script>