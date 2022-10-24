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
			<el-menu-item index="addressMap">坐标识取</el-menu-item>
			<el-submenu index="2">
				<template slot="title">表单</template>
				<el-menu-item index="dynaForm">动态表单</el-menu-item>
			</el-submenu>
		</el-menu>
		<code-area v-if="activeIndex=='area'" />
		<address-map v-else-if="activeIndex=='addressMap'" />
		<dyna-form v-if="activeIndex=='dynaForm'" />
	</div>
</template>

<script>
import Area from './area'
import AddressMap from './addressMap'
import DynaForm from './dynaForm'

export default {
  name: "CodeExample",
	components: {
		'code-area' : Area,
		'address-map': AddressMap,
		'dyna-form': DynaForm
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