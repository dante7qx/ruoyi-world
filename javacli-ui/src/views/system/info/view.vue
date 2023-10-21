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
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入标题"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="发布时间">
            <el-date-picker
              v-model="daterangePublishTime"
              style="width: 220px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="置顶" prop="setTop">
            <el-select v-model="queryParams.setTop" placeholder="请选择" clearable style="width: 120px;">
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="评论" prop="commentable">
            <el-select v-model="queryParams.commentable" placeholder="请选择" clearable style="width: 120px;">
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="infoList" v-adaptive height="100">
          <el-table-column type="index" label="序号" width="50" align="center" />
          <el-table-column label="栏目" align="center" prop="categoryName" width="90"/>
              <el-table-column label="标题" align="center" prop="title" width="250" />
          <el-table-column label="副标题" align="center" prop="subTitle" />
          <el-table-column label="封面" align="center" prop="cover" width="100">
            <template slot-scope="scope">
              <image-preview :src="scope.row.cover" :width="50" :height="50"/>
            </template>
          </el-table-column>
          <el-table-column label="来源" align="center" prop="source" />
          <el-table-column label="作者" align="center" prop="author" />
          <el-table-column label="浏览量" align="center" prop="viewCount" width="70" />
          <el-table-column label="收藏量" align="center" prop="favorCount" width="70" />
          <el-table-column label="点赞量" align="center" prop="praiseCount" width="70" />
          <el-table-column label="已置顶" align="center" prop="setTop" width="60">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.setTop"/>
            </template>
          </el-table-column>
          <el-table-column label="允许评论" align="center" prop="commentable" width="80">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.commentable"/>
            </template>
          </el-table-column>
          <el-table-column label="发布时间" align="center" prop="publishTime" width="140" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="80" fixed="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleViewDetail(scope.row)"
              >详情</el-button>
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
  </div>
</template>

<script>
import { listCategory } from "@/api/system/infocategory";
import { listInfo4View } from "@/api/system/info";

export default {
  name: "Info",
  dicts: ['sys_yes_no'],
  data() {
    return {
      PUBLISH_STATUS: '2',
      // 遮罩层
      loading: true,
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
        commentable: null,
        publishTime: null,
        status: null,
      },
      // 栏目树
      categoryOptions: [],
      categoryProps: {
        children: 'children',
        label: 'categoryName',
        disabled: 'disabled'
      },
      filterCategoryName: '',
    };
  },
  computed: {
    categoryTreeHeight() {
      return `${document.documentElement.clientHeight}` - 176;
    }
  },
  created() {
    this.queryParams.status = this.PUBLISH_STATUS;
    this.loadCategoryData();
    this.getList();
  },
  methods: {
    loadCategoryData() {
      listCategory(this.queryParams).then(response => {
        const treeData = this.handleTree(response.data, "categoryId");
        this.categoryOptions = treeData
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
      if (null != this.daterangePublishTime && '' != this.daterangePublishTime) {
        this.queryParams.params["beginPublishTime"] = this.daterangePublishTime[0] + " 00:00:00";
        this.queryParams.params["endPublishTime"] = this.daterangePublishTime[1] + " 23:59:59";
      }
      listInfo4View(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.daterangePublishTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleViewDetail(row) {
      const routeUrl = this.$router.resolve({
        path: '/publish-info-preview',
        query: {id: row.infoId}

      });
      window.open(routeUrl.href)
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
