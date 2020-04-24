<template>
  <div>
    <div>
      <div style="display: flex;justify-content: space-between ">
        <router-link to="/">
          <el-button type="success" class="formStyle" @click="setActivePath"
            >返回首页</el-button
          >
        </router-link>
        <el-button @click="saveArticle" type="primary" class="formStyle"
          >提交
        </el-button>
      </div>
      <el-divider></el-divider>
    </div>
    <!--    <CoverUploader></CoverUploader>-->
    <!--    <el-divider></el-divider>-->
    <el-form label-position="right" label-width="80px">
      <el-form-item label="标签" class="formStyle holdHeight">
        <el-row type="flex">
          <tagsInput ref="tagsInput"></tagsInput>
        </el-row>
      </el-form-item>
      <el-form-item label="标题" class="formStyle titleWidth" required>
        <el-row type="flex">
          <titleInput ref="titleInput"></titleInput>
        </el-row>
      </el-form-item>
      <el-form-item label="文章描述" class="formStyle">
        <el-row type="flex">
          <descriptionInput ref="description"></descriptionInput>
        </el-row>
      </el-form-item>
      <el-form-item label="选择属性" class="formStyle" required>
        <el-row type="flex">
          <attributeChoice ref="attributeChoice"></attributeChoice>
        </el-row>
      </el-form-item>
    </el-form>
    <el-divider></el-divider>
    <editor ref="editor"></editor>
  </div>
</template>

<script>
import ArticleEditor from "../../components/admin/article/ArticleEditor";
import TagsInput from "../../components/admin/article/form/TagsInput";
import Description from "../../components/admin/article/form/Description";
import Title from "../../components/admin/article/form/Title";
import AttributeChoice from "../../components/admin/article/form/AttributeChoice";
import axios from "axios";
export default {
  name: "ArticleManagementPage",

  components: {
    editor: ArticleEditor,
    tagsInput: TagsInput,
    descriptionInput: Description,
    titleInput: Title,
    attributeChoice: AttributeChoice
  },
  data() {
    return {
      title: "",
      description: "",
      tags: [],
      attribute: "",
      article: "",
      attributeList: []
    };
  },
  methods: {
    setActivePath() {
      window.sessionStorage.setItem("activePath", "/admin/homepage");
    },

    saveArticle() {
      this.attribute = this.$refs.attributeChoice.getData();
      this.title = this.$refs.titleInput.getData();
      this.description = this.$refs.description.getData();
      this.article = this.$refs.editor.getData();
      this.tags = this.$refs.tagsInput.getData();
      let putUrl = axios.defaults.baseURL + "/article";
      axios
        .post(putUrl, {
          title: this.title,
          description: this.description,
          attributeId: this.attribute,
          articleContent: this.article,
          tagList: this.tags
        })
        .then(res => {
          if (res.status === 200 && res.data !== 0) {
            this.$router.push("/admin/articles");
          } else {
            this.$message({
              showClose: true,
              message: "写文章，请稍后再试",
              type: "warning"
            });
          }
        });
    }
  }
};
</script>

<style scoped>
.formStyle {
  padding-top: 10px;
}
.holdHeight {
  height: 40px;
}
.titleWidth {
  width: 33%;
}
</style>
