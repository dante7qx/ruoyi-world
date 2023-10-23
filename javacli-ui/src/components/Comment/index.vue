<template>
  <div class="container">
    <div class="comment" v-for="item in comments" :key="item.commentId">
      <div class="info">
        <img class="avatar" :src="item.fromAvatar ? baseUrl + item.fromAvatar : defaultAvatar" width="36" height="36"/>
        <div class="right">
          <div class="name">{{item.fromName}}</div>
          <div class="date">{{item.commentDate}}</div>
        </div>
      </div>
      <div class="content">
        <span>{{item.content}}</span>
        <br v-if="item.imgUrl || item.videoUrl"/>
        <div class="media">
          <el-image 
            style="width: 100px; height: 100px"
            :src="baseUrl+item.thumbUrl" 
            :preview-src-list="[baseUrl+item.imgUrl]"
            v-if="item.imgUrl">
          </el-image>
          <video-player :src="baseUrl + item.videoUrl" :width="300" :height="100" class="video" v-if="item.videoUrl"/>
        </div>
      </div>
      <div class="control">
        <span class="like" :class="{active: item.isLike}" @click="likeClick(item)">
          <svg-icon class="iconfont" icon-class="like" />
          <span class="like-num">{{item.likeNum > 0 ? item.likeNum + '人赞' : '赞'}}</span>
        </span>
        <span class="comment-reply" @click="showCommentInput(item)">
          <i class="iconfont el-icon-s-comment"></i>
          <span>回复</span>
          <span style="margin-left: 20px;" @click.stop.prevent="handleDelComment(item.commentId)" v-if="curLoginUser == item.fromId || checkRole(['admin'])">
            <i class="iconfont el-icon-delete-solid"></i>
            <span>删除</span>
          </span>
        </span>
      </div>
      <div class="reply">
        <div class="item" v-for="reply in item.reply" :key="reply.replyId">
          <div class="reply-content">
            <span class="from-name">{{reply.fromName}}</span><span>: </span>
            <span class="to-name">@{{reply.toName}}</span>
            <span>{{reply.content}}</span>
          </div>
          <div class="reply-media">
            <el-image 
              style="width: 100px; height: 100px"
              :src="baseUrl+reply.thumbUrl" 
              :preview-src-list="[baseUrl+reply.imgUrl]"
              v-if="reply.imgUrl">
            </el-image>
            <video-player :src="baseUrl + reply.videoUrl" :width="300" :height="100" class="video" v-if="reply.videoUrl"/>
          </div>
          <div class="reply-bottom">
            <span>{{reply.commentDate}}</span>
            <span class="reply-text" @click="showCommentInput(item, reply)">
              <i class="iconfont el-icon-s-comment"></i>
              <span>回复</span>
              <span style="margin-left: 20px;" @click.stop.prevent="handleDelReply(reply.replyId)" v-if="curLoginUser == reply.fromId || checkRole(['admin'])">
                <i class="iconfont el-icon-delete-solid"></i>
                <span>删除</span>
              </span>
            </span>
          </div>
        </div>
        <transition name="fade">
          <div class="input-wrapper" v-if="showItemId == item.commentId">
            <el-input class="gray-bg-input"
              v-model="inputComment"
              type="textarea"
              :rows="3"
              placeholder="请写下您的评论">
            </el-input>
            <div class="btn-upload" v-if="showImg">
              <el-upload
                class="upload-picture"
                :action="uploadImgUrl"
                :headers="headers"
                :on-success="handleUploadImgSuccess"
                :before-upload="handleBeforeUploadImg"
                :on-error="handleUploadError"
                :on-exceed="handleExceed"
                :on-remove="handleRemoveImg"
                :file-list="imgList"
                :limit="1">
                <el-tooltip content="上传图片" placement="top">
                  <i class="el-icon-picture"></i>
                </el-tooltip>
              </el-upload> 
            </div>
            <div class="btn-upload" v-if="showVideo">
              <el-upload
                class="upload-video"
                :action="uploadVideoUrl"
                :headers="headers"
                :on-success="handleUploadVideoSuccess"
                :before-upload="handleBeforeUploadVideo"
                :on-error="handleUploadError"
                :on-exceed="handleExceed"
                :on-remove="handleRemoveVideo"
                :file-list="videoList"
                :limit="1">
                <el-tooltip content="上传视频" placement="top">
                  <i class="el-icon-video-camera-solid"></i>
                </el-tooltip>
              </el-upload>
            </div>
            <div class="btn-control">
              <span class="cancel" @click="cancel">取消</span>
              <el-button class="btn" type="success" round @click="commitComment">确定</el-button>
            </div>
          </div>
        </transition>
      </div>
    </div>
    <div class="newComment" style="margin-top: 10px;">
      <div class="write-reply" @click="addNewComment()" v-if="comments.length > 0">
        <i class="el-icon-edit"></i>
        <span class="add-comment">添加新评论</span>
      </div>
      <transition name="fadeNew">
        <div class="input-wrapper" v-if="newComment">
          <el-input class="gray-bg-input"
            v-model="inputComment"
            type="textarea"
            :rows="3"
            autofocus
            placeholder="请写下您的评论">
          </el-input>
          <div class="btn-upload" v-if="showImg">
            <el-upload
              class="upload-picture"
              :style="imgStyle"
              :action="uploadImgUrl"
              :headers="headers"
              :on-success="handleUploadImgSuccess"
              :before-upload="handleBeforeUploadImg"
              :on-error="handleUploadError"
              :on-exceed="handleExceed"
              :on-remove="handleRemoveImg"
              :file-list="imgList"
              :limit="1">
              <el-tooltip content="上传图片" placement="top">
                <i class="el-icon-picture"></i>
              </el-tooltip>
            </el-upload> 
          </div>
          <div class="btn-upload" v-if="showVideo">
            <el-upload
              class="upload-video"
              :style="videoStyle"
              :action="uploadVideoUrl"
              :headers="headers"
              :on-success="handleUploadVideoSuccess"
              :before-upload="handleBeforeUploadVideo"
              :on-error="handleUploadError"
              :on-exceed="handleExceed"
              :on-remove="handleRemoveVideo"
              :file-list="videoList"
              :limit="1">
              <el-tooltip content="上传视频" placement="top">
                <i class="el-icon-video-camera-solid"></i>
              </el-tooltip>
            </el-upload>
          </div>
          <div class="btn-control">
            <span class="cancel" @click="cancel">取消</span>
            <el-button class="btn" type="success" round @click="commitComment">确定</el-button>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import profile from '@/assets/images/profile.jpg'
import { getToken } from "@/utils/auth"
import { checkRole } from "@/utils/permission"
import { getBizComment, addComment, addCommentReply, delReply, delComment, userLike } from '@/api/system/comment'

export default {
  name: 'Comment',
  props: {
    bizModel: {
      type: String,
      required: true
    },
    bizId: {
      type: Number,
      required: true
    },
    showImg: {
      type: Boolean,
      required: false,
      default: false
    },
    imgFileSize: {
      type: Number,
      required: false,
      default: 1  // 图片大小，默认1Mb
    },
    showVideo: {
      type: Boolean,
      required: false,
      default: false
    },
    videoFileSize: {
      type: Number,
      required: false,
      default: 8  // 视频大小，默认8Mb
    },
  },
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      defaultAvatar: profile,
      curLoginUser: this.$store.state.user.name,
      showItemId: '',
      comments: [],
      curComment: {},
      inputComment: '',
      newComment: false,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload_img_with_thumb",
      imgType: ["png", "jpg", "jpeg", "bmp"],
      uploadVideoUrl: process.env.VUE_APP_BASE_API + "/common/upload_only",
      videoType: ["mp4", "avi"],
      headers: {
        Authorization: "Bearer " + getToken()
      },
      imgUrl: null,
      thumbnail: null,
      imgList: [],
      videoUrl: null,
      videoList: [],
    }
  },
  computed: {
    imgStyle: function () {
      return {
        position: 'relative',
        top: this.videoUrl && !this.imgUrl ? '-38px' : '0px'
      }
    },
    videoStyle: function () {
      return {
        position: 'relative',
        top: this.imgUrl && !this.videoUrl ? '-38px' : '0px'
      }
    }
  },
  created() {
    this.loadComment(this.bizModel, this.bizId);
  },
  methods: {
    checkRole,
    // 加载评论数组
    loadComment(bizModel, bizId) {
      getBizComment(bizModel, bizId).then(res => {
        this.comments = res.data
        if(this.comments.length == 0) {
          this.newComment = true
        }
      })
    },
    // 点赞
    likeClick(item) {
      if (item.isLike === null) {
        Vue.$set(item, "isLike", true);
      } else {
        item.isLike = !item.isLike;
      }
      userLike(item.commentId).then(res => {
        this.loadComment(this.bizModel, this.bizId)
      })
    },   
    // 点击评论按钮显示输入框
    showCommentInput(item, reply) {
      this.newComment = false
      this.imgUrl = null
      this.thumbnail = null
      this.imgList = []
      this.videoUrl = null
      this.videoList = []
      if (reply) {
        this.inputComment = "@" + reply.fromName + " "
        this.curComment = reply
      } else {
        this.inputComment = ''
        this.curComment = item
      }
      this.showItemId = item.commentId
    },
    // 添加新评论
    addNewComment() {
      this.newComment = true
      this.showItemId = ''
      this.inputComment = ''
      this.imgUrl = null
      this.thumbnail = null
      this.imgList = []
      this.videoUrl = null
      this.videoList = []
    },
    // 提交评论
    commitComment() {
      if(!this.inputComment) {
        this.$modal.msgError("请填写您的评论信息！");
        return false;
      }
      let commentData = {
        bizModel: this.bizModel,
        bizId: this.bizId,
        content: this.inputComment,
        imgUrl: this.imgUrl,
        thumbUrl: this.thumbnail,
        videoUrl: this.videoUrl
      }
      if(this.newComment) {
        addComment(commentData).then(res => {
          this.loadComment(this.bizModel, this.bizId)
          this.clearFrom()
          this.$modal.msgSuccess("恭喜你，评论成功！")
        })
      } else {
        const commentPrefix = "@" + this.curComment.fromName + " "
        if(!this.inputComment || this.inputComment.trim() == commentPrefix.trim()) {
          this.$modal.msgError("请填写您的评论信息！");
          return false;
        }
        commentData['commentId'] = this.curComment.commentId
        commentData['toId'] = this.curComment.fromId
        commentData['content'] = this.inputComment.replace(commentPrefix.trim(), "")
        commentData['imgUrl'] = this.imgUrl
        commentData['thumbUrl'] = this.thumbnail
        commentData['videoUrl'] = this.videoUrl
        addCommentReply(commentData).then(res => {
          this.loadComment(this.bizModel, this.bizId)
          this.clearFrom()
          this.$modal.msgSuccess("恭喜你，评论成功！")
        })
      }
    },
    // 点击取消按钮
    cancel() {
      this.showItemId = ''
      this.inputComment = ''
      this.imgUrl = null
      this.thumbnail = null
      this.imgList = []
      this.videoUrl = null
      this.videoList = []
      this.newComment = this.comments.length > 0 ? false : true
    },
    // 删除评论
    handleDelComment(commentId) {
      delComment(commentId).then(res => {
        this.loadComment(this.bizModel, this.bizId)
        this.clearFrom()
        this.$modal.msgSuccess("删除成功！")
      })
    },
    // 删除评论回复
    handleDelReply(replyId) {
      delReply(replyId).then(res => {
        this.loadComment(this.bizModel, this.bizId)
        this.clearFrom()
        this.$modal.msgSuccess("删除成功！")
      })
    },
    clearFrom() {
      this.curComment = {}
      this.inputComment = ''
      this.showItemId = ''
      this.newComment = false
      this.imgUrl = null
      this.thumbnail = null
      this.imgList = []
      this.videoUrl = null
      this.videoList = []
    },
    handleBeforeUploadImg(file) {
      let isImg = false;
      if (this.imgType.length) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        isImg = this.imgType.some(type => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
      } else {
        isImg = file.type.indexOf("image") > -1;
      }

      if (!isImg) {
        this.$modal.msgError(`文件格式不正确, 请上传${this.imgType.join("/")}图片格式文件!`);
        return false;
      }
      if (this.imgFileSize) {
        const isLt = file.size / 1024 / 1024 < this.imgFileSize;
        if (!isLt) {
          this.$modal.msgError(`上传图片大小不能超过 ${this.imgFileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传图片，请稍候...");
      this.number++;
    },
    handleUploadImgSuccess(res) {
      this.$modal.closeLoading();
      this.$modal.msgSuccess("上传成功！")
      this.imgUrl = res.fileName
      this.thumbnail = res.thumbnailName
      this.imgList = [{name: res.originalFilename, url: res.fileName}]
    },
    handleRemoveImg(file, fileList) {
      this.imgUrl = null
      this.thumbnail = null 
      this.imgList = []
    },
    handleBeforeUploadVideo(file) {
      let isVideo = false;
      if (this.videoType.length) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        isVideo = this.videoType.some(type => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
      } else {
        isVideo = file.type.indexOf("video") > -1;
      }

      if (!isVideo) {
        this.$modal.msgError(`文件格式不正确, 请上传${this.videoType.join("/")}视频格式文件!`);
        return false;
      }
      if (this.videoFileSize) {
        const isLt = file.size / 1024 / 1024 < this.videoFileSize;
        if (!isLt) {
          this.$modal.msgError(`上传视频大小不能超过 ${this.videoFileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传视频，请稍候...");
      this.number++;
    },
    handleUploadVideoSuccess(res) {
      this.$modal.closeLoading();
      this.$modal.msgSuccess("上传成功！")
      this.videoUrl = res.fileName
      this.videoList = [{name: res.originalFilename, url: res.fileName}]
    },
    handleRemoveVideo(file, fileList) {
      this.videoUrl = null
      this.videoList = []
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError("上传文件数量不能超过1个!");
    },
    // 上传失败
    handleUploadError() {
      this.$modal.msgError("上传失败，请重试");
      this.$modal.closeLoading();
    }
  }
}
</script>

<style scoped lang="scss">
.container {
  padding: 0 10px;
  box-sizing: border-box;
  .comment {
    display: flex;
    flex-direction: column;
    padding: 10px;
    border-bottom: 1px solid #F2F6FC;
    .info {
      display: flex;
      align-items: center;
      .avatar {
        border-radius: 50%;
      }
      .right {
        display: flex;
        flex-direction: column;
        margin-left: 10px;
        .name {
          font-size: 16px;
          color: #303133;
          margin-bottom: 5px;
          font-weight: 500;
        }
        .date {
          font-size: 12px;
          color: #909399;
        }
      }
    }
    .content {
      font-size: 16px;
      color: #303133;
      line-height: 20px;
      padding: 10px 0;
      float: left;
      .media {
        float: left;
        margin-top: 5px;
        display: inline-flex;
        .video {
          margin-left: 10px;
        }
      }
    }
    .control {
      display: flex;
      align-items: center;
      font-size: 14px;
      color: #909399;
      .like {
        display: flex;
        align-items: center;
        margin-right: 20px;
        cursor: pointer;
        &.active, &:hover {
          color: #409EFF;
        }
        .iconfont {
          font-size: 14px;
          margin-right: 5px;
        }
      }
      .comment-reply {
        display: flex;
        align-items: center;
        cursor: pointer;
        &:hover {
          color: #333;
        }
        .iconfont {
          font-size: 16px;
          margin-right: 5px;
        }
      }

    }
    .reply {
      margin: 10px 0;
      border-left: 2px solid #DCDFE6;
      .item {
        margin: 0 10px;
        padding: 10px 0;
        border-bottom: 1px dashed #EBEEF5;
        .reply-content {
          display: flex;
          align-items: center;
          font-size: 14px;
          color: #303133;
          .from-name {
            color: #409EFF;
          }
          .to-name {
            color: #409EFF;
            margin-left: 5px;
            margin-right: 5px;
          } 
        }
        .reply-media {
          margin-top: 5px;
          margin-bottom: 5px;
          display: inline-flex;
          .video {
            margin-left: 10px;
          }
        }
        .reply-bottom {
          display: flex;
          align-items: center;
          margin-top: 6px;
          font-size: 12px;
          color: #909399;
          .reply-text {
            display: flex;
            align-items: center;
            margin-left: 10px;
            cursor: pointer;
            &:hover {
              color: #333;
            }
            .icon-comment {
              margin-right: 5px;
            }
          }
        }
      }
      .write-reply {
        display: flex;
        align-items: center;
        font-size: 14px;
        color: #909399;
        padding: 10px;
        cursor: pointer;
        &:hover {
          color: #303133;
        }
        .el-icon-edit {
          margin-right: 5px;
        }
      }
      .fade-enter-active, fade-leave-active {
        transition: opacity 0.5s;
      }
      .fade-enter, .fade-leave-to {
        opacity: 0;
      }
      .input-wrapper {
        padding: 10px;
        .btn-control {
          display: flex;
          justify-content: flex-end;
          align-items: center;
          padding-top: 10px;
          .cancel {
            font-size: 16px;
            color: #606266;
            margin-right: 20px;
            cursor: pointer;
            &:hover {
              color: #333;
            }
          }
          .confirm {
            font-size: 16px;
          }
        }
        .btn-upload {
          margin-top: 6px; 
          margin-left: 10px; 
          display: inline-block;
        }
      }
    }
  }
}

.newComment {
  padding: 0 10px;
  box-sizing: border-box;
  .write-reply {
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #909399;
    padding: 10px;
    cursor: pointer;
    &:hover {
      color: #303133;
    }
    .el-icon-edit {
      margin-right: 5px;
    }
  }
  .input-wrapper {
    padding: 10px;
    .btn-control {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      padding-top: 10px;
      .cancel {
        font-size: 16px;
        color: #606266;
        margin-right: 20px;
        cursor: pointer;
        &:hover {
          color: #333;
        }
      }
      .confirm {
        font-size: 16px;
      }
    }
    .btn-upload {
      margin-top: 6px; 
      margin-left: 10px; 
      display: inline-block;
    }
  }

}

.upload-video {
  position: relative;
  // top: -38px;

}
</style>