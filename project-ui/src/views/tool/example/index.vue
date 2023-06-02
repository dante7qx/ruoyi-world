<template>
  <div class="code-example-container">
		<el-menu 
			:default-active="activeIndex"
			class="el-menu-demo" 
			mode="horizontal" 
			background-color="#1f2d3d"
  		text-color="#fff"
  		active-text-color="#3b8be0"
			@select="handleSelect">
			<el-menu-item index="sysDeptTree">部门树</el-menu-item>
			<el-menu-item index="sysUserSel">用户选择</el-menu-item>
			<el-submenu index="1">
				<template slot="title">下拉框</template>
				<el-menu-item index="area">地区</el-menu-item>
			</el-submenu>
			<el-menu-item index="addressMap">地址地图</el-menu-item>
			<el-submenu index="2">
				<template slot="title">表格表单</template>
				<el-menu-item index="dynaForm">动态表单</el-menu-item>
				<el-menu-item index="longTableCol">长表格字段</el-menu-item>
				<el-menu-item index="iframe">iframe标签</el-menu-item>
			</el-submenu>
			<el-menu-item index="videoMonitor">视频监控</el-menu-item>
			<el-menu-item index="watermark">页面水印</el-menu-item>
			<el-menu-item index="calendar">日历</el-menu-item>
			<el-menu-item index="qrcode">二维码</el-menu-item>
			<el-menu-item index="comment">留言板</el-menu-item>
			<el-menu-item index="voicepodcast">语音播报</el-menu-item>
		</el-menu>
		<code-area v-if="activeIndex=='area'" />
		<address-map v-else-if="activeIndex=='addressMap'" />
		<dyna-form v-else-if="activeIndex=='dynaForm'" />
		<long-tab-col v-else-if="activeIndex=='longTableCol'" />
		<code-video v-else-if="activeIndex=='videoMonitor'" />
		<water-mark v-else-if="activeIndex=='watermark'" />
		<code-calendar v-else-if="activeIndex=='calendar'" />
		<sys-user-sel v-else-if="activeIndex=='sysUserSel'" />
		<gen-qrcode v-else-if="activeIndex=='qrcode'" />
		<comment-page v-else-if="activeIndex=='comment'" />
		<iframe-page v-else-if="activeIndex=='iframe'" />
		<sys-dept-tree-sel v-else-if="activeIndex=='sysDeptTree'" />
		<voice-podcast :key="nanoid()" v-else-if="activeIndex=='voicepodcast'" />
	</div>
</template>

<script>
import Area from './area'
import AddressMap from './addressMap'
import DynaForm from './dynaForm'
import LongTabCol from './longTableCol'
import Video from './video'
import Watermark from './watermark'
import Calendar from './calendar'
import SysUserSel from './sysUserSel'
import QrCode from './qrcode'
import Comment from './comment'
import Iframe from './iframe'
import SysDeptTreeSel from './deptTree'
import VoicePodcast from './voicePodcast'

export default {
  name: "CodeExample",
	components: {
		'code-area' : Area,
		'address-map': AddressMap,
		'dyna-form': DynaForm,
		'long-tab-col': LongTabCol,
		'code-video': Video,
		'water-mark': Watermark,
		'code-calendar': Calendar,
		'sys-user-sel': SysUserSel,
		'gen-qrcode': QrCode,
		'comment-page': Comment,
		'iframe-page': Iframe,
		'sys-dept-tree-sel': SysDeptTreeSel,
		'voice-podcast': VoicePodcast,
	},
	data() {
    return {
			activeIndex: 'sysDeptTree'
		}
	},
	methods: {
		handleSelect(key, keyPath) {
			this.activeIndex = key
		}
	}
}
</script>
<style scoped>
.code-example-container {
	margin: 5px;
}
</style>