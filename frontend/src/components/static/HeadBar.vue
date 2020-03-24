<template>
    <div class="layoutDiv">
        <div class="leftItem">
            <el-breadcrumb separator-class="el-icon-arrow-right" >
                <el-breadcrumb-item>首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="middleItem">
            预留作为搜索或其他功能
        </div>
        <div class="rightItem">
            预留作为登入登出
        </div>
    </div>
</template>

<script>
    export default {
        name: "HeadBar",
        computed: {
            currentPathName() {
                return this.$route.name;
            }
        }
    };
</script>

<style scoped>
    .el-breadcrumb {
        align-content: center;
        width: 300px;
    }

    .layoutDiv{
        display: flex;
        padding-top: 20px;
        justify-content: space-between;
    }
    .rightItem{

    }

    .el-breadcrumb__item {
        font-size: 16px !important;
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
        "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    }
</style>
