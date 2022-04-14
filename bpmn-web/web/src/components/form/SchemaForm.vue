<template>
    <el-form :model="modelValue" ref="formRef" label-width="120px">
        <object-field v-model="modelValue" :schema="modelSchema" @change="onChange"></object-field>
        <el-form-item>
            <el-button type="primary" @click="onSubmit(formRef)">Create</el-button>
            <el-button>Cancel</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import ObjectField from './fields/ObjectField.vue'
import { ref, computed } from "vue";
import { UPDATE_MODEL_EVENT } from '@/constants'
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

const getRefSchema = (refKey, schema) => {
    const routers = refKey.split('/');
    let result = schema;
    for (let i = 1; i < routers.length; i++) {
        result = result[routers[i]]
    }
    return result;
}

const getSchema = (schema, root) => {
    if (schema.hasOwnProperty('$ref')) {
        const refValue = getRefSchema(schema['$ref'], root)
        const { $ref, ...others } = schema;
        return { ...others, ...refValue }
    } else {
        return Object.entries(schema).map(v => {
            if (typeof v[1] == 'object') {
                v[1] = getSchema(v[1], root)
            }
            return v;

        }).map(v => Object.fromEntries([v])).reduce((all, item) => { return { ...all, ...item } }, {});
    }
}

const modelSchema = computed(() => getSchema(props.schema, props.schema))
</script>