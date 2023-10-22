<template>
	<div class="app-container">
    <h2>语音播报 - views/tool/example/voicePodcast.vue</h2>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="文字">
        <el-input type="textarea" v-model="form.txt" placeholder="播报文字" :autosize="{ minRows: 10, maxRows: 180}" resize="none"></el-input>
      </el-form-item>
      <el-form-item label="选择语言" v-if="supportSpeechSynthesis">
        <el-select v-model="form.voice" filterable placeholder="请选择" style="width: 500px;">
          <el-option
            v-for="item in voiceOptions"
            :key="item.voiceURI"
            :label="item.voiceURI + ' [' + item.lang + ']'"
            :value="item.voiceURI">
          </el-option>
        </el-select>
      </el-form-item>
      <div style="text-align: center" v-if="supportSpeechSynthesis">
        <el-button type="success" icon="el-icon-video-play" @click="playVoice" v-if="!speaking">播放</el-button>
        <el-button type="danger" icon="el-icon-video-pause" @click="pauseVoice" v-else="speaking">暂停</el-button>
        <el-button type="info" icon="el-icon-refresh" @click="cancelVoice">停止</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "VoicePodCast",
  data() {
    return {
      synth: null,
      utterance: null,
      supportSpeechSynthesis: false,
      voiceOptions:[],
      played: false,
      paused: false,
      form: {
        voice: '',
        txt: ''
      }, 
      initTxt: `鹧鸪天

道隐隐，无迹可寻。只在心中，有时浮现。
问谁能识，谁能明。只有自己，才能体验。

道淡淡，无欲无求。只在自然，有时显现。
问谁能得，谁能修。只有圣人，才能成就。

道高高，无上无下。只在天地，有时运转。
问谁能通，谁能达。只有神仙，才能参透。

道幽幽，无始无终。只在无中，有时生灭。
问谁能说，谁能听。只有无我，才能悟道。`
    };
  },
  computed: {
    speaking: function () {
      return this.played
    }
  },
  created() {
    this.form.txt = this.initTxt
    if('speechSynthesis' in window) {
      this.supportSpeechSynthesis = true;
      this.synth = window.speechSynthesis;
      this.utterance = new SpeechSynthesisUtterance();
      this.loadVoices();
      this.utterance.onboundary = (event) => {
        console.log(event)
      };
      this.utterance.onstart = (event) => {
        this.played = true;
        this.paused = false;
      };
      this.utterance.onpause = (event) => {
        this.played = false;
        this.paused = true;
      };
      this.utterance.onresume = (event) => {
        this.played = true;
        this.paused = false;
      };
      this.utterance.onend = (event) => {
        this.played = false
        console.log(`一共耗时：${event.elapsedTime / 1000} 秒`)
      };
    }
  },
  methods: {
    loadVoices() {
      const that = this
      this.voiceOptions = this.synth.getVoices();
      setTimeout(function() {
        if(that.voiceOptions.length == 0) {
          that.loadVoices()
        }
      }, 10)
    },
    playVoice() {
      if(this.paused) {
        this.synth.resume();    // 播放
      } else {
        if(this.form.txt) {
          this.handleSpeak(this.form.txt) // 传入需要播放的文字
        }
      }
    },
    pauseVoice() {
      this.synth.pause();
    },
    cancelVoice() {
      this.synth.cancel(this.utterance);
      this.played = false;
      this.paused = false;
    },
    handleSpeak(text) {
      this.utterance.text = text;     
      this.utterance.lang = "zh-CN";  // 使用的语言:中文
      this.utterance.volume = 1;      // 声音音量：1
      this.utterance.rate = 1;        // 语速：1
      this.utterance.pitch = 1;       // 音高：1
      if(this.form.voice) {
        this.utterance.voice = this.voiceOptions.filter(item => item.voiceURI == this.form.voice)[0]
      }
      this.synth.speak(this.utterance);    // 播放
    }
  }
}

</script>