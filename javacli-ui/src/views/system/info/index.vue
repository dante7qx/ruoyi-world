<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="head-container">
          <el-input
            v-model="filterCategoryName"
            placeholder="按栏目名称过滤"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px;width: 90%;"
          />
        </div>
        <div :style="{ height: categoryTreeHeight + 'px', overflow: 'auto'}">
          <el-tree
            node-key="categoryId"
            icon-class="el-icon-s-help"
            highlight-current
            :data="categoryOptions"
            :props="categoryProps"
            :expand-on-click-node="false"
            :filter-node-method="filterCategoryNode"
            ref="categoryTree"
            default-expand-all
            @node-click="categoryNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20">
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
              <el-form-item label="发布时间" v-if="queryStatus === 2">
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
              <el-form-item label="置顶" prop="setTop" v-if="queryStatus === 2">
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
              <el-form-item label="匿名访问" prop="anonymous" v-if="queryStatus === 2">
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
              <el-form-item label="停用" prop="disabled" v-if="queryStatus === 2">
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
              <el-form-item label="评论" prop="commentable">
                <el-select v-model="queryParams.commentable" placeholder="请选择" clearable style="width: 240px;">
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
          <div v-if="queryStatus===0 && checkRole(['info_record','info_mgr'])">
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
                @click="handleUpdate(null, true)"
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
          <div v-if="queryStatus===1 && checkRole(['info_mgr'])">
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
          <div v-if="queryStatus===2 && checkRole(['info_record','info_mgr'])">
            <el-col :span="1.5">
              <el-dropdown size="mini" split-button type="primary" :disabled="multiple" @command="handleComment" v-hasPermi="['system:info:edit']">
                评论设置
                <el-dropdown-menu v-slot="dropdown">
                  <el-dropdown-item :command="1">
                    <el-link type="success" :underline="false" icon="el-icon-s-comment">允许评论</el-link>
                  </el-dropdown-item>
                  <el-dropdown-item :command="0">
                    <el-link type="danger" :underline="false" icon="el-icon-s-comment">禁用评论</el-link>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
            <el-col :span="1.5">
              <el-dropdown size="mini" split-button type="warning" :disabled="multiple" @command="setAnonymous" v-hasPermi="['system:info:edit']">
                匿名访问设置
                <el-dropdown-menu v-slot="dropdown">
                  <el-dropdown-item :command="1">
                    <el-link type="success" :underline="false" icon="el-icon-view">允许匿名访问</el-link>
                  </el-dropdown-item>
                  <el-dropdown-item :command="0">
                    <el-link type="danger" :underline="false" icon="el-icon-view">禁止匿名访问</el-link>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-attract"
                size="mini"
                :disabled="multiple"
                @click="openInfoRange"
                v-hasPermi="['system:info:edit']"
              >批量设置访问范围</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-dropdown size="mini" split-button type="danger" :disabled="multiple" @command="setPublishStatus" v-hasPermi="['system:info:edit']">
                状态设置
                <el-dropdown-menu v-slot="dropdown">
                  <el-dropdown-item :command="2" v-if="checkRole(['info_mgr'])">
                    <el-link type="danger" :underline="false" icon="el-icon-refresh-left">撤 销</el-link>
                  </el-dropdown-item>
                  <el-dropdown-item :command="0">
                    <el-link type="success" :underline="false" icon="el-icon-view">启 用</el-link>
                  </el-dropdown-item>
                  <el-dropdown-item :command="1">
                    <el-link type="danger" :underline="false" icon="el-icon-view">停 用</el-link>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
          </div>
          <el-col :span="1.5">
            <el-radio-group class="statusGroup" size="small" v-model="queryStatus" @change="changeQueryStatus">
              <el-radio-button :label="0" border size="medium">草 稿</el-radio-button>
              <el-radio-button :label="1" border size="medium">待审批</el-radio-button>
              <el-radio-button :label="2" border size="medium">已发布</el-radio-button>
            </el-radio-group>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange" v-adaptive height="100">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column type="index" label="序号" width="50" align="center" fixed="left" />
          <el-table-column label="栏目" align="center" prop="categoryName"  width="90" fixed="left"/>
          <el-table-column label="标题" align="center" prop="title" width="250" fixed="left"/>
          <el-table-column label="副标题" align="center" prop="subTitle" />
          <el-table-column label="封面" align="center" prop="cover" width="100">
            <template v-slot="scope">
              <image-preview :src="scope.row.cover" :width="50" :height="50"/>
            </template>
          </el-table-column>
          <el-table-column label="来源" align="center" prop="source" width="90" />
          <el-table-column label="作者" align="center" prop="author" width="110" />
          <el-table-column label="浏览量" align="center" prop="viewCount" width="70" v-if="queryStatus===2" />
          <el-table-column label="收藏量" align="center" prop="favorCount" width="70" v-if="queryStatus===2" />
          <el-table-column label="点赞量" align="center" prop="praiseCount" width="70" v-if="queryStatus===2" />
          <el-table-column label="已置顶" align="center" prop="setTop" v-if="queryStatus===2" width="60">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.setTop"/>
            </template>
          </el-table-column>
          <el-table-column label="已停用" align="center" prop="disabled" v-if="queryStatus===2" width="60">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.disabled"/>
            </template>
          </el-table-column>
          <el-table-column label="匿名访问" align="center" prop="anonymous" v-if="queryStatus===2" width="80">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.anonymous"/>
            </template>
          </el-table-column>
          <el-table-column label="允许评论" align="center" prop="commentable" v-if="queryStatus===2" width="80">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.commentable"/>
            </template>
          </el-table-column>
          <el-table-column label="发布时间" align="center" prop="publishTime" width="140" v-if="queryStatus===2"/>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100" fixed="right">
            <template v-slot="scope">
              <el-dropdown>
                <span class="el-dropdown-link">
                  更多操作<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu v-slot="dropdown">
                  <el-dropdown-item v-hasPermi="['system:info:add']">
                    <span @click="handleProp(scope.row)"><i slot="suffix" class="el-icon-s-help" />属 性</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:info:query']" v-if="queryStatus === 0">
                    <span @click="handleUpdate(scope.row, true)"><i slot="suffix" class="el-icon-view" />查 看</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:info:edit']" v-hasRole="['info_record', 'info_mgr']" v-if="queryStatus === 0">
                    <span @click="handleUpdate(scope.row, false)"><i slot="suffix" class="el-icon-edit" />修 改</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:info:remove']" v-hasRole="['info_record', 'info_mgr']" v-if="queryStatus === 0 || (queryStatus === 2 && scope.row.disabled === 1)">
                    <span @click="handleDelete(scope.row)"><i slot="suffix" class="el-icon-delete" />删 除</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:info:edit']" v-hasRole="['info_mgr']" v-if="queryStatus === 1">
                    <span @click="handleUpdate(scope.row, true)"><i slot="suffix" class="el-icon-edit" />处 理</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-if="queryStatus === 2 && scope.row.disabled === 0">
                    <span @click="handleViewDetail(scope.row)"><i slot="suffix" class="el-icon-view" />预 览</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:info:edit']" v-hasRole="['info_record', 'info_mgr']" v-if="queryStatus === 2 && scope.row.setTop === 0">
                    <span @click="setTop(scope.row)"><i slot="suffix" class="el-icon-upload2" />置 顶</span>
                  </el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:info:edit']" v-hasRole="['info_record', 'info_mgr']" v-if="queryStatus === 2">
                    <span @click="openInfoRange(scope.row)"><i slot="suffix" class="el-icon-attract" />访问范围</span>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
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
      </el-col>
    </el-row>

    <!-- 添加或修改信息发布对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" v-dialog-drag append-to-body>
      <detail :key="key" :infoId="infoId" :categoryId="queryParams.categoryId" :disabled="disabled"  @closeWindow="closeFlowWin" />
    </el-dialog>

    <!-- 信息属性对话框 -->
    <el-dialog title="信息属性" :visible.sync="openProp" width="1000px" v-dialog-drag append-to-body>
      <info-prop :key="nanoid()" :infoId="infoId" />
    </el-dialog>

    <!-- 审批对话框-->
    <el-dialog title="审批" :visible.sync="openApproval" width="600px" v-dialog-drag append-to-body>
      <el-form ref="formApproval" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="发布时间" prop="publishTime" v-if="pass">
          <el-date-picker clearable
                          v-model="form.publishTime"
                          type="datetime"
                          format="yyyy-MM-dd HH:mm"
                          value-format="yyyy-MM-dd HH:mm"
                          placeholder="请选择发布时间"
                          @input="changePublishTime($event)"
                          style="width: 100%;">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批意见" prop="comment">
          <el-input v-model="form.comment" type="textarea" :autosize="{ minRows: 4, maxRows: 6}" resize="none" show-word-limit maxlength="128" placeholder="请输入审批意见" />
        </el-form-item>
        <el-form-item label="匿名访问" prop="anonymous" v-if="pass">
          <el-switch v-model="form.anonymous" :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#ff4949" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right;">
        <el-button type="primary" @click="approvalSubmit">确 定</el-button>
        <el-button @click="cancelApproval">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 访问范围对话框-->
    <el-dialog title="设置访问范围" :visible.sync="openRange" width="600px" v-dialog-drag append-to-body>
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
            :max-height="300"
            placeholder="请选择部门" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right; margin-top: 200px;">
        <el-button type="primary" @click="rangeSubmit">确 定</el-button>
        <el-button @click="cancelRange">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCategory } from '@/api/system/infocategory'
import {
  batchApproval,
  batchWithdraw,
  delInfo,
  getInfoRange,
  listApproval,
  listInfo,
  setAnonymousInfo,
  setCommentInfo,
  setDisabledInfo,
  setRangeInfo,
  setTopInfo
} from '@/api/system/info'
import { allDeptTreeSelect } from '@/api/system/user'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Detail from './detail'
import InfoProp from './prop/index'
import { checkRole } from '@/utils/permission'

export default {
  name: "Info",
  components: {
    Treeselect,
    "detail": Detail,
    "info-prop": InfoProp

  },
  dicts: ['sys_yes_no'],
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
        categoryId: null,
        setTop: null,
        anonymous: null,
        commentable: null,
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
        anonymous: null,
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
      // 栏目树
      categoryOptions: [],
      categoryProps: {
        children: 'children',
        label: 'categoryName',
        disabled: 'disabled'
      },
      filterCategoryName: '',
      // 信息属性
      openProp: false
    };
  },
  computed: {
    categoryTreeHeight() {
      return `${document.documentElement.clientHeight}` - 176;
    }
  },
  created() {
    this.queryParams.status = this.queryStatus + ''
    this.getList();
    this.loadDeptData();
    this.loadCategoryData();
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
    loadCategoryData() {
      listCategory(this.queryParams).then(response => {
        this.categoryOptions = this.handleTree(response.data, "categoryId")
        // this.$set(this.categoryOptions, 'children', treeData)
      });
    },
    filterCategoryNode(value, data) {
      if (!value) return true;
      return data.categoryName.indexOf(value) !== -1;
    },
    categoryNodeClick(node) {
      this.queryParams.categoryId = node.categoryId;
      this.getList();
    },
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePublishTime && this.daterangePublishTime.length > 0) {
        this.queryParams.params["beginPublishTime"] = this.daterangePublishTime[0] + " 00:00:00";
        this.queryParams.params["endPublishTime"] = this.daterangePublishTime[1] + " 23:59:59";
      }
      if(this.queryParams.status === this.statusMap.APPROVAL) {
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
      this.key = this.nanoid();
      this.disabled = disabled;
      this.infoId = row != null ? row.infoId : this.ids[0];
      if(this.disabled) {
        this.title = "查看信息发布";
      } else {
        this.title = "修改信息发布";
      }
      if(row && row.status === this.statusMap.APPROVAL) {
        this.title = "审批信息发布";
      }
      this.open = true;
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
      const routeUrl = this.$router.resolve({
        path: '/publish-info-preview',
        query: {id: row.infoId}

      });
      window.open(routeUrl.href)
    },
    handleProp(row) {
      this.openProp = true
      this.infoId = row.infoId
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
      if(infoIds.length === 0) return false
      this.openApproval = true
      this.pass = pass;
      if(this.pass) {
        this.form.publishTime = this.$moment(new Date()).format("YYYY-MM-DD HH:mm")
        this.form.comment = '同意'
        this.form.anonymous = 1
      } else {
        this.form.comment = '退回'
        this.form.anonymous = null
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
      if(infoIds.length === 0) return false
      const prompt = disabled === 1 ? '是否确认停用？' : '是否确认启用？';
      this.$modal.confirm(prompt).then(function() {
        return setDisabledInfo({ids: infoIds, disabled: disabled});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    withdrawPublish() {
      const infoIds = this.ids;
      if(infoIds.length === 0) return false
      const that = this
      this.$modal.confirm('是否确认撤销已发布的信息？').then(function() {
        return batchWithdraw({ids: infoIds, status: that.statusMap.DRAFT});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("撤销成功");
      }).catch(() => {});
    },
    setPublishStatus(status) {
      if(status === 2) {
        this.withdrawPublish()
      } else {
        this.setInfoDisabled(status)
      }
    },
    setAnonymous(anonymous) {
      const infoIds = this.ids;
      if(infoIds.length === 0) return false
      const prompt = anonymous === 1 ? '是否确认允许匿名访问？' : '是否确认禁用匿名访问？';
      this.$modal.confirm(prompt).then(function() {
        return setAnonymousInfo({ids: infoIds, anonymous: anonymous});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("设置成功");
      }).catch(() => {});
    },
    handleComment(allowed) {
      const infoIds = this.ids;
      if(infoIds.length === 0) return false
      const prompt = allowed === 1 ? '是否确认允许评论？' : '是否确认禁用评论？';
      this.$modal.confirm(prompt).then(function() {
        return setCommentInfo({ids: infoIds, commentable: allowed});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("设置成功");
      }).catch(() => {});
    },
    openInfoRange(row) {
      if(!row.infoId && this.ids.length === 0) {
        return false
      }
      if(row.infoId) {
        this.form.ids = [row.infoId]
      } else if(this.ids) {
        this.form.ids = this.ids
      }
      if(this.form.ids.length === 1) {
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
  },
  watch: {
    filterCategoryName(val) {
      this.$refs.categoryTree.filter(val);
    }
  }
};
</script>

<style scoped>
.statusGroup {
  margin-top: -5px;
}
</style>
