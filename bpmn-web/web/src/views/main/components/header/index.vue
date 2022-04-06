<template>
  <div class="app-header">
    <a-button @click="toggleSideMenu()">
      <template #icon>
        <menu-unfold-outlined v-if="collapsed" />
        <menu-fold-outlined v-else />
      </template>
    </a-button>
  </div>
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
