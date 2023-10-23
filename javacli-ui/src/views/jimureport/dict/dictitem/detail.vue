<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="字典项" prop="itemText">
        <el-input v-model="form.itemText" placeholder="请输入字典项文本" maxlength="100" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="字典值" prop="itemValue">
        <el-input v-model="form.itemValue" placeholder="请输入字典项值" maxlength="100" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" placeholder="请输入描述" maxlength="255" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder">
        <el-input v-model="form.sortOrder" placeholder="请输入排序"  :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-switch v-model="form.status" active-text="停用" inactive-text="启用" active-color="#ff4949" inactive-color="#13ce66" :disabled="disabled" />
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDictitem, addDictitem, updateDictitem } from "@/api/jimureport/dictitem";

export default {
  name: "DictitemDetail",
  props: {
    id: {
      type: Number,
      required: true,
      default: 0
    },
    dictId: {
      type: String,
      required: true
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
        itemText: [
          { required: true, message: "字典项不能为空", trigger: "blur" }
        ],
        itemValue: [
          { required: true, message: "字典值不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.loadForm()
  },
  methods: {
    loadForm() {
      this.reset();
      if(this.id > 0) {
        getDictitem(this.id).then(response => {
          this.form = response.data;
        });
      }
    },
    reset() {
      this.form = {
        id: null,
        dictId: null,
        itemText: null,
        itemValue: null,
        description: null,
        sortOrder: null,
        status: 0,
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
          this.form.dictId = this.dictId;
          if (this.form.id != null) {
            updateDictitem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addDictitem(this.form).then(response => {
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
