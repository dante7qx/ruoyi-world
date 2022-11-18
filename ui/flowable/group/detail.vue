<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="审批组名称" prop="groupName">
        <el-input v-model="form.groupName" placeholder="请输入审批组名称" maxlength="30" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" show-word-limit maxlength="500"  placeholder="请输入内容" :disabled="disabled"/>
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getGroup, addGroup, updateGroup } from "@/api/flowable/group";

export default {
  name: "GroupDetail",
  props: {
    groupId: {
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
        groupName: [
          { required: true, message: "审批组名称不能为空", trigger: "blur" }
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
      if(this.groupId > 0) {
        getGroup(this.groupId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        groupId: null,
        groupName: null,
        remark: null,
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
          if (this.form.groupId != null) {
            updateGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addGroup(this.form).then(response => {
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
