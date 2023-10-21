<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="属性名称" prop="propName">
        <el-input v-model="form.propName" placeholder="请输入属性名称" maxlength="64" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="属性类型" prop="propType">
        <el-select v-model="form.propType" clearable filterable placeholder="请选择属性类型" :disabled="disabled" style="width: 100%" @change="changePropType">
          <el-option
            v-for="(dict, index) in propTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="字典类型值" prop="propTypeVal" v-if="form.propType == 'dict'">
        <el-select v-model="form.propTypeVal" clearable filterable placeholder="请选择字典类型" :disabled="disabled" style="width: 100%">
          <el-option
            v-for="(dict, index) in dictTypeOptions"
            :key="index"
            :label="dict.dictName"
            :value="dict.dictType">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注信息" prop="remark">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" show-word-limit maxlength="500"  placeholder="请输入内容" :readonly="disabled"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";
import { getPropType, getProp, addProp, updateProp } from "@/api/system/infocategory";

export default {
  name: "InfoDetail",
  props: {
    propId: {
      type: Number,
      required: true,
      default: 0
    },
    categoryId: {
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
      form: {},
      rules: {
        propName: [
          { required: true, message: "属性名称不能为空", trigger: "blur" }
        ],
        propType: [
          { required: true, message: "属性类型不能为空", trigger: "change" }
        ],
      },
      propTypeOptions: [],  // 属性类型
      dictTypeOptions: [] // 字典类型
    };
  },
  created() {
    this.loadForm();
    this.loadSelectOption();
  },
  methods: {
    loadSelectOption() {
      this.propTypeOptions = getPropType();
      getDictOptionselect().then(response => {
        this.dictTypeOptions = response.data;
      });
    },
    loadForm() {
      this.reset();
      if(this.propId > 0) {
        getProp(this.propId).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        propId: null,
        categoryId: null,
        propName: null,
        propType: null,
        propTypeVal: null,
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
          this.form.categoryId = this.categoryId;
          if (this.form.propId != null) {
            updateProp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addProp(this.form).then(response => {
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
    changePropType(propType) {
      if(propType == 'dict') {
        this.rules['propTypeVal'] = [
          { required: true, message: "字典类型值不能为空", trigger: ["blur", "change"] }
        ]
        this.$refs["form"].validateField('propTypeVal')
      } else {
        this.form.propTypeVal = ''
        this.rules['propTypeVal'] = []
        this.$refs["form"].clearValidate('propTypeVal')
      }
    }
  }
};
</script>
