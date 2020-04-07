<template>
  <div>
    <el-select placeholder="文章类型" v-model="selectValue">
      <el-option
        v-for="attribute in articleKind"
        :key="attribute.value"
        :label="attribute.label"
        :value="attribute.value"
      ></el-option>
    </el-select>
  </div>
</template>

<script>
export default {
  name: "AttributeChoice",
  data() {
    return {
      articleKind: [
        {
          value: "原创",
          label: "原创"
        },
        {
          value: "转载",
          label: "转载"
        }
      ],
      selectValue: ""
    };
  }
};
</script>

<style scoped>
.el-select {
  width: 290px;
}
</style>
