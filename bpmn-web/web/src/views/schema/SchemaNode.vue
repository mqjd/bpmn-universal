<template>
  <span class="tree-node-content" @dragstart="dragStart">
    <span class="tree-node-text">{{ node.label }}</span>
    <span class="tree-node-type">
      {{ nodeType }}
    </span>
    <el-button
      @click.stop="append(data, node)"
      v-if="data.type === 'object' || data.type === 'array'"
      circle
    >
      <el-icon :size="15">
        <circle-plus />
      </el-icon>
    </el-button>
    <el-button @click.stop="remove(data, node)" circle v-if="node.level !== 1">
      <el-icon :size="15">
        <delete />
      </el-icon>
    </el-button>
  </span>
</template>
<script setup>
import { computed } from "vue";
const props = defineProps({
  node: {
    type: Object,
    required: true,
  },
  data: {
    type: Object,
    required: true,
  },
  append: Function,
  remove: Function,
});

const nativeData = computed(() => {
  return props.data;
});

const getNodeType = (data) => {
  const type = data.type;
  if (!type) {
    return "?";
  }
  if ("array" === type) {
    const childrenCount = data.children.length;

    const childrenType =
      childrenCount === 0
        ? "?"
        : childrenCount === 1
        ? getNodeType(data.children[0])
        : "OBJECT";
    return `ARRAY<${childrenType}>`;
  } else {
    return type.toUpperCase();
  }
};
const nodeType = computed(() => getNodeType(nativeData.value));
</script>
<style>
.tree-node-content {
  display: inline-flex;
  align-items: center;
}

.tree-node-text {
  display: inline-block;
  margin-left: 10px;
  margin-right: 10px;
  font-size: 1.2em;
}

.tree-node-type {
  display: inline-block;
  padding: 2px 8px;
  margin-right: 10px;
  font-size: 0.8em;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color))
    inset;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
}
</style>
