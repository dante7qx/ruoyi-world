<template>
  <div>
    <el-card :key="category" v-for="category in Object.keys(flowTypeMap)">
      <div slot="header" class="clearfix">
        <span class="flow-category">{{ selectDictLabel(dict.type.sys_process_category, category) }}</span>
      </div>
      <div class="flow-type" 
        :key="type" 
        v-for="type in flowTypeMap[category]" 
        @click="doStart(type)" 
        :style="{width: computeTypeWidth(type)}">
        {{ selectDictLabel(dict.type.sys_process_type, type) }}
      </div>
    </el-card>
    <span v-if="Object.keys(flowTypeMap).length == 0" class="flow-span">您没有可用的流程，请确认后再试！</span>
  </div>
</template>

<script>
import { newFlow } from "@/api/flowable/process"

export default {
  name: "NewFlowPage",
  dicts: ['sys_process_category', 'sys_process_type'],
  data() {
    return {
      flowTypeMap: {}
    }
  },
  created() {
    this.getFlowTypeMap();
  },
  methods: {
    getFlowTypeMap() {
      newFlow().then(res => {
        this.flowTypeMap = res.data
      })
    },
    doStart(flowType) {
      this.$emit('closeWindow', flowType);
    },
    computeTypeWidth(type) {
      const val = this.selectDictLabel(this.dict.type.sys_process_type, type)
      return `${val.length > 4 ? val.length * 16 : 70}px`
    }
  }
}
</script>

<style scoped>
.flow-category {
  font-size: 16px;
  font-weight: bold;
}
.flow-type {
  color: #fff;
  background-color: #594eee;
  width: 70px;
  height: 70px;
  margin-right: 10px;
  text-align: center;
  vertical-align: middle;
  padding-top: 25px;
  display: inline-block;
  cursor: pointer;
  border-radius: 20%;
}

.flow-span {
  color: red;
  font-size: 18px;
}
</style>