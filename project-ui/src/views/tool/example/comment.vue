<template>
	<div class="app-container">
    <h2>留言组件 - views/tool/example/comment.vue</h2>

    <el-descriptions title="业务信息" :column="1" border>
      <template v-slot="extra">
        <el-button type="primary" size="small" @click="clear">清空</el-button>
      </template>
      <el-descriptions-item label="业务模块">{{ bizModel }}</el-descriptions-item>
      <el-descriptions-item label="业务Id">{{ bizId }}</el-descriptions-item>
    </el-descriptions>

    <comment :key="key" :bizModel="bizModel" :bizId="bizId" :showImg="true" :showVideo="true"/>
  </div>
</template>

<script>
import { delBizComment } from '@/api/system/comment'
import Comment from '@/components/Comment'

export default {
	name: 'CommentPage',
  components: { Comment },
  data() {
    return {
      bizModel: 'example',
      bizId: 100,
      key: 0
    }
  },
  methods: {
    clear() {
      delBizComment(this.bizModel, this.bizId).then(res => {
        this.$modal.msgSuccess("清空成功！");
        this.key += 1;
      })
    }
  }
}
</script>
