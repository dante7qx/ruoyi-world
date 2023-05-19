<template>
  <iframe class="iframe" :src="url" frameborder="0" scrolling="auto"></iframe>
</template>

<script>
import { getToken } from "@/utils/auth"

export default {
  name: "JimuReport",
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      url: ''
    }
  },
  created() {
    const prefix = window.location.protocol+"//"+window.location.host;
    this.url = prefix + this.baseUrl + '/jmreport/list?token='+getToken();
  }
}
</script>

<style scoped>
.iframe{
  height: calc(100vh - 88px);
  width: 100%;
}
</style>