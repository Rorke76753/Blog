<template>
  <el-table
    :data="tableData"
    border
    style="width: auto;margin-top: 30px"
    stripe
  >
    <el-table-column type="selection" width="40"></el-table-column>
    <el-table-column prop="id" label="标签id" width="70"></el-table-column>
    <el-table-column
      prop="attribute"
      label="标签内容"
      width="100"
    ></el-table-column>
    <el-table-column
      prop="publishDate"
      label="标签发表时间"
    ></el-table-column>
    <el-table-column
      prop="relativeArticles"
      label="相关文章数"
    ></el-table-column>
    <el-table-column label="操作" width="120">
      <el-button type="primary" icon="el-icon-edit" circle></el-button>
      <el-button type="danger" icon="el-icon-delete" circle></el-button>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "TagTable",
  data() {
    return {
      input: "",
      tableData: [
        {
          id: 1,
          attribute: "原创"
        }
      ]
    };
  },
  methods:{
    setActivePath() {
      window.sessionStorage.setItem("activePath", "/homepage");
    }
  }
};
</script>

<style scoped></style>
