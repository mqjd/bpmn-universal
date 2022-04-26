<template>
  <span class="tree-node-content" @dragstart="dragStart">
    <el-tooltip :content="node.label" :show-after="300">
      <span>
        <span class="tree-node-text">{{ data.$key }}</span>
        <span class="tree-node-type">
          {{ nodeType }}
        </span>
      </span>
    </el-tooltip>
    <el-button @click.stop="append(data, node)" v-if="showAppend" circle>
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
      childrenCount === 0 ? "?" : getNodeType(data.children[0]);
    return `ARRAY<${childrenType}>`;
  } else {
    return type.toUpperCase();
  }
};
const showAppend = computed(() => {
  const type = nativeData.value.type;
  if ("object" === type) {
    return true;
  } else if ("array" === type) {
    return nativeData.value.children.length < 1;
  } else {
    return false;
  }
});
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
