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
          <titleInput ref="titleInput"></titleInput>
        </el-col>
        <el-col :span="3">
          <attribute-choice ref="attributeChoice"></attribute-choice>
        </el-col>
        <el-col :span="5">
          <el-date-picker
            v-model="datePicker"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="至"
            start-placeholder="发表时间"
            end-placeholder="发表时间"
          >
          </el-date-picker>
        </el-col>
        <el-col :span="2">
          <el-button
            icon="el-icon-search"
            round
            type="primary"
            @click="doSearching"
          >
            搜索
          </el-button>
        </el-col>
      </el-row>
      <articleTable ref="articleTable"></articleTable>
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
import Title from "../../../../../components/article/form/Title";
import AttributeChoice from "../../../../../components/article/form/AttributeChoice";
import ArticleTable from "../../../../../components/table/management/article/ArticleInfoTable";
import axios from "axios";
export default {
  name: "ArticleManagementPage",
  components: {
    titleInput: Title,
    attributeChoice: AttributeChoice,
    articleTable: ArticleTable
  },

  data() {
    return {
      datePicker: "",
      articleAttribute: [],
      selectValue: "",
      titleInput: "",
      articleList: [],
      multiId: [],
      currentPage: 1,
      totalElements: 1,
      searchingFlag: false,
      pageSizes: [5, 10, 20, 50],
      pageSize: 10
    };
  },
  created() {
    this.initData();
  },
  methods: {
    selectAttribute(attribute) {
      this.selectValue = attribute;
    },

    initData() {
      this.dynamicTable();
    },

    handleSizeChange(res) {
      this.pageSize = res;
      this.currentPage = 1;
      this.dynamicTable();
    },

    doSearching() {
      this.currentPage = 1;
      this.dynamicTable();
    },

    dynamicTable() {
      let url = axios.defaults.baseURL + "/articles/searching";
      let startDate, endDate;
      if (this.datePicker == null) {
        startDate = null;
        endDate = null;
      } else {
        startDate = this.datePicker[0];
        endDate = this.datePicker[1];
      }
      let title, attributeId;
      if (this.$refs.titleInput === undefined) {
        title = "";
      } else {
        title = this.$refs.titleInput.getData();
      }
      if (this.$refs.attributeChoice === undefined) {
        attributeId = 0;
      } else {
        attributeId = this.$refs.attributeChoice.getData();
      }
      axios
        .post(url, {
          startDate: startDate,
          endDate: endDate,
          title: title,
          attributeId: attributeId,
          page: this.currentPage,
          pageSize: this.pageSize
        })
        .then(res => {
          this.articleList = res.data.content;
          this.totalElements = res.data.totalElements;
          this.currentPage = res.data.pageable.pageNumber + 1;
        })
        .finally(() => {
          this.transferDataToComponent();
        });
    },

    setActivePath() {},

    handleCurrentChange(res) {
      this.currentPage = res;
      this.dynamicTable();
    },

    currentPage2() {
      return this.articleList.number + 1;
    },

    multiDelete() {
      this.multiId = this.$refs.articleTable.getMultiArr();
      let confirmMessage = "是否删除文章：共" + this.multiId.length + "篇文章";
      let deleteUrl = axios.defaults.baseURL + "/articles";
      this.$confirm(confirmMessage, "确认删除多篇文章", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          axios
            .delete(deleteUrl, {
              params: {
                articleIds: this.multiId + ""
              }
            })
            .then(() => {
              location.reload();
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(() => {});
    },

    transferDataToComponent() {
      this.$refs.articleTable.setArticleInfoList(this.articleList,true);
    }
  }
};
</script>

<style scoped></style>
