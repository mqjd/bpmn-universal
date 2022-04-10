<template>
    <component :is="schema.type + '-field'" :schema="schema" v-model="modelValue" @change="handleChange" />
</template>
<script>
import { defineComponent, defineEmits} from "vue";
import ArrayField from './ArrayField.vue';
import BooleanField from './BooleanField.vue'
import StringField from './StringField.vue'
import IntegerField from './IntegerField.vue'
import NumberField from './NumberField.vue'
const emit = defineEmits(['change', 'delete'])
export default defineComponent({
    name: "SchemaField",
    props: {
        schema: Object,
        modelValue: String
    },
    components: {
        ArrayField,
        BooleanField,
        StringField,
        IntegerField,
        NumberField,
    },
    methods: {
        handleChange(event) {
            console.log(event)
        }
    }
})
</script>