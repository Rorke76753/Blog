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
      <!--            <el-footer>Footer</el-footer>-->
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "../../components/static/SideBar";
import HeadBar from "../../components/static/HeadBar";
export default {
  data() {
    return {};
  },
  methods: {},
  components: { SideBar, HeadBar }
};
</script>

<style scoped>
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-header {
  height: 52px !important;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.12), 0 0 4px rgba(0, 0, 0, 0.04) !important;
}

.el-aside {
  background-color: #2c3e50;
  text-align: left;
  line-height: 200px;
}

.el-main {
  color: #333;
  text-align: left;
  font-size: 30px;
  background-color: beige;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
