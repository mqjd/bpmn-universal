import { defineStore } from "pinia";

export const useTagStore = defineStore("tag", {
  state: () => {
    return {
      visitedPages: [],
      cachedPages: [],
    };
  },
  actions: {
    addPage(page) {
      this.addVisitedPage(page);
      this.addCachedPage(page);
    },
    addVisitedPage(page) {
      if (this.visitedPages.some((v) => v.path === page.path)) return;
      this.visitedPages.push(
        Object.assign({}, page, {
          name: page.name || "no-name",
        })
      );
    },
    addCachedPage(page) {
      if (this.cachedPages.includes(page.name)) return;
      if (!page.meta.noCache) {
        this.cachedPages.push(page.name);
      }
    },
    delPage(page) {
      return new Promise((resolve) => {
        this.delVisitedPage(page);
        this.delCachedPage(page);
        resolve({
          visitedPages: [...this.visitedPages],
          cachedPages: [...this.cachedPages],
        });
      });
    },
    delVisitedPage(page) {
      const index = this.visitedPages.findIndex((v) => v.path === page.path);
      this.visitedPages.splice(index, 1);
    },
    delCachedPage(page) {
      const index = this.cachedPages.indexOf(page.name);
      if (index !== -1) {
        this.cachedPages.splice(index, 1);
      }
    },
    delOtherPage(page) {
      return new Promise((resolve) => {
        this.delOtherVisitedPage(page);
        this.delOtherCachedPage(page);
        resolve({
          visitedPages: [...this.visitedPages],
          cachedPages: [...this.cachedPages],
        });
      });
    },
    delOtherVisitedPage(page) {
      const index = this.visitedPages.findIndex((v) => v.name === page.name);
      this.visitedPages = state.visitedPages.slice(index, index + 1);
    },
    delOtherCachedPage(page) {
      const index = this.cachedPages.indexOf(page.name);
      if (index !== -1) {
        this.cachedPages = state.cachedPages.slice(index, index + 1);
      }
    },
    delAllPage() {
      return new Promise((resolve) => {
        this.deleteAllCacheedPage();
        resolve({
          visitedPages: [...this.visitedPages],
          cachedPages: [...this.cachedPages],
        });
      });
    },
    deleteAllCacheedPage() {
      this.visitedPages = [];
      this.cachedPages = [];
    },
  },
});
