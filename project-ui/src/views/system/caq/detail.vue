<template>
  <div>
    <el-table v-loading="loading" :data="condList" max-height="500">
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="列名" align="center" prop="colName" v-if="false"/>
      <el-table-column label="字段" align="center" prop="colDesc" />
      <el-table-column label="查询类型" align="center" prop="queryType">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(operOptions, scope.row.queryType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字典类型" align="center" prop="dictType">
        <template slot-scope="scope">
          <span>{{ formatDictType(scope.row.dictType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Java字段名" align="center" prop="javaField" v-if="false"/>
      <el-table-column label="Java字段类型" align="center" prop="javaType" v-if="false"/>
    </el-table>
  </div>
</template>

<script>
import { getCaqSetupCond } from "@/api/system/caq";
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";

export default {
  name: "CaqDetail",
  props: {
    queryId: {
      type: Number,
      required: true,
      default: 0
    },
    disabled: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  data() {
    return {
      loading: true,
      condList: [],
      dictTypeOptions: [],
      operOptions: [{
        value: '=',
        label: '等于'
      }, {
        value: '!=',
        label: '不等于'
      }, {
        value: 'LIKE',
        label: '模糊匹配'
      }, {
        value: '&gt;',
        label: '大于'
      }, {
        value: '&gt;=',
        label: '大于等于'
      }, {
        value: '&lt;',
        label: '小于'
      }, {
        value: '&lt;=',
        label: '小于等于'
      }, {
        value: 'BETWEEN',
        label: '日期范围'
      }],
    };
  },
  created() {
    
    getDictOptionselect().then(response => {
      this.dictTypeOptions = response.data;
    });
    this.getList();
  },
  methods: {
    getList() {
      getCaqSetupCond(this.queryId).then(res => {
        this.condList = res.data
        this.loading = false;
      })
    },
    formatDictType(dictType) {
      const dict = this.dictTypeOptions.find(d => d.dictType == dictType)
      return dict ? dict.dictName: '';
    },
    cancel() {
      this.reset();
      this.$emit('closeWindow');
    }
  }
};
</script>
