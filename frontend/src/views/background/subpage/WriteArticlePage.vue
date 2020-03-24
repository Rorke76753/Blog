<template>
  <div>
    <div>
      <div style="display: flex;justify-content: space-between ">
        <a href="/">
          <el-button type="success" class="formStyle" @click="setActivePath"
            >返回首页</el-button
          >
        </a>
        <el-button @click="getContent" type="primary" class="formStyle"
          >提交
        </el-button>
      </div>
      <el-divider></el-divider>
    </div>
    <CoverUploader></CoverUploader>
    <el-divider></el-divider>
    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章标签</p></el-col>
      <TagsInput ref="TagsInput"></TagsInput>
    </el-row>
    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章标题</p></el-col>
      <Title ref="Title"></Title>
    </el-row>
    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章类型</p></el-col>
      <AttributeChoice ref="AttributeChoice"></AttributeChoice>
    </el-row>
    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章描述</p></el-col>
      <Description ref="Description"></Description>
    </el-row>
    <el-divider></el-divider>
    <ArticleEditor class="formStyle" ref="ArticleEditor"></ArticleEditor>
  </div>
</template>

<script>
import ArticleEditor from "../../../components/article/ArticleEditor";
import CoverUploader from "../../../components/article/CoverUploader";
import TagsInput from "../../../components/article/form/TagsInput";
import AttributeChoice from "../../../components/article/form/AttributeChoice";
import Description from "../../../components/article/form/Description";
import Title from "../../../components/article/form/Title";

export default {
  name: "WriteArticlePage",
  components: {
    ArticleEditor,
    CoverUploader,
    TagsInput,
    AttributeChoice,
    Description,
    Title
  },
  data() {
    return {};
  },
  methods: {
    getContent() {
      this.$refs.ArticleEditor.getArticle();
    },
    onSubmit() {
      console.log(document.getElementsByName("articleContent"));
    },
    setActivePath() {
      window.sessionStorage.setItem("activePath", "/homepage");
    }
  },

};
</script>

<style scoped>
.formStyle {
  padding-top: 10px;
}
.descriptionStyle {
  font-size: 14px;
  align: left;
}
</style>
