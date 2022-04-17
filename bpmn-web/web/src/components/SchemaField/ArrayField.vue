<template>
    <el-button @click="appendItem" circle v-if="arrayValues.length === 0">
        <el-icon :size="15">
            <circle-plus />
        </el-icon>
    </el-button>
    <div class="schema-field-container" v-for="(item, index) of arrayValues" :key="index">
        <schema-field
            class="schema-field"
            :schema="schema.items"
            :root-schema="rootSchema"
            v-model="arrayValues[index]"
            @change="(value) => onItemChange(value, index)"
        />
        <el-button @click="appendItem(index)" circle>
            <el-icon :size="15">
                <circle-plus />
            </el-icon>
        </el-button>
        <el-button @click="removeItem(index)" circle>
            <el-icon :size="15">
                <delete />
            </el-icon>
        </el-button>
    </div>
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from '@/constants'
import { ref, watch, computed } from "vue";

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT])
const props = defineProps({
    schema: Object,
    rootSchema: Object,
    modelValue: {
        type: Array,
        default: []
    }
})

const nativeValue = computed(() => props.modelValue)
const arrayValues = ref(nativeValue.value)

watch(nativeValue, (newValue, oldValue) => {
    arrayValues.value = newValue;
})
watch(arrayValues, (newValue, oldValue) => {
    emits(UPDATE_MODEL_EVENT, newValue)
})

const appendItem = (index) => {
    arrayValues.value.splice(index + 1, 0, null)
    onItemChange();
}

const removeItem = (index) => {
    arrayValues.value.splice(index, 1)
    onItemChange();
}
const onItemChange = (value, index) => {
    arrayValues.value[index] = value
    emits(UPDATE_MODEL_EVENT, arrayValues.value)
    emits(CHANGE_EVENT, arrayValues.value)
}

</script>
<style>
.schema-field-container {
    display: flex;
    align-items: center;
    margin-bottom: 2px;
}
.schema-field {
    display: inline-block;
    margin-right: 1em;
}
</style>