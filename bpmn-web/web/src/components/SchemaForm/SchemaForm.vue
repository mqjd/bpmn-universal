<template>
    <el-form :model="modelValue" ref="formRef" label-width="120px" label-position="left">
        <object-field
            v-model="modelValue"
            :schema="schema"
            :root-schema="modelSchema"
            @change="onChange"
        ></object-field>
        <el-form-item>
            <el-button type="primary" @click="onSubmit(formRef)">Create</el-button>
            <el-button>Cancel</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import { ref } from "vue";
import { UPDATE_MODEL_EVENT } from '@/constants'
import { tileSchemaOnlyDefs } from '@/utils/json-schema'
const formRef = ref()

const props = defineProps({
    schema: {
        type: Object,
        required: true
    },
    modelValue: {
        type: Object
    }
})
const emits = defineEmits([UPDATE_MODEL_EVENT])
const onChange = (value) => {
    emits(UPDATE_MODEL_EVENT, value)
}

const modelSchema = tileSchemaOnlyDefs(props.schema)
</script>