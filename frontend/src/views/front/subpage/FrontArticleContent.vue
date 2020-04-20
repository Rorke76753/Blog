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
        <div style="padding-left: 100px;width: 360px;margin-top: 30px">
          <div>
            <div style="border-left: #0088ff 5px solid;padding-left: 20px">
              <h3>最近发表</h3>
            </div>
            //正在开发中^^
          </div>
          <div>
            <div style="border-left: #0088ff 5px solid;padding-left: 20px">
              <h3>推荐阅读</h3>
            </div>
            //正在开发中^^
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import ArticleContent from "../../../components/front/articleContent/ArticleContent";
export default {
  name: "FrontArticleContent",
  components: {
    ArticleContent
  },
  data() {
    return {
      articleInfo: {},
      articleId: ""
    };
  },
  created() {
    if (this.$route.params.articleInfo) {
      this.articleInfo = this.$route.params.articleInfo;
      sessionStorage.setItem("articleInfo", JSON.stringify(this.articleInfo));
    } else {
      this.articleInfo = JSON.parse(sessionStorage.getItem("articleInfo"));
    }
    this.articleId = this.$route.params.articleId;
    this.getContent();
  },
  methods: {
    getContent() {
      axios.get("/article/" + this.$route.params.articleId).then(res => {
        if (res.status === 200) {
          this.articleContent = res.data.articleContent;
          this.$refs.articleContent.initContent(this.articleContent);
        }
      });
    }
  }
};
</script>

<style scoped></style>
