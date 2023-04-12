<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="接收人" prop="sendTo">
        <el-input v-model="form.sendTo" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="短信内容">
        <el-input v-model="form.content" type="textarea" :autosize="{ minRows: 4, maxRows: 6}" resize="none" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="发送时间" prop="sendDate">
        <span v-text="form.sendDate" />
      </el-form-item>
      <el-form-item label="发送日志" prop="sendLog">
        <el-input v-model="form.sendLog" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" placeholder="请输入内容" :disabled="disabled"/>
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getSmslog, addSmslog, updateSmslog } from "@/api/monitor/smslog";

export default {
  name: "SmslogDetail",
  props: {
    smsId: {
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
      if(this.smsId > 0) {
        getSmslog(this.smsId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        smsId: null,
        sendTo: null,
        content: null,
        sendDate: null,
        sendLog: null,
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
          if (this.form.smsId != null) {
            updateSmslog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addSmslog(this.form).then(response => {
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
