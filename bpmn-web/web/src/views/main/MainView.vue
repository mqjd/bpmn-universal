<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-container>
      <el-aside width="200px">
        <main-menu></main-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
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
.el-container {
  height: 100%;
}
</style>