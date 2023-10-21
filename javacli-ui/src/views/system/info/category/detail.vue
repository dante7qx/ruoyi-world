<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="上级栏目" prop="parentId">
        <treeselect 
          v-model="form.parentId" 
          :options="categoryOptions" 
          :normalizer="normalizer" 
          :default-expand-level="1" 
          :max-height="260"
          placeholder="选择上级栏目" 
          :disabled="disabled" />
      </el-form-item>
      <el-form-item label="栏目名称" prop="categoryName">
        <el-input v-model="form.categoryName" placeholder="请输入栏目名称" maxlength="64" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="栏目键" prop="categoryKey">
        <el-input v-model="form.categoryKey" placeholder="请输入栏目键" maxlength="32" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="栏目类型" prop="categoryType">
        <el-select v-model="form.categoryType" placeholder="请选择栏目类型" :disabled="disabled" style="width: 100%;">
          <el-option
            v-for="dict in dict.type.sys_info_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="显示顺序" prop="orderNum">
        <el-input v-model="form.orderNum" placeholder="请输入显示顺序"  :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="停用" prop="disabled">
        <el-switch v-model="form.disabled" inactive-color="#ff4949" :disabled="disabled"  />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { listCategory, getCategory, addCategory, updateCategory } from "@/api/system/infocategory";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "InfoCategoryDetail",
  components: { Treeselect },
  props: {
    categoryId: {
      type: Number,
      required: true,
      default: 0
    },
    parentId: {
      type: Number,
      required: false,
      default: 0
    },
    disabled: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  dicts: ['sys_info_type', 'sys_normal_disable'],
  data() {
    return {
      // 栏目树选项
      categoryOptions: [{
        categoryId: 0,
        categoryName: '根栏目'
      }],
      form: {},
      rules: {
        parentId: [
          { required: true, message: "父栏目不能为空", trigger: ["blur", "change"] }
        ],
        categoryName: [
          { required: true, message: "栏目名称不能为空", trigger: "blur" }
        ],
        categoryType: [
          { required: true, message: "栏目类型不能为空", trigger: ["blur", "change"] }
        ],
        orderNum: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {
    this.loadTree();
    this.loadForm()
  },
  methods: {
    loadTree() {
      listCategory(this.queryParams).then(response => {
        const treeData = this.handleTree(response.data, "categoryId");
        this.$set(this.categoryOptions[0], 'children', treeData)
      });
    },
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.categoryId,
        label: node.categoryName,
        children: node.children,
        isDisabled: node.disabled,
      };
    },
    loadForm() {
      this.reset();
      if(this.categoryId > 0) {
        getCategory(this.categoryId).then(response => {
          this.form = response.data;
        });
      }
      if(this.parentId > 0) {
        this.form.parentId = this.parentId;
      }
    },
    reset() {
      this.form = {
        categoryId: null,
        parentId: null,
        ancestors: null,
        categoryName: null,
        categoryKey: null,
        categoryType: null,
        orderNum: null,
        disabled: null,
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
          if (this.form.categoryId != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addCategory(this.form).then(response => {
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
    }
  }
};
</script>
