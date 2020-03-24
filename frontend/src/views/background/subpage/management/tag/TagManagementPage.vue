<template>
  <div>
    <a href="/">
      <el-button type="success" class="formStyle" @click="setActivePath"
      >返回首页</el-button
      >
    </a>
    <TagTable></TagTable>
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
import TagTable from "../../../../../components/table/tag/TagTable";
import Pagination from "../../../../../components/table/Pagination";

export default {
  name: "TagManagementPage",
  components: {
    TagTable,
      Pagination
  }
};
</script>

<style scoped></style>
