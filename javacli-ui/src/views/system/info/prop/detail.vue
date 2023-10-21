<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="属性名" prop="categoryPropId">
        <el-select v-model="form.categoryPropId" clearable filterable placeholder="请选择属性" :disabled="disabled" style="width: 100%" @change="changeProp">
          <el-option
            v-for="(item, index) in categoryProps"
            :key="index"
            :label="item.propName"
            :value="item.propId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="属性值" prop="propVal" v-if="form.categoryPropId">
        <el-input v-model="form.propVal" placeholder="请输入属性值" :disabled="disabled" v-if="propType == 'input'"/>
        <el-input v-model="form.propVal" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" placeholder="请输入属性值" :readonly="disabled" v-else-if="propType == 'textarea'"/>
        <image-upload v-model="form.propVal" :limit="5" :fileSize="1" :disabled="disabled" v-else-if="propType == 'img'"/>
        <file-upload v-model="form.propVal" :bizModel="'SysInfo'" :fileType="['mp4','avi','wmv']" :fileSize="20" :disabled="disabled" v-else-if="propType == 'video'"/>
        <file-upload v-model="form.propVal" :bizModel="'SysInfo'" :limit="5" :disabled="disabled" v-else-if="propType == 'file'"/>
        <el-date-picker v-model="form.propVal" clearable type="date" value-format="yyyy-MM-dd" placeholder="请选择" :disabled="disabled" v-else-if="propType == 'date'" />
        <el-date-picker v-model="form.propVal" clearable type="datetime" format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" placeholder="请选择" :disabled="disabled" v-else-if="propType == 'datetime'" />
        <el-date-picker v-model="form.propVal" clearable type="daterange" value-format="yyyy-MM-dd" placeholder="请选择" :disabled="disabled" v-else-if="propType == 'daterange'" />
        <el-date-picker v-model="form.propVal" clearable type="datetimerange" format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" placeholder="请选择" :disabled="disabled" v-else-if="propType == 'datetimerange'" />
        <editor v-model="form.propVal" :disabled="disabled" :max-height="550" v-else-if="propType == 'text'"/>
        <el-select v-model="form.propVal" :disabled="disabled" :placeholder="请选择" clearable v-else>
          <el-option
            v-for="dict in dictOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
import { getProp, addProp, updateProp } from "@/api/system/info";
import { listProp } from "@/api/system/infocategory";

export default {
  name: "InfoPropDetail",
  props: {
    propId: {
      type: Number,
      required: true,
      default: 0
    },
    infoId: {
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
        categoryPropId: [
          { required: true, message: "属性名不能为空", trigger: ["blur","change"] }
        ],
      },
      categoryProps: [],
      selectPropId: 0,
      propType: null,
      dictOptions: []
    };
  },
  created() {
    this.loadCategoryProp();
    this.loadForm();
  },
  methods: {
    loadForm() {
      this.reset();
      if(this.propId > 0) {
        getProp(this.propId).then(response => {
          this.form = response.data;
          this.propType = this.form.propType
          if(this.form.propType == 'daterange' || this.form.propType == 'datetimerange') {
            this.form.propVal = this.form.propVal.split(',')
          }
        });
      }
    },
    loadCategoryProp() {
      listProp({pageSize: 100, infoId: this.infoId}).then(res => {
        this.categoryProps = res.rows;
      })
    },
    reset() {
      this.form = {
        propId: null,
        infoId: null,
        categoryPropId: null,
        propVal: null,
        remark: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.infoId = this.infoId;
          const formData = Object.assign({}, this.form)
          formData.propVal = formData.propVal.toString();
          if (formData.propId != null) {
            updateProp(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addProp(formData).then(response => {
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
    changeProp(propId) {
      this.propType = ''
      if(this.selectPropId != propId) {
        this.form.propVal = null
      }
      this.selectPropId = propId
      if(propId) {
        const prop = this.categoryProps.find(p => p.propId == propId);
        this.propType = prop.propType;
        if(this.propType == 'dict') {
          this.dictOptions = []
          this.getDicts(prop.propTypeVal).then(response => {
            this.dictOptions = response.data
          });
        } 
      } 
    }
  }
};
</script>
