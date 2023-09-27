<template>
	<div class="app-container">
		<h2 style="text-align: center">视频监控 - views/tool/example/camera.vue</h2>
    <h3 style="text-align: center" v-text="currentCamera"></h3>
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="head-container">
          <el-tree
            node-key="monitorId"
            highlight-current
            :data="treeData"
            :props="treeProps"
            :expand-on-click-node="false"
            ref="deptTree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="12" v-loading="loading">
        <canvas ref="videoCanvas" @dblclick="fullscreen" style="background-color: #000;" ></canvas>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listCamera, playCamera } from "@/api/monitor/camera"

export default {
	name: 'CameraPage',
	data() {
		return {
      treeData: [],
      treeProps: {
        label: 'monitorName',
        children: 'children',
      },
      loading: true,
      currentNodeId: null,
      currentCamera: '',
      jsMpegPlayer: null,
			isFullScreen: false
		}
	},
  created() {
    this.loadTree()
  },
	methods: {
    loadTree() {
      listCamera({}).then(response => {
        const result = response.data
        this.treeData = this.handleTree(result.data, "monitorId");
        this.loadJS('/static/camera/jsmpeg.js').then(() => {
          this.playMonitor(this.treeData[0])
		    })
      })
    },
    playMonitor(node) {
      if(node.monitorId != this.currentNodeId) {
        this.loading = true;
        this.currentNodeId = node.monitorId
        this.currentCamera = node.monitorName
        playCamera(node).then(res => {
          if(this.jsMpegPlayer != null) {
            this.jsMpegPlayer.destroy();
          }
          this.jsMpegPlayer = new JSMpeg.Player(res.data.wsUrl, {canvas: this.$refs.videoCanvas, audio: false});
          this.loading = false;
        })
      }
    },
    handleNodeClick(node) {
      this.playMonitor(node)
    },
		fullscreen() {
      let canvas = this.$refs.videoCanvas;
      if (this.isFullScreen) {
        this.exitFullscreen();
      } else {
        this.enterFullscreen(canvas);
      }
      this.isFullScreen = !this.isFullScreen;
    },
    // 进入全屏
    enterFullscreen(element) {
      if (element.requestFullscreen) {
          element.requestFullscreen();
      } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
      } else if (element.webkitRequestFullscreen) {
          element.webkitRequestFullscreen();
      } else if (element.msRequestFullscreen) {
          element.msRequestFullscreen();
      }
    },
    exitFullscreen() {
      if (document.exitFullscreen) {
          document.exitFullscreen();
      } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
      } else if (document.webkitExitFullscreen) {
          document.webkitExitFullscreen();
      } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
      }
    }
	}
}
</script>