<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="业务名称" prop="demoName">
        <el-input v-model="form.demoName" placeholder="请输入业务名称" maxlength="30" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="部门ID" prop="deptId">
        <el-input v-model="form.deptId" placeholder="请输入部门ID"  :disabled="disabled"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDemoall, addDemoall, updateDemoall } from "@/api/biz/demo/demoall";

export default {
  name: "DemoallDetail",
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
        demoName: [
          { required: true, message: "业务名称不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "部门ID不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {
    this.loadForm()
  },
  methods: {
    loadForm() {
      this.reset();
      if(this.demoId > 0) {
        getDemoall(this.demoId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        demoId: null,
        demoName: null,
        deptId: 200,
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
            updateDemoall(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addDemoall(this.form).then(response => {
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
