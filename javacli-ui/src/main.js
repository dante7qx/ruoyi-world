import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css
import 'video.js/dist/video-js.css'  // video css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, parseIdCard, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree, printLongTableColumn, desensitizeIdCard, desensitizeMobilePhone, encodeBase64, decodeBase64,loadJS } from "@/utils/spirit";
import moment from 'moment';
import { nanoid } from 'nanoid';
import vueAwesomeCountdown from 'vue-awesome-countdown'

// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'
// 地址地图选择组件
import AddressMapSelect from '@/components/AddressMapSelect'
// 视频播放组件
import VideoMonitor from '@/components/Video'
import VideoPlayer from '@/components/Video/play' 
// 列表长字段展示组件
import LongTableCol from '@/components/LongTableCol'
// 部门人员选择
import DeptUserSelect from '@/components/DeptUserSelect'
// 部门树选择
import DeptTreeSelect from '@/components/DeptTree'

// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.parseIdCard = parseIdCard
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree
Vue.prototype.printLongTableColumn = printLongTableColumn
Vue.prototype.desensitizeIdCard = desensitizeIdCard
Vue.prototype.desensitizeMobilePhone = desensitizeMobilePhone
Vue.prototype.encodeBase64 = encodeBase64
Vue.prototype.decodeBase64 = decodeBase64
Vue.prototype.loadJS = loadJS
Vue.prototype.$moment = moment
Vue.prototype.nanoid = nanoid

// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)
Vue.component('AddressMapSelect', AddressMapSelect)
Vue.component('VideoMonitor', VideoMonitor)
Vue.component('VideoPlayer', VideoPlayer)
Vue.component('LongTableCol', LongTableCol)
Vue.component('SysDeptUserSelect', DeptUserSelect)
Vue.component('SysDeptTree', DeptTreeSelect)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
Vue.use(vueAwesomeCountdown, 'vac')
DictData.install()

// 模态框点击空白不消失
Element.Dialog.props.closeOnClickModal.default = false

// 表格带有边框
Element.Table.props.border = {
  default: true,
  type: Boolean
}

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
