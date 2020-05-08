<template>
  <div>
    <OperationTable></OperationTable>
    <el-row style="padding-top: 10px;display: flex;justify-content: start">
      <el-col></el-col>
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
import OperationTable from "../../../components/admin/table/log/OperationTable";

export default {
  name: "OperationLog",
  components: {
    OperationTable,
  }
};
</script>

<style scoped></style>
