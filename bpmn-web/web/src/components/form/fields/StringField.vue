<template>
    <el-select
        v-model="modelValue"
        :placeholder="schema.placeholder"
        clearable
        v-if="schema.enum"
        @change="handleChange"
    >
        <el-option v-for="item in schema.enum" :key="item" :label="item" :value="item" />
    </el-select>
    <el-input
        v-model="modelValue"
        @change="handleChange"
        :maxLength="schema.maxLength"
        :minLength="schema.minLength"
        :placeholder="schema.placeholder"
        v-else
    />
</template>
<script setup>
const props = defineProps({
    schema: Object,
    modelValue: String,
})
const emit = defineEmits(['change', 'delete'])
const handleChange = (value) => {
    emit('change', value)
}
</script>