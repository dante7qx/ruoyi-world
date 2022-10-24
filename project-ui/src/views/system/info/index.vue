<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入标题"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
              <el-option
                v-for="dict in dict.type.sys_info_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="发布时间" v-if="queryStatus == 2">
            <el-date-picker
              v-model="daterangePublishTime"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="置顶" prop="setTop" v-if="queryStatus == 2">
            <el-select v-model="queryParams.setTop" placeholder="请选择" clearable>
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      
        <el-col :span="6">
          <el-form-item label="匿名访问" prop="anonymous" v-if="queryStatus == 2">
            <el-select v-model="queryParams.anonymous" placeholder="请选择" clearable>
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="停用" prop="disabled" v-if="queryStatus == 2">
            <el-select v-model="queryParams.disabled" placeholder="请选择" clearable>
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <div v-if="queryStatus==0 && checkRole(['info_record','info_mgr'])">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:info:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:info:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:info:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:info:export']"
            v-show="false"
          >导出</el-button>
        </el-col>
      </div>
      <div v-if="queryStatus==1 && checkRole(['info_record', 'info_mgr'])">
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-circle-check"
            size="mini"
            :disabled="multiple"
            @click="approval(true)"
            v-hasPermi="['system:info:edit']"
          >批量通过</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-circle-close"
            size="mini"
            :disabled="multiple"
            @click="approval(false)"
            v-hasPermi="['system:info:edit']"
          >批量驳回</el-button>
        </el-col>
      </div>
      <div v-if="queryStatus==2 && checkRole(['info_mgr'])">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-view"
            size="mini"
            :disabled="multiple"
            @click="setAnonymous(1)"
            v-hasPermi="['system:info:edit']"
          >设置匿名访问</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-view"
            size="mini"
            :disabled="multiple"
            @click="setAnonymous(0)"
            v-hasPermi="['system:info:edit']"
          >取消匿名访问</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-attract"
            size="mini"
            :disabled="multiple"
            @click="openInfoRange"
            v-hasPermi="['system:info:edit']"
          >批量设置访问范围</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-video-play"
            size="mini"
            :disabled="multiple"
            @click="setInfoDisabled(0)"
            v-hasPermi="['system:info:edit']"
          >启用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-video-pause"
            size="mini"
            :disabled="multiple"
            @click="setInfoDisabled(1)"
            v-hasPermi="['system:info:edit']"
          >停用</el-button>
        </el-col>
      </div>
      <el-col :span="1.5">
        <el-radio-group class="statusGroup" v-model="queryStatus" @change="changeQueryStatus">
          <el-radio-button :label="0" border size="medium">草 稿</el-radio-button>
          <el-radio-button :label="1" border size="medium">待审批</el-radio-button>
          <el-radio-button :label="2" border size="medium">已发布</el-radio-button>
        </el-radio-group>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="类型" align="center" prop="type"  width="90">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_info_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="副标题" align="center" prop="subTitle" />
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="已置顶" align="center" prop="setTop" v-if="queryStatus==2" width="60">
        <template slot-scope="scope">
          {{ scope.row.setTop == 0 ? '否' : '是' }}
        </template>
      </el-table-column>
      <el-table-column label="已停用" align="center" prop="disabled" v-if="queryStatus==2" width="60">
        <template slot-scope="scope">
          {{ scope.row.disabled == 0 ? '否' : '是' }}
        </template>
      </el-table-column>
      <el-table-column label="是否匿名访问" align="center" prop="anonymous" v-if="queryStatus==2" width="100">
        <template slot-scope="scope">
          {{ scope.row.anonymous == 0 ? '否' : '是' }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="110" v-if="queryStatus==2"/>
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['system:info:query']"
            v-if="queryStatus == 0"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['system:info:edit']"
            v-hasRole="['info_record', 'info_mgr']"
            v-if="queryStatus == 0"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:info:remove']"
            v-hasRole="['info_record', 'info_mgr']"
            v-if="queryStatus == 0"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['system:info:edit']"
            v-hasRole="['info_mgr']"
            v-if="queryStatus == 1"
          >处理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewDetail(scope.row)"
            v-hasPermi="['system:info:edit']"
            v-hasRole="['info_record', 'info_mgr']"
            v-if="queryStatus == 2"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="setTop(scope.row)"
            v-hasPermi="['system:info:edit']"
            v-hasRole="['info_record', 'info_mgr']"
            v-if="queryStatus == 2 && scope.row.setTop == 0"
          >置顶</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-attract"
            @click="openInfoRange(scope.row)"
            v-hasPermi="['system:info:edit']"
            v-hasRole="['info_record', 'info_mgr']"
            v-if="queryStatus == 2"
          >访问范围</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    
    <!-- 添加或修改信息发布对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <detail :key="key" :infoId="infoId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 审批对话框-->
    <el-dialog title="审批" :visible.sync="openApproval" width="600px" append-to-body>
      <el-form ref="formApproval" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="发布时间" prop="publishTime" v-if="pass">
          <el-date-picker clearable
            v-model="form.publishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间"
            @input="changePublishTime($event)"
            style="width: 100%;">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批意见" prop="comment">
          <el-input v-model="form.comment" type="textarea" :autosize="{ minRows: 4, maxRows: 6}" resize="none" show-word-limit maxlength="128"  placeholder="请输入审批意见" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right;">
        <el-button type="primary" @click="approvalSubmit">确 定</el-button>
        <el-button @click="cancelApproval">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 访问范围对话框-->
    <el-dialog title="设置访问范围" :visible.sync="openRange" width="600px" append-to-body>
      <el-form ref="formRange" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="访问范围" prop="rangeDeptIds">
          <treeselect 
            v-model="form.rangeDeptIds" 
            :options="deptOptions" 
            empty-text="未找到部门信息" 
            :show-count="true"
						:multiple="true" 
            :flat="true" 
            :normalizer="normalizer" 
            search-nested 
            placeholder="请选择部门" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right;">
        <el-button type="primary" @click="rangeSubmit">确 定</el-button>
        <el-button @click="cancelRange">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, listApproval, batchApproval, setTopInfo, setDisabledInfo, setAnonymousInfo, setRangeInfo, getInfoRange, delInfo } from "@/api/system/info";
import { allDeptTreeSelect } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Detail from "./detail"
import { checkRole } from "@/utils/permission";

export default {
  name: "Info",
  components: {
    Treeselect,
    "detail": Detail
  },
  dicts: ['sys_info_type', 'sys_yes_no'],
  data() {
    return {
      statusMap: {
        DRAFT: '0', // 草稿
        APPROVAL: '1',  // 待审批
        PUBLISH: '2' // 已发布
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 信息发布表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangePublishTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        type: null,
        setTop: null,
        anonymous: null,
        disabled: null,
        publishTime: null,
        status: null,
      },
      infoId: 0,
      disabled: false,
      key: '',
      queryStatus: 0,
      // 批量审批
      openApproval: false,
      form: {
        publishTime: null,
        comment: null,
        rangeDeptIds: null
      },
      rules: {
        publishTime: [
          { required: true, message: "发布时间不能为空", trigger: "change" }
        ],
        comment: [
          { required: true, message: "审批意见不能为空", trigger: "blur" }
        ],
        rangeDeptIds: [
          { required: true, message: "访问范围不能为空", trigger: "change" }
        ],
      },
      pass: false,
      // 访问范围
      openRange: false,
      deptOptions: [],

    };
  },
  created() {
    this.queryParams.status = this.queryStatus + ''
    this.getList();
    this.loadDeptData();
  },
  methods: {
    checkRole,
    loadDeptData() {
      allDeptTreeSelect().then(res => {
        this.deptOptions = res.data
      })
    },
    normalizer(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children && node.children.length > 0 ? node.children : 0,
        isDefaultExpanded: true
      }
    },
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePublishTime && '' != this.daterangePublishTime) {
        this.queryParams.params["beginPublishTime"] = this.daterangePublishTime[0] + " 00:00:00";
        this.queryParams.params["endPublishTime"] = this.daterangePublishTime[1] + " 23:59:59";
      }
      if(this.queryParams.status == this.statusMap.APPROVAL) {
        listApproval(this.queryParams).then(response => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      } else {
        listInfo(this.queryParams).then(response => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      }
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.daterangePublishTime = [];
      this.resetForm("queryForm");
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        title: null,
        type: null,
        setTop: null,
        anonymous: null,
        disabled: null,
        publishTime: null,
        status: null,
      },
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.infoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true;
      this.title = "添加信息发布";
      this.infoId = 0;
      this.disabled = false;
      this.key = this.nanoid();
    },
    handleUpdate(row, disabled) {
      this.disabled = disabled;
      this.infoId = row.infoId;
      if(this.disabled) {
        this.title = "查看信息发布";
      } else {
        this.title = "修改信息发布";
      }
      if(row && row.status == this.statusMap.APPROVAL) {
        this.title = "审批信息发布";
      }
      this.open = true;
      this.key = this.nanoid();
    },
    handleDelete(row) {
      const infoIds = row.infoId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delInfo(infoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    },
    handleViewDetail(row) {
      this.$modal.msgSuccess("信息模板开发中...");
    },
    closeFlowWin() {
      this.title = "";
      this.open = false;
      this.handleQuery();
    },
    changeQueryStatus(val) {
      this.queryType = parseInt(val, 10);
      this.multiple = true;
      this.queryParams.status = val
      this.handleQuery()
    },
    changePublishTime(e){
			this.$forceUpdate()
			this.form.publishTime = e
		},
    approval(pass) {
      const infoIds = this.ids;
      if(infoIds.length == 0) return false
      this.openApproval = true
      this.pass = pass;
      if(this.pass) {
        this.form.publishTime = this.$moment(new Date()).format("YYYY-MM-DD")
      }
    },
    approvalSubmit() {
      this.$refs["formApproval"].validate(valid => {
        if (valid) {
          this.form.ids = this.ids;
          this.form.approval = this.pass;
          batchApproval(this.form).then(res => {
            this.$modal.msgSuccess(this.pass ? "批量发布成功" : "批量驳回成功");
            this.cancelApproval()
            this.getList();
          })
        }
      });
    },
    cancelApproval() {
      this.openApproval = false
      this.pass = false;
      this.resetForm();
    },
    setTop(row) {
      const formData = Object.assign({}, row)
      formData.setTop = 1
      setTopInfo(formData).then(response => {
        this.$modal.msgSuccess('置顶成功');
        this.getList()
      });
    },
    setInfoDisabled(disabled) {
      const infoIds = this.ids;
      if(infoIds.length == 0) return false
      const prompt = disabled == 1 ? '是否确认停用？' : '是否确认启用？'; 
      this.$modal.confirm(prompt).then(function() {
        return setDisabledInfo({ids: infoIds, disabled: disabled});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    setAnonymous(anonymous) {
      const infoIds = this.ids;
      if(infoIds.length == 0) return false
      const prompt = anonymous == 1 ? '是否确认设置匿名访问？' : '是否确认取消匿名访问？'; 
      this.$modal.confirm(prompt).then(function() {
        return setAnonymousInfo({ids: infoIds, anonymous: anonymous});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("设置成功");
      }).catch(() => {});
    },
    openInfoRange(row) {
      if(!row.infoId && this.ids.length == 0) {
        return false
      } 
      if(row.infoId) {
        this.form.ids = [row.infoId]
      } else if(this.ids) {
        this.form.ids = this.ids
      }
      if(this.form.ids.length == 1) {
        this.loadInfoRange(this.form.ids[0])
      }
      this.openRange = true
    },
    rangeSubmit() {
      this.$refs["formRange"].validate(valid => {
        if (valid) {
          setRangeInfo(this.form).then(res => {
            this.getList();
            this.cancelRange();
            this.$modal.msgSuccess("设置成功");
          })
        }
      });
    },
    cancelRange() {
      this.openRange = false
      this.resetForm();
    },
    loadInfoRange(id) {
      getInfoRange(id).then(res => {
        this.form.rangeDeptIds = res.data
      })
    },
    resetForm() {
      this.form = {
        publishTime: null,
        comment: null,
        rangeDeptIds: null
      };
    }
  }
};
</script>

<style scoped>
.statusGroup {
  margin-top: -5px;
}
</style>
