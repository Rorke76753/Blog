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
import LoginPage from "../views/admin/LoginPage";
import Authenticating from "../views/front/subpage/Authenticating";
Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    component: LoginPage
  },
  {
    path: "/admin",
    redirect: "/admin/articles",
    component: Admin,
    meta: {
      requireAuth: true
    },
    children: [
      {
        path: "/admin/articles",
        name: "文章管理",
        meta: {
          requireAuth: true
        },
        component: ArticleListPage
      },
      {
        path: "/admin/articleInfo",
        name: "ArticleManagementPage",
        meta: {
          requireAuth: true
        },
        component: ArticleManagementPage
      },
      {
        path: "/admin/tags",
        name: "标签管理",
        meta: {
          requireAuth: true
        },
        component: TagManagementPage
      },
      {
        path: "/admin/comments",
        name: "文章选择",
        meta: {
          requireAuth: true
        },
        component: ArticlesChoicePage
      },
      {
        path: "/admin/comments/:articleId",
        name: "评论管理",
        meta: {
          requireAuth: true
        },
        component: CommentManagementPage
      },
      {
        path: "/admin/operationLog",
        name: "操作日志",
        meta: {
          requireAuth: true
        },
        component: OperationLog
      },
      {
        path: "/admin/loginLog",
        name: "登录日志",
        meta: {
          requireAuth: true
        },
        component: LoginLog
      },
      {
        path: "/admin/writeArticle",
        name: "写文章",
        meta: {
          requireAuth: true
        },
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
        path: "/timeline",
        name: "articleTimeLine",
        component: FrontTimeLine
      }
    ]
  },
  {
    path: "/authentication/:thirdParty",
    name: "验证",
    component: Authenticating
  }
];
const router = new VueRouter({
  mode: "history",
  routes
});

import adminLogin from "../http/api/admin/login.js"
export default router;
router.beforeEach((to, from, next) => {
  // 对 to.matched 数组中的每个路由调用箭头函数
  if (to.meta.requireAuth) {
    // 判断登录状态
    if (sessionStorage.getItem("token")) {
      // 继续路由
      let token = sessionStorage.getItem("token");
      adminLogin
        .validateToken(token)
        .then(res => {
          if (res.data) {
            next();
          } else {
            next({
              path: "/login",
              query: { redirect: to.fullPath }
            });
          }
        })
        .catch(() => {
          next({
            path: "/login",
            query: { redirect: to.fullPath }
          });
        });
    } else {
      // 重定向到登录界面
      next({
        path: "/login",
        query: { redirect: to.fullPath }
      });
    }
  } else {
    // 继续路由
    next();
  }
});
