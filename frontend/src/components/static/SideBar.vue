<template>
  <div>
    <el-menu
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      background-color="#2C3E50"
      text-color="#FFFFFF"
      active-text-color="#409EFF"
      router
      :default-active="currentActivePath"
    >
      <el-menu-item @click="handleCollapse" class="darkBackground">
        <i class="el-icon-s-unfold" v-show="isCollapse"></i>
        <div v-show="!isCollapse">
          <i class="el-icon-s-fold"></i>
          <span slot="title">后台管理</span>
        </div>
      </el-menu-item>
      <el-menu-item index="/admin/homepage" v-on:click="saveActivePath('/homepage')">
        <i class="el-icon-s-promotion"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span slot="title">管理</span>
        </template>
        <el-menu-item
          index="/admin/articles"
          v-on:click="saveActivePath('/articles')"
        >
          <i class="el-icon-view"></i>
          <span slot="title">文章管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/tags" v-on:click="saveActivePath('/tags')">
          <i class="el-icon-collection-tag"></i>
          <span slot="title">标签管理</span>
        </el-menu-item>
        <el-menu-item
          index="/admin/comments"
          v-on:click="saveActivePath('/comments')"
        >
          <i class="el-icon-chat-dot-round"></i>
          <span slot="title">评论管理</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span slot="title">日志</span>
        </template>
        <el-menu-item
          index="/admin/operationLog"
          v-on:click="saveActivePath('/operationLog')"
        >
          <i class="el-icon-data-analysis"></i>
          <span slot="title">操作日志</span>
        </el-menu-item>
        <el-menu-item
          index="/admin/loginLog"
          v-on:click="saveActivePath('/loginLog')"
        >
          <i class="el-icon-time"></i>
          <span slot="title">登录日志</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item
        index="/admin/writeArticle"
        v-on:click="saveActivePath('/writeArticle')"
      >
        <i class="el-icon-edit"></i>
        <span slot="title">写文章</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: true,
      currentActivePath: ""
    };
  },
  methods: {
    handleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    saveActivePath(activePath) {
      window.sessionStorage.setItem("activePath", activePath);
    }
  },
  created() {
    this.currentActivePath = window.sessionStorage.getItem("activePath");
  }
};
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  height: 100%;
}

.darkBackground {
  background-color: #131821 !important;
}

.el-menu {
  border-right: none;
}
</style>
