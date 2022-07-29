<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="业务名称" prop="bizName">
        <el-input v-model="form.bizName" placeholder="请输入业务名称" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="业务时间" prop="bizTime">
        <el-date-picker clearable
          v-model="form.bizTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择业务时间"
          :disabled="disabled">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="业务附件" prop="attachment">
        <el-input v-model="form.attachment" type="textarea" placeholder="请输入内容" :disabled="disabled"/>
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getBiz, addBiz, updateBiz } from "@/api/biz/biz";

export default {
  name: "BizDetail",
  props: {
    bizId: {
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
      if(this.bizId > 0) {
        getBiz(this.bizId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        bizId: null,
        bizName: null,
        bizTime: null,
        attachment: null,
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
          if (this.form.bizId != null) {
            updateBiz(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addBiz(this.form).then(response => {
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
