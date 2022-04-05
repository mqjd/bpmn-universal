<template>
  <main-header />
</template>
<script>
import MainHeader from "./components/header/index.vue";
import { defineComponent, ref, watch } from "vue";
import {
  MailOutlined,
  QqOutlined,
  AppstoreOutlined,
  SettingOutlined,
} from "@ant-design/icons-vue";
export default defineComponent({
  components: {
    MainHeader,
  },
  setup() {
    return {};
  },
});
</script>