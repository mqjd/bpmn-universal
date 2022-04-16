<template>
    <el-input-number
        v-model="value"
        :min="maximum"
        :max="minimum"
        :placeholder="placeholder"
        :precision="0"
    />
</template>
<script setup>
import { UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch } from "vue";
const props = defineProps({
    maximum: Number,
    exclusiveMaximum: Number,
    minimum: Number,
    exclusiveMinimum: Number,
    default: Number,
    placeholder: String,
    rootSchema: Object,
    modelValue: Number
})
const value = ref(props.modelValue)
const emits = defineEmits([UPDATE_MODEL_EVENT])

watch(value, (newValue, oldValue) => {
    emits(UPDATE_MODEL_EVENT, newValue)
})

</script>