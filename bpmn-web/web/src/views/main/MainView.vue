<template>
  <a-layout>
    <a-layout-header>
      <main-header />
    </a-layout-header>
    <a-layout>
      <a-layout-sider :collapsed="collapsed">
        <main-menu></main-menu>
      </a-layout-sider>
      <a-layout-content>
        <router-view />
      </a-layout-content>
    </a-layout>
    <a-layout-footer>Footer</a-layout-footer>
  </a-layout>
</template>
<script>
import MainHeader from "./components/header/index.vue";
import MainMenu from "./components/menu/index.vue";
import { RouterView } from "vue-router";
import { mapWritableState } from "pinia";
import { useMenuStore } from "@/stores/menu";
import { defineComponent } from "vue";
export default defineComponent({
  components: {
    MainHeader,
    MainMenu,
    RouterView
  },
  computed: {
    ...mapWritableState(useMenuStore, ["collapsed"]),
  },
});
</script>
<style>
.ant-layout {
  height: 100%;
}
.ant-layout-header,
.ant-layout-footer,
.ant-layout-sider {
  background: #ffffff;
}
.ant-layout-header {
  padding-left: 10px;
}
</style>