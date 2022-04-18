import { defineStore } from "pinia";
import { routers } from "@/router";
export const useMenuStore = defineStore("menu", {
  state: () => {
    return {
      menus: routers,
      collapsed: false,
    };
  },
  getters: {
    avalibleMenus() {
      return this.menus.filter((menu) => menu.meta && !menu.meta.hidden);
    },
  },
  actions: {
    toggleSideMenu() {
      this.collapsed = !this.collapsed;
    },
  },
});
