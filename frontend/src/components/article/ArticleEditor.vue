<template>
  <div>

  </div>
</template>

<script>

export default {
  name: "ArticleEditor",

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
      console.log(this.$refs.editor.invoke("getMarkdown"));
    }
  }
};
</script>

<style scoped>
.editor {
  padding-bottom: 10px;
}
</style>
