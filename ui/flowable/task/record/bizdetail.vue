<template>
  <div class="app-container">
    <leave-detail :key="key" :demoId="bizId" :disabled="disabled" @closeWindow="closeWindow" v-if="bizModel=='FlowDemo'"/>
  </div>
</template>

<script>
import LeaveDetail from "@/views/flowable/demo/detail"

export default {
  name: "BizDetail",
  components: {
    LeaveDetail
  },
  props: {
    bizModel: {
      type: String,
      required: true
    },
    bizId: {
      type: Number,
      required: true
    },
    disabled: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  data() {
    return {
      key: '',
    }
  },
  created() {
    this.key = this.nanoid();
    
  },
  methods: {
    closeWindow() {
      this.$emit('closeWindow');
    }
  }
}
</script>