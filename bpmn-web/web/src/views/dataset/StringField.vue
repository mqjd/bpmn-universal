<template>
  <el-input
    v-model="refa"
    @change="onChange"
    :maxLength="schema.maxLength"
    :minLength="schema.minLength"
    :placeholder="schema.placeholder"
  />
</template>
<script setup>
import { toRefs, watch, ref } from "vue";
const props = defineProps(["schema", "a"]);
const refa = ref("");
const emits = defineEmits(["a"]);
const { a } = toRefs(props);
const onChange = (data) => {
  emits("a", data);
};
</script>
