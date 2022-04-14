<template>
    <div>
        <component :is="components[schema.type]" :schema="schema" v-model="value" />
    </div>
</template>
<script setup>
import ArrayField from './ArrayField.vue';
import BooleanField from './BooleanField.vue'
import StringField from './StringField.vue'
import IntegerField from './IntegerField.vue'
import NumberField from './NumberField.vue'

import { CHANGE_EVENT } from '@/constants'
import { ref, watch } from "vue";
const props = defineProps({
    schema: Object,
    modelValue: String | Array,
})
const value = ref(props.modelValue)

const emits = defineEmits([CHANGE_EVENT])

watch(value, (newValue, oldValue) => {
    emits(CHANGE_EVENT, newValue)
})

const components = {
    'string': StringField,
    'boolean': BooleanField,
    'integer': IntegerField,
    'number': NumberField,
    'array': ArrayField
}
</script>