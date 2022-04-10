<template>
    <el-form :model="form" ref="formRef" label-width="120px">
        <object-field v-model="form" :schema="properties"></object-field>
        <el-form-item>
            <el-button type="primary" @click="onSubmit(formRef)">Create</el-button>
            <el-button>Cancel</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import { reactive, ref } from "vue";
import ObjectField from './fields/ObjectField.vue'
const formRef = ref()
const props = defineProps({
    schema: Object,
})

const setRef = (schema, root) => {
    if (typeof schema !== 'object') {
        return;
    }
    if (schema.hasOwnProperty('$ref')) {
        const ref = getRef(schema['$ref'], root)
        for (let key in ref) {
            schema[key] = ref[key]
        }
    } else {
        for (let key in schema) {
            setRef(schema[key], root)
        }
    }
    return schema;
}

const getRef = (ref, schema) => {
    const routers = ref.split('/');
    let result = schema;
    for (let i = 1; i < routers.length; i++) {
        result = result[routers[i]]
    }
    return result;
}
const properties = setRef(props.schema, props.schema)

const form = reactive({
    type: 'string'
})

const onSubmit = (formRef) => {
    console.log(form)
}
</script>