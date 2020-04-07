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
      <el-col :span="4">
        <Title></Title>
      </el-col>
      <el-col :span="4">
        <AttributeChoice ref="AttributeChoice"></AttributeChoice>
      </el-col>
      <el-col :span="2">
        <el-button icon="el-icon-search" round type="primary">
          搜索
        </el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"

      style="width: 100%;margin-top: 30px"
      stripe
    >
      <el-table-column type="selection" width="45"></el-table-column>
      <el-table-column prop="id" label="文章id" width="65"></el-table-column>
      <el-table-column
        prop="attribute"
        label="文章属性"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="title"
        label="文章标题"
        width="250"
      ></el-table-column>

      <el-table-column prop="description" label="文章描述"></el-table-column>
      <el-table-column
        prop="publishDate"
        label="发表时间"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="lastUpdate"
        label="最后修改"
        width="200"
      ></el-table-column>

      <el-table-column prop="top" label="置顶" width="80"></el-table-column>
      <el-table-column label="操作" width="120">
        <el-button type="primary" icon="el-icon-edit" circle></el-button>
        <el-button type="danger" icon="el-icon-delete" circle></el-button>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import AttributeChoice from "../../../article/form/AttributeChoice";
import Title from "../../../article/form/Title";

export default {
  name: "ArticleTable",
  components: {
    Title,
    AttributeChoice
  },
  data() {
    return {
      input: "",
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
