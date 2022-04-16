<template>
    <div>
        <component
            :is="schema.type + '-field'"
            :schema="schema"
            :root-schema="rootSchema"
            v-model="schemaValue"
        />
    </div>
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch, computed } from "vue";
const props = defineProps({
    schema: {
        type: Object,
        required: true
    },
    rootSchema: Object,
    modelValue: Object | String
})

const nativeInputValue = computed(() => props.modelValue)
const schemaValue = ref(nativeInputValue.value)

watch(nativeInputValue, (newValue, oldValue) => {
    schemaValue.value = newValue;
})
const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT])

watch(schemaValue, (newValue, oldValue) => {
    emits(CHANGE_EVENT, newValue)
    emits(UPDATE_MODEL_EVENT, newValue)
})
</script>