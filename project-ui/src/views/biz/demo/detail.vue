<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="业务名称123业务名称456业务名称" prop="demoName" class="long-form-label">
        <el-input v-model="form.demoName" placeholder="请输入业务名称" maxlength="30" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="业务时间" prop="demoTime">
        <el-date-picker clearable
          v-model="form.demoTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择业务时间"
          :disabled="disabled"
          style="width: 100%">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="业务手机" prop="demoPhone">
        <el-input v-model="form.demoPhone" placeholder="请输入业务手机" maxlength="11" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="业务图片">
        <image-upload v-model="form.demoImage" :disabled="disabled" />
      </el-form-item>
      <el-form-item label="业务附件" prop="attachment">
        <file-upload v-model="form.attachment" :bizModel="'demo'" :fileSize="10" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="业务内容">
        <editor v-model="form.demoContent" :disabled="disabled" :max-height="400"/>
      </el-form-item>
      <el-form-item label="角色ID" prop="roleId">
        <el-input v-model="form.roleId" placeholder="请输入角色ID"  :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="岗位ID" prop="postId">
        <el-input v-model="form.postId" placeholder="请输入岗位ID"  :disabled="disabled"/>
      </el-form-item>
	  </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="exportWord" v-show="disabled">导 出</el-button>
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDemo, addDemo, updateDemo } from "@/api/biz/demo/demo";

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
        demoName: [
          { required: true, message: "业务名称不能为空", trigger: "blur" }
        ],
        demoPhone: [
          { required: true, message: "业务手机不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  async created() {
    await this.loadForm()
  },
  methods: {
    async loadForm() {
      this.reset();
      if(this.demoId > 0) {
        await getDemo(this.demoId).then(response => {
          this.form = response.data ? response.data : {};
        });
      }
    },
    reset() {
      this.form = {
        demoId: null,
        demoName: null,
        demoTime: null,
        demoPhone: null,
        demoImage: null,
        demoContent: null,
        attachment: null,
        roleId: null,
        postId: null,
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
          this.form.demoPhoneSearch = this.form.demoPhone.substring(this.form.demoPhone.length - 4);
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
    },
    exportWord() {
      const id = this.demoId
      this.download(
        "biz/demo/exportDoc", {
          demoId: id
        },`业务示例(${this.form.demoTime}).docx`
      );
    }
  }
};
</script>
