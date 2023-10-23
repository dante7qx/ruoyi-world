<template>
  <div>
  <video
    ref="video"
    class="video-js vjs-default-skin vjs-big-play-centered"
    :width="width"
    :height="height"
    controls
  >
    <source :src="src" />
  </video>
  </div>
</template>
<script>
import videojs from "video.js";
import "video.js/dist/video-js.css";

export default {
  name: "VideoPlayerComponent",
  props: {
    src: {
      type: String, 
      default: '',
      required: true
    },
    width: {
      type: Number,
      default: 600,
      required: false
    },
    height: {
      type: Number,
      default: 400,
      required: false
    }
  },
  
  data() {
    return {
      player: null, // 用来存储当前 video
    };
  },
  mounted() { // 渲染视频
    this.player = videojs(this.$refs.video);
  },
};
</script>

<style>
.video-js .vjs-big-play-button {
  font-size: 2.5em !important;
  line-height: 2.3em !important;
  height: 2.5em !important;
  width: 2.5em !important;
  -webkit-border-radius: 2.5em !important;
  -moz-border-radius: 2.5em !important;
  border-radius: 2.5em !important;
  background-color: #73859f;
  background-color: rgba(115, 133, 159, 0.5) !important;
  border-width: 0.15em !important;
  margin-top: -1.25em !important;
  margin-left: -1.25em !important;
}
.vjs-big-play-button .vjs-icon-placeholder {
  font-size: 1.63em !important;
}
.video-js .vjs-time-control {
  display: block !important;
}
.video-js .vjs-remaining-time {
  display: none !important;
}
</style>
