<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入标题" maxlength="128" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="副标题" prop="subTitle">
        <el-input v-model="form.subTitle" placeholder="请输入副标题" maxlength="64" show-word-limit :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择类型" :disabled="disabled">
          <el-option
            v-for="dict in dict.type.sys_info_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"            
            style="width: 100%;"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="封面">
        <image-upload v-model="form.cover" :limit="1" :fileSize="1" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <editor v-model="form.content" :disabled="disabled"/>
      </el-form-item>
      
	  </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="submitForm('0')" v-show="!disabled && !showApproval">确 定</el-button>
      <el-button type="success" @click="submitForm('1')" v-show="!disabled && !showApproval">提 交</el-button>
      <el-button type="primary" @click="approval(true)" v-show="showApproval">通 过</el-button>
      <el-button type="danger" @click="approval(false)" v-show="showApproval" >驳 回</el-button>
      <el-button @click="cancelApproval">取 消</el-button>
    </div>

    <!-- 审批对话框-->
    <el-dialog title="审批" :visible.sync="openApproval" width="600px" append-to-body>
      <el-form ref="formApproval" :model="formApproval" :rules="rulesApproval" label-width="100px">
        <el-form-item label="发布时间" prop="publishTime" v-if="pass">
          <el-date-picker clearable
            v-model="formApproval.publishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间"
            @input="changePublishTime($event)"
            style="width: 100%;">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批意见" prop="comment">
          <el-input v-model="formApproval.comment" type="textarea" :autosize="{ minRows: 4, maxRows: 6}" resize="none" show-word-limit maxlength="200"  placeholder="请输入审批意见" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right;">
        <el-button type="primary" @click="approvalSubmit">确 定</el-button>
        <el-button @click="cancelApproval">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getInfo, addInfo, updateInfo, batchApproval } from "@/api/system/info";

export default {
  name: "InfoDetail",
  props: {
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
  dicts: ['sys_info_type'],
  data() {
    return {
      form: {},
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "内容不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ]
      },
      showApproval: false,
      // 审批窗口
      openApproval: false,
      formApproval: {},
      rulesApproval: {
        publishTime: [
          { required: true, message: "发布时间不能为空", trigger: "change" }
        ],
        comment: [
          { required: true, message: "审批意见不能为空", trigger: "blur" }
        ],
      },
      pass: false
    };
  },
  created() {
    this.loadForm()
  },
  methods: {
    loadForm() {
      this.reset();
      if(this.infoId > 0) {
        getInfo(this.infoId).then(response => {
          this.form = response.data;
          if(this.form.status == '1') {
            this.showApproval = true
          }
        });
      }
    },
    reset() {
      this.form = {
        infoId: null,
        title: null,
        subTitle: null,
        content: null,
        type: null,
        setTop: 0,
        anonymous: null,
        publishTime: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    submitForm(status) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.status = status
          if (this.form.infoId != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess(status == '0' ? "修改成功" : status == '1' ? '提交成功' : '发布成功');
              this.cancel();
            });
          } else {
            addInfo(this.form).then(response => {
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
    changePublishTime(e){
			this.$forceUpdate()
			this.formApproval.publishTime = e
		},
    approval(pass) {
      this.openApproval = true;
      this.pass = pass;
      if(this.pass) {
        this.formApproval.publishTime = this.$moment(new Date()).format("YYYY-MM-DD")
      }
    },
    approvalSubmit() {
      this.$refs["formApproval"].validate(valid => {
        if (valid) {
          this.formApproval.ids = [this.form.infoId]
          this.formApproval.approval = this.pass
          batchApproval(this.formApproval).then(res => {
            this.$modal.msgSuccess(this.pass ? "发布成功" : "驳回成功");
            this.cancelApproval();
          })
        }
      });
    },
    cancelApproval() {
      this.openApproval = false;
      this.pass = false;
      this.$emit('closeWindow');
    }
  }
};
</script>
