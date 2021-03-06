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
import basicSchema from "./schema";
import SchemaNode from "./SchemaNode.vue";
import {
  schemaToTree,
  createValidater,
  treeToSchema,
} from "@/utils/json-schema";
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from "@/constants";
import { ref, nextTick, computed, watch } from "vue";
const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT]);
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
  modelValue: {
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

const nativeSchema = computed(() => props.modelValue);
const schemaValue = ref(nativeSchema.value);
const schemaValidater = createValidater(basicSchema);
let currentNodeValue = ref();
let currentNode = ref();

watch(nativeSchema, (newValue, oldValue) => {
  schemaValue.value = newValue;
  currentNodeValue.value = null;
  currentNode.value = null;
});

const treeData = computed(() => [schemaToTree(schemaValue.value)]);

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

const onFormValueChange = (value) => {
  nextTick(() => {
    validateNodeValue(value);
  });
};
const validateNodeValue = (data) => {
  if (!hasChildren(data.type)) {
    data.children = [];
  }
  data.notValid = !schemaValidater(data);
};

const hasInValidNode = (nodes) => {
  for (let i = 0; i < nodes.length; i++) {
    if (nodes[i].notValid) {
      return true;
    }
    if (
      nodes[i].children &&
      nodes[i].children.length !== 0 &&
      hasInValidNode(nodes[i].children)
    ) {
      return true;
    }
  }
  return false;
};

const onSchemaChanged = () => {
  if (!hasInValidNode(treeData.value)) {
    const schema = treeToSchema(treeData.value);
    emits(UPDATE_MODEL_EVENT, schema);
    emits(CHANGE_EVENT, schema);
  }
};
const allowDrag = (node) => {
  return node.level !== 1;
};
const allowDrop = (draggingNode, dropNode, type) => {
  if ("inner" === type) {
    const schemaNodeType = dropNode.data.type;
    if ("object" === schemaNodeType) {
      return true;
    } else if ("array" === schemaNodeType) {
      return dropNode.data.children.length < 1;
    } else {
      return false;
    }
  }
  return dropNode.level !== 1 && "array" !== dropNode.parent.data.type;
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
  background-color: var(--el-fill-color);
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
