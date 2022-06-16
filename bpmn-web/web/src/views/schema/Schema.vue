<template>
  <schema-editor-dialog
    v-model:visible="editorSchemaDialogVisible"
    v-model="schema"
    :onSave="
      () => {
        editorSchemaDialogVisible = false;
      }
    "
    :onCancel="
      () => {
        editorSchemaDialogVisible = false;
      }
    "
    v-if="editorSchemaDialogVisible"
  />
  <schema-transform-dialog
    v-model:visible="transformSchemaDialogVisible"
    v-model="schema"
    :onSave="
      () => {
        transformSchemaDialogVisible = false;
      }
    "
    :onCancel="
      () => {
        transformSchemaDialogVisible = false;
      }
    "
    v-if="transformSchemaDialogVisible"
  />
  <el-button icon="Plus" circle @click="editorSchemaDialogVisible = true" />
  <el-button icon="Edit" circle @click="transformSchemaDialogVisible = true" />
  <el-table
    v-loading="loading"
    height="calc(100% - 50px)"
    :data="tableData"
    stripe
    border
  >
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="address" label="Address" />
    <el-table-column fixed="right" label="Operations" width="120">
      <template #default>
        <el-button size="small">Detail</el-button>
        <el-button size="small">Edit</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    background
    v-model:currentPage="currentPage"
    v-model:page-size="pageSize"
    layout="sizes, prev, pager, next"
    :page-sizes="[20, 30, 50, 100]"
    :total="1000"
  />
</template>
<script setup>
import { ref } from "vue";
import SchemaEditorDialog from "./editor/SchemaEditorDialog.vue";
import SchemaTransformDialog from "./transform/SchemaTransformDialog.vue";
const tableData = new Array(100).fill({
  date: "2016-05-03",
  name: "Tom",
  address: "No. 189, Grove St, Los Angeles",
});
const pageSize = ref(100);
const currentPage = ref(1);
const loading = ref(true);
const editorSchemaDialogVisible = ref(false);
const transformSchemaDialogVisible = ref(false);
const schema = ref();

const closeditorDialog = () => {
  editorSchemaDialogVisible.value = false;
};

setTimeout(() => {
  loading.value = false;
}, 800);
</script>
<style>
.el-table {
  height: 100%;
}
.is-fullscreen .el-dialog__body {
  height: calc(100% - 116px);
  overflow: auto;
}
.dialog-header {
  display: flex;
  align-items: center;
}
</style>
