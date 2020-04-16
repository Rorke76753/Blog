<template>
  <div>
    <ArticleChoiceTable></ArticleChoiceTable>
    <el-row style="padding-top: 10px;display: flex;justify-content: start">
      <el-col><Pagination></Pagination></el-col>
    </el-row>
  </div>
</template>

<script>
import ArticleChoiceTable from "../../../../components/admin/table/management/comment/ArticleChoiceTable";
import Pagination from "../../../../components/admin/table/Pagination";

export default {
  name: "CommentManagementPage",
  components: {
    ArticleChoiceTable,
    Pagination
  }
};
</script>

<style scoped></style>
