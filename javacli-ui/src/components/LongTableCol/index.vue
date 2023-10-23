<template>
  <div>
    <el-popover
      :placement="placement"
      :trigger="trigger"
      :content="str">
      <div class="longColDesc" :style="{ width:  width + 'px', maxHeight: maxHeight + 'px'}">
        {{ str }}
      </div>
      <span slot="reference" v-text="printLongTableColumn(str, len)"></span>
    </el-popover>
  </div>
</template>

<script>
export default {
  name: 'LongTableColPage',
  props: {
    str: {
      type: String,
      default: '',
      required: false
    },
    // 列表上显示的字段长度
    len: {
      type: Number,
      default: 10,
      required: false
    },
    width: {
      type: Number,
      default: 350,
      required: false
    },
    maxHeight: {
      type: Number,
      default: 200,
      required: false
    },
    placement: {
      type: String,
      default: 'top-start',
      required: false
    },
    trigger: {
      type: String,
      default: 'hover',
      required: false
    }
  },
  data() {
    return {
      
    }
  },
}
</script>
<style scoped>
.longColDesc {
  margin: 0;
  overflow: auto;
}
</style>