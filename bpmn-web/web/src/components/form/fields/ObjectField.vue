<template>
    <el-form-item v-for="item of properties" :key="item.key" :label="item.value['meta:ui:title']">
        <schema-field :schema="item.value" v-model="modelValue[item.key]" v-if="item.value.type !== 'object'" />
    </el-form-item>
</template>
<script setup>
import SchemaField from './SchemaField.vue';

const props = defineProps({
    schema: Object,
    modelValue: Object
})
const properties = Object.keys(props.schema.properties).map(v => {
    return {
        key: v,
        value: props.schema.properties[v]
    }
})

</script>