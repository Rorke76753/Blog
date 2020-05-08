<template>
  <div>
    <el-table
      :data="articleInfoList"
      style="width: 100%;margin-top: 30px"
      stripe
      fit
      @select="selectMultiple"
      @select-all="selectAll"
    >
      <el-table-column type="selection" width="45" fixed v-if="operated"></el-table-column>
      <el-table-column
        fixed
        prop="articleId"
        label="文章id"
        width="65"
      ></el-table-column>
      <el-table-column
        prop="title"
        label="文章标题"
        width="440"
        fixed
      ></el-table-column>
      <el-table-column prop="attributeName" label="文章属性" width="100" >
      </el-table-column>
      <el-table-column
        prop="description"
        label="文章描述"
        width="720"
        v-if="operated"
      ></el-table-column>

      <el-table-column
        prop="publishDate"
        label="发表时间"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="lastUpdate"
        label="最后修改"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="clickNum"
        label="点击量"
        width="80"
      ></el-table-column>
      <el-table-column
        prop="commentNum"
        label="评论数"
        width="80"
      ></el-table-column>
      <el-table-column
        prop="likeNum"
        label="点赞数"
        width="80"
      ></el-table-column>
      <el-table-column prop="top" label="置顶" width="80" v-if="operated"></el-table-column>
      <el-table-column prop="tagList" label="标签" width="1000" v-if="operated">
        <template slot-scope="scope">
          <el-tag
            v-for="tag in scope.row.tagList"
            :key="tag.tagId"
            effect="plain"
            >{{ tag.tagContent }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right" v-if="operated">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="goToEdit(scope.row)"
            size="mini"
          ></el-button>

          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="
              deleteArticle(scope.$index, scope.row.articleId, scope.row.title)
            "
            size="mini"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ArticleTable",
  data() {
    return {
      articleInfoList: [],
      multiDeleteArr: [],
      multiId: [],
      operated: ""
    };
  },
  methods: {
    setArticleInfoList(articleInfo,operated) {
      this.articleInfoList = articleInfo;
      this.operated = operated;
    },

    setActivePath() {
      window.sessionStorage.setItem("activePath", "/homepage");
    },
    // eslint-disable-next-line no-unused-vars
    selectMultiple(select, row) {
      this.multiDeleteArr = select;
    },

    selectAll(selection) {
      this.multiDeleteArr = selection;
    },

    deleteArticle(index, articleId, articleTitle) {
      let _this = this;
      let confirmMessage = "是否删除文章：" + articleTitle;
      this.$confirm(confirmMessage, "确认删除文章", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          axios
            .delete("/admin/article/"+articleId)
            .then(res => {
              if (res.status === 200) {
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
        .catch(() => {})
        .finally(() => {
          _this.articleInfoList.splice(index, 1);
        });
    },
    goToEdit(row) {
      this.$router.push({
        name: "ArticleManagementPage",
        params: {
          articleInfo: row
        }
      });
    },
    getMultiArr() {
      for (let i = 0; i < this.multiDeleteArr.length; i++) {
        this.multiId.push(this.multiDeleteArr[i].articleId);
      }
      return this.multiId;
    }
  }
};
</script>

<style scoped></style>
