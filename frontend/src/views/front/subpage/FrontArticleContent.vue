<template>
  <div style="top:0; left: 0; height: 100%;width: 100%; position: absolute ">
    <el-row>
      <el-col :span="17"
        ><ArticleContent
          :article-info="articleInfo"
          ref="articleContent"
        ></ArticleContent
      ></el-col>
      <el-col :span="7">
        <div style="padding-left: 100px;width: 82%;margin-top: 30px">
          <div>
            <div style="border-left: #0088ff 5px solid;padding-left: 20px">
              <h3>最近发表</h3>
            </div>
            <div v-for="recentInfo in recentList" :key="recentInfo.articleId">
              <el-link
                @click="jumpTo(recentInfo)"
                style="padding-left: 20px;padding-top: 10px"
                >【{{ recentInfo.attributeName }}】{{
                  recentInfo.title
                }}</el-link
              >
            </div>
          </div>
          <div>
            <div style="border-left: #0088ff 5px solid;padding-left: 20px">
              <h3>推荐阅读</h3>
            </div>
            <div
              v-for="recommendInfo in recommendList"
              :key="recommendInfo.articleId"
            >
              <el-link
                @click="jumpTo(recommendInfo)"
                style="padding-left: 20px;padding-top: 10px"
              >
                【{{ recommendInfo.attributeName }}】{{ recommendInfo.title }}
              </el-link>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import frontArticle from "../../../http/api/front/article";
import ArticleContent from "../../../components/front/articleContent/ArticleContent";
import recommendService from "../../../http/api/front/recommend";
import recentService from "../../../http/api/front/recent";
import clickService from "../../../http/api/front/click";
export default {
  name: "FrontArticleContent",
  components: {
    ArticleContent
  },
  data() {
    return {
      articleInfo: {},
      recommendList: [],
      recentList: []
    };
  },
  created() {
    if (this.$route.params.articleInfo) {
      this.articleInfo = this.$route.params.articleInfo;
      sessionStorage.setItem("articleInfo", JSON.stringify(this.articleInfo));
    } else {
      this.getInfo();
    }
    this.getContent();
    this.getRecommend();
    this.getRecent();
  },
  methods: {
    getInfo() {
      let id = this.$route.params.articleId;
      frontArticle.getArticleInfo(id).then(res => {
        this.articleInfo = res.data;
        sessionStorage.setItem("articleInfo", JSON.stringify(this.articleInfo));
      });
    },
    getContent() {
      let id = this.$route.params.articleId;
      frontArticle.getArticleContent(id).then(res => {
        this.articleContent = res.data.articleContent;
        this.$refs.articleContent.initContent(this.articleContent);
      });
    },
    getRecommend() {
      recommendService.getRecommendList().then(res => {
        this.recommendList = res.data;
      });
    },
    getRecent() {
      recentService.getRecentList().then(res => {
        this.recentList = res.data;
      });
    },
    jumpTo(articleInfo) {
      if (articleInfo.articleId !== this.$route.params.articleId) {
        this.articleInfo = articleInfo;
        clickService.countClick({
          articleId: articleInfo.articleId,
          ip: sessionStorage.getItem("ip")
        });
        this.$router.push({
          name: "articleContent",
          params: {
            articleId: articleInfo.articleId,
            articleInfo: articleInfo
          }
        });
        this.getContent();
      }
    }
  }
};
</script>

<style scoped></style>
