<template>
  <a-menu>
    <side-menu-item
      v-for="item of menus"
      :key="item.name"
      :base-path="item.path"
      :item="item"
    >
    </side-menu-item>
  </a-menu>
</template>

<script>
import SideMenuItem from "./SideMenuItem.vue";
import { defineComponent } from "vue";
export default defineComponent({
  components: {
    SideMenuItem,
  },
  setup() {
    return {};
  },
});
</script>
</script>