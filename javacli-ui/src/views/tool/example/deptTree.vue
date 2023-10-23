<template>
	<div class="app-container">
		<h2>部门树 - views/tool/example/deptTree.vue</h2>
    <el-row :gutter="20">
      <el-col :span="24" v-show="false">
        <el-radio-group v-model="deptLevel" @input="levelChange">
          <el-radio-button :label="0">全部部门</el-radio-button>
          <el-radio-button :label="1">部门及下级</el-radio-button>
          <el-radio-button :label="2">本部门</el-radio-button>
          <el-radio-button :label="3">部门及上级</el-radio-button>
        </el-radio-group>
        <el-divider></el-divider>
      </el-col>
    </el-row>
    <!-- 无面板分割-->
    <!--
    <el-row :gutter="20">
      <el-col :span="5">
        <sys-dept-tree :key="deptLevel" :level="deptLevel" :maxHeight="700" :deptSelected="listDeptInfo"/>
      </el-col>
      <el-col :span="19">
        已选部门：{{ `${deptTreeNodeId} - ${JSON.stringify(deptTreeNode)}` }}
        <div>
          <el-divider></el-divider>
          <editor v-model="summary" :disabled="true"/>
        </div>
      </el-col>
    </el-row>
    -->
    <splitpanes vertical>
      <pane size="20" min-size="20">
        <sys-dept-tree :key="deptLevel" :level="deptLevel" :maxHeight="700" :deptSelected="listDeptInfo"/>
      </pane>
      <pane>
        已选部门：{{ `${deptTreeNodeId} - ${JSON.stringify(deptTreeNode)}` }}
        <div>
          <el-divider></el-divider>
          <editor v-model="summary" :disabled="true"/>
        </div>
      </pane>
    </splitpanes>
    
  </div>
</template>

<script>
import { Splitpanes, Pane } from 'splitpanes'
import 'splitpanes/dist/splitpanes.css'

export default {
	name: 'DeptTreeDemo',
  components: { Splitpanes, Pane },
  data() {
		return {
      summary: `
      <p><span style="font-size: 14pt; font-family: SimHei, STHeiti;">组件说明</span></p>
      <p><span style="font-size: 14pt; font-family: SimHei, STHeiti;">组件说明</span></p>
      <p>一. 概述</p>
      <p>本组件适用于部门树的场景，页面布局为左（部门树）树右（业务），数据权限分为四种情况：</p>
      <ol>
      <li>所有部门</li>
      <li>当前登录部门及下级部门</li>
      <li>当前登录部门</li>
      <li>当前登录部门及上级部门</li>
      </ol>
      <p>二. 使用说明</p>
      <p>组件已注册为全局组件，最简单的使用方式 &lt;sys-dept-tree /&gt;，全部参数 &lt;sys-dept-tree :level="deptLevel" :deptSelected="listDeptInfo"/&gt;</p>
      <p>组件参数</p>
      <div style="color: #d4d4d4; background-color: #1e1e1e; font-family: Menlo, Monaco, 'Courier New', monospace; font-size: 12px; line-height: 18px; white-space: pre;">
      <div><span style="color: #9cdcfe;">props</span><span style="color: #9cdcfe;">:</span> {</div>
      <div style="text-indent: 2em;"><span style="color: #6a9955;">// 部门层级 0: 全部 1: 部门及下级 2: 本部门 3: 部门及上级</span></div>
      <div style="text-indent: 2em;"><span style="color: #9cdcfe;">level</span><span style="color: #9cdcfe;">:</span> {</div>
      <div style="text-indent: 2em;"><span style="color: #4ec9b0;"> type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Number</span>,</div>
      <div style="text-indent: 2em;"><span style="color: #9cdcfe;"> required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">false</span>,</div>
      <div style="text-indent: 2em;"><span style="color: #9cdcfe;"> default</span><span style="color: #9cdcfe;">:</span> <span style="color: #b5cea8;">0</span></div>
      <div style="text-indent: 2em;">},</div>
      <div style="text-indent: 2em;"><span style="color: #6a9955;">// 部门树节点点击回调方法</span></div>
      <div style="text-indent: 2em;"><span style="color: #9cdcfe;">deptSelected</span><span style="color: #9cdcfe;">:</span> {</div>
      <div style="text-indent: 2em;"><span style="color: #4ec9b0;"> type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Function</span>,</div>
      <div style="text-indent: 2em;"><span style="color: #9cdcfe;"> required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">false</span>,</div>
      <div style="text-indent: 2em;"><span style="color: #9cdcfe;"> default</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">null</span></div>
      <div style="text-indent: 2em;">},</div>
      <div style="text-indent: 2em;">
      <div style="line-height: 18px;">
      <div><span style="color: #6a9955;">// 是否部门树加载后触发点击事件</span></div>
      <div><span style="color: #9cdcfe;">clickOnload</span><span style="color: #9cdcfe;">:</span> {</div>
      <div><span style="color: #4ec9b0;"> type</span><span style="color: #9cdcfe;">:</span> <span style="color: #4ec9b0;">Boolean</span>,</div>
      <div><span style="color: #9cdcfe;"> required</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">false</span>,</div>
      <div><span style="color: #9cdcfe;"> default</span><span style="color: #9cdcfe;">:</span> <span style="color: #569cd6;">true</span></div>
      <div>}</div>
      </div>
      </div>
      <div>},</div>
      </div>
      `,
      deptLevel: 0,
      deptTreeNodeId: null,
      deptTreeNode: null
    }
  },
  methods: {
    levelChange(level) {
      this.deptLevel = level;
    },
    listDeptInfo(deptId, deptNode) {
      this.deptTreeNodeId = deptId;
      this.deptTreeNode = deptNode;
    }
  }
}


</script>