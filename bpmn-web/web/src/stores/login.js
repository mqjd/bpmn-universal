import { defineStore } from "pinia";

export const useLoginStore = defineStore("loginStore", {
  state: () => {
    return {
      token: null,
      userInfo: null,
    };
  },
});
