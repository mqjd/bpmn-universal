<template>
    <el-select v-model="stringValue" :placeholder="schema.placeholder" clearable v-if="schema.enum">
        <el-option v-for="item in schema.enum" :key="item" :label="item" :value="item" />
    </el-select>
    <el-input
        v-model="stringValue"
        :maxLength="schema.maxLength"
        :minLength="schema.minLength"
        :placeholder="schema.placeholder"
        v-else
    />
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from '@/constants'
import { ref, computed, watch } from "vue";

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT])
const props = defineProps({
    schema: Object,
    modelValue: String | Array
})

const nativeValue = computed(() => props.modelValue)
const stringValue = ref(nativeValue.value)

watch(nativeValue, (newValue, oldValue) => {
    stringValue.value = newValue;
})

watch(stringValue, (newValue, oldValue) => {
    emits(UPDATE_MODEL_EVENT, newValue)
    emits(CHANGE_EVENT, newValue)
})
</script>