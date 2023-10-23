<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="6">
        <span v-html="'\u00a0'"></span>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <el-input v-model="title" placeholder="请输入内容"></el-input>
        </el-card>
      </el-col>
      <el-col :span="6"></el-col>
    </el-row>
  </div>

</template>

<script>
export default {
  name: 'AddSurvey',
  data() {
    return {
      title: "关于XX的问卷调查"
    }
  },
  methods: {
    
  }
} 


</script>

<style scoped>

</style>