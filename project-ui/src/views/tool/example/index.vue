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
			<el-submenu index="1">
				<template slot="title">下拉框</template>
				<el-menu-item index="area">地区</el-menu-item>
			</el-submenu>
			<el-menu-item index="addressMap">地址地图</el-menu-item>
			<el-submenu index="2">
				<template slot="title">表单</template>
				<el-menu-item index="dynaForm">动态表单</el-menu-item>
			</el-submenu>
			<el-menu-item index="videoMonitor">视频监控</el-menu-item>
			<el-menu-item index="watermark">页面水印</el-menu-item>
		</el-menu>
		<code-area v-if="activeIndex=='area'" />
		<address-map v-else-if="activeIndex=='addressMap'" />
		<dyna-form v-else-if="activeIndex=='dynaForm'" />
		<code-video v-else-if="activeIndex=='videoMonitor'" />
		<water-mark v-else-if="activeIndex=='watermark'" />
	</div>
</template>

<script>
import Area from './area'
import AddressMap from './addressMap'
import DynaForm from './dynaForm'
import Video from './video'
import Watermark from './watermark'

export default {
  name: "CodeExample",
	components: {
		'code-area' : Area,
		'address-map': AddressMap,
		'dyna-form': DynaForm,
		'code-video': Video,
		'water-mark': Watermark
	},
	data() {
    return {
			activeIndex: 'area'
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