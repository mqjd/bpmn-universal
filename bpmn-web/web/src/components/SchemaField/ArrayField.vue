<template>
    <div v-for="(item, index) of arrayValues" :key="index">
        <div class="schema-field-container" v-if="keysStatus[index]">
            <schema-field
                class="schema-field"
                :schema="schema.items"
                :root-schema="rootSchema"
                v-model="arrayValues[index]"
                @change="(value) => onItemChange(value, index)"
            />
            <el-button @click="appendItem" circle v-if="index === arrayValues.length - 1">
                <el-icon :size="15">
                    <circle-plus />
                </el-icon>
            </el-button>
            <el-button @click="removeItem(index)" circle v-if="index < arrayValues.length - 1">
                <el-icon :size="15">
                    <delete />
                </el-icon>
            </el-button>
        </div>
    </div>
</template>
<script setup>
import { UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch } from "vue";
const props = defineProps({
    schema: Object,
    rootSchema: Object,
    modelValue: {
        type: Array,
        default: [null]
    }
})
const arrayValues = ref(props.modelValue)
const keysStatus = ref(new Array(arrayValues.value.length).fill(true));
const emits = defineEmits([UPDATE_MODEL_EVENT])

watch(arrayValues, (newValue, oldValue) => {
    console.log(newValue)
    emits(UPDATE_MODEL_EVENT, newValue)
})

const appendItem = () => {
    arrayValues.value.push(null)
    keysStatus.value.push(true)
    onItemChange();
}

const removeItem = (index) => {
    keysStatus.value[index] = false;
    onItemChange();
}
const onItemChange = (value, index) => {
    arrayValues.value[index] = value
    emits(UPDATE_MODEL_EVENT, arrayValues.value.filter((item, index) => keysStatus.value[index]))
}

</script>
<style>
.schema-field-container {
    display: flex;
    align-items: center;
}
.schema-field {
    display: inline-block;
    margin-right: 1em;
}
</style>