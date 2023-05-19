<template>
  <iframe class="iframe" :src="url" frameborder="0" scrolling="auto" v-if="viewId"></iframe>
  <div class="app-container" v-else>未指定报表编号，请检查您的路由信息配置！</div>
</template>

<script>
import { getToken } from "@/utils/auth"

export default {
  name: "Jmview",
  data() {
    return {
      viewId: '',
      baseUrl: process.env.VUE_APP_BASE_API,
      url: ''
    }
  },
  created() {
    this.viewId = this.$router && this.$route.query.id
    if(this.viewId) {
      const prefix = window.location.protocol+"//"+window.location.host;
      this.url = prefix + this.baseUrl + '/jmreport/view/' + this.viewId + '?token='+getToken();
    }

    
  }
}
</script>

<style scoped>
.iframe{
  height: calc(100vh - 88px);
  width: 100%;
}
</style>