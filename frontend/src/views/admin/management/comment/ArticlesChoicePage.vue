<template>
  <div>
    <ArticleChoiceTable></ArticleChoiceTable>
    <el-row style="padding-top: 10px;display: flex;justify-content: start">
    </el-row>
  </div>
</template>

<script>
import ArticleChoiceTable from "../../../../components/admin/table/management/comment/ArticleChoiceTable";

export default {
  name: "CommentManagementPage",
  components: {
    ArticleChoiceTable,
  }
};
</script>

<style scoped></style>
