<template>
  <div class="app-container">
    <div id="luckysheet" style="margin:5px;padding:0px;position:absolute;width:99%;height:98%;left: 0px;top: 0px;"></div>
  </div>
</template>

<script>
import axios from "axios"
import {getToken} from "@/utils/auth";
import LuckyExcel from 'luckyexcel'
export default {
  name: "ExcelPreview",
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    }
  },
  mounted() {
    this.init()
  },
  methods:{
    init() {
      //配置项
      const fileUrl = this.$route.query && this.$route.query.fileUrl;
      const fileName = this.$route.query && this.$route.query.fileName;
      const that = this;
      this.getFileBlob(fileUrl, res => {
        const file = new File([res.data], fileName, {type: "contentType", lastModified: Date.now()});
        that.$nextTick(function (){
          that.getExcelFile(file, fileUrl, fileName);
        })
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
    getExcelFile(evt, fileUrl, fileName) {
      const that = this;
      const files = evt;
      if (files == null || files.length == 0) {
        this.$modal.msgWarning("未找到文件");
        return;
      }
      LuckyExcel.transformExcelToLuckyByUrl(this.baseUrl + fileUrl, fileName,function (exportJson, luckysheetfile) {
        console.log(exportJson)
        if (exportJson.sheets == null || exportJson.sheets.length == 0) {
          that.$modal.msgWarning("暂不适配xls文件，请下载后查看");
          return;
        }
        window.luckysheet.destroy();
        that.$nextTick(function (){
          window.luckysheet.create({
            container: 'luckysheet', //luckysheet is the container id
            allowCopy: false, // 是否允许拷贝
            showtoolbar: false, // 是否显示工具栏
            showinfobar: false, // 是否显示顶部信息栏
            showsheetbar: true, // 是否显示底部sheet页按钮
            showstatisticBar: false, // 是否显示底部计数栏
            sheetBottomConfig: false, // sheet页下方的添加行按钮和回到顶部按钮配置
            allowEdit: false, // 是否允许前台编辑
            enableAddRow: false, // 允许增加行
            enableAddCol: false, // 允许增加列
            userInfo: false, // 右上角的用户信息展示样式
            showRowBar: false, // 是否显示行号区域
            showColumnBar: false, // 是否显示列号区域
            sheetFormulaBar: false, // 是否显示公式栏
            enableAddBackTop: false,//返回头部按钮
            rowHeaderWidth: 0,//纵坐标
            columnHeaderHeight: 0,//横坐标
            showstatisticBarConfig: {
              count:false,
              view:false,
              zoom:false,
            },
            showsheetbarConfig: {
              add: false, //新增sheet
              menu: false, //sheet管理菜单
              sheet: true, //sheet页显示
            },
            forceCalculation: true,//强制计算公式
            data: exportJson.sheets,
            title: exportJson.info.name,
            userInfo: exportJson.info.name.creator
          });
        })
      });
    },
  }
}
</script>
