<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
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
      <el-form-item label="请假原因" prop="leaveReason">
        <el-input v-model="form.leaveReason" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" show-word-limit maxlength="50"  placeholder="请输入内容" :readonly="disabled"/>
      </el-form-item>
	  </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;" v-if="!disabled">
      <el-button type="primary" @click="submitForm(false)" v-show="!disabled">保 存</el-button>
      <el-button type="success" @click="submitForm(true)" v-show="!disabled">提 交</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDemo, addDemo, updateDemo, commitDemo } from "@/api/flowable/demo";

export default {
  name: "DemoDetail",
  props: {
    demoId: {
      type: Number,
      required: true,
      default: 0
    },
    disabled: {
      type: Boolean,
      required: true,
      default: false
    },
  },
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
      form: {},
      rules: {
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "change" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "change" },
          { validator: checkLeaveTime, trigger: 'change' }
        ],
        leaveReason: [
          { required: true, message: "请假原因不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.loadForm()
  },
  methods: {
    loadForm() {
      this.reset();
      if(this.demoId > 0) {
        getDemo(this.demoId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        demoId: null,
        leaveUserId: null,
        uid: null,
        startTime: null,
        endTime: null,
        leaveReason: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    submitForm(commit) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(commit) {
            commitDemo(this.form).then(response => {
              this.$modal.msgSuccess("提交成功");
              this.cancel();
            });
          } else {
            if (this.form.demoId != null) {
              updateDemo(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.cancel();
              });
            } else {
              addDemo(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.cancel();
              });
            }
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
