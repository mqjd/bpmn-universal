<template>
  <el-form
    :model="formtValue"
    :rules="rules"
    ref="formRef"
    label-width="120px"
    label-position="left"
  >
    <object-field
      v-model="formtValue"
      :schema="schema"
      :root-schema="modelSchema"
      @change="onFormValueChange"
    ></object-field>
    <el-form-item>
      <el-button type="primary" @click="onSubmit(formRef)">Create</el-button>
      <el-button>Cancel</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { CHANGE_EVENT, UPDATE_MODEL_EVENT } from "@/constants";
import { tileSchemaOnlyDefs } from "@/utils/json-schema";
const formRef = ref();

const emits = defineEmits([CHANGE_EVENT, UPDATE_MODEL_EVENT]);
const props = defineProps({
  schema: {
    type: Object,
    required: true,
  },
  modelValue: {
    type: Object,
  },
});

const nativeValue = computed(() => props.modelValue);
const formtValue = ref(nativeValue.value);

watch(nativeValue, (newValue, oldValue) => {
  formRef.value.clearValidate();
  formtValue.value = newValue;
});

const modelSchema = tileSchemaOnlyDefs(props.schema);

// TODO schema to from validator
const rules = computed(() =>
  Object.fromEntries(
    props.schema.required.map((key) => {
      return [
        key,
        [
          {
            required: true,
            message: "this field is reqquired",
            trigger: "blur",
          },
        ],
      ];
    })
  )
);
const onFormValueChange = (value) => {
  emits(UPDATE_MODEL_EVENT, value);
  emits(CHANGE_EVENT, value);
};

const onSubmit = async (formEl) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      console.log("submit!");
    } else {
      console.log("error submit!");
      return false;
    }
  });
};
</script>
