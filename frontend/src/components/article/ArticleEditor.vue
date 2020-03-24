<template>
  <div>
    <editor
      :value="editorText"
      :options="editorOptions"
      :html="editorHtml"
      :visible="editorVisible"
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

export default {
  name: "ArticleEditor",
  components: {
    editor: Editor
  },
  data() {
    return {
      editorText: "",
      editorOptions: {
        hideModeSwitch: false
      },
      editorHtml: "",
      editorVisible: true
    };
  },
  methods: {
    getArticle() {
      console.log(123);
      console.log(this.$refs.editor.invoke('getMarkdown'));
    }
  }
};
</script>

<style scoped>
.editor {
  padding-bottom: 10px;
}
</style>
