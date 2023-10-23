<template>
  <div>
    <video id="videoArea" style="width: 100%;height: auto;" class="video-js vjs-default-skin" preload="auto">
      <source :src="src" type="application/x-mpegURL">
    </video>
  </div>
</template>

<script>
import videojs from 'video.js'
import 'videojs-contrib-hls'
import "@videojs/http-streaming"

export default {
  name: "VideoComponent",
  props: {
    videoSrc: {
      type: String, 
      default: '',
      required: true
    },
    autoplay: {
      type: Boolean,
      default: true,
      required: false
    },
    controls: {
      type: Boolean,
      default: false,
      required: false
    },

  },
  data() {
    return {
      src: '',
      player: null,
    }
  },
  mounted() {
    this.init(100)
  },
  methods: {
    init(delay) {
			const that = this;
			setTimeout(function() {
				if(that.videoSrc != '') {
          that.initVideo();
					return;
				}
      	that.init(delay);
  		}, delay);
		},
    initVideo() {
      this.src = this.videoSrc
      // 播放器初始化
      const that = this
      this.$nextTick(() => {
        this.player = videojs('videoArea' , {
          autoplay: that.autoplay,
          controls: that.controls,
          bigPlayButton: false,
          textTrackDisplay: false,
          posterImage: true,
          errorDisplay: false,
          controlBar: false,
          muted: true //静音模式 、、 解决首次页面加载播放。
        }, function () {
          // 自动播放
          if(that.autoplay) {
            this.play()
          }
        })
      })
    },
    
  },
  // 离开页面销毁视频播放器
  beforeDestroy() {
    if (this.player != null) {
      this.player.dispose() // dispose()会直接删除Dom元素
    }
  }
}
</script>
<style scoped>

</style>