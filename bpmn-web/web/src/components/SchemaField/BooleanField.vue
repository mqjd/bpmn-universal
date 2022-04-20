<template>
  <el-radio-group v-model="booleanValue">
    <el-radio :label="true">是</el-radio>
    <el-radio :label="false">否</el-radio>
  </el-radio-group>
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from "@/constants";
import { ref, watch, computed } from "vue";

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT]);
const props = defineProps({
  schema: Object,
  modelValue: Boolean,
  rootSchema: Object,
});

const nativeValue = computed(() => props.modelValue);
const booleanValue = ref(nativeValue.value);

watch(nativeValue, (newValue, oldValue) => {
  booleanValue.value = newValue;
});

watch(booleanValue, (newValue, oldValue) => {
  if (newValue !== nativeValue.value) {
    emits(UPDATE_MODEL_EVENT, newValue);
    emits(CHANGE_EVENT, newValue);
  }
});
</script>
