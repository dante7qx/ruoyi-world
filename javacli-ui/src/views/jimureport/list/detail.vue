<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="编码" prop="code">
        <span v-text="form.code"></span>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称" maxlength="50" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="说明" prop="note">
        <el-input v-model="form.note" placeholder="请输入说明" maxlength="255" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="是否删除" prop="delFlag">
        <el-select v-model="form.delFlag" placeholder="请选择" :disabled="disabled">
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value, 10)"
          />
        </el-select>
      </el-form-item>
	  </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getJimureport, addJimureport, updateJimureport } from "@/api/jimureport/report";

export default {
  name: "JimureportDetail",
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
  dicts: ['sys_yes_no'],
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
      if(this.id > 0) {
        getJimureport(this.id).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        note: null,
        status: "0",
        type: null,
        jsonStr: null,
        apiUrl: null,
        thumb: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        apiMethod: null,
        apiCode: null,
        template: null,
        viewCount: null,
        cssStr: null,
        jsStr: null,
        tenantId: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJimureport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addJimureport(this.form).then(response => {
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
