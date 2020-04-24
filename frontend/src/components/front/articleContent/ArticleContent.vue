<template>
  <div class="contentClass">
    <el-card>
      <p style="font-size: 30px;margin: 10px  0px">
        <b>【{{ articleInfo.attributeName }}】{{ articleInfo.title }}</b>
      </p>
      <el-button
        type="plain"
        v-for="tag in articleInfo.tagList"
        :key="tag.tagId"
        size="mini"
        :style="{
          'background-color': color[articleInfo.tagList.indexOf(tag) % 10],
          color: 'white',
          padding: '4px 8px',
          'border-width': '0px'
        }"
        >{{ tag.tagContent }}</el-button
      >
      <div style="display: flex;justify-content: space-between;color:#808080">
        <div style="display: flex">
          <p style="font-size: 14px;padding-right: 50px">
            <i class="el-icon-s-opportunity"></i>{{ articleInfo.likeNum }}
          </p>
          <p style="font-size: 14px">
            <i class="el-icon-view"></i>
            {{ articleInfo.clickNum }}
          </p>
        </div>
        <div style="display: flex">
          <p style="font-size: 14px">
            publish at / {{ articleInfo.publishDate }}
          </p>
        </div>
      </div>
      <div slot="default" style="padding-top: 50px">
        <viewer
          :initial-value="articleContent"
          height="500px"
          ref="viewer"
        ></viewer>
      </div>
    </el-card>
    <el-card style="margin-top: 30px">
      <div class="commentList" style="text-align: center">
        <span>
          暂无评论
        </span>
      </div>
      <el-divider></el-divider>
      <div class="commentForm">
        <div style="display: flex;justify-content: space-between">
          <el-input v-model="nickName" placeholder="用户昵称"></el-input>
          <el-input v-model="emailAddress" placeholder="联系邮箱"></el-input>
          <el-tooltip
            class="item"
            effect="dark"
            content="等待开发中"
            placement="bottom"
          >
            <el-button type="primary" disabled>发表评论</el-button>
          </el-tooltip>
        </div>
        <div style="padding-top:20px">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="评论内容"
            v-model="commentContent"
          >
          </el-input>
        </div>
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
      color: ["OrangeRed", "orange", "#ffd152", "LimeGreen", "DodgerBlue"],
      nickName: "",
      emailAddress: "",
      commentContent: ""
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
  width:80%;
  margin-left: 22%;
  margin-top: 30px;
}
.el-input {
  width: 200px;
  height: 30px !important;
}
</style>
