<template>
  <div>
    <div>
      <el-row :gutter="20">
        <el-col :span="2">
          <router-link to="/">
            <el-button type="success" class="formStyle" @click="setActivePath"
              >返回首页</el-button
            >
          </router-link>
        </el-col>
        <el-col :span="4">
          <el-input
            placeholder="标题"
            clearable
            maxlength="30"
            show-word-limit
            v-model="titleInput"
          ></el-input>
        </el-col>
        <el-col :span="4">
          <el-select
            placeholder="文章类型"
            v-model="selectValue.attributeName"
            @change="selectAttribute"
          >
            <el-option
              v-for="attribute in articleAttribute"
              :key="attribute.attributeId"
              :label="attribute.attributeName"
              :value="attribute"
              class="attributeChoice"
            ></el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button
            icon="el-icon-search"
            round
            type="primary"
            @click="searchWithTitle"
          >
            搜索
          </el-button>
        </el-col>
      </el-row>
      <el-table
        :data="articleList"
        style="width: 100%;margin-top: 30px"
        stripe
        @select="selectMultiple"
        @select-all="selectAll"
      >
        <el-table-column type="selection" width="45"></el-table-column>
        <el-table-column
          prop="articleId"
          label="文章id"
          width="65"
        ></el-table-column>
        <el-table-column
          prop="attributeName"
          label="文章属性"
          width="100"
        ></el-table-column>
        <el-table-column prop="title" label="文章标题" width="250">
        </el-table-column>

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
          <template slot-scope="scope">
            <router-link :to="{ path: '/article/' + scope.row.id }"
              ><el-button type="primary" icon="el-icon-edit" circle></el-button
            ></router-link>
            <el-button
              type="danger"
              icon="el-icon-delete"
              circle
              @click="
                deleteArticle(scope.$index, scope.row.id, scope.row.title)
              "
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-row style="padding-top: 10px;display: flex;justify-content: start">
      <el-col
        ><div class="block">
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizes"
            :page-size="pageSize"
            layout="sizes, prev, pager, next"
            :total="totalElements"
          >
          </el-pagination></div
      ></el-col>
      <el-col
        ><el-button
          style="width: 120px;height:100%;float: right"
          type="danger"
          icon="el-icon-delete"
          @click="multiDelete"
          >批量删除</el-button
        ></el-col
      >
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "ArticleManagementPage",
  components: {},
  data() {
    return {
      articleAttribute: [],
      selectValue: {
        attributeName: "",
        attributeId: ""
      },
      titleInput: "",
      articleList: [],
      multiDeleteArr: [],
      multiId: [],
      currentPage: 1,
      totalElements: 1,
      pageSizes: [5, 10, 20, 50],
      pageSize: 10
    };
  },
  created() {
    this.getData(this.currentPage, this.pageSize);
  },
  methods: {
    selectAttribute(attribute) {
      this.selectValue = attribute;
    },
    getData(page, pageSize) {
      let url = axios.defaults.baseURL + "/articles";
      let _this = this;
      axios
        .get(url, {
          params: {
            page: page,
            pageSize: pageSize
          }
        })
        .then(res => {
          _this.articleList = res.data.content;
          _this.totalElements = res.data.totalElements;
        })
        .catch(error => {
          console.log(error);
        });
    },

    handleSizeChange(res) {
      this.pageSize = res;
      this.currentPage = 1;
      this.getData(this.currentPage, this.pageSize);
    },

    searchWithTitle() {
      let url = axios.defaults.baseURL + "/articles/searchTitle";
      axios
        .get(url, {
          data: {
            title: this.titleInput,
            currentPage: this.currentPage,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          console.log(res);
          this.articleList = res.data.content;
          this.totalElements = res.data.totalElements;
        });
    },

    setActivePath() {},

    handleCurrentChange(res) {
      this.currentPage = res;
      this.getData(this.currentPage, this.pageSize);
    },

    currentPage2() {
      return this.articleList.number + 1;
    },

    selectMultiple(select, row) {
      this.multiDeleteArr = select;
      console.log(select, row);
    },

    selectAll(selection) {
      this.multiDeleteArr = selection;
    },

    multiDelete() {
      let confirmMessage =
        "是否删除文章：共" + this.multiDeleteArr.length + "篇文章";
      let deleteUrl = axios.defaults.baseURL + "/articles";
      this.$confirm(confirmMessage, "确认删除多篇文章", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          for (let i = 0; i < this.multiDeleteArr.length; i++) {
            this.multiId.push(this.multiDeleteArr[i].id);
          }
          axios
            .delete(deleteUrl, {
              params: {
                deleteList: this.multiId + ""
              }
            })
            .then(res => {
              if (res) {
                this.$message({
                  type: "success",
                  message: "删除成功!"
                });
                for (let i = 0; i < this.multiDeleteArr.length; i++) {
                  let index = this.articleList.indexOf(
                    this.multiDeleteArr[i],
                    0
                  );
                  this.articleList.splice(index, 1);
                }
              } else {
                this.$message({
                  type: "waring",
                  message: "部分删除成功!"
                });
                location.reload();
              }
            })
            .catch(error => {
              console.log(error);
            })
            .finally(_ => {
              console.log(_);
              this.multiDeleteArr = [];
              this.multiId = [];
            });
        })
        .catch(() => {});
    },

    deleteArticle(index, articleId, articleTitle) {
      let confirmMessage = "是否删除文章：" + articleTitle;
      let deleteUrl = axios.defaults.baseURL + "/article/" + articleId;
      console.log(deleteUrl);
      this.$confirm(confirmMessage, "确认删除文章", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          axios
            .delete(deleteUrl)
            .then(res => {
              if (res.data) {
                this.articleList.splice(index, 1);
                this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(() => {});
    }
  }
};
</script>

<style scoped></style>
