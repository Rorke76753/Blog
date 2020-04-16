<template>
  <el-input
    v-model="description"
    placeholder="文章描述，若不填写则自动选择文章前50个字为文章描述"
    clearable
    maxlength="50"
    show-word-limit
  ></el-input>
</template>

<script>
export default {
  name: "Description",
  data() {
    return {
      description:""
    };
  },
  methods: {
    setDescriptionInput(description){
      this.description = description;
    },
    getData() {
      return this.description;
    }
  }
};
</script>

<style scoped>
.el-input {
  width: 47%;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
</style>
