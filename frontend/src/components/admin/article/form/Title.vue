<template>
  <el-input
    v-model="title"
    placeholder="标题"
    clearable
    maxlength="30"
    show-word-limit
  ></el-input>
</template>

<script>
export default {
  name: "Title",
  data() {
    return {
      title:"",
    };
  },
  methods: {
    setTitleInput(title){
      this.title = title;
    },
    getData() {
      return this.title;
    }
  }
};
</script>

<style scoped>
.el-input {
  width: 100%;
}
</style>
