<template>
  <el-menu :collapse="collapsed">
    <menu-item
      v-for="item of avalibleMenus"
      :key="item.name"
      :base-path="item.path"
      :item="item"
    ></menu-item>
  </el-menu>
  <button class="toggle" @click="toggleSideMenu()">
    <el-icon size="2em">
      <component :is="toggleIcon" />
    </el-icon>
  </button>
</template>

<script>
import { mapWritableState } from "pinia";
import { useMenuStore } from "@/stores/menu";
import MenuItem from "./MenuItem.vue";
import { defineComponent } from "vue";
export default defineComponent({
  components: {
    MenuItem,
  },
  setup() {
    const store = useMenuStore();
    return {
      store,
    };
  },
  computed: {
    ...mapWritableState(useMenuStore, ["avalibleMenus", "collapsed"]),
    toggleIcon() {
      return this.collapsed ? "fold" : "expand";
    },
  },
  methods: {
    toggleSideMenu() {
      this.store.toggleSideMenu();
    },
  },
});
</script>
<style>
.el-menu {
  height: calc(100% - 40px);
  border-right: none;
  width: 200px;
}
.el-menu--collapse {
  width: auto;
}
.toggle {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  line-height: 1;
  height: 40px;
  width: 100%;
  white-space: nowrap;
  cursor: pointer;
  background-color: white;
  border: none;
  border-top: solid 1px var(--el-border-color);
  color: var(--el-button-text-color);
  -webkit-appearance: none;
  text-align: center;
  box-sizing: border-box;
  outline: 0;
  transition: 0.1s;
  font-weight: var(--el-button-font-weight);
  -webkit-user-select: none;
  user-select: none;
  vertical-align: middle;
  padding: 8px 15px;
  font-size: var(--el-font-size-base);
  border-radius: var(--el-border-radius-base);
}
.toggle:active {
  color: var(--el-color-primary);
}
</style>
