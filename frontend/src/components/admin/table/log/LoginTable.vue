<template>
  <div>
    <el-row>
      <el-col :span="2">
        <router-link to="/">
          <el-button type="success" class="formStyle" @click="setActivePath"
            >返回首页</el-button
          >
        </router-link>
      </el-col>
      <el-col :span="3">
        <el-date-picker
          v-model="dateValue"
          type="date"
          placeholder="选择日期时间"
        >
        </el-date-picker>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" round>
          搜索
        </el-button>
      </el-col>
    </el-row>
    <el-table :data="tableData" style="width: 100%;margin-top: 30px" stripe>
      <el-table-column type="selection" width="45"></el-table-column>
      <el-table-column
        prop="title"
        label="用户名称"
        width="205"
      ></el-table-column>
      <el-table-column
        prop="method"
        label="登陆登录方式"
        width="300"
      ></el-table-column>
      <el-table-column prop="id" label="登录地址" width="500"></el-table-column
      ><el-table-column
        prop="attribute"
        label="登陆状态"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="description"
        label="浏览器"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="publishDate"
        label="登录时间"
        width="522"
      ></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "LoginTable",

  data() {
    return {
      input: "",
      dateValue: "",
      tableData: [
        {
          id: 1,
          attribute: "原创"
        }
      ]
    };
  },
  methods: {
    setActivePath() {
      window.sessionStorage.setItem("activePath", "/homepage");
    }
  }
};
</script>

<style scoped></style>
