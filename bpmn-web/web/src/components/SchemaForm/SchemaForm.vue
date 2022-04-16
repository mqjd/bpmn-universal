<template>
    <el-form :model="formtValue" ref="formRef" label-width="120px" label-position="left">
        <object-field v-model="formtValue" :schema="schema" :root-schema="modelSchema"></object-field>
        <el-form-item>
            <el-button type="primary" @click="onSubmit(formRef)">Create</el-button>
            <el-button>Cancel</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from '@/constants'
import { tileSchemaOnlyDefs } from '@/utils/json-schema'
const formRef = ref()

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT])
const props = defineProps({
    schema: {
        type: Object,
        required: true
    },
    modelValue: {
        type: Object
    }
})

const nativeValue = computed(() => props.modelValue)
const formtValue = ref(nativeValue.value)

watch(nativeValue, (newValue, oldValue) => {
    formtValue.value = newValue;
})

watch(formtValue, (newValue, oldValue) => {
    emits(UPDATE_MODEL_EVENT, newValue)
    emits(CHANGE_EVENT, newValue)
})

const modelSchema = tileSchemaOnlyDefs(props.schema)

const onSubmit = (formEl) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            console.log('submit!')
        } else {
            console.log('error submit!')
            return false
        }
    })
}
</script>