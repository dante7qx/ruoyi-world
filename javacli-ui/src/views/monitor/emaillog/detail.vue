<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="接受人" prop="sendTo">
        <el-input v-model="form.sendTo" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="抄送人" prop="sendCc">
        <el-input v-model="form.sendCc" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" :disabled="disabled"/>
      </el-form-item>
      <!--
      <el-form-item label="密送人" prop="sendBcc">
        <el-input v-model="form.sendBcc" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" :disabled="disabled"/>
      </el-form-item>
      -->
      <el-form-item label="邮件主题" prop="subject">
        <el-input v-model="form.subject" placeholder="请输入邮件主题" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="邮件内容">
        <el-input v-model="form.content" type="textarea" :autosize="{ minRows: 4, maxRows: 6}" resize="none" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="发送时间" prop="sendDate">
        <span v-text="form.sendDate" />
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getEmaillog, addEmaillog, updateEmaillog } from "@/api/monitor/emaillog";

export default {
  name: "EmaillogDetail",
  props: {
    emailId: {
      type: Number,
      required: true,
      default: 0
    },
    disabled: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  data() {
    return {
      form: {},
      rules: {
      }
    };
  },
  created() {
    this.loadForm()
  },
  methods: {
    loadForm() {
      this.reset();
      if(this.emailId > 0) {
        getEmaillog(this.emailId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        emailId: null,
        sendTo: null,
        sendCc: null,
        sendBcc: null,
        subject: null,
        content: null,
        sendDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.emailId != null) {
            updateEmaillog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addEmaillog(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.cancel();
            });
          }
        }
      });
    },
    cancel() {
      this.reset();
      this.$emit('closeWindow');
    }
  }
};
</script>
