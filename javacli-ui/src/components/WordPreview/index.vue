<template>
  <div class="app-container">
    <div ref="docxView"></div>
  </div>
</template>

<script>
import axios from "axios"
import {getToken} from "@/utils/auth";
let docPreview = require("docx-preview")
window.JSZip = require('jszip')
export default {
  name: "WordPreview",
  props: {
    file: {
      type: String,
      defalut: null,
    }
  },
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      docxView: false,
      excelView: false,
      imgMap:{
        open:false,
        src:null,
      }
    }
  },
  mounted() {
    //配置项
    const fileUrl = this.$route.query && this.$route.query.fileUrl;
    const fileName = this.$route.query && this.$route.query.fileName;
    this.viewDocxFile(fileUrl, fileName);

  },
  methods: {
    viewDocxFile(fileUrl, fileName) {
      const _this = this;
      this.getFileBlob(fileUrl, res => {
        //获取文件格式
        const urlArr =  res.config && res.config.url && res.config.url.split(".");
        const formatString = urlArr && urlArr[urlArr.length - 1];
        if (formatString.indexOf("doc") != -1) {
          _this.docxView = true;
          _this.excelView = false;
          _this.$nextTick(function(){
            console.log("data",_this.$refs.docxView)
            docPreview.renderAsync(res.data,_this.$refs.docxView);
          })

        }
      })
    },
    getFileBlob(file, fn) {
      axios({
        methods: "get",
        responseType: "blob",
        url: this.baseUrl + file,
      }).then(res => {
        fn && fn(res);
      })
    },
  }
}
</script>

<style scoped>

</style>