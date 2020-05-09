<template>
  <div>
    <el-row>
      <el-col :span="17"
        ><div class="subHeaderClass">
          <span style="padding: 10px">文章列表</span>
          <div class="orderClass">
            排序：
            <el-button type="text" @click="sortArticleInfo('publishDate')"
              >按最新</el-button
            >|
            <el-button type="text" @click="sortArticleInfo('likeNum')"
              >按点赞数</el-button
            >
          </div>
        </div>

        <div v-if="articleInfoList.length > 0">
          <div
            v-for="articleInfo in articleInfoList"
            :key="articleInfo.articleId"
          >
            <ArticleInfo :article-info="articleInfo"></ArticleInfo>
          </div>
        </div>
        <div v-else>
          <el-card class="emptyCard" shadow="never">
            <img src="../../../assets/logo.png" alt="missing article" />
            <div slot="default" style="text-align: center">
              暂时没有文章哦
            </div>
          </el-card>
        </div>
        <el-pagination
          page-size="10"
          @current-change = "handleCurrentChange"
          :page-count="totalPage"
          layout="prev, pager, next"
          :total="totalElements"
          style="margin-left: 50%;margin-top: 30px"
        >
        </el-pagination>
      </el-col>
      <el-col :span="7">
        <div style="padding-left: 100px;width: 80%;margin-top: 30px">
          <el-card shadow="never">
            <div>
              //TODO: 个人简介
            </div>
            <el-divider></el-divider>
            <div>
              //TODO：个人简介
            </div>
          </el-card>
          <div>
            <div>
              <div
                style="border-left: #0088ff 5px solid;padding-left: 20px;margin-top: 20px"
              >
                <h3>标签墙</h3>
              </div>
              <p style="padding-left: 20px;padding-top: 20px">
                <el-button
                  v-for="tag in tagList"
                  :key="tag.tagId"
                  size="mini"
                  :style="{
                    padding: '5px 5px',
                    'margin-left': '5px',
                    'margin-top': '5px',
                    width: '152px',
                    'text-align': 'left',
                    'font-size': '12px',
                    'background-color': tagColor[tagList.indexOf(tag) % 5],
                    color: 'white',
                    'border-width': '0px'
                  }"
                  >{{ tag.tagContent }}[{{ tag.relativeNum }}]</el-button
                >
              </p>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import frontArticleList from "../../../http/api/front/articleList";
import frontTagList from "../../../http/api/front/tagList";
import ArticleInfo from "../../../components/front/articleInfo/ArticleInfo";
import { GetCurrentBrowser } from "../../../assets/ClientInfo";

export default {
  name: "FrontArticleInfo",
  data() {
    return {
      articleInfoList: [],
      tagList: [],
      sortBy: "publishDate",
      tagColor: [
        "#ff0000",
        "rgba(253,88,19,0.8)",
        "#fcbf28",
        "#26b420",
        "#0090ff"
      ],
      currentPage: 1,
      totalElements: 1,
      totalPage: 1
    };
  },
  methods: {
    getBrowser: function() {
      sessionStorage.setItem("browser", GetCurrentBrowser());
    },
    initData() {
      this.getBrowser();
      frontArticleList
        .dynamicArticlesPagination({
          page: this.currentPage,
          pageSize: 10,
          sortBy: this.sortBy
        })
        .then(res => {
          this.articleInfoList = res.data.content;
          this.totalElements = res.data.totalElements;
          this.currentPage = res.data.pageable.pageNumber + 1;
        });
    },
    sortArticleInfo(orderBy) {
      this.orderBy = orderBy;
      this.initData();
    },
    handleCurrentChange(res) {
      this.currentPage = res;
      this.initData();
    },
    getTags() {
      let dynamicSearch = {
        page: 1,
        pageSize: 10,
        sortBy: "relativeNum"
      };
      frontTagList.getTagWallList(dynamicSearch).then(res => {
        this.tagList = res.data.content;
      });
    }
  },
  created() {
    this.initData();
    this.getTags();
  },
  components: {
    ArticleInfo
  }
};
</script>

<style scoped>
.emptyCard {
  height: 100%;
  margin-left: 22%;
  margin-top: 30px;
}
.subHeaderClass {
  width: 80%;
  margin-left: 22%;
  margin-top: 30px;
  display: flex;
  justify-content: space-between;
}
.orderClass {
  font-size: 14px;
}
</style>
