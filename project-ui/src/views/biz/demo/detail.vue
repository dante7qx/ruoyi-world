<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="业务名称" prop="demoName">
        <el-input v-model="form.demoName" placeholder="请输入业务名称" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="业务时间" prop="demoTime">
        <el-date-picker clearable
          v-model="form.demoTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择业务时间"
          :disabled="disabled">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="业务图片">
        <image-upload v-model="form.demoImage" />
      </el-form-item>
      <el-form-item label="业务附件" prop="attachment">
        <file-upload v-model="form.attachment" :disabled="disabled"/>
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDemo, addDemo, updateDemo } from "@/api/biz/demo";

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
      if(this.demoId > 0) {
        getDemo(this.demoId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        demoId: null,
        demoName: null,
        demoTime: null,
        demoImage: null,
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
      });
    },
    cancel() {
      this.reset();
      this.$emit('closeWindow');
    }
  }
};
</script>
