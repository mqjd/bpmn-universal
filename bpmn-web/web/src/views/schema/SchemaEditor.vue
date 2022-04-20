<template>
  <el-row>
    <el-col :span="12">
      <el-tree
        class="schema-tree"
        :data="treeData"
        :props="defaultProps"
        :expand-on-click-node="false"
        node-key="id"
        @current-change="onSelectChange"
        default-expand-all
        draggable
        :allow-drag="allowDrag"
        :allow-drop="allowDrop"
      >
        <template #default="{ node, data }">
          <schema-node
            :data="data"
            :node="node"
            :append="append"
            :remove="remove"
          ></schema-node>
        </template>
      </el-tree>
    </el-col>
    <el-col :span="12">
      <schema-form
        :schema="basicSchema"
        v-model="currentNodeValue"
        @change="onFormValueChange"
        v-if="currentNodeValue"
      ></schema-form>
    </el-col>
  </el-row>
</template>
<script setup>
import basicSchema from "./BasicSchema";
import SchemaNode from "./SchemaNode.vue";
import { dragEventsKey } from "element-plus/lib/components/tree/src/model/useDragNode";
import { schemaToTree, createValidater } from "@/utils/json-schema";
import { ref, nextTick, computed } from "vue";
const defaultProps = {
  children: "children",
  label: "meta:ui:title",
  class: (data) => {
    return {
      "not-valid": data.notValid,
    };
  },
};
const props = defineProps({
  schema: {
    type: Object,
    default: () => {
      return {
        type: "object",
        notValid: true,
        "meta:ui:title": "schema",
      };
    },
  },
});

const hasChildren = (type) => {
  return type === "object" || type === "array";
};

const nativeSchema = computed(() => props.schema);
const schemaValue = ref(nativeSchema.value);

const schemaValidater = createValidater(basicSchema);
let currentNodeValue = ref();
let currentNode = ref();
const onSelectChange = (data, node) => {
  currentNodeValue.value = data;
  currentNode.value = node;
  validateNodeValue(data);
};

const keys = {};
const append = (data) => {
  if (!keys[data.id]) {
    keys[data.id] = (data.children || []).reduce((result, item) => {
      return Math.max(+item.id.substring(data.id.length + 1), result);
    }, 0);
  }
  keys[data.id]++;
  const id = data.id + "-" + keys[data.id];
  const newChild = {
    id: id,
    "meta:ui:title": "字段" + id,
    notValid: true,
    children: [],
  };
  if (!data.children) {
    data.children = [];
  }
  data.children.push(newChild);
  validateNodeValue(data);
  onSchemaChanged();
};
const remove = (data, node) => {
  const parent = node.parent;
  const children = parent.data.children || parent.data;
  const index = children.findIndex((d) => d.id === data.id);
  children.splice(index, 1);
  if (currentNodeValue.value && currentNodeValue.value.id === data.id) {
    currentNodeValue.value = null;
  }
  validateNodeValue(parent.data);
  onSchemaChanged();
};
const treeData = ref([schemaToTree(schemaValue.value)]);

const onFormValueChange = (value) => {
  nextTick(() => validateNodeValue(value));
  onSchemaChanged();
};
const validateNodeValue = (data) => {
  if (!hasChildren(data.type)) {
    data.children = [];
    data.notValid = !schemaValidater(data);
  } else {
    if (data.children.length === 0) {
      data.notValid = true;
    } else {
      data.notValid = !schemaValidater(data);
    }
  }
};

const onSchemaChanged = () => {};
const allowDrag = (node) => {
  return node.level !== 1;
};
const allowDrop = (draggingNode, dropNode, type) => {
  return type === "inner"
    ? hasChildren(dropNode.data.type)
    : dropNode.level !== 1;
};
</script>
<style>
.el-row {
  height: 100%;
}

.schema-tree {
  height: 100%;
}

.is-current > .el-tree-node__content:nth-child(1) {
  background-color: var(--el-tree-node-hover-bg-color);
}

.not-valid > .el-tree-node__content:nth-child(1) {
  border-left: 5px var(--el-color-danger) solid;
  box-shadow: none;
}

.el-button {
  border: none;
}

.el-tree-node div {
  margin: 5px;
}

.el-tree-node__content {
  padding-top: 20px;
  padding-bottom: 20px;
}

.el-tree-node__expand-icon,
.el-tree-node__expand-icon svg {
  height: 1.5em;
  width: 1.5em;
}
</style>
