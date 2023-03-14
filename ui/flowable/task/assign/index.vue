<template>
  <div>
    <el-form ref="assignForm" :model="assignForm" :rules="assignRules" label-width="100px">
      <el-form-item label="接收人" prop="assignee">
        <el-autocomplete v-model="assignee" :fetch-suggestions="queryUser" @select="handleSelect" placeholder="请输入接收人"  style="width: 100%;"/>
      </el-form-item>
      <el-form-item label="转办说明" prop="comment">
        <el-input type="textarea" v-model="assignForm.comment" placeholder="请输入转办说明" resize="none" :autosize="{ minRows: 4, maxRows: 6}" maxlength="100" show-word-limit />
      </el-form-item>
      <el-form-item prop="keepTodo">
        <el-checkbox v-model="assignForm.keepTodo">保留我的待办</el-checkbox>
      </el-form-item>
      
    </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm()" >保 存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { assignFlow } from "@/api/flowable/process"
import { userList } from "@/api/flowable/definition";

export default {
  name: "FlowAssign",
  props: {
    taskId: {
      type: Array,
      default: [],
      required: true
    }
  },
  data() {
    return {
      // 接收人
      assignee: '',
      assignForm: {
        assignTaskIds: [],
        assignee: '',
        keepTodo: false,
        comment: ''
      }, 
      assignRules: {
        assignee: [
          { required: true, message: "接收人不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    console.log(this.taskId)
  },
  methods: {
    queryUser(queryString, cb) {
      userList({userName: queryString}).then(res => {
        let data = []
        if(res.data != null) {
          res.data.forEach(u => {
            data.push({ value: u.nickName, userId: u.userId })
          })
          cb(data)
        }
      })
    },
    handleSelect(item) {
      this.assignForm.assignee = item.userId.toString()
    },
    submitForm() {
      this.$refs["assignForm"].validate(valid => {
        if (valid) {
          this.assignForm.assignTaskIds = this.taskId;
          assignFlow(this.assignForm).then(res => {
            this.$modal.msgSuccess("转办成功！");
            this.cancel();
          })
        }
      });
    },
    cancel() {
      this.$emit('closeWindow');
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
