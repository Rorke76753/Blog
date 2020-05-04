<template>
  <div class="contentClass">
    <el-card>
      <p style="font-size: 30px;margin: 10px  0">
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
          <div style="align-content: center">
            <div v-if="currentUserThirdPartyToken !== '' && isThirdPartyToken">
              <el-avatar
                :size="30"
                :src="currentUserInfo.avatarUrl"
              ></el-avatar>
              <el-link
                @click="visitThirdPartyUserInfo(currentUserInfo.htmlUrl)"
                >{{ currentUserInfo.username }}</el-link
              >
            </div>
            <div v-else>
              <el-button type="primary" @click="loginWithGithub()"
                >Login With GitHub</el-button
              >
            </div>
          </div>
          <div>
            <el-button
              v-show="currentUserThirdPartyToken !== '' && isThirdPartyToken"
            ></el-button>
          </div>
        </div>
        <div v-if="currentUserInfo.errorMessage !== null">
          <span style="color: red"> {{ currentUserInfo.errorMessage }}</span>
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
import axios from "axios";
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
      commentContent: "",
      currentUserThirdPartyToken: "",
      currentUserInfo: {},
      platform: "",
      isThirdPartyToken: false
    };
  },
  methods: {
    initContent(articleContent) {
      this.articleContent = articleContent;
      this.$refs.viewer.invoke("setMarkdown", this.articleContent);
    },
    validateThirdPartyToken() {
      let thirdPartyToken = JSON.parse(sessionStorage.getItem("thirdPartyToken"));
      axios
        .post("/login/oauth/user", {
          accessToken: thirdPartyToken.accessToken,
          platform: thirdPartyToken.platform
        })
        .then(res => {
          if (res.status === 200) {
            if (res.data.errorMessage === null) {
              this.isThirdPartyToken = true;
            }
            this.currentUserInfo = res.data;
          }
        });
    },
    visitThirdPartyUserInfo(htmlUrl) {
      window.location.href = htmlUrl;
    },
    loginWithGithub() {
      sessionStorage.setItem("afterLogin", this.$route.path);
      axios.get("/login/oauth").then(res => {
        if (res.status === 200) {
          window.location.href = res.data;
        }
      });
    }
  },
  created() {
    if (
      sessionStorage.getItem("thirdPartyToken") !== null &&
      sessionStorage.getItem("thirdPartyToken") !== undefined
    ) {
      this.currentUserThirdPartyToken = JSON.parse(
        sessionStorage.getItem("thirdPartyToken")
      ).accessToken;
      this.platform = JSON.parse(
        sessionStorage.getItem("thirdPartyToken")
      ).platform;
      this.validateThirdPartyToken();
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
.el-input {
  width: 200px;
  height: 30px !important;
}
</style>
