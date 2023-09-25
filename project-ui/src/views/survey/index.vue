<template>
  <div class="app-container">
    <el-button type="primary" icon="el-icon-plus" @click="add">新增问卷</el-button>
  </div>

</template>

<script>
export default {
  name: 'Survey',
  methods: {
    add() {
      this.$router.push("/survey/add");
      // this.$router.push("/monitor/job-log/index");
    }
  }
} 
</script>
