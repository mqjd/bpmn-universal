<template>

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

</style>
