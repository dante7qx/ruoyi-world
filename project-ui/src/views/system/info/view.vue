<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入标题"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
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
            <el-select v-model="queryParams.setTop" placeholder="请选择" clearable>
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

    <el-table v-loading="loading" :data="infoList" >
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
      <el-table-column label="已置顶" align="center" prop="setTop" width="60">
        <template slot-scope="scope">
          {{ scope.row.setTop == 0 ? '否' : '是' }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="110" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
  </div>
</template>

<script>
import { listInfo4View } from "@/api/system/info";

export default {
  name: "Info",
  dicts: ['sys_info_type', 'sys_yes_no'],
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
        type: null,
        setTop: null,
        publishTime: null,
        status: null,
      }
    };
  },
  created() {
    this.queryParams.status = this.PUBLISH_STATUS;
    this.getList();
  },
  methods: {
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
      this.$modal.msgSuccess("信息模板开发中...");
    }
  }
};
</script>

<style scoped>
.statusGroup {
  margin-top: -5px;
}
</style>
