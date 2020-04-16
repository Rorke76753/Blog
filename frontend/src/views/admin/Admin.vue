<template>
  <el-container
    style="top:0; left: 0; height: 100%;width: 100%; position: absolute "
  >
    <el-aside width="auto" class="el-aside">
      <SideBar></SideBar>
    </el-aside>
    <el-container>
      <el-header>
        <HeadBar></HeadBar>
      </el-header>
      <el-main>
        <el-card class="box-card">
          <router-view></router-view>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "../../components/admin/static/SideBar";
import HeadBar from "../../components/admin/static/HeadBar";
export default {
  data() {
    return {};
  },
  methods: {},
  components: { SideBar, HeadBar }
};
</script>

<style scoped>
.el-aside {
  background-color: #2c3e50;
  text-align: left;
  line-height: 200px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.el-main {
  background-color: #d7d7bd;
}
</style>
