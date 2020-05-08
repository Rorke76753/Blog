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
//axios.defaults.headers.common["token"] = sessionStorage.getItem("token");
axios.defaults.baseURL = "http://localhost:7625/api";
axios.defaults.baseURL = "http://rorke76753.xyz:7625/api";
new Vue({
  router,
  store,
  render: h => h(App),
  http: {
    headers: {
      token: sessionStorage.getItem("token")
    }
  }
}).$mount("#app");
