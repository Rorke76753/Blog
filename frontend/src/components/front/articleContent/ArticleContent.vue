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
      <div class="commentList">
        <div v-for="comment in commentList" :key="commentList.indexOf(comment)">
          <div style="display: flex;justify-content: space-between">
            <div v-if="comment.errorMessage === null">
              <el-avatar :size="30" :src="comment.avatarUrl"></el-avatar>
              <el-link @click="visitThirdPartyUserInfo(comment.htmlUrl)">{{
                comment.username
              }}</el-link>
            </div>
            <div v-else>
              <span style="color: red">{{ comment.errorMessage }}</span>
            </div>
            <div>
              <span>{{ comment.publishDate }}</span>
            </div>
          </div>

          <div style="padding-top: 10px">
            <viewer :initial-value="comment.commentContent"></viewer>
          </div>
          <div style="display:flex;justify-content: flex-end">
            <div style="display: flex">
              <span>#{{ commentList.indexOf(comment) }}</span>
              <el-tooltip
                class="item"
                effect="dark"
                content="暂未开放"
                placement="bottom"
              >
                <el-button size="mini" type="text">回复</el-button>
              </el-tooltip>
            </div>
          </div>
          <el-divider
            v-if="commentList.indexOf(comment) < commentList.length - 1"
          ></el-divider>
        </div>
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
              plain
              @click="postNewComment"
              ref="postCommentBtn"
              >发表评论</el-button
            >
          </div>
        </div>
        <div v-if="currentUserInfo.errorMessage !== null">
          <span style="color: red"> {{ currentUserInfo.errorMessage }}</span>
        </div>
        <div style="padding-top:20px">
          <el-input
            type="textarea"
            maxlength="200"
            show-word-limit
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
import oauthService from "../../../http/api/front/oauth/oauthLogin";
import frontComment from "../../../http/api/front/comment";
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
      commentContent: "",
      currentUserThirdPartyToken: "",
      currentUserInfo: {},
      platform: "",
      isThirdPartyToken: false,
      commentList: []
    };
  },
  methods: {
    postNewComment() {
      this.$refs.postCommentBtn.disabled = true;
      frontComment
        .postComment({
          commentContent: this.commentContent,
          articleId: this.$route.params.articleId,
          accessToken: this.currentUserThirdPartyToken,
          platform: this.platform
        })
        .then(res => {
          this.commentList.push(res.data);
          this.commentContent = "";
        })
        .catch(() => {
          this.$message.error("评论出错，请稍后再试");
        })
        .finally(() => {
          this.$refs.postCommentBtn.disabled = false;
        });
    },
    getCommentList() {
      frontComment
        .getCommentsOfArticle(this.$route.params.articleId)
        .then(res => {
          this.commentList = res.data;
        });
    },
    initContent(articleContent) {
      this.articleContent = articleContent;
      this.$refs.viewer.invoke("setMarkdown", this.articleContent);
    },
    validateThirdPartyToken() {
      let thirdPartyToken = JSON.parse(
        sessionStorage.getItem("thirdPartyToken")
      );
      oauthService
        .validateToken({
          accessToken: thirdPartyToken.accessToken,
          platform: thirdPartyToken.platform
        })
        .then(res => {
          if (res.data.errorMessage === null) {
            this.isThirdPartyToken = true;
          }
          this.currentUserInfo = res.data;
        });
    },
    visitThirdPartyUserInfo(htmlUrl) {
      window.location.href = htmlUrl;
    },
    loginWithGithub() {
      sessionStorage.setItem("afterLogin", this.$route.path);
      oauthService.getOauthLink();
      oauthService.getOauthLink("Github").then(res => {
        window.location.href = res.data;
      });
    }
  },
  created() {
    if (
      sessionStorage.getItem("thirdPartyToken") !== null &&
      sessionStorage.getItem("thirdPartyToken") !== undefined
    ) {
      let thirdPartyToken = JSON.parse(
        sessionStorage.getItem("thirdPartyToken")
      );
      this.currentUserThirdPartyToken = thirdPartyToken.accessToken;
      this.platform = thirdPartyToken.platform;
      this.validateThirdPartyToken();
    }
    this.getCommentList();
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
