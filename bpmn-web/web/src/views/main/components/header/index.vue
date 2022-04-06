<template>
  <button class="toggle" @click="toggleSideMenu()">
    <el-icon size="2em">
      <component :is="toggleIcon" />
    </el-icon>
  </button>
</template>
<script>
import { mapWritableState } from "pinia";
import { useMenuStore } from "@/stores/menu";
import { defineComponent } from "vue";
export default defineComponent({
  setup() {
    const store = useMenuStore();
    return {
      store,
    };
  },
  computed: {
    ...mapWritableState(useMenuStore, ["collapsed"]),
    toggleIcon() {
      return this.collapsed ? "fold" : "expand"
    }
  },
  methods: {
    toggleSideMenu() {
      this.store.toggleSideMenu();
    },
  },
});
</script>

<style>
.toggle {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  line-height: 1;
  height: 32px;
  white-space: nowrap;
  cursor: pointer;
  background-color: white;
  border: none;
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
