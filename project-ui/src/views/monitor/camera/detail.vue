<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="上级监控" prop="parentId">
        <treeselect 
          v-model="form.parentId" 
          :options="categoryOptions" 
          :normalizer="normalizer" 
          :default-expand-level="1" 
          :max-height="260"
          placeholder="选择上级" 
          :disabled="disabled" />
      </el-form-item>

      <el-form-item label="监控名称" prop="monitorName">
        <el-input v-model="form.monitorName" placeholder="请输入监控名称" maxlength="64" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="显示顺序" prop="orderNum">
        <el-input v-model="form.orderNum" placeholder="请输入显示顺序"  :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="RTSP地址" prop="rtspUrl">
        <el-input v-model="form.rtspUrl"  show-word-limit maxlength="1024"  placeholder="请输入内容" :readonly="disabled"/>
      </el-form-item>
      <!--
      <el-form-item label="播放地址" prop="playUri">
        <el-input v-model="form.playUri" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" show-word-limit maxlength="1024"  placeholder="请输入内容" :readonly="disabled"/>
      </el-form-item>
      -->
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" resize="none" show-word-limit maxlength="512"  placeholder="请输入内容" :readonly="disabled"/>
      </el-form-item>
	</el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm" v-show="!disabled">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { listCamera, getCamera, addCamera, updateCamera } from "@/api/monitor/camera";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "CameraDetail",
  components: { Treeselect },
  props: {
    monitorId: {
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
  data() {
    return {
      // 栏目树选项
      categoryOptions: [{
        monitorId: 0,
        monitorName: '根监控'
      }],
      form: {},
      rules: {
        parentId: [
          { required: true, message: "父监控不能为空", trigger: ["blur", "change"] }
        ],
        monitorName: [
          { required: true, message: "监控名称不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ],
        rtspUrl: [
          { required: true, message: "RTSP地址不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.loadTree()
    this.loadForm()
  },
  methods: {
    loadTree() {
      listCamera({}).then(response => {
        const result = response.data
        const treeData = this.handleTree(result.data.filter(node => node.parentId == 0), "monitorId");
        this.$set(this.categoryOptions[0], 'children', treeData)
      });
    },
    normalizer(node) {
      if(this.monitorId != 0 && node.monitorId == this.monitorId) {
        return false
      }
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.monitorId,
        label: node.monitorName,
        children: node.children,
      };
    },
    loadForm() {
      this.reset();
      if(this.monitorId > 0) {
        getCamera(this.monitorId).then(response => {
          this.form = response.data;
        });
      }
      if(this.parentId > 0) {
        this.form.parentId = this.parentId;
      }
    },
    reset() {
      this.form = {
        monitorId: null,
        parentId: null,
        ancestors: null,
        monitorName: null,
        orderNum: null,
        rtspUrl: null,
        playUri: null,
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
          if (this.form.monitorId != null) {
            updateCamera(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancel();
            });
          } else {
            addCamera(this.form).then(response => {
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
