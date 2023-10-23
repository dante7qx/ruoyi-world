<template>
	<div class="app-container">
		<h2>视频播放 - views/tool/example/video.vue</h2>
    <video-monitor :key="nanoid()" :videoSrc="videoSrc" />
  </div>
</template>

<script>
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
			this.videoSrc = 'https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8'
		}
	}
}
</script>