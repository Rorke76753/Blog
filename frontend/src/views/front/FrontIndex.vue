<template>
  <div style="top:0; left: 0; height: 100%;width: 100%; position: absolute ">
    <el-row
      ><el-col :span="24"><HeadBar></HeadBar></el-col
    ></el-row>
    <el-row>
      <router-view></router-view>
    </el-row>
  </div>
  <!--    <el-footer></el-footer>-->
</template>

<script>
import HeadBar from "../../components/front/static/HeadBar";

export default {
  name: "index",
  components: { HeadBar }
};
</script>

<style scoped></style>
