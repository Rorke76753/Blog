<template>
  <div>
    <div>
      <div style="display: flex;justify-content: space-between ">
        <router-link to="/">
          <el-button type="success" class="formStyle" @click="setActivePath"
            >返回首页</el-button
          >
        </router-link>
        <el-button @click="updateArticle" type="primary" class="formStyle"
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
          <tagsInput ref="tagsInput" ></tagsInput>
        </el-row>
      </el-form-item>
      <el-form-item label="标题" class="formStyle">
        <el-row type="flex">
          <titleInput ref="titleInput" ></titleInput>
        </el-row>
      </el-form-item>
      <el-form-item label="文章描述" class="formStyle">
        <el-row type="flex">
          <descriptionInput
            ref="descriptionInput"

          ></descriptionInput>
        </el-row>
      </el-form-item>
      <el-form-item label="选择属性" class="formStyle">
        <el-row type="flex">
          <attributeChoice
            ref="attributeChoice"
          ></attributeChoice>
        </el-row>
      </el-form-item>
    </el-form>
    <el-divider></el-divider>
    <editor ref="editor"></editor>
  </div>
</template>

<script>
import ArticleEditor from "../../../../components/admin/article/ArticleEditor";
import TagsInput from "../../../../components/admin/article/form/TagsInput";
import Description from "../../../../components/admin/article/form/Description";
import Title from "../../../../components/admin/article/form/Title";
import AttributeChoice from "../../../../components/admin/article/form/AttributeChoice";

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
      articleId: "",
      title: "",
      description: "",
      articleContent: "",
      tags: [],
      attributeName: "",
      attributeId: ""
    };
  },
  methods: {
    initData() {
      let requestUrl = axios.defaults.baseURL + "/article/" + this.articleId;
      axios.get(requestUrl).then(res => {
        if (res.status === 200) {
          this.articleContent = res.data.articleContent;
          this.$refs.editor.setData(this.articleContent);
        }
      });
    },

    transferDataToComponent(){
      this.$refs.tagsInput.setTagsInput(this.tags);
      this.$refs.titleInput.setTitleInput(this.title);
      this.$refs.descriptionInput.setDescriptionInput(this.description);
      this.$refs.attributeChoice.setAttributeChoice(this.attributeName);
    },

    setActivePath() {
      window.sessionStorage.setItem("activePath", "/admin/homepage");
    },

    getData() {
      this.attributeId = this.$refs.attributeChoice.getData();
      this.title = this.$refs.titleInput.getData();
      this.description = this.$refs.descriptionInput.getData();
      this.tags = this.$refs.tagsInput.getData();
      this.articleContent = this.$refs.editor.getData();
    },

    updateArticle() {
      let putUrl = axios.defaults.baseURL + "/article/" + this.articleId;
      this.getData();
      axios
        .put(putUrl, {
          articleId: this.articleId,
          title: this.title,
          description: this.description,
          attributeId: this.attributeId,
          articleContent: this.articleContent,
          tagList: this.tags
        })
        .then(res => {
          if (res.status === 200 && res.data !== 0) {
            this.$router.push("/admin/articles");
          } else {
            this.$message({
              showClose: true,
              message: "更新文章失败，请稍后再试",
              type: "warning"
            });
          }
        });
    }
  },

  created() {
    let data = this.$route.params.articleInfo;
    if (typeof data == "undefined") {
      this.$router.push("/admin/articles");
    } else {
      this.articleId = data.articleId;
      this.title = data.title;
      this.description = data.description;
      this.attributeName = data.attributeName;
      this.tags = data.tagList;
      this.initData();
    }
  },
  mounted() {
    this.transferDataToComponent();
  }
};
</script>

<style scoped>
.formStyle {
  padding-top: 10px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.holdHeight {
  height: 40px;
}
</style>
