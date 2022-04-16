<template>
  <el-row>
    <el-col :span="12">
      <el-tree
        :data="treeData"
        :props="defaultProps"
        :expand-on-click-node="false"
        @current-change="onSelectChange"
        default-expand-all
      >
        <template #default="{ node, data }">
          <span class="tree-node-content">
            <span class="tree-node-text">{{ node.label }}</span>
            <el-button @click="append(data, node)" v-if="node.level === 1 || !node.isLeaf" circle>
              <el-icon :size="15">
                <circle-plus />
              </el-icon>
            </el-button>
            <el-button @click="remove(data, node)" circle v-if="node.level !== 1">
              <el-icon :size="15">
                <delete />
              </el-icon>
            </el-button>
          </span>
        </template>
      </el-tree>
    </el-col>
    <el-col :span="12">
      <schema-form :schema="basicSchema" v-model="modelValue.value"></schema-form>
    </el-col>
  </el-row>
</template>
<script setup>
import SchemaForm from '@/components/SchemaForm/SchemaForm.vue'
import basicSchema from './BasicSchema'
import { schemaToTree } from '@/utils/json-schema'
import { reactive, computed } from "vue";
let id = 1000;
const defaultProps = {
  children: "children",
  label: "meta:ui:title",
}
let modelValue = reactive({value:{}})
const onSelectChange = (data, node) => {
  modelValue.value = data
}

const append = (data) => {
  const newChild = { id: id++, "meta:ui:title": "testtest", children: [] };
  if (!data.children) {
    data.children = [];
  }
  data.children.push(newChild);
}
const remove = (data, node) => {
  const parent = node.parent;
  const children = parent.data.children || parent.data;
  const index = children.findIndex((d) => d.id === data.id);
  children.splice(index, 1);
}

const treeData = computed(() => [schemaToTree(basicSchema)])
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
  font-size: 1.2em;
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