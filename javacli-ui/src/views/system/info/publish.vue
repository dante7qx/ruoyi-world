<template>
	<div>
		<div class="huter">
      <div v-if="!hasAcl">
        <h1 class="title" >您没有权限或文章已撤销！</h1>
        <div style="text-align: center;">
          <button class="btn-close" @click="closeWindow" >关 闭</button>
        </div>
      </div>
			<div class="ui-tit" v-if="hasAcl">
				<h1 class="title">{{infoData.title}}</h1>
        <h4 class="sub-title">{{infoData.subTitle}}</h4>
				<dl class="article">
					<dt>发布日期：{{infoData.publishTime}}</dt>
					<dt>来源：{{infoData.source}}</dt>
          <dt>作者：{{infoData.author}}</dt>
					<dt>浏览量：{{infoData.viewCount}}</dt>
					<div class="wz_wai noprint">
						<div class="wz_font">
							<a href="javascript:void(0)" @click="print">打印</a>
						</div>
						<div class="wz_font" style="width: 70px;">
							<a href="javascript:void(0)" @click="doPraise" style="width: 70px;">{{ praise }}: {{ infoData.praiseCount }}</a>
						</div>
						<div class="wz_font" style="width: 70px;">
							<a href="javascript:void(0)" @click="doFavor" style="width: 70px;">{{ favorite }}: {{ infoData.favorCount }}</a>
						</div>
					</div>
				</dl>
			</div>
			<div class="ui-wb" v-if="hasAcl" v-html="infoData.content"></div>
		</div>
		<div class="comment" v-if="token && infoData.commentable == 1">
			<comment :key="infoId" bizModel="'SysInfo'" :bizId="infoId" :showImg="true" :showVideo="true"/>
		</div>
	</div>
</template>

<script>
import store from "@/store";
import { getInfo4View, favorInfo, praiseInfo } from "@/api/system/info";
import Comment from '@/components/Comment';

export default {
  name: 'InfoPublishPage',
	components: { Comment },
  data() {
    return {
			infoId: 0,
			token: store.getters.token,
      hasAcl: false,
      infoData: {
        title: null,
        subTitle: null,
        publishTime: null,
        content: null,
				commentable: 0
      },
			favorite: '收藏',
			praise: '点赞'
    }
  },
  created() {
		this.infoId = this.$route.query && this.$route.query.id;
    this.loadData()
  },
  methods: {
    loadData() {
      if(this.infoId != null) {
        getInfo4View(this.infoId).then(res => {
          if(res.data !== undefined) {
            this.hasAcl = true;
            this.infoData = res.data
          }
        })
      } 
    },
    print() {
      window.print()
    },
		doPraise() {
			if(this.praise == '点赞') {
				praiseInfo(this.infoId, true).then(res => {
					this.praise = '已点赞';
					this.infoData.praiseCount += 1;
				})
			}	else {
				praiseInfo(this.infoId, false).then(res => {
					this.praise = '点赞';
					this.infoData.praiseCount -= 1;
				})
			}
		},
		doFavor() {
			if(this.favorite == '收藏') {
				this.$modal.msgSuccess("加入收藏夹请使用 Ctrl+D ");
				favorInfo(this.infoId).then(res => {
					this.favorite = '已收藏';
					this.infoData.favorCount += 1;
				})
			}
		},
    closeWindow() {
      window.close()
    }
  }
}
</script>

<style scoped>
	* {
		margin: 0;
		padding: 0;
    background-color: #fff;
	}

	li {
		list-style: none;
	}

	.huter {
		width: 1138px;
		min-height: 325px;
		border: 0px solid #d9d9d9;
		margin: 50px auto 0px;
		height: auto;
		overflow: hidden;
	}

	.ui-tit {
		width: 1000px;
		height: auto;
		margin: 0 auto;
		border-bottom: 1px solid #d9d9d9;
	}

	.title {
		width: 100%;
		min-height: 90px;
		line-height: 70px;
		color: #333;
		text-align: center;
		font-size: 28px;
		font-family: "微软雅黑";
		overflow: hidden;
		margin-top: 30px;
	}

  .sub-title {
    text-align: center;
    font-family: "微软雅黑";
    font-size: 18px;
  }
	.xian{
		margin: 0 8px;
		margin-top: -2px;
	}
	.article {
		display: block;
		width: 100%;
		height: 40px;
		margin: 0 auto;
	}

	.article dt {
		height: 40px;
		line-height: 40px;
		color: #333;
		float: left;
		font-size: 14px;
		/* font-family: "微软雅黑"; */
		margin-left: 24px;
	}

	.wz_wai {
		position: relative;
	}

	.wz_font {
		width: 55px;
		float: right;
		height: 40px;
		line-height: 40px;
		font-size: 14px;
		display: flex;
		align-items: center;
	}

	.wz_font a {
		float: left;
		width: 45px;
		font-size: 14px;
		color: rgb(51, 51, 51);
		text-decoration: none
	}

	.zh {
		display: block;
		width: 58px;
		text-align: right;
		font-size: 14px;
		line-height: 24px;
		color: rgb(51, 51, 51);
		margin-right: 4px;

	}

	.ul-li {
		position: absolute;
		top: 41px;
		right: 214px;
		height: 105px;
		width: 40px;
		background-color: #e7e7e7;
	}

	.ul-li li {
		margin-top: 8px;
		font-size: 16px;
	}

	.ui-wb {
		width: 1000px;
		margin: 0 auto;
		line-height: 34px;
		height: auto;
		padding-top: 40px;
		padding-bottom: 30px;
		text-align:left; 
	}

  .btn-close {
    border: 1px solid #d9d9d9;
    cursor: pointer;
    width: 70px;
    height: 30px;
  }

  @media print {
		.noprint { display: none }
		@page {
			margin: 0;
		}
		body {
			margin: 1cm;
		}
	}
	.comment {
		width: 1060px;
		margin: 50px auto 0px;
	}
</style>
