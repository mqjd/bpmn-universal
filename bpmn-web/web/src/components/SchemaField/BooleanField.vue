<template>
    <el-radio-group v-model="value">
        <el-radio :label="true">是</el-radio>
        <el-radio :label="false">否</el-radio>
    </el-radio-group>
</template>
<script setup>
import { UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch } from "vue";
const props = defineProps({
    schema: Object,
    modelValue: Boolean,
    rootSchema: Object
})
const value = ref(props.modelValue)
const emits = defineEmits([UPDATE_MODEL_EVENT])

watch(value, (newValue, oldValue) => {
    emits(UPDATE_MODEL_EVENT, newValue)
})

</script>