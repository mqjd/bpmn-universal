<template>
  <div class="app-header">
    <a-button :class="isCollapse ? 'active' : ''" @click="toggleSideMenu()">
      <template #icon><menu-unfold-outlined /></template>
    </a-button>
  </div>
</template>
<script>
import { useMenuStore } from "../../../../stores/menu";
import { defineComponent, ref, watch } from "vue";
export default defineComponent({
  setup() {
    const store = useMenuStore();
    return {
      store,
    };
  },
  computed: {
    isCollapse() {
      return this.store.collapse;
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
.a-button {
  margin: 5px 0px;
  border: none;
  font-size: 18px;
  padding: 7px;
  transition: 0.38s;
  transform-origin: 50% 50%;
}

.a-button:active {
  transform: rotate(90deg);
}
</style>
