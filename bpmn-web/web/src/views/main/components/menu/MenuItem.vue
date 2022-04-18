<template>
  <el-sub-menu :index="item.name" v-if="item.children">
    <template #title>
      <el-icon>
        <component :is="item.meta.icon" />
      </el-icon>
      <span>{{ item.meta.title }}</span>
    </template>
    <menu-item
      v-for="child of item.children"
      :key="child.name"
      :item="child"
      :base-path="resolvePath(child.path)"
    ></menu-item>
  </el-sub-menu>
  <el-menu-item :index="item.name" v-else>
    <el-icon>
      <component :is="item.meta.icon" />
    </el-icon>
    <template #title>
      <router-link class="link" :to="resolvePath(item.path)">{{
        item.meta.title
      }}</router-link>
    </template>
  </el-menu-item>
</template>

<script>
import { RouterView } from "vue-router";
import { defineComponent } from "vue";
export default defineComponent({
  name: "MenuItem",
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
        return (this.basePath + "/" + routePath).replace(/\/{2,}/, "/");
      } else {
        return this.basePath;
      }
    },
  },
});
</script>
<style>
.link {
  text-decoration: none;
}

.is-dark .link {
  color: var(--el-color-white);
}
.link:active,
.link:visited,
.link:hover,
.link:link {
  color: initial;
}
.el-menu-item.is-active .link {
  color: var(--el-menu-active-color);
}
.is-dark .link:active,
.is-dark .link:visited,
.is-dark .link:hover,
.is-dark .link:link {
  color: var(--el-color-white);
}
</style>
