<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model="form.dictName" placeholder="请输入字典名称" maxlength="100" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="字典编码" prop="dictCode">
        <el-input v-model="form.dictCode" placeholder="请输入字典编码" maxlength="100" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" placeholder="请输入描述" maxlength="255" show-word-limit :disabled="disabled"/>
      </el-form-item>
  	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDict, addDict, updateDict } from "@/api/jimureport/dict";

export default {
  name: "DictDetail",
  props: {
    id: {
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
        dictName: [
          { required: true, message: "字典名称不能为空", trigger: "blur" }
        ],
        dictCode: [
          { required: true, message: "字典编码不能为空", trigger: "blur" }
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
      if(this.id > 0) {
        getDict(this.id).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        id: null,
        dictName: null,
        dictCode: null,
        description: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        type: null,
        tenantId: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDict(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addDict(this.form).then(response => {
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
