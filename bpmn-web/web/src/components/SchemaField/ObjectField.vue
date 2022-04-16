<template>
    <el-form-item v-for="item of properties" :key="item[0]" :label="item[1]['meta:ui:title']">
        <schema-field
            :schema="item[1]"
            v-model="objectValue[item[0]]"
            :root-schema="rootSchema"
            @change="(value) => onChange(item[0], value, item[1])"
        />
    </el-form-item>
</template>
<script setup>
import { CHANGE_EVENT,UPDATE_MODEL_EVENT } from '@/constants'
import { createValidater, tileSchema } from '@/utils/json-schema'
import { reactive, watch, computed } from "vue";
const props = defineProps({
    schema: {
        type: Object,
        required: true
    },
    rootSchema: {
        type: Object
    },
    modelValue: {
        type: Object,
        default: {}
    }
})
const objectValue = reactive(props.modelValue)
const emits = defineEmits([CHANGE_EVENT,UPDATE_MODEL_EVENT])
watch(objectValue, (newValue, oldValue) => {
    emits(CHANGE_EVENT, newValue)
    emits(UPDATE_MODEL_EVENT, newValue)
})

const onChange = (key, value, schema) => {
    objectValue[key] = value;
}

const ifValidators = computed(() => {
    const allOf = props.schema.allOf
    if (allOf) {
        return allOf.map(v => [createValidater(v.if), v.then])
    }
    return [];
})

const ifProperties = computed(() => {
    var validators = ifValidators.value
    for (let i = 0; i < validators.length; i++) {
        if (validators[i][0](objectValue)) {
            return Object.entries(validators[i][1].properties)
        }
    }
    return [];
})

const properties = computed(() => {
    return Object.entries(props.schema.properties)
        .concat(ifProperties.value)
        .map(v => [v[0], tileSchema(v[1], props.rootSchema)]);
})
</script>