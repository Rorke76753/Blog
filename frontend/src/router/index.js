import Vue from "vue";
import VueRouter from "vue-router";
import Admin from "../views/admin/Admin.vue";
import WriteArticlePage from "../views/admin/WriteArticlePage";
import ArticleListPage from "../views/admin/management/article/ArticleListPage";
import TagManagementPage from "../views/admin/management/tag/TagManagementPage";
import ArticlesChoicePage from "../views/admin/management/comment/ArticlesChoicePage";
import CommentManagementPage from "../views/admin/management/comment/CommentManagementPage";
import OperationLog from "../views/admin/log/OperationLog";
import LoginLog from "../views/admin/log/LoginLog";
import ArticleManagementPage from "../views/admin/management/article/ArticleManagementPage";
import FrontIndex from "../views/front/FrontIndex";
import FrontArticleContent from "../views/front/subpage/FrontArticleContent";
import FrontArticleInfo from "../views/front/subpage/FrontArticleInfo";
import FrontTimeLine from "../views/front/subpage/FrontTimeLine";
Vue.use(VueRouter);

const routes = [
  {
    path: "/admin",
    redirect: "/admin/homepage",
    component: Admin,
    beforeEnter: (to, from, next) => {
      console.log(to, from, next);
      if (to) {
        next(false);
      } else {
        next();
      }
    },
    children: [
      {
        path: "/admin/articles",
        name: "文章管理",
        component: ArticleListPage
      },
      {
        path: "/admin/articleInfo",
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
  },
  {
    path: "/",
    name: "首页",
    component: FrontIndex,
    redirect: "/index",
    children: [
      {
        path: "/index",
        name: "index",
        component: FrontArticleInfo
      },
      {
        path: "/article/:articleId",
        name: "articleContent",
        component: FrontArticleContent
      },
      {
        path:"/timeline",
        name: "articleTimeLine",
        component: FrontTimeLine
      }
    ]
  }
];

const router = new VueRouter({
  routes
});

export default router;
