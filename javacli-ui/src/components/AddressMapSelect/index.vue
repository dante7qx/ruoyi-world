<template>
  <div class="map">
    <iframe id="mapPage" width="100%" height="600px" frameborder=0 :src="getSrc"></iframe>
  </div>
</template>

<script>
export default {
  name: 'index',
  props: {
    mapKey: {
      type: String,
      default: 'G7ZBZ-SQRCU-2OUVB-BXMUT-OZRU3-FXBUU'
    },
    keyName: {
      type: String,
      default: '精灵Spirit开发框架'
    },
    lat: {
      type: [String, Number]
    },
    lng: {
      type: [String, Number]
    }
  },
  data() {
    return {
      address: null,
      latlng: {
        lat: null,
        lng: null,
      }
    }
  },
  mounted() {
    var self = this
    window.addEventListener('message', function(event) {
      // 对于无法识别的地址，直接返回无法选择
      var loc = event.data
      if (loc.poiname === '我的位置' || loc.poiaddress === '') {
        self.$toast('无法识别该地址，请移动地图重新选择')
        return false
      }
      if (loc && loc.module === 'locationPicker') { // 防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
        self.address = loc.poiaddress;
        self.latlng = loc.latlng;
      }
    }, false)
  },
  computed: {
    getSrc() {
      var baseUrl = 'https://apis.map.qq.com/tools/locpicker?search=1&type=1&key=' + this.mapKey + '&referer=' + this.keyName
      if (this.lat && this.lng) {
        baseUrl += `&coord=${this.lat},${this.lng}`
      }
      return baseUrl
    }
  },
}
</script>

<style lang="scss" scoped>
.map {
  width: 100%;
  height: 100%;
}
</style>
