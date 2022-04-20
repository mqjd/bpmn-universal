<template>
  <el-form-item
    v-for="item of properties"
    :key="item[0]"
    :label="item[1]['meta:ui:title']"
    :prop="item[0]"
  >
    <schema-field
      :schema="item[1]"
      v-model="objectValue[item[0]]"
      :root-schema="rootSchema"
      @change="(value) => onChange(item[0], value, item[1])"
    />
  </el-form-item>
</template>
<script setup>
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from "@/constants";
import { createValidater, tileSchema } from "@/utils/json-schema";
import { ref, watch, computed } from "vue";

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT]);
const props = defineProps({
  schema: {
    type: Object,
    required: true,
  },
  rootSchema: {
    type: Object,
  },
  modelValue: {
    type: Object,
    default: () => {},
  },
});

const nativeValue = computed(() => props.modelValue);
const objectValue = ref(nativeValue.value);

watch(nativeValue, (newValue, oldValue) => {
  objectValue.value = newValue;
});

const onChange = (key, value, schema) => {
  emits(CHANGE_EVENT, objectValue.value);
  emits(UPDATE_MODEL_EVENT, objectValue.value);
};

const ifValidators = computed(() => {
  const allOf = props.schema.allOf;
  if (allOf) {
    return allOf.map((v) => [createValidater(v.if), v.then]);
  }
  return [];
});

const id = computed(() => objectValue.value.id);

const ifProperties = computed(() => {
  var validators = ifValidators.value;
  for (let i = 0; i < validators.length; i++) {
    if (validators[i][0](objectValue.value)) {
      return Object.entries(validators[i][1].properties);
    }
  }
  return [];
});

watch(
  [id, ifProperties],
  ([newId, newIfProperties], [oldId, oldIfProperties]) => {
    if (newId === oldId) {
      oldIfProperties.forEach((v) => {
        delete objectValue.value[v[0]];
      });
    }
  }
);

const properties = computed(() => {
  return Object.entries(props.schema.properties)
    .concat(ifProperties.value)
    .map((v) => [v[0], tileSchema(v[1], props.rootSchema)]);
});
</script>
<style>
.el-form-item__content > div,
.el-form-item__content > div > div {
  width: 100%;
}
</style>
