<template>
	<div class="app-container">
		<h2>视频监控 - views/tool/example/video.vue</h2>
    <video-player :key="nanoid()" :videoSrc="videoSrc" />
  </div>
</template>

<script>
import { fetchData } from '@/api/tool/codeExample.js'

export default {
	name: 'VideoPlayerPage',
	data() {
		return {
			videoSrc: ''
		}
	},
	created() {
		
	},
  mounted() {
    this.loadData()
  },
	methods: {
		loadData() {
			fetchData('http://36.137.3.207:9000/index?name=bwg').then(res => {
        const result = res.data
        if(result.error == 200) {
          // this.videoSrc = result.data[0].url
          this.videoSrc = 'https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8'
        }
      })
		}
	}
}
</script>