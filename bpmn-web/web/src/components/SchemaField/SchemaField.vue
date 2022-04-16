<template>
    <div>
        <component
            :is="schema.type + '-field'"
            :schema="schema"
            :root-schema="rootSchema"
            v-model="value"
        />
    </div>
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch } from "vue";
const props = defineProps({
    schema: {
        type: Object,
        required: true
    },
    rootSchema: Object,
    modelValue: Object | String
})
const value = ref(props.modelValue)

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT])

watch(value, (newValue, oldValue) => {
    emits(CHANGE_EVENT, newValue)
    emits(UPDATE_MODEL_EVENT, newValue)
})

</script>