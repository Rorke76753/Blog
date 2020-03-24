import Vue from "vue";
import VueRouter from "vue-router";
import Admin from "../views/background/Admin.vue";
import Home from "../views/background/subpage/Home";
import WriteArticlePage from "../views/background/subpage/WriteArticlePage";
import ArticleManagementPage from "../views/background/subpage/management/article/ArticlesPage";
import TagManagementPage from "../views/background/subpage/management/tag/TagManagementPage";
import ArticlesChoicePage from "../views/background/subpage/management/comment/ArticlesChoicePage";
import CommentManagementPage from "../views/background/subpage/management/comment/CommentManagementPage";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/homepage",
    component: Admin,
    children: [
      {
        path: "/homepage",
        component: Home
      },
      {
        path: "/articles",
        name: "文章管理",
        component: ArticleManagementPage
      },
      {
        path: "/tags",
        name: "标签管理",
        component: TagManagementPage
      },
      {
        path: "/comments",
        name: "文章选择",
        component: ArticlesChoicePage
      },
      {
        path: "/comments/:artcileid",
        name: "评论管理",
        component: CommentManagementPage
      },
      {
        path: "/operationLog",
        name: "操作日志"
      },
      {
        path: "/loginLog",
        name: "登录日志"
      },
      {
        path: "/writeArticle",
        name: "写文章",
        component: WriteArticlePage
      }
    ]
  }
];

const router = new VueRouter({
  routes
});

export default router;
