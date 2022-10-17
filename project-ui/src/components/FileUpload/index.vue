<template>
  <div class="upload-file">
    <el-upload
      multiple
      :action="uploadFileUrl"
      :before-upload="handleBeforeUpload"
      :file-list="fileList"
      :limit="limit"
      :on-error="handleUploadError"
      :on-exceed="handleExceed"
      :on-success="handleUploadSuccess"
      :show-file-list="false"
      :headers="headers"
      class="upload-file-uploader"
      ref="upload"
      v-if="!disabled"
    >
      <!-- 上传按钮 -->
      <el-button size="mini" type="primary" v-if="!disabled">选取文件</el-button>
      <!-- 上传提示 -->
      <div class="el-upload__tip" slot="tip" v-if="showTip && !disabled">
        请上传
        <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
        <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
        的文件
      </div>
    </el-upload>

    <!-- 文件列表 -->
    <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
      <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in fileList">
        <!--
        <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
          <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
        </el-link>
        -->
        <el-link @click="filePreview(file.url)" :underline="false" target="_blank">
          <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
        </el-link>
        <div class="ele-upload-list__item-content-action" >
          <el-tooltip class="item" effect="dark" content="下载" placement="top">
            <el-link :underline="false" @click="fileDownload(file.url, file.name)" type="success"><i class="el-icon-download"></i></el-link>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-link :underline="false" @click="handleDelete(index)" type="danger" v-if="!disabled" title="删除"><i class="el-icon-delete"></i></el-link>
          </el-tooltip>
        </div>
      </li>
    </transition-group>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { getAttachmentByUrl } from "@/api/system/attachment"
import axios from "axios"
require("docx-preview")

export default {
  name: "FileUpload",
  props: {
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 5,
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "pdf", "jpg", "jpeg", "bmp", "png"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    },
    disabled: {
      type: Boolean,
      default: false
    },
    bizModel: {
      type: String,
      required: true
    },
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload/" + this.bizModel, // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
      fileDetail: [],
    }
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = { name: item, url: item };
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      // this.isShowTip = !this.disabled
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // 校检文件类型
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        const isTypeOk = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
          return false;
        }
      }
      // 校验文件名是否含有非法字符
      if(file.name.indexOf(",") >= 0) {
        this.$modal.msgError(`文件名含有非法字符 , `);
        return false;
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      // 校验业务模块必填
      if(this.bizModel == null) {
        this.$modal.msgError('业务模块不能为空！');
        return false;
      }
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
      return true;
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError("上传图片失败，请重试");
      this.$modal.closeLoading()
    },
    // 上传成功回调
    handleUploadSuccess(res) {
      const attachment = res.data
      this.uploadList.push({ id: attachment.attachId, name: attachment.fileUrl, url: attachment.fileUrl });
      if (this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 删除文件
    handleDelete(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        const ext = name.slice(name.lastIndexOf("."));
        const actualName = name.slice(name.lastIndexOf("/") + 1, name.lastIndexOf("_"));
        return actualName + ext;
      } else {
        return "";
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    },
    //文件下载
    fileDownload(file, name) {
      axios({
        methods: "get",
        responseType: "blob",
        url: this.baseUrl + file,
      }).then(res => {
        let href = window.URL.createObjectURL(res.data); //创建下载的链接
        let downloadElement = document.createElement('a');
        downloadElement.href = href;
        downloadElement.download = this.getFileName(name); //下载后文件名
        document.body.appendChild(downloadElement);
        downloadElement.click(); //点击下载
        document.body.removeChild(downloadElement); //下载完成移除元素
        window.URL.revokeObjectURL(href); //释放掉blob对象
      })
    },
    // 文件预览
    filePreview(fileUrl) {
      let fileExtension = "";
      if (fileUrl.lastIndexOf(".") > -1) {
        fileExtension = fileUrl.slice(fileUrl.lastIndexOf(".") + 1);
      }
      if(['doc','xls','ppt','pptx','xlsx'].indexOf(fileExtension) >= 0) {
        this.$modal.msgWarning(fileExtension+'格式的文件不支持在线预览，请您下载后进行查看！');
      } else if(fileExtension == 'docx') {
        const fileName = this.getFileName(fileUrl)
        let routeUrl = this.$router.resolve({
          path: "/word-preview",
          query: {fileUrl: fileUrl, fileName: fileName}
        });
        window.open(routeUrl.href)
      } else {
        window.open(this.baseUrl + fileUrl)
      }
    },
    loadFileDetail(fileUrl) {
      getAttachmentByUrl({fileUrl: fileUrl}).then(res => {
        this.fileDetail = [res.data]
      })
    },
  }
};
</script>

<style scoped lang="scss">
.upload-file-uploader {
  margin-bottom: 5px;
}
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}
.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}
.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}
</style>
