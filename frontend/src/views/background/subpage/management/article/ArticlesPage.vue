<template>
  <div>

    <ArticleTable></ArticleTable>
    <el-row style="padding-top: 10px;display: flex;justify-content: start">
      <el-col><Pagination></Pagination></el-col>
      <el-col
        ><el-button
          style="width: 120px;height:100%;float: right"
          type="danger"
          icon="el-icon-delete"
          >批量删除</el-button
        ></el-col
      >
    </el-row>
  </div>
</template>

<script>
import ArticleTable from "../../../../../components/table/article/ArticleTable";
import Pagination from "../../../../../components/table/Pagination";

export default {
  name: "ArticleManagementPage",
  components: {
    ArticleTable,
    Pagination
  }
};
</script>

<style scoped></style>
