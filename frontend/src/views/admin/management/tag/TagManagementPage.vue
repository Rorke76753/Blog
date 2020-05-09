<template>
  <div>
    <router-link to="/">
      <el-button type="success" class="formStyle">返回首页</el-button>
    </router-link>
    <TagTable ref="tagTable"></TagTable>
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
    </el-row>
  </div>
</template>

<script>
import TagTable from "../../../../components/admin/table/management/tag/TagTable";
import adminTag from "../../../../http/api/admin/tag";
export default {
  name: "TagManagementPage",
  components: {
    TagTable
  },
  data() {
    return {
      pageSizes: [5, 10, 20, 50],
      pageSize: 10,
      currentPage: 1,
      totalElements: 1,
      tagList: []
    };
  },
  methods: {
    handleSizeChange(res) {
      this.pageSize = res;
      this.currentPage = 1;
      this.initData();
    },
    handleCurrentChange(res) {
      this.currentPage = res;
      this.initData();
    },

    initData() {
      adminTag
        .getTagRelativeArticles({
          page: this.currentPage,
          pageSize: this.pageSize
        })
        .then(res => {
          this.tagList = res.data.content;
          this.totalElements = res.data.totalElements;
          this.currentPage = res.data.pageable.pageNumber + 1;
        })
        .finally(() => {
          this.transferData();
        });
    },
    transferData() {
      this.$refs.tagTable.setData(this.tagList);
    }
  },
  created() {
    this.initData();
  },
  mounted() {
    this.transferData();
  }
};
</script>

<style scoped></style>
