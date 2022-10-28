<template>
  <div class="app-container">
    <el-dialog title="修改初始密码" :visible.sync="open" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false">
      <reset-pwd :showClose="false" @closeWindow="closeWin"/>
    </el-dialog>
  </div>
</template>

<script>
import { monitorPwdModify } from "@/api/system/user";
import ResetPwd from "@/views/system/user/profile/resetPwd"

export default {
  name: "InitModifyPwdPage",
  components: { 
    ResetPwd 
  },
  data() {
    return {
      open: false
    };
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      monitorPwdModify().then(res => {
        if(res.data.modifyPwd) {
          this.open = true
        } else if(res.data.PromptModifyPwd > 0) {
          this.notifyUpdatePwd(res.data.PromptModifyPwd)
        }
      })
    },
    notifyUpdatePwd(day) {
      this.$notify({
        title: '修改密码',
        duration: 10000,
        dangerouslyUseHTMLString: true,
        message: `<strong>您的密码已经超过${day}天为进行修改，请及时进行修改！</strong>`
      });
    },
    closeWin() {
      this.open = false;
    }
  }

}

</script>