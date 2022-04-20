<template>
  <el-input-number
    v-model="numberValue"
    :min="schema.minimum"
    :max="schema.maximum"
    :placeholder="schema.placeholder"
    :precision="0"
  />
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from "@/constants";
import { ref, watch, computed } from "vue";

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT]);
const props = defineProps({
  schema: {
    type: Object,
    required: true,
  },
  rootSchema: Object,
  modelValue: Number,
});

const nativeValue = computed(() => props.modelValue);
const numberValue = ref(nativeValue.value);

watch(nativeValue, (newValue, oldValue) => {
  numberValue.value = newValue;
});

watch(numberValue, (newValue, oldValue) => {
  if (newValue !== nativeValue.value) {
    emits(UPDATE_MODEL_EVENT, newValue);
    emits(CHANGE_EVENT, newValue);
  }
});
</script>
