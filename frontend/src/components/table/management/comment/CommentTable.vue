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
      <el-col :span="2">
        <router-link to="/comments">
          <el-button type="primary" class="formStyle">返回上一层</el-button>
        </router-link>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"

      style="width: 100%;margin-top: 30px"
      stripe
    >
      <el-table-column type="selection" width="45"></el-table-column>
      <el-table-column prop="id" label="评论id" width="65"></el-table-column>
      <el-table-column
        prop="attribute"
        label="评论用户"
        width="100"
      ></el-table-column>
      <el-table-column prop="title" label="相关文章标题" width="250">
        <template>
          <router-link to="/comments/1">{{ tableData[0].title }}</router-link>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="评论内容"></el-table-column>
      <el-table-column
        prop="publishDate"
        label="发表时间"
        width="200"
      ></el-table-column>

      <el-table-column label="操作" width="120">
        <el-button type="primary" icon="el-icon-edit" circle></el-button>
        <el-button type="danger" icon="el-icon-delete" circle></el-button>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "CommentTable",

  data() {
    return {
      input: "",
      tableData: [
        {
          id: 1,
          attribute: "原创",
          title: "test"
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
