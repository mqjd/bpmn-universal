<template>
  <a-menu mode="inline">
    <menu-item v-for="item of avalibleMenus" :key="item.name" :base-path="item.path" :item="item"></menu-item>
  </a-menu>
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
  computed: {
    ...mapWritableState(useMenuStore, ["avalibleMenus"]),
  },
});
</script>