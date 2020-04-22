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
          :page-size="20"
          :pager-count="11"
          layout="prev, pager, next"
          :total="1000"
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
              <div style="border-left: #0088ff 5px solid;padding-left: 20px">
                <h3>标签墙</h3>
              </div>
              <p style="padding-left: 20px">
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
import axios from "axios";
import ArticleInfo from "../../../components/front/articleInfo/ArticleInfo";
export default {
  name: "FrontArticleInfo",
  data() {
    return {
      articleInfoList: [],
      tagList: [],
      orderBy: "publishDate",
      tagColor: [
        "#ff0000",
        "rgba(253,88,19,0.8)",
        "#fcbf28",
        "#26b420",
        "#0090ff"
      ]
    };
  },
  methods: {
    initData() {
      axios
        .post("/articles", {
          page: 1,
          pageSize: 10,
          orderBy: this.orderBy
        })
        .then(res => {
          this.articleInfoList = res.data.content;
        });
    },
    sortArticleInfo(orderBy) {
      this.orderBy = orderBy;
      this.initData();
    },
    getTags() {
      axios
        .post("/tags", {
          page: 1,
          pageSize: 10
        })
        .then(res => {
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
  width: 80%;
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
