<template>
  <div>
    <el-dialog :title="title" :visible.sync="open" width="1000px" @close="" v-dialog-drag append-to-body>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-tree
            node-key="queryId"
            highlight-current
            :data="templateData"
            :props="templateProps"
            :expand-on-click-node="false"
            ref="templateTree"
            default-expand-all
            :render-content="renderTree"
          />
        </el-col>
        <el-col :span="18">
          <el-tabs type="border-card" v-model="activeTab" @tab-click="handleTabClick">
            <el-tab-pane label="模版条件" name="cond" lazy>
              <el-table ref="condTable" :data="columns" row-key="colName" :max-height="tableHeight" v-if="templateId > 0">
                <el-table-column type="index" label="序号" width="50" align="center" />
                <el-table-column label="字段列名" prop="colName" min-width="10%" v-if="false" />
                <el-table-column label="显示名称" min-width="10%" align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.colDesc"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="java属性" min-width="10%" v-if="false">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.javaField"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="查询" min-width="5%" align="center">
                  <template slot-scope="scope">
                    <el-checkbox :true-label="1" :false-label="0" v-model="scope.row.queryFlag"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column label="查询方式" min-width="10%" align="center">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.queryType" v-if="scope.row.javaType == 'String'">
                      <el-option
                        v-for="dict in strOperOptions"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"/>
                    </el-select>
                    <el-select v-model="scope.row.queryType" v-else-if="scope.row.javaType == 'Date'">
                      <el-option
                        v-for="dict in dateOperOptions"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"/>
                    </el-select>
                    <el-select v-model="scope.row.queryType" v-else-if="scope.row.javaType == 'Integer'">
                      <el-option
                        v-for="dict in numberOperOptions"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"/>
                    </el-select>
                    <span v-else></span>
                  </template>
                </el-table-column>
                <el-table-column label="字典类型" min-width="12%" align="center">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.dictType" clearable filterable placeholder="请选择" :disabled="scope.row.javaType != 'String'">
                      <el-option
                        v-for="dict in dictTypeOptions"
                        :key="dict.dictType"
                        :label="dict.dictName"
                        :value="dict.dictType">
                        <span style="float: left">{{ dict.dictName }}</span>
                      </el-option>
                    </el-select>
                  </template>
                </el-table-column>
              </el-table>
              <div v-else><span>请添加或选择查询模板！</span></div>
            </el-tab-pane>
            <el-tab-pane label="查询表单" name="qfrom" lazy>
              <div v-if="queryColumns.length > 0">
                <el-form ref="queryForm" :model="queryForm" label-width="100px">
                  <el-form-item :label="column.colDesc" :prop="column.javaField" v-for="column in queryColumns" :key="column.condId">
                    <el-select  
                      v-if="column.dictType && column.dictType != ''"
                      v-model="queryForm[column.javaField]"
                      :placeholder="'请选择' + column.colDesc" 
                      clearable 
                      style="width: 240px"
                      >
                      <el-option
                        v-for="dict in dictOptions[`${column.dictType}`]"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      />
                    </el-select>
                    <el-input 
                      v-else-if="column.javaType == 'String'"
                      v-model="queryForm[column.javaField]"
                      :placeholder="'请输入' + column.colDesc"  
                      clearable 
                      style="width: 240px"
                      />
                    <el-input 
                      v-else-if="column.javaType == 'Integer'"
                      v-model.number="queryForm[column.javaField]"
                      :placeholder="'请输入' + column.colDesc"  
                      clearable 
                      style="width: 240px"
                      />
                    <el-date-picker
                      v-else-if="column.javaType == 'Date' && column.queryType != 'BETWEEN'"
                      v-model="queryForm[column.javaField]"
                      value-format="yyyy-MM-dd"
                      type="date"
                      :placeholder="'请选择' + column.colDesc"  
                      clearable 
                      @input="$forceUpdate()"
                      style="width: 240px"
                    />
                    <el-date-picker
                      v-else-if="column.javaType == 'Date' && column.queryType == 'BETWEEN'"
                      v-model="queryForm[column.javaField]"
                      value-format="yyyy-MM-dd"
                      type="daterange"
                      range-separator="-"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间"
                      clearable 
                      style="width: 240px"
                    />
                    <span v-else>暂不支持</span>
                  </el-form-item>
                </el-form>
                <div style="margin-left: 200px;">
                  <el-button type="primary" @click="queryAdv">查 询</el-button>
                  <el-button @click="cancelAdv">重 置</el-button>
                </div>
              </div>
              <div v-else><span>未设置模板条件！</span></div>
            </el-tab-pane>
          </el-tabs>
          <el-form label-width="100px" v-if="condBtnShow && templateId>0">
            <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
              <el-button type="primary" @click="submitCond">保 存</el-button>
              <el-button @click="handleClose">返 回</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 新增查询模板 -->
    <el-dialog title="新增查询模板" :visible.sync="openNewTemplate" width="600px" @close="" v-dialog-drag append-to-body>
      <el-form :inline="true" ref="formTemplate" :model="formTemplate" :rules="rulesTemplate" label-width="100px">
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="formTemplate.templateName" placeholder="请输入模板名称" maxlength="64" show-word-limit style="width: 240px" />
        </el-form-item>
        <el-button type="primary" @click="submitTemplate">确 定</el-button>
        <el-button @click="cancelTemplate">取 消</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";
import { listCaq, addCaq, updateCaq, delCaq, getCaqCond, addCaqConds } from "@/api/system/caq";

export default {
  name: "CustAdvQuery",
  props: {
    tableName: {
      type: String,
      required: true,
    },
    tableAlias: {
      type: String,
      required: true,
    },
    searchFunc: {
      type: Function,
      required: true,
      default: function () {}
    }
  },
  data() {
    return {
      tableHeight: document.documentElement.scrollHeight - 245 + "px",
      activeTab: 'cond',
      dictTypeOptions: [],
      strOperOptions: [{
        value: '=',
        label: '等于'
      }, {
        value: '!=',
        label: '不等于'
      }, {
        value: 'LIKE',
        label: '模糊匹配'
      }],
      numberOperOptions: [{
        value: '=',
        label: '等于'
      }, {
        value: '!=',
        label: '不等于'
      }, {
        value: '&gt;',
        label: '大于'
      }, {
        value: '&gt;=',
        label: '大于等于'
      }, {
        value: '&lt;',
        label: '小于'
      }, {
        value: '&lt;=',
        label: '小于等于'
      }],
      dateOperOptions: [{
        value: '=',
        label: '等于'
      }, {
        value: '&gt;',
        label: '大于'
      }, {
        value: '&gt;=',
        label: '大于等于'
      }, {
        value: '&lt;',
        label: '小于'
      }, {
        value: '&lt;=',
        label: '小于等于'
      }, {
        value: 'BETWEEN',
        label: '日期范围'
      }],
      title: '自定义查询',
      open: true,
      openNewTemplate: false,
      templateData: [{queryId: 0, templateName: '所有模板', children: []}],
      templateProps: {
        children: 'children',
        label: 'templateName'
      },
      templateId: 0,
      formTemplate: {},
      rulesTemplate: {
        templateName: [
          { required: true, message: "模板名称不能为空", trigger: "blur" }
        ],
      },
      condBtnShow: true,
      columns: [],
      queryForm: {},
      queryColumns: [],
      dictOptions: {},
    };
  },
  created() {
    this.loadTemplateTree();
    getDictOptionselect().then(response => {
      this.dictTypeOptions = response.data;
    });
  },
  methods: {
    loadTemplateTree() {
      const param = {tableName: this.tableName, pageSize: 100}
      listCaq(param).then(response => {
        this.templateData[0].children = response.rows
        if(response.rows && response.rows.length > 0) {
          this.selectFirstNode(response.rows[0].queryId)
        }
      });
    },
    renderTree(h, { node, data, store }) {
      if(data.queryId > 0) {
        return (
        <span>
          <span on-click={ () => this.nodeClick(data.queryId) }>{node.label}</span>
          <span v-show="data.queryId > 0" style="margin-left: 20px;">
            <i class="el-icon-edit" title="编辑" on-click={ () => this.editTemplate(node) }></i>
            <i class="el-icon-delete" title="删除" on-click={ () => this.removeTemplate(data.queryId) } style="margin-left: 10px;"></i>
          </span>
        </span>);
      } else {
        return (
          <span>
            <span on-click={ () => this.nodeClick(data.queryId) }>{node.label}</span>
            <span v-show="data.queryId > 0" style="margin-left: 20px;">
            <i class="el-icon-plus" title="新增模板" on-click={ () => this.addTemplate() }></i>
            </span>
          </span>);
      }
    },
    nodeClick(queryId) {
      this.templateId = queryId
      if(queryId == 0) {
        this.columns = []
        this.queryColumns = []
      } else {
        this.loadCond(queryId)
      }
    },
    setCurrentNode(queryId) {
      this.$nextTick(() => {
        this.$refs.templateTree.setCurrentKey(queryId);
        this.nodeClick(queryId);
      })
    },
    selectFirstNode(queryId) {
      this.setCurrentNode(queryId)
      this.activeTab = 'qfrom';
      this.condBtnShow = false;
    },
    addTemplate() {
      this.openNewTemplate = true;
    },
    editTemplate(node) {
      this.openNewTemplate = true;
      this.formTemplate = node.data
    },
    removeTemplate(queryId) {
      this.$modal.confirm('是否确认删除？').then(function() {
        return delCaq(queryId);
      }).then(() => {
        this.columns = []
        this.queryColumns = []
        const children = this.templateData[0].children
        const index = children.findIndex(item => item.queryId == queryId);
        if (index !== -1) {
          children.splice(index, 1);
        }
        if(children.length > 0) {
          this.selectFirstNode(children[0].queryId)
        }
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    submitTemplate() {
      this.$refs["formTemplate"].validate(valid => {
        if (valid) {
          this.formTemplate.tableName = this.tableName
          this.formTemplate.tableAlias = this.tableAlias
          if (this.formTemplate.queryId != null) {
            updateCaq(this.formTemplate).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.cancelTemplate();
            });
          } else {
            addCaq(this.formTemplate).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.templateData[0].children.push(response.data);
              this.setCurrentNode(response.data.queryId)
              this.cancelTemplate();
            });
          }
        }
      });
    },
    cancelTemplate() {
      this.openNewTemplate = false
      this.formTemplate = {};
    },
    handleTabClick(tab) {
      if(tab.name == 'cond') {
        this.condBtnShow = true;
      } else if(tab.name == 'qfrom') {
        this.condBtnShow = false;
      }
    },
    /** 模板条件相关 */
    loadCond(queryId) {
      this.templateId = queryId
      getCaqCond(queryId).then(res => {
        this.columns = res.data
        this.loadQeryForm()
      })
    },
    submitCond() {
      addCaqConds(this.templateId, this.columns).then(res => {
        this.loadQeryForm();
        this.cancelAdv()
        this.$modal.msgSuccess("保存成功");
      })
    },
    /** 查询表单相关 */
    loadQeryForm() {
      this.queryForm = {}
      this.queryColumns = []
      this.queryColumns = this.columns.filter(col => col.queryFlag);
      const dictType = this.queryColumns.filter(col => col.dictType !== '').map(col => col.dictType);
      for (const dict of dictType) {
        this.getDicts(dict).then(response => {
          this.$set(this.dictOptions, dict, response.data);
        });
      }
    },
    queryAdv() {
      const nodeData =  this.$refs.templateTree.getCurrentNode();
      let param = {
        tableName: nodeData.tableName,
        tableAlias: nodeData.tableAlias,
        pkCol: nodeData.pkCol,
        conditions: []
      }
      for (const [key, val] of Object.entries(this.queryForm)) {
        if(val != null && val != "") {
          const column = this.queryColumns.find(col => col.javaField == key);
          param.conditions.push({
            col: column.colName,
            queryType: column.queryType,
            javaType: column.javaType,
            value: val
          })
        }
      }
      // console.log(JSON.stringify(param))
      this.open = false;
      this.searchFunc('customizeAdvancedSearch', JSON.stringify(param))
    },
    cancelAdv() {
      this.resetForm("queryForm");
    },
    handleClose() {
      this.open = false;
    }
  }
}

</script>
