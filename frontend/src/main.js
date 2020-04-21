import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./plugins/element.js";
import "./plugins/element.js";
//import Antd from 'ant-design-vue';
import "ant-design-vue/dist/antd.css";
import axios from "axios";
//Vue.use(Antd);
Vue.config.productionTip = false;
Vue.prototype.$http = axios;
axios.defaults.baseURL = "http://localhost:7625/api";
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
