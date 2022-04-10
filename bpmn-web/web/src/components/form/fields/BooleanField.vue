<template>
    <el-checkbox v-model="input" size="large" />
</template>
<script setup>
import { ref } from 'vue'
const input = ref(true)

const props = defineProps({
    maxLength: Number,
    minLength: Number,
    placeholder: String,
})

</script>