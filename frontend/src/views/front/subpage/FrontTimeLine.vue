<template>
  <el-row
    ><el-col :span="17">
      <div class="timeLineClass">
        <el-card>
          <a-timeline>
            <a-timeline-item v-for="t in testData" :key="testData.indexOf(t)">
              {{ t.test }}
            </a-timeline-item>
          </a-timeline>
          <a-timeline>
            <a-timeline-item>创建服务现场 2015-09-01</a-timeline-item>
            <a-timeline-item>初步排除网络异常 2015-09-01</a-timeline-item>
            <a-timeline-item>技术测试异常 2015-09-01</a-timeline-item>
            <a-timeline-item>网络异常正在修复 2015-09-01</a-timeline-item>
          </a-timeline>
        </el-card>
      </div>
    </el-col></el-row
  >
</template>

<script>
export default {
  name: "FrontTimeLine",
  data() {
    return {
      testData: [
        {
          test: "1"
        },
        {
          test: "1"
        },
        {
          test: "1"
        },
        {
          test: "1"
        },
        {
          test: "1"
        },
        {
          test: "1"
        }
      ]
    };
  }
};
</script>

<style scoped>
.timeLineClass {
  height: 100%;
  width: 80%;
  margin-left: 22%;
  margin-top: 30px;
}
</style>
