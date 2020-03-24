<template>
  <div class = "holdHeight">
    <el-row type="flex"></el-row>
    <el-tag
      :key="tag"
      v-for="tag in dynamicTags"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)"
    >
      {{ tag }}
    </el-tag>
    <el-input
      class="input-new-tag"
      v-if="inputVisible"
      v-model="inputValue"
      ref="saveTagInput"
      size="small"
      @keyup.enter.native="handleInputConfirm"
      @blur="handleInputConfirm"
      maxlength="10"
      show-word-limit
    >
    </el-input>
    <el-button
      v-else-if="dynamicTags.length < 10"
      class="button-new-tag"
      size="small"
      @click="showInput"
      align="center"
      >+ New Tag
    </el-button>
    <span class="tagCount">{{ dynamicTags.length }}/10</span>
  </div>
</template>

<script>
export default {
  name: "TagsInput",
  data() {
    return {
      dynamicTags: [],
      inputVisible: false,
      inputValue: "",
      tagsSet: new Set()
    };
  },
  methods: {
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        console.log(_);
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if (!this.tagsSet.has(inputValue)) {
          this.tagsSet.add(inputValue);
          this.dynamicTags.push(inputValue);
        } else {
          this.tagWarning();
        }
      }
      this.inputVisible = false;
      this.inputValue = "";
    },

    tagWarning() {
      this.$message({
        showClose: true,
        message: "不允许添加重复标签",
        type: "warning"
      });
    }
  }
};
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  margin-left: 10px;
  width: 190px;
  vertical-align: bottom;
}

.tagCount {
  padding-left: 30px;
  font-size: 20px;
}
  .holdHeight{
    height: 40px;
  }
</style>
