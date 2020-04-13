import Vue from "vue";
import VueRouter from "vue-router";
import Admin from "../views/background/Admin.vue";
import Home from "../views/background/subpage/Home";
import WriteArticlePage from "../views/background/subpage/WriteArticlePage";
import ArticleListPage from "../views/background/subpage/management/article/ArticleListPage";
import TagManagementPage from "../views/background/subpage/management/tag/TagManagementPage";
import ArticlesChoicePage from "../views/background/subpage/management/comment/ArticlesChoicePage";
import CommentManagementPage from "../views/background/subpage/management/comment/CommentManagementPage";
import OperationLog from "../views/background/subpage/log/OperationLog";
import LoginLog from "../views/background/subpage/log/LoginLog";
import ArticleManagementPage from "../views/background/subpage/management/article/ArticleManagementPage";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/admin/homepage",
    component: Admin,
    children: [
      {
        path: "/admin/homepage",
        component: Home
      },
      {
        path: "/admin/articles",
        name: "文章管理",
        component: ArticleListPage
      },
      {
        path: "/admin/article",
        name: "ArticleManagementPage",
        component: ArticleManagementPage
      },
      {
        path: "/admin/tags",
        name: "标签管理",
        component: TagManagementPage
      },
      {
        path: "/admin/comments",
        name: "文章选择",
        component: ArticlesChoicePage
      },
      {
        path: "/admin/comments/:articleId",
        name: "评论管理",
        component: CommentManagementPage
      },
      {
        path: "/admin/operationLog",
        name: "操作日志",
        component: OperationLog
      },
      {
        path: "/admin/loginLog",
        name: "登录日志",
        component: LoginLog
      },
      {
        path: "/admin/writeArticle",
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
