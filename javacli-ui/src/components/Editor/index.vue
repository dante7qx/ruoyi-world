<template>
  <div class="tinymce-editor">
    <editor v-model="myValue"
      :init="init"
      :disabled="disabled"
      class="tox-tinymce-aux"
     >
    </editor>
  </div>
</template>

<script>
import tinymce from 'tinymce/tinymce'
import Editor from '@tinymce/tinymce-vue'
// 主题
import 'tinymce/themes/silver/theme'
import 'tinymce/icons/default/icons'
// 样式
import 'tinymce/skins/ui/oxide/skin.min.css'
import 'tinymce/skins/ui/oxide/content.inline.min.css'
import '../../../public/static/tinymce/langs/zh_CN.js'
// 插件
import '../../../public/static/tinymce/indent2em.js'  // 首行缩进插件
import '../../../public/static/tinymce/tpLayout.js'  // 一键排版
import '../../../public/static/tinymce/media.js'  // 插入视频插件
import 'tinymce/plugins/image'  // 插入上传图片插件
import 'tinymce/plugins/table'  // 插入表格插件
import 'tinymce/plugins/link' //超链接插件
import 'tinymce/plugins/code' //代码块插件
import 'tinymce/plugins/lists'  // 列表插件
import 'tinymce/plugins/wordcount'  // 字数统计插件
import 'tinymce/plugins/colorpicker'  //选择颜色插件
import 'tinymce/plugins/textcolor' //文本颜色插件
import 'tinymce/plugins/hr' // 水平线
import 'tinymce/plugins/preview'  // 预览
import 'tinymce/plugins/fullscreen' // 全屏
import 'tinymce/plugins/searchreplace'  // 查找替换
import 'tinymce/plugins/autoresize' // 编辑器大小自适应

import request from '@/utils/request'
import { getToken } from "@/utils/auth";

export default {
	name: 'TinymceEditorWidget',
  components: {
    Editor
  },
	props: {
    //传入一个value，使组件支持v-model绑定
    value: {
      type: String,
      default: ''
    },
    baseUrl: {
      type: String,
      default: '',
    },
    disabled: {
      type: Boolean,
      default: false
    },
    minHeight: {
      type: Number,
      default: 450
    },
    maxHeight: {
      type: Number,
      default: 700
    },
    plugins: {
      type: [String, Array],
      default: 'lists image media table textcolor wordcount link hr searchreplace autoresize preview fullscreen code indent2em tpLayout'
    },
    toolbar: {
      type: [String, Array, Boolean],
      default: 'styleselect | bold italic underline strikethrough fontselect fontsizeselect forecolor backcolor | alignleft aligncenter alignright alignjustify | hr bullist numlist indent2em blockquote subscript superscript | removeformat undo redo | image media link table | tpLayout searchreplace preview fullscreen code'
    },
    menubar: {
      type: [String, Boolean],
      default: 'file edit insert view format table tools'
      // default: false
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    }
  },
  data() {
		return {
      uploadUrl: process.env.VUE_APP_BASE_API + "/common/upload_only", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken()
      },
			//初始化配置
      init: {
        deprecation_warnings: false,
        placeholder: "在这里输入文字",
        convert_urls: false,  //禁用URL自动转换
        language: 'zh_CN',
        // content_style: 'body { font-size: 12pt; word-break: break-all; text-align: justify; line-height: 25px; } p {text-indent: 2em;}',
        content_style: 'body { font-size: 12pt; word-break: break-all; text-align: justify; line-height: 25px; }',
        object_resizing: true, //禁用表格内联样式拖拽拉伸
        table_resize_bars: true,//禁用表格单元格拖拽拉伸
        protect: [
          /\<\/?(if|endif)\>/g, //<if> & </endif>
          /\<xsl\:[^>]+\>/g, //<xsl:...>
          /<\?php.*?\?>/g, //php代码
        ],
        min_height: this.minHeight,
        max_height: this.maxHeight,
        autoresize_bottom_margin: 50,
        plugins: this.plugins,
        menubar: this.disabled ? false : this.menubar,
        toolbar: this.disabled ? false : this.toolbar,
        toolbar_mode: 'wrap',
        font_formats: "宋体=SimSun,STSong;黑体=SimHei,STHeiti;仿宋=FangSong,STFangsong;楷体=KaiTi,STKaiti;微软雅黑=Microsoft YaHei;Andale Mono=andale mono,times;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Times New Roman=times new roman,times;",
        branding: false,
        resize: false,
        default_link_target: '_blank',
        tp_layout_options: {
          style: {
            'font-size': '12pt',
            'text-align':'justify',
            'text-indent':'2em',
            'line-height': '25px'
          },
          filterTags: ['table>*','tbody'],
          // clearStyle: ['text-indent'],
        },
        init_instance_callback: editor => {
          editor.on('paste', (evt) => {
            this.onPaste(evt)
          })
        },
        file_picker_types: "file image media",
        file_picker_callback: (cb, value, meta) => {
          const that = this
          if (meta.filetype == 'file') {
            let input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', '.doc, .docx, .xls, .xlsx, .ppt, .pptx, .pdf');
            input.onchange = function() {
              that.handleFileUpload(this.files[0], cb, 'file')
            }
            input.click();
          } else if (meta.filetype == 'image') {
            let input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');
            /*
            input.onchange = async(e) => {
              this.handleFileUpload(e.path[0].files[0], cb, 'image')
            }
            */
            input.onchange = function() {
              that.handleFileUpload(this.files[0], cb, 'image')
            }
            input.click();
          } else if (meta.filetype == 'media') {
            let input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'video/*');
            input.onchange = function() {
              that.handleFileUpload(this.files[0], cb, 'media')
            }
            input.click();
          }
        },
      },
      myValue: this.value
		}
	},
	mounted() {
		tinymce.init({})
	},
  methods: {
    handleBeforeUpload(file) {
      // 校验文件名是否含有非法字符
      if(file.name.indexOf(",") >= 0) {
        // cbFailure('文件名含有非法字符 , ', { remove: true });
        this.$message({message: '文件名含有非法字符 , ', type:'error', center: true, duration: 1000, offset: 300, customClass: 'tinymce-alert'});
        return false;
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          // cbFailure(`上传文件大小不能超过 ${this.fileSize} MB!`, { remove: true });
          this.$message({message: `上传文件大小不能超过 ${this.fileSize} MB!`, type:'error', center: true, duration: 1000, offset: 300, customClass: 'tinymce-alert'});
          return false;
        }
      }
      return true;
    },
    handleFileUpload(file, cb, fileType) {
      if(this.handleBeforeUpload(file)) {
        let formdata = new FormData()
        formdata.set('file', file)
        return request({
          url: '/common/upload_only',
          method: 'post',
          data: formdata
        }).then(res => {
          if(fileType == 'file') {
            cb(process.env.VUE_APP_BASE_API + res.fileName, {text: file.name})
          } else if(fileType == 'image') {
            cb(process.env.VUE_APP_BASE_API + res.fileName, {alt: file.name})
          } else if(fileType == 'media') {
            cb(process.env.VUE_APP_BASE_API + res.fileName, {title: file.name})
          }
        })
      }
    },
    onPaste(event) {
      const items = (event.clipboardData || window.clipboardData).items
      if (items[0].type.indexOf('image') !== -1) {
        const file = items[0].getAsFile()
        const formData = new FormData()
        formData.append('file', file)
        return request({
          url: '/common/upload_only',
          method: 'post',
          data: formData
        }).then(res => {
          if(res.code == 200) {
            this.myValue = this.myValue.replace(/<img src="data:image.*"/, '<img src="'+process.env.VUE_APP_BASE_API + res.fileName+'" alt=""')
          }
        })
      }
    }
  },
  watch: {
    value(newValue) {
      this.myValue = newValue;
    },
    myValue(newValue) {
      this.$emit("input", newValue);
    },
  }
}
</script>
<style>
.tox-tinymce-aux {
  z-index: 2509!important;
}
.tinymce-alert {
  z-index: 2510 !important;
}
</style>
