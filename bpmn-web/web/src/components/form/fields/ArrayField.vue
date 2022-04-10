<template>
    <el-input
        v-model="input"
        :maxLength="props.maxLength"
        :minLength="props.minLength"
        :placeholder="placeholder"
    />
</template>
<script setup>
import { ref } from 'vue'
const input = ref('')

const props = defineProps({
    maxLength: Number,
    minLength: Number,
    placeholder: String,
})

</script>