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
			<el-submenu index="2">
				<template slot="title">其他</template>
				<el-menu-item index="21">选项1</el-menu-item>
				<el-menu-item index="22">选项2</el-menu-item>
			</el-submenu>
		</el-menu>
		<code-area :key="activeIndex" v-if="activeIndex=='area'" />
	</div>
</template>

<script>
import Area from './area'

export default {
  name: "CodeExample",
	components: {
		'code-area' : Area
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