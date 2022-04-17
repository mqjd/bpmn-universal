<template>
  <el-row>
    <el-col :span="12">
      <el-tree
        class="schema-tree"
        :data="treeData"
        :props="defaultProps"
        :expand-on-click-node="false"
        @current-change="onSelectChange"
        default-expand-all
      >
        <template #default="{ node, data }">
          <span class="tree-node-content">
            <span class="tree-node-text" :class="{ 'not-valid': data.notValid }">{{ node.label }}</span>
            <span class="tree-node-type" v-if="data.type">{{ data.type.toUpperCase() }}</span>
            <el-button
              @click="append(data, node)"
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
import basicSchema from './BasicSchema'
import { schemaToTree, createValidater } from '@/utils/json-schema'
import { ref, nextTick } from "vue";
let id = 1000;
const defaultProps = {
  children: "children",
  label: "meta:ui:title"
}
const schemaValidater = createValidater(basicSchema)
let currentNodeValue = ref()
let currentNode = ref()
const onSelectChange = (data, node) => {
  currentNodeValue.value = data
  currentNode.value = node
  validateCurrentNodeValue()
}

const keys = {}
const append = (data) => {
  if (!keys[data.id]) {
    keys[data.id] = (data.children || []).reduce((result, item) => {
      return Math.max(+item.id.substring(data.id.length + 1), result)
    }, 0)
  }
  keys[data.id]++
  const id = data.id + '-' + keys[data.id];
  const newChild = { id: id, "meta:ui:title": "字段" + id, notValid: true, children: [] };
  if (!data.children) {
    data.children = [];
  }
  data.children.push(newChild);
  onSchemaChanged();
}
const remove = (data, node) => {
  const parent = node.parent;
  const children = parent.data.children || parent.data;
  const index = children.findIndex((d) => d.id === data.id);
  children.splice(index, 1);
  if (currentNodeValue.value && currentNodeValue.value.id === data.id) {
    currentNodeValue.value = null
  }
  onSchemaChanged();
}
const treeData = ref([schemaToTree(basicSchema)])

const onFormValueChange = (value) => {
  nextTick(validateCurrentNodeValue)
  onSchemaChanged()
}
const validateCurrentNodeValue = () => {
  if ('object' !== currentNodeValue.value.type){
    currentNodeValue.value.children = []
  }
  currentNodeValue.value && (currentNodeValue.value.notValid = !schemaValidater(currentNodeValue.value))
}

const onSchemaChanged = () => {

}
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
.not-valid {
  border-left: 5px var(--el-color-danger) solid;
  padding-left: 3px;
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
.tree-node-type {
  display: inline-block;
  padding: 2px 8px;
  margin-right: 10px;
  font-size: 0.8em;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color))
    inset;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
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