<template>
  <div>
    <el-table
      :data="tagList"
      style="width: auto;margin-top: 30px"
      stripe
      :cell-style="rowClass"
      :header-cell-style="rowClass"
    >
      <el-table-column prop="tagId" label="标签id" width="65"></el-table-column>
      <el-table-column
        prop="relativeNum"
        label="相关文章数"
        width="400"
      ></el-table-column>
      <el-table-column prop="tagContent" label="标签内容"></el-table-column>
      <el-table-column label="操作" width="224">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="modifyTagContent(scope.row)"
            >修改</el-button
          >
          <el-button
            type="warning"
            icon="el-icon-search"
            size="mini"
            @click="
              getRelativeArticle(
                scope.row.tagId,
                scope.row.tagContent,
                scope.row.hasChildren
              )
            "
            >查看相关文章</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="dynamicTitle" :visible.sync="dialogTableVisible">
      <ArticleInfoTable ref="articleInfo"></ArticleInfoTable>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import ArticleInfoTable from "../article/ArticleInfoTable";
export default {
  name: "TagTable",
  components: {
    ArticleInfoTable
  },
  data() {
    return {
      input: "",
      tagList: [],
      dynamicTitle: "",
      dialogTableVisible: false
    };
  },
  methods: {
    setActivePath() {
      window.sessionStorage.setItem("activePath", "/homepage");
    },

    setData(tagList) {
      this.tagList = tagList;
      for (let i = 0; i < this.tagList.length; i++) {
        if (this.tagList[i].relativeNum > 0) {
          this.tagList[i].hasChildren = true;
        } else {
          this.tagList[i].hasChildren = false;
        }
      }
    },

    rowClass() {
      return "text-align:center";
    },

    modifyTagContent(row) {
      let tagId = row.tagId;
      let tagContent = row.tagContent;
      let relativeNum = row.relativeNum;
      this.$prompt('将标签内容 "' + tagContent + '" 更新为：', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /[^ \f\n\r\t\v]/,
        inputErrorMessage: "标签内容不可为空"
      }).then(({ value }) => {
        let putTagUrl = axios.defaults.baseURL + "/tag/" + tagId;
        axios
          .put(putTagUrl, {
            tagId: tagId,
            tagContent: value,
            relativeNum: relativeNum
          })
          .then(res => {
            if (res.status === 200) {
              let resId = res.data.tagId;
              let resContent = res.data.tagContent;
              let resRelativeNum = res.data.relativeNum;
              if (
                resId === tagId &&
                resContent === value &&
                resRelativeNum === resRelativeNum
              ) {
                this.$message({
                  message: "修改成功",
                  type: "success"
                });
                row.tagContent = value;
              } else {
                this.$message({
                  message: "修改未成功，请稍后再试",
                  type: "warning"
                });
              }
            } else {
              this.$message({
                message: "修改发生错误，请稍后再试",
                type: "danger"
              });
            }
          });
      });
    },

    getRelativeArticle(tagId, tagContent, hasChildren) {
      if (hasChildren) {
        this.dynamicTitle = "标签 '" + tagContent + "' 相关的文章";
        this.dialogTableVisible = true;
        let relativeArticleUrl =
          axios.defaults.baseURL + "/tag/" + tagId + "/relative";
        axios.get(relativeArticleUrl).then(res => {
          this.$refs.articleInfo.setArticleInfoList(res.data, false);
        });
      } else {
        this.$message({
          message: '标签 \''+tagContent+'\'暂时没有相关文章哦~',
          type: 'warning'
        });
      }
    }
  }
};
</script>

<style scoped></style>
