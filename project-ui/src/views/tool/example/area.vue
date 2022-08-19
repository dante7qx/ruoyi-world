<template>
	<div>
		<h2>地区选择 - views/tool/area.vue</h2>
		<el-form ref="form" :model="form" :rules="rules" label-width="80px">
			<el-form-item label="地区" prop="areaData">
				<el-cascader
					v-model="form.areaData"
					filterable
					clearable
					:options="areaOptions"
					:props="{
						checkStrictly:true,
						expandTrigger: 'hover',
						label:'name',
						value:'name',
						children:'childs'}"
					style="width: 240px;">
			</el-cascader>
			</el-form-item>
			<el-form-item label="表单数据">
				<span v-html="formData" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="submitCascade">提交</el-button>
				<el-button>取消</el-button>
			</el-form-item>
		</el-form>
		<el-row>
			<el-table :data="tableList" style="width: 500px">
				<el-table-column type="index" label="序号" width="55" align="center" />
      	<el-table-column prop="key" label="键值" width="100" />
				<el-table-column prop="areaData" label="地区" />
			</el-table>
		</el-row>
	</div>
</template>

<script>
import { getAreaData } from '@/utils/areaSel.js'
import { getDataDetail, submitData } from '@/api/tool/codeExample.js'

export default {
	name: 'AreaSelect',
	data() {
		return {
			areaOptions: null,
			tableList: [],
			form: {
				key: 'AreaSel',
				areaData: null,
			},
			rules: { 
				areaData: [{ required: true, message: '请选择地区', trigger: 'change' }]
			},
			formData: ''
		}
	},
	created() {
		this.loadAreaOptions()
		this.loadData()
	},
	methods: {
		// 获取地区数据
		loadAreaOptions() {
			this.areaOptions = getAreaData()
		},
		loadData() {
			getDataDetail(this.form.key).then(res => {
				if(res.data) {
					this.form = res.data
					// 将数据库中地区字符串转化为数组
					this.form.areaData = this.form.areaData ? this.form.areaData.split(',') : null
					this.tableList.push(this.form)
				}
			})
		},
		submitCascade() {
			this.$refs.form.validate((valid) => {
          if (valid) {
            const formData = Object.assign({}, this.form)
						// 将地区数组转化为字符串
						formData.areaData = formData.areaData ? formData.areaData.toString() : ''
						this.formData = formData
						submitData(formData).then(res => {
							this.$modal.msgSuccess("提交成功");
						})
          } 
			});
			
		}
	}
}
</script>