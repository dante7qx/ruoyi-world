<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="预览地址" prop="previewUrl">
        <span v-text="baseUrl + form.previewUrl"></span>
      </el-form-item>
      <el-form-item label="密码锁" prop="previewLock">
        <el-input v-model="form.previewLock" type="password" placeholder="请输入密码锁" maxlength="4" show-word-limit show-password />
      </el-form-item>
      <el-form-item label="最后更新时间" prop="lastUpdateTime">
        <el-date-picker clearable
          v-model="form.lastUpdateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择最后更新时间"
          :disabled="disabled"
          style="width: 100%;">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="有效期" prop="termOfValidity">
        <el-select v-model="form.termOfValidity" placeholder="请选择有效期" :disabled="disabled">
          <el-option
            v-for="dict in termOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"            
            style="width: 100%;"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否过期" prop="status">
        <el-select v-model="form.status" placeholder="请选择是否过期" :disabled="true">
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"            
            style="width: 100%;"
          ></el-option>
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
import { getShare, addShare, updateShare } from "@/api/jimureport/share";

export default {
  name: "ShareDetail",
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
      baseUrl: window.location.protocol+"//"+window.location.host + process.env.VUE_APP_BASE_API,
      termOptions: [{
          value: '0',
          label: '永久有效'
        }, {
          value: '1',
          label: '1天'
        }, {
          value: '2',
          label: '7天'
      }],
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
        getShare(this.id).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        id: null,
        reportId: null,
        previewUrl: null,
        previewLock: null,
        previewLockStatus: "0",
        lastUpdateTime: null,
        termOfValidity: null,
        status: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateShare(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addShare(this.form).then(response => {
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
