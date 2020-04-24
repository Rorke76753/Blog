<template>
  <div>
    <el-row class="frontHeader" type="flex" justify="space-between">
      <el-col :span="3"> </el-col>
      <el-col :span="3">
        <el-tooltip
          class="item"
          effect="dark"
          content="等待接入elastic search"
          placement="bottom"
        >
          <el-input
            v-model="titleInput"
            placeholder="输入标题，按回车进行搜索"
            clearable
            disabled
          ></el-input>
        </el-tooltip>
      </el-col>
      <el-col :span="6">
        <el-link icon="el-icon-s-tools" class="linkClass" @click="jumpTo('index')">博客</el-link>
        <el-link icon="el-icon-collection-tag" class="linkClass">标签</el-link>
        <el-link icon="el-icon-date" class="linkClass" @click="jumpTo('articleTimeLine')">时间轴</el-link>
        <el-link icon="el-icon-position" class="linkClass">关于我</el-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "HeadBar",
  data() {
    return {
      titleInput: ""
    };
  },
  methods: {
    jumpTo(routeName){
      this.$router.push({
        name:routeName
      })
    }
  }
};
</script>

<style scoped>
.linkClass {
  height: 60px;
  align-content: center;
  padding-left: 20px;
  padding-right: 20px;
}
.el-input {
  padding-top: 10px;
  width: 300px;
}
.frontHeader {
  margin: 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.el-button:hover {
  border-bottom-width: 3px;
  border-bottom: #2c3e50;
}
</style>
