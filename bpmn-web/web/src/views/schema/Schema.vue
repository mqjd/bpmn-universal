<template>
  <el-row>
    <el-col :span="12">
      <el-tree :data="data" :props="defaultProps" default-expand-all :expand-on-click-node="false">
        <template #default="{ node, data }">
          <span class="tree-node-content">
            <span class="tree-node-text">{{ node.label }}</span>
            <el-button @click="append(data)" v-if="!node.isLeaf" circle>
              <el-icon :size="15">
                <circle-plus />
              </el-icon>
            </el-button>
            <el-button @click="remove(node, data)" circle>
              <el-icon :size="15">
                <delete />
              </el-icon>
            </el-button>
          </span>
        </template>
      </el-tree>
    </el-col>
    <el-col :span="12">
      <schema-form :schema="schema"></schema-form>
    </el-col>
  </el-row>
</template>
<script setup>
import SchemaForm from '@/components/form/SchemaForm.vue'
import schema from './BasicSchema'
let id = 1000;
const defaultProps = {
  children: "children",
  label: "label",
}
const data = [
  {
    id: 1,
    label: "Level one 1",
    children: [
      {
        id: 3,
        label: "Level two 2-1",
        children: [
          {
            id: 4,
            label: "Level three 3-1-1",
          },
          {
            id: 5,
            label: "Level three 3-1-2",
            disabled: true,
          },
        ],
      },
      {
        id: 2,
        label: "Level two 2-2",
        disabled: true,
        children: [
          {
            id: 6,
            label: "Level three 3-2-1",
          },
          {
            id: 7,
            label: "Level three 3-2-2",
            disabled: true,
          },
        ],
      },
    ],
  },
]
const append = (data) => {
  const newChild = { id: id++, label: "testtest", children: [] };
  if (!data.children) {
    data.children = [];
  }
  data.children.push(newChild);
}
const remove = (node, data) => {
  const parent = node.parent;
  const children = parent.data.children || parent.data;
  const index = children.findIndex((d) => d.id === data.id);
  children.splice(index, 1);
}
</script>
<style>
.el-row {
  height: 100%;
}
.el-button {
  border: none;
}
.el-tree-node div {
  margin: 5px;
}
.tree-node-content {
  display: inline-flex;
  align-items: center;
}
.tree-node-text {
  display: inline-block;
  margin-right: 10px;
  font-size: 1.5em;
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