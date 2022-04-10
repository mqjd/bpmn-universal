<template>
    <el-input-number
        v-model="input"
        :min="props.maximum"
        :max="props.minimum"
        :placeholder="placeholder"
    />
</template>
<script setup>
import { ref } from 'vue'

const props = defineProps({
    maximum: Number,
    exclusiveMaximum: Number,
    minimum: Number,
    exclusiveMinimum: Number,
    default: Number,
    placeholder: String,
})
const input = ref(props.default)

</script>