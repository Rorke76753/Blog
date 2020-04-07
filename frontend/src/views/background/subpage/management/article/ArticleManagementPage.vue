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

    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章类型</p></el-col>
      <el-select placeholder="文章类型" v-model="selectValue">
        <el-option
          v-for="attribute in articleKind"
          :key="attribute.key"
          :label="attribute.label"
          :value="attribute.value"
          class="attributeChoice"
        ></el-option>
      </el-select>
    </el-row>
    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章标题</p></el-col>
      <el-input
        v-model="title"
        placeholder="标题"
        clearable
        maxlength="30"
        show-word-limit
        class="titleInput"
      ></el-input>
    </el-row>

    <el-row type="flex" class="formStyle">
      <el-col :span="1"><p class="descriptionStyle">文章描述</p></el-col>
      <el-input
        v-model="description"
        placeholder="文章描述，若不填写则自动选择文章前50个字为文章描述"
        clearable
        maxlength="50"
        show-word-limit
        class="descriptionInput"
      ></el-input>
    </el-row>

    <el-row type="flex" class="formStyle holdHeight">
      <el-col :span="1"><p class="descriptionStyle">文章标签</p></el-col>
      <el-row type="flex"></el-row>
      <el-tag
        :key="tag"
        v-for="tag in dynamicTags"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)"
      >
        {{ tag.content }}
      </el-tag>
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
        maxlength="10"
        show-word-limit
      >
      </el-input>
      <el-button
        v-else-if="dynamicTags.length < 10"
        class="button-new-tag"
        size="small"
        @click="showInput"
        >+ New Tag
      </el-button>
      <span class="tagCount">{{ dynamicTags.length }}/10</span>
    </el-row>
    <el-divider></el-divider>
    <editor
      :initial-value="editorText"
      :options="editorOptions"
      previewStyle="vertical"
      height="700px"
      mode="markdown"
      name="articleContent"
      ref="editor"
    ></editor>
  </div>
</template>

<script>
import Editor from "@toast-ui/vue-editor/src/Editor.vue";
import "tui-editor/dist/tui-editor.css";
import "tui-editor/dist/tui-editor-contents.css";
import "codemirror/lib/codemirror.css";
import axios from "axios";
export default {
  name: "ArticleManagementPage",

  components: {
    editor: Editor
  },
  data() {
    return {
      articleKind: [
        {
          label: "原创",
          value: "原创",
          key: 1
        },
        {
          label: "转载",
          value: "转载",
          key: 2
        }
      ],
      title: "",
      description: "",
      publishDate: "",

      dynamicTags: [],
      inputVisible: false,
      inputValue: "",
      tagsSet: new Set(),

      editorText: "",
      editorOptions: {
        hideModeSwitch: false
      },

      selectValue: ""
    };
  },
  methods: {
    getData() {
      let requestUrl = axios.defaults.baseURL + this.$route.path;
      axios.get(requestUrl).then(res => {
        if (res.status === 200) {
          this.title = res.data.title;
          this.description = res.data.description;
          this.editorText = res.data.content;
          this.dynamicTags = res.data.tags;
          this.publishDate = res.data.publishDate;
          this.selectValue = res.data.attribute;
          for (let i = 0; i < this.dynamicTags.length; i++) {
            this.tagsSet.add(this.dynamicTags[i].content);
          }
          this.$refs.editor.invoke("setMarkdown", this.editorText);
        }
      });
    },

    setActivePath() {
      window.sessionStorage.setItem("activePath", "/homepage");
    },

    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        console.log(_);
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if (!this.tagsSet.has(inputValue)) {
          this.tagsSet.add(inputValue);
          let tag = {
            id: 0,
            content: inputValue
          };
          this.dynamicTags.push(tag);
        } else {
          this.$message({
            showClose: true,
            message: "不允许添加重复标签",
            type: "warning"
          });
        }
      }
      this.inputVisible = false;
      this.inputValue = "";
    },

    updateArticle() {
      let putUrl = axios.defaults.baseURL + this.$route.path;
      axios
        .put(putUrl, {
          title: this.title,
          description: this.description,
          attribute: this.selectValue,
          content: this.$refs.editor.invoke("getMarkdown"),
          tags: this.dynamicTags,
          publishDate: this.publishDate
        })
        .then(res => {
          if (res.status === 200 && res.data !== 0) {
            this.$router.push("/articles");
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
    this.getData();
  }
};
</script>

<style scoped>
.formStyle {
  padding-top: 10px;
}
.descriptionStyle {
  font-size: 14px;
  align: left;
  width: 65px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  margin-left: 10px;
  width: 190px;
  vertical-align: bottom;
}

.tagCount {
  padding-left: 30px;
  font-size: 20px;
}
.holdHeight {
  height: 40px;
}
.attributeChoice {
  width: 290px;
}
.titleInput {
  width: 300px;
}
.descriptionInput {
  width: 800px;
}
</style>
