<template>
	<div class="app-container">
    <h2>部门用户选择 - views/tool/example/sysUserSel.vue</h2>
    <div>
      <div>
        <el-button slot="append" type="primary" icon="el-icon-user" @click="showUserSel">人员选择</el-button>
        <div style="margin: 15px; display:inline-block;width: 280px;">{{ selUser }}</div>
      </div>
      <el-divider></el-divider>
      <editor v-model="summary" :disabled="true"/>
    </div>
    <el-dialog :title="title" :visible.sync="open" width="60%" v-dialog-drag append-to-body>
      <el-radio-group v-model="level" @input="levelChange">
        <el-radio-button :label="0">全部部门</el-radio-button>
        <el-radio-button :label="1">部门及下级</el-radio-button>
        <el-radio-button :label="2">本部门</el-radio-button>
        <el-radio-button :label="3">部门及上级</el-radio-button>
      </el-radio-group>
      <el-divider></el-divider>
      <sys-dept-user-select :key="key" :level="level" :multi="true" :userSelFinish="userSelFinish" :userSelCancel="userSelCancel"/>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SysUserSelDemo",
  data() {
    return {
      level: 0,
      title: '用户选择',
      open: false,
      key: '',
      selUser: null,
      summary: `
        <p><span style="font-size: 14pt; font-family: SimHei, STHeiti;">组件说明</span></p>
        <p>一. 概述</p>
        <p>本组件适用于用户选择的场景，页面布局为左（部门）树右（用户列表）表，用户单选或多选，数据权限分为四种情况：</p>
        <ol>
        <li>所有部门</li>
        <li>当前登录部门及下级部门</li>
        <li>当前登录部门</li>
        <li>当前登录部门及上级部门</li>
        </ol>
        <p>二. 使用说明</p>
        <p>组件已注册为全局组件，最简单的使用方式&nbsp;<span style="text-indent: 0px;">&lt;sys-dept-user-select :key="key" /&gt;</span></p>
        <p><span style="text-indent: 0px;">组件参数：</span></p>
        <table style="border-collapse: collapse; width: 700px; height: 450px;" border="1" cellspacing="30">
        <tbody>
        <tr style="height: 432px;">
        <td style="width: 99.7372%; height: 432px;">
        <div style="color: #d4d4d4; background-color: #1e1e1e; font-family: Menlo, Monaco, 'Courier New', monospace; font-size: 12px; line-height: 18px; white-space: pre;">
        <div><span style="color: #6a9955; font-size: 10pt;"> // 部门层级 0: 全部 1: 部门及下级 2: 本部门 3: 部门及上级</span></div>
        <div><span style="font-size: 10pt;"><span style="color: #9cdcfe;">level</span><span style="color: #9cdcfe;">:</span> {</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #4ec9b0;">type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Number</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #9cdcfe;">required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">false</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #9cdcfe;">default</span><span style="color: #9cdcfe;">:</span> <span style="color: #b5cea8;">0</span></span></div>
        <div><span style="font-size: 10pt;">},</span></div>
        <div><span style="color: #6a9955; font-size: 10pt;">// 用户多选（true: 多选 false: 单选）</span></div>
        <div><span style="font-size: 10pt;"><span style="color: #9cdcfe;">multi</span><span style="color: #9cdcfe;">:</span> {</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #4ec9b0;">type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Boolean</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #9cdcfe;">required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">false</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #9cdcfe;">default</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">true</span></span></div>
        <div><span style="font-size: 10pt;">},</span></div>
        <div><span style="color: #6a9955; font-size: 10pt;">// 用户选择完成回调方法</span></div>
        <div><span style="font-size: 10pt;"><span style="color: #9cdcfe;">userSelFinish</span><span style="color: #9cdcfe;">:</span> {</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #4ec9b0;">type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Function</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #9cdcfe;">required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">false</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #dcdcaa;">default</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">function</span> () {}</span></div>
        <div><span style="font-size: 10pt;">},</span></div>
        <div><span style="color: #6a9955; font-size: 10pt;">// 取消用户选择回调方法</span></div>
        <div><span style="font-size: 10pt;"><span style="color: #9cdcfe;">userSelCancel</span><span style="color: #9cdcfe;">:</span> {</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #4ec9b0;">type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Function</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #9cdcfe;">required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">true</span>,</span></div>
        <div style="text-indent: 2em;"><span style="font-size: 10pt;"><span style="color: #dcdcaa;">default</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">function</span> () {}</span></div>
        <div><span style="font-size: 10pt;">},</span></div>
        </div>
        </td>
        </tr>
        <tr style="height: 18px;">
        <td style="width: 99.7372%; height: 18px;">&nbsp;</td>
        </tr>
        </tbody>
        </table>
      `
    }
  },
  methods: {
    showUserSel() {
      this.open = true
      this.key = this.nanoid()
    },
    levelChange(val) {
      this.key = this.nanoid()
    },
    // 组件参数
    userSelFinish(users) {
      if(users != null && users.length > 0) {
        this.selUser = users.map(u => u.nickName).toString()
        this.selUser += ' (' + users.map(u => u.userId).toString() + ')'
      } else {
        this.selUser = null
      }
    },
    userSelCancel() {
      this.open = false
    }

  }

}
</script>