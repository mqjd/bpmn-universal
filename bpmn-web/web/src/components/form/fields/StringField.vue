<template>
    <el-select v-model="value" :placeholder="schema.placeholder" clearable v-if="schema.enum">
        <el-option v-for="item in schema.enum" :key="item" :label="item" :value="item" />
    </el-select>
    <el-input
        v-model="value"
        :maxLength="schema.maxLength"
        :minLength="schema.minLength"
        :placeholder="schema.placeholder"
        v-else
    />
</template>
<script setup>
import { UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch } from "vue";
const props = defineProps({
    schema: Object,
    modelValue: String | Array,
})
const value = ref(props.modelValue)
const emits = defineEmits([UPDATE_MODEL_EVENT])

watch(value, (newValue, oldValue) => {
    emits(UPDATE_MODEL_EVENT, newValue)
})
</script>