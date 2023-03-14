<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="流程类型" prop="typeName">
            <el-select v-model="form.typeName" placeholder="请选择流程类型" clearable :disabled="disabled" style="width: 100%">
              <el-option
                v-for="dict in dict.type.sys_process_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="流程组" prop="flowGroupId">
            <el-select v-model="form.flowGroupId" placeholder="请选择流程组" clearable :disabled="disabled" style="width: 100%">
              <el-option
                v-for="group in groupList"
                :key="group.groupId"
                :label="group.groupName"
                :value="group.groupId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="流程定义" prop="flowDefKey">
            <el-select v-model="form.flowDefKey" placeholder="请选择流程定义" @change="selectFlowDefKey" clearable :disabled="disabled" style="width: 100%">
              <el-option
                v-for="procDef in procDefList"
                :key="procDef.id"
                :label="`${procDef.name} | ${procDef.flowKey}`"
                :value="procDef.flowKey"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="权重" prop="orderNum">
            <el-input v-model.number="form.orderNum" placeholder="请输入权重"  :disabled="disabled" style="width: 100%"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="流程分类" prop="flowCategory">
            <el-select v-model="form.flowCategory" placeholder="流程分类" clearable :disabled="true" style="width: 100%">
              <el-option
                v-for="dict in dict.type.sys_process_category"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注" maxlength="200" show-word-limit :disabled="disabled"/>
          </el-form-item>
        </el-col>
      </el-row>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { listLatestDefinition } from "@/api/flowable/definition";
import { getGroups } from "@/api/flowable/group";
import { getType, addType, updateType } from "@/api/flowable/type";

export default {
  name: "TypeDetail",
  props: {
    typeId: {
      type: Number,
      required: true,
      default: 0
    },
    disabled: {
      type: Boolean,
      required: true,
      default: false
    },
    groups: {
      type: Array,
      required: false,
      default: null
    },
  },
  dicts: ['sys_process_category', 'sys_process_type'],
  data() {
    return {
      form: {},
      rules: {
        typeName: [
          { required: true, message: "流程类型不能为空", trigger: "change" }
        ],
        flowGroupId: [
          { required: true, message: "流程组不能为空", trigger: "change" }
        ],
        flowDefKey: [
          { required: true, message: "流程定义不能为空", trigger: "change" }
        ],
        orderNum: [
          { required: true, message: "权重不能为空", trigger: "blur" },
          { type: 'number', message: '权重必须为数字值' }
        ]
      },
      groupList: [],
      procDefList: []
    };
  },
  created() {
    this.loadGroup()
    this.loadProcDef()
    this.loadForm()
  },
  methods: {
    loadGroup() {
      if(this.groups) {
        this.groupList = this.groups;
      } else {
        getGroups().then(response => {
          this.groupList = response.data;
        })
      }
    },
    loadProcDef() {
      listLatestDefinition().then(response => {
        this.procDefList = response.data;
      })
    },
    loadForm() {
      this.reset();
      if(this.typeId > 0) {
        getType(this.typeId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        typeId: null,
        typeName: null,
        flowCategory: null,
        flowGroupId: null,
        flowDefKey: null,
        flowDeployId: null,
        orderNum: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.typeId != null) {
            updateType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addType(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.cancel();
            });
          }
        }
      });
    },
    cancel() {
      this.reset();
      this.$emit('closeWindow');
    },
    selectFlowDefKey(flowDefKey) {
      this.form.flowCategory = this.procDefList.find(p => p.flowKey === flowDefKey).category
    }
  }
};
</script>
