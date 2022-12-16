<template>
	<div class="app-container">
    <h2>生成二维码 - views/tool/example/qrcode.vue</h2>
    <el-form :model="form" :inline="true" ref="form" :rules="rules" label-width="110px" size="medium">
      <el-form-item label="姓名" prop="name" >
        <el-input v-model="form.name" placeholder="请输入姓名"/>
      </el-form-item>
      <el-form-item label="年龄" prop="age" >
        <el-input v-model.number="form.age" placeholder="请输入年龄"/>
      </el-form-item>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">清 除</el-button>
    </el-form>

    <div>
      <span class="demonstration">二维码</span>
      <br />
      <el-image :src="qrCode"></el-image>
  </div>
   
  </div>
</template>

<script>
import { genQrCode } from '@/api/tool/codeExample.js'

export default {
	name: 'QrCodePage',
  data() {
    return {
      form: {},
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { type: 'number', message: '年龄必须为数字值'}
        ]
      },
      baseUrl: process.env.VUE_APP_BASE_API,
      qrCode: ""
    }
  },
  methods: {
    submitForm() {
      this.form.key = 'GenQrCode',
      this.$refs.form.validate((valid) => {
        if (valid) {
          genQrCode(this.form).then(res => {
            this.qrCode = this.baseUrl + res.msg
          })
        } 
			});
    },
    cancel() {
      this.qrCode = ""
      this.form = {
        name: null,
        age: null
      };
      this.resetForm("form");
    }
  }

}
</script>