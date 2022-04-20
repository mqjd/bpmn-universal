<template>
  <el-dialog v-model="dialogVisible" fullscreen>
    <template #title>
      <div class="dialog-header">
        <el-icon color="var(--el-color-success)">
          <circle-plus />
        </el-icon>
        <span style="margin-left: 10px">Schema</span>
      </div>
    </template>
    <schema-editor></schema-editor>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
  <el-button icon="Plus" circle @click="dialogVisible = true" />
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
        <el-button type="text" size="small">Detail</el-button>
        <el-button type="text" size="small">Edit</el-button>
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
import SchemaEditor from "./SchemaEditor.vue";
const tableData = new Array(100).fill({
  date: "2016-05-03",
  name: "Tom",
  address: "No. 189, Grove St, Los Angeles",
});
const pageSize = ref(100);
const currentPage = ref(1);
const loading = ref(true);
const dialogVisible = ref(false);
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
