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
          <!--
					<dt>浏览量：{{infoData.viewes}}</dt>
          -->
					<div class="wz_wai">
						<div class="wz_font">
							<a href="javascript:void(0)" @click="print">打印</a>
						</div>
					</div>
				</dl>
			</div>
			<div class="ui-wb" v-html="infoData.content" v-if="hasAcl"></div>
		</div>
	</div>
</template>

<script>
import { getInfo4View } from "@/api/system/info";

export default {
  name: 'InfoPublishPage',
  data() {
    return {
      hasAcl: false,
      infoData: {
        title: null,
        subTitle: null,
        publishTime: null,
        content: null,
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      const id = this.$route.query && this.$route.query.id;
      if(id != null) {
        getInfo4View(id).then(res => {
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
		margin-right: 24px;
		width: 45px;
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
  @page {
    margin: 0;
  }
  body {
    margin: 1cm;
  }
}
</style>
