<template>
  <div class="contentClass">
    <el-card >
      <p style="font-size: 30px;margin: 10px  0px">
        <b>{{ articleInfo.title }}</b>
      </p>


      <el-button
        type="plain"
        v-for="tag in articleInfo.tagList"
        :key="tag.tagId"
        size="mini"
        :style="{
          'background-color': color[articleInfo.tagList.indexOf(tag) % 10],
          color: 'white',
          padding: '4px 8px'
        }"
        >{{ tag.tagContent }}</el-button
      >
      <el-row style="margin: 10px">
        <el-col :span="8"><p style="font-size: 14px">{{articleInfo.likeNum}}</p></el-col>
        <el-col :span="8"><p style="font-size: 14px">{{articleInfo.clickNum}}</p></el-col>
        <el-col :span="8">
          <p style="font-size: 14px">publish at / {{articleInfo.publishDate}}</p>
        </el-col>
      </el-row>


      <div slot="default" style="padding-top: 50px">
        <viewer
          :initial-value="articleContent"
          height="500px"
          ref="viewer"
        ></viewer>
      </div>
    </el-card>
  </div>
</template>

<script>
import Viewer from "@toast-ui/vue-editor/src/Viewer.vue";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";
export default {
  name: "ArticleContent",
  components: {
    viewer: Viewer
  },
  props: {
    articleInfo: Object
  },
  data() {
    return {
      articleContent: "",
      color: ["OrangeRed", "orange", "#ffd152", "LimeGreen", "DodgerBlue"]
    };
  },
  methods: {
    initContent(articleContent) {
      this.articleContent = articleContent;
      this.$refs.viewer.invoke("setMarkdown", this.articleContent);
    }
  }
};
</script>

<style scoped>
.contentClass {
  height: 100%;
  width: 80%;
  margin-left: 22%;
  margin-top: 30px;
}
</style>
