<template>
  <a-sub-menu :key="item.name" v-if="item.children">
    <template #icon>
      <component :is="item.meta.icon" />
    </template>
    <template #title>{{ item.meta.title }}</template>

    <side-menu-item
      v-for="child of item.children"
      :key="child.name"
      :item="child"
      :base-path="resolvePath(child.path)"
    ></side-menu-item>
  </a-sub-menu>
  <router-link :to="resolvePath(item.path)" v-else>
    <a-menu-item :key="item.name">
      <template #icon>
        <component :is="item.meta.icon" />
      </template>
      {{ item.meta.title }}
    </a-menu-item>
  </router-link>
</template>

<script>
import { RouterView } from "vue-router";
import { defineComponent } from "vue";
export default defineComponent({
  name: "SideMenuItem",
  components: {
    RouterView,
  },
  props: {
    item: {
      type: Object,
      required: true,
    },
    basePath: {
      type: String,
    },
  },
  methods: {
    resolvePath(routePath) {
      if (this.item.children) {
        return (this.basePath + "/" + routePath).replace(/\/{2,}/, '/')
      } else {
        return this.basePath;
      }
    },
  },
});
</script>
