<template>
	<div class="app-container">
    <h2>地址地图 - views/tool/example/addressMap.vue</h2>
    <el-button type="primary" @click="openDialog">打 开</el-button>
    <el-descriptions title="地址信息" :column="2" style="margin-top: 15px;">
      <el-descriptions-item label="经度">{{ form.lat }}</el-descriptions-item>
      <el-descriptions-item label="纬度">{{ form.lng }}</el-descriptions-item>
      <el-descriptions-item label="地址详情">{{ form.address }}</el-descriptions-item>
    </el-descriptions>

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <address-map-select ref="AddressMapSelect" :lat="form.lat" :lng="form.lng" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
	name: 'AddressMapPage',
  data() {
    return {
      title: '坐标识取',
      open: false,
      form: {
        address: null,
        lat: null,
        lng: null
      }
    }
  },
  methods: {
    openDialog() {
      this.open = true
    },
    submitForm() {
      const latlng = this.$refs.AddressMapSelect.latlng;
      const address = this.$refs.AddressMapSelect.poiaddress;
      this.form.address = address
      this.form.lat = latlng.lat
      this.form.lng = latlng.lng
      this.open = false;
    },
    cancel() {
      this.open = false
    }
  }

}
</script>