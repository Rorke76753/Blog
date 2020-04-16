<template>
  <div>
    <el-upload
      class="upload-demo formStyle"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      auto-upload
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">上传封面</div>
      <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "CoverUploader"
};
</script>

<style scoped></style>
