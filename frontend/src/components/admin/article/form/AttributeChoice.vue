<template>
  <div>
    <el-select
      placeholder="文章类型"
      v-model="selectValue"
      @change="selectAttribute"
      clearable
    >
      <el-option
        v-for="attribute in articleAttribute"
        :key="attribute.attributeId"
        :label="attribute.attributeName"
        :value="attribute.attributeName"
        class="attributeChoice"
      ></el-option>
    </el-select>
  </div>
</template>

<script>
import attributeList from "../../../../http/api/front/attributeList";
export default {
  name: "AttributeChoice",
  data() {
    return {
      selectValue: "",
      articleAttribute: []
    };
  },
  methods: {
    setAttributeChoice(selectValue) {
      this.selectValue = selectValue;
    },

    selectAttribute(attribute) {
      this.selectValue = attribute;
    },

    getData() {
      let attributeId;
      for (let i = 0; i < this.articleAttribute.length; i++) {
        if (this.articleAttribute[i].attributeName === this.selectValue) {
          attributeId = this.articleAttribute[i].attributeId;
        }
      }
      return attributeId;
    },
    getAttributeList() {
      attributeList.getAttributeList().then(res => {
        this.articleAttribute = res.data;
      });
    }
  },
  created() {
    this.getAttributeList();
  }
};
</script>

<style scoped>
.el-select {
  width: 100%;
}
</style>
