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
      description: ""
    };
  }
};
</script>

<style scoped>
.el-input {
  width: 70%;
}
</style>
