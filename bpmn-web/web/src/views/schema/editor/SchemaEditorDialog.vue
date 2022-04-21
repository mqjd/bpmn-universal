<template>
  <el-dialog :model-value="visible" fullscreen>
    <template #title>
      <div class="dialog-header">
        <el-icon color="var(--el-color-success)">
          <circle-plus />
        </el-icon>
        <span style="margin-left: 10px">Schema</span>
      </div>
    </template>
    <schema-editor :schema="schema" />
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="onSave">Cancel</el-button>
        <el-button type="primary" @click="onCancel">Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import SchemaEditor from "./SchemaEditor.vue";
import { computed, ref, watch } from "vue";
const emits = defineEmits(["update:visible"]);
const props = defineProps({
  visible: {
    type: Boolean,
    default: () => {
      return false;
    },
  },
  schema: Object,
});

const onSave = () => {
  emits("update:visible", false);
};
const onCancel = () => {
  emits("update:visible", false);
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
