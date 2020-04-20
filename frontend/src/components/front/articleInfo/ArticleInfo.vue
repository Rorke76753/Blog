<template>
  <el-card
    shadow="hover"
    v-model="articleInfo"
    :body-style="{ 'padding-top': '0px' }"
  >
    <div slot="header">
      <el-link
        class="titleClass"
        @click="getArticleContent(articleInfo.articleId)"
        >【{{ articleInfo.attributeName }}】{{ articleInfo.title }}</el-link
      >
    </div>
    <div slot="default" class="bodyClass">
      <div class="tagClass">
        <el-button
          type="text"
          v-for="tag in articleInfo.tagList"
          :key="tag.tagId"
          size="mini"
          :style="{ color: color[articleInfo.tagList.indexOf(tag) % 10] }"
          icon="el-icon-collection-tag"
          >{{ tag.tagContent }}</el-button
        >
      </div>
      <div class="descriptionClass">
        {{ articleInfo.description }}
      </div>
      <div class="infoClass">
        <el-row type="flex" justify="start">
          <el-col class="publishDate">
            <el-tooltip effect="dark" content="发表日期" placement="bottom">
              <el-link icon="el-icon-date">{{
                articleInfo.publishDate
              }}</el-link>
            </el-tooltip>
          </el-col>
          <el-col class="like">
            <el-tooltip effect="dark" content="点赞数" placement="bottom">
              <el-link icon="el-icon-star-off">{{
                articleInfo.likeNum
              }}</el-link>
            </el-tooltip>
          </el-col>
          <el-col class="view">
            <el-tooltip effect="dark" content="点击数" placement="bottom">
              <el-link icon="el-icon-view">{{ articleInfo.clickNum }}</el-link>
            </el-tooltip>
          </el-col>
          <el-col class="comment">
            <el-tooltip effect="dark" content="评论数" placement="bottom">
              <el-link icon="el-icon-chat-round">{{
                articleInfo.commentNum
              }}</el-link>
            </el-tooltip>
          </el-col>
        </el-row>
      </div>
    </div>
  </el-card>
</template>

<script>
import axios from "axios";
export default {
  name: "ArticleInfo",
  props: {
    articleInfo: Object
  },
  components: {},
  data() {
    return {
      color: ["OrangeRed", "orange", "#ffd152", "LimeGreen", "DodgerBlue"]
    };
  },
  methods: {
    getArticleContent(id) {
      let _this = this;
      axios.get("/click/" + id);
      this.$router.push({
        name: "articleContent",
        params: {
          articleId: id,
          articleInfo: _this.articleInfo
        }
      });
    }
  }
};
</script>

<style scoped>
.el-card {
  width: 80%;
  margin-left: 22%;
  margin-top: 20px;
}
.descriptionClass {
  padding-top: 5px;
}
.titleClass {
  font-size: 26px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.descriptionClass {
  font-size: 14px;
}
.infoClass {
  padding-top: 10px;
}
.tagClass {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.bodyClass {
  padding-top: 0 !important;
}
</style>
