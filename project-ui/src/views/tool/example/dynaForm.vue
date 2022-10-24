<template>
	<div class="app-container">
    <h2>动态表单（验证）、身份证验证及出生日期、性别提取 - views/tool/example/dynaForm.vue</h2>
    
    <el-form :model="form" :inline="true" ref="form" label-width="110px" size="medium">
      <el-row v-for="(item,index) in form.list" :key="index">
        <el-form-item label="身份证参数"
          :prop="'list.' + index + '.arg1'"
          :rules="rules.arg1">
          <el-input v-model="item.arg1" placeholder="请输入参数1"/>
        </el-form-item>
        <el-form-item label="日期参数"
          :prop="'list.' + index + '.arg2'"
          :rules="[{ required: true, message: '参数2',trigger: 'change'}]">
          <el-date-picker clearable
            v-model="item.arg2"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择时间参数">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="数字参数"
          :prop="'list.' + index + '.arg3'"
          :rules="rules.arg3">
          <el-input type="number" v-model="item.arg3" autocomplete="off" placeholder="请输入排序参数"/>
        </el-form-item>
        <el-button type="danger" v-if="form.list.length > 1" size="medium" @click="removeRow(index)">删除</el-button>
      </el-row>
      <el-row>
        <el-button type="success" size="medium" @click="addRow">新增条目</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="danger" @click="clear">清 空</el-button>
        <el-button @click="cancel">取 消</el-button>
      </el-row>
    </el-form>
    <el-row>
			<el-table :data="tableList" style="width: 800px; margin-top: 15px;">
				<el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column label="身份证参数" header-align="center">
          <el-table-column prop="arg1" label="身份证" width="180"/>
          <el-table-column label="出生日期">
            <template slot-scope="scope">
              <span>{{  parseIdCard(scope.row.arg1).birthday }}</span>
            </template>
          </el-table-column>
          <el-table-column label="性别">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user_sex" :value="parseIdCard(scope.row.arg1).sex"/>
            </template>
          </el-table-column>
        </el-table-column>
				<el-table-column prop="arg2" label="时间参数" />
        <el-table-column prop="arg3" label="数字参数" />
			</el-table>
		</el-row>
  </div>
</template>

<script>
import { validIdCard } from '@/utils/validate'
import { getDataDetail, submitData, clearData } from '@/api/tool/codeExample.js'

export default {
	name: 'DynaFormPage',
  dicts: [ 'sys_user_sex' ],
  data() {
    return {
      title: '动态表单',
      form: {
        key: 'DynaForm',
        list: [{ 
          arg1: null,
          arg2: null,
          arg3: null
        }]
      },
      rules: {
        arg1: [
          { required: true, message: '请输入身份证参数', trigger: 'change' },
          { validator: (rule, value, callback) => { 
            if(validIdCard(value)) {
              callback();
            } else {
              callback(new Error('身份证输入错误'));
            }
          }}
        ],
        arg3: [
          { required: true, message: '请输入排序参数', trigger: 'change' },
          {
            validator: (rule, value, callback) => {
              if (value < 0) {
                callback(new Error('必须大于0'));
              } else if (value.length > 5) {
                callback(new Error('不超过3位数字'));
              } else if (!value) {
                callback(new Error('排序不能为空'));
              } else {
                callback();
              }
            },
            trigger: 'change',
          },
        ],
      },
      tableList: []
    }
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      getDataDetail(this.form.key).then(res => {
				this.tableList = res.data ? res.data.list : []
			})
    },
    addRow() {
      this.form.list.push({
        arg1: null,
        arg2: null,
        arg3: null
      });
    },
    removeRow(index) {
      this.form.list.splice(index, 1);
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          submitData(this.form).then(res => {
            this.$modal.msgSuccess("提交成功");
            this.loadData();
          })
        } 
			});
    },
    cancel() {
      this.form.list = [{ 
        arg1: null,
        arg2: null,
        arg3: null
      }]
    },
    clear() {
      clearData(this.form.key).then(res => {
        this.loadData()
      })
    }
  }

}
</script>