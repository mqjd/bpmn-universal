<template>
    <el-form-item v-for="item of properties" :key="item.key" :label="item.value['meta:ui:title']">
        <schema-field
            :schema="item.value"
            v-model="objectValue[item.key]"
            v-if="item.value.type !== 'object'"
            @change="(value) => onChange(item.key, value)"
        />
    </el-form-item>
</template>
<script setup>
import SchemaField from './SchemaField.vue';
import { CHANGE_EVENT } from '@/constants'
import { reactive, watch, computed } from "vue";
const props = defineProps({
    schema: {
        type: Object,
        required: true
    },
    modelValue: {
        type: Object,
        default: {}
    }
})
const objectValue = reactive(props.modelValue)
const emits = defineEmits([CHANGE_EVENT])
const onChange = (key, value) => {
    objectValue[key] = value
}

watch(objectValue, (newValue, oldValue) => {
    emits(CHANGE_EVENT, newValue)
})

const properties = computed(() => Object.keys(props.schema.properties).map(v => {
    return {
        key: v,
        value: props.schema.properties[v]
    }
}))
</script>