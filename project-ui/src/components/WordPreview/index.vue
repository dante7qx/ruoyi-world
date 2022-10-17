<template>
  <div class="app-container">
    <div ref="docxView" v-if="docxView"></div>
    <div id="luckysheet"
         v-if="excelView"
         style="margin:0px;padding:0px;position:absolute;width:100%;height:100%;left: 0px;top: 0px;"></div>
  </div>
</template>

<script>
import axios from "axios"
import {getToken} from "@/utils/auth";
import LuckyExcel from 'luckyexcel'
let docPreview = require("docx-preview")
window.JSZip = require('jszip')
export default {
  name: "index",
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
        if (formatString.indexOf("xls") != -1) {
          _this.docxView = false;
          _this.excelView = true;
          //创建excel画面
          const file = new File([res.data], fileName, {type: "contentType", lastModified: Date.now()});
          _this.$nextTick(function (){
            _this.getExcelFile(file,fileUrl,fileName);
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
    getExcelFile(evt,fileUrl,fileName) {
      const _this = this;
      const files = evt;
      if (files == null || files.length == 0) {
        this.$modal.msgWarning("未找到文件");
        return;
      }
      let name = files.name;
      let suffixArr = name.split("."), suffix = suffixArr[suffixArr.length - 1];
      if (suffix.indexOf("xls") == -1 ) {
        _this.creatExcelView();
        _this.$modal.msgWarning("当前文件不支持预览");
        return;
      }
      LuckyExcel.transformExcelToLuckyByUrl(this.baseUrl + fileUrl, fileName,function (exportJson, luckysheetfile) {
        if (exportJson.sheets == null || exportJson.sheets.length == 0) {
          _this.$modal.msgWarning("暂不适配xls文件，请下载后查看");
          return;
        }
        window.luckysheet.destroy();
        _this.$nextTick(function (){
          window.luckysheet.create({
            container: 'luckysheet', //luckysheet is the container id
            showinfobar: false,
            data: exportJson.sheets,
            title: exportJson.info.name,
            userInfo: exportJson.info.name.creator
          });
        })
      });
    },
    //创建excel视图
    creatExcelView() {
      this.$nextTick(function () {
        luckysheet.create({
          container: 'luckysheet', // 设定DOM容器的id
          title: 'Luckysheet Demo', // 设定表格名称
          lang: 'zh', // 设定表格语言
          plugins: ['chart'],
          data: [
            {
              "name": "Cell", //工作表名称
              "color": "", //工作表颜色
              "index": 0, //工作表索引
              "status": 1, //激活状态
              "order": 0, //工作表的下标
              "hide": 0,//是否隐藏
              "row": 36, //行数
              "column": 18, //列数
              "defaultRowHeight": 19, //自定义行高
              "defaultColWidth": 73, //自定义列宽
              "celldata": [], //初始化使用的单元格数据
              "config": {
                "merge": {}, //合并单元格
                "rowlen": {}, //表格行高
                "columnlen": {}, //表格列宽
                "rowhidden": {}, //隐藏行
                "colhidden": {}, //隐藏列
                "borderInfo": {}, //边框
                "authority": {}, //工作表保护

              },
              "scrollLeft": 0, //左右滚动条位置
              "scrollTop": 315, //上下滚动条位置
              "luckysheet_select_save": [], //选中的区域
              "calcChain": [],//公式链
              "isPivotTable": false,//是否数据透视表
              "pivotTable": {},//数据透视表设置
              "filter_select": {},//筛选范围
              "filter": null,//筛选配置
              "luckysheet_alternateformat_save": [], //交替颜色
              "luckysheet_alternateformat_save_modelCustom": [], //自定义交替颜色
              "luckysheet_conditionformat_save": {},//条件格式
              "frozen": {}, //冻结行列配置
              "chart": [], //图表配置
              "zoomRatio": 1, // 缩放比例
              "image": [], //图片
              "showGridLines": 1, //是否显示网格线
              "dataVerification": {} //数据验证配置
            },
            {
              "name": "Sheet2",
              "color": "",
              "index": 1,
              "status": 0,
              "order": 1,
              "celldata": [],
              "config": {}
            },
            {
              "name": "Sheet3",
              "color": "",
              "index": 2,
              "status": 0,
              "order": 2,
              "celldata": [],
              "config": {},
            }
          ]
        });
      })
    },
  }
}
</script>

<style scoped>

</style>