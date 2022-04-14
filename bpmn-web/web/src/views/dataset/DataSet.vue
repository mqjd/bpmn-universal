<template>
  <string-field :schema="schema" :a="model" @a="onA"></string-field>
  <el-button @click="onClick">Default</el-button>
</template>

<script setup>
import StringField from './StringField.vue'
import { ref, reactive } from "vue";
const schema = reactive({
  "meta:ui:title": "字段名称",
  "meta:ui:description": "字段名称",
  "meta:ui:placeholder": "请输入字段名称",
})
const model = ref('asd')
const onClick = () => {
  console.log(model.value)
}
const onA = (value) => {
  model.value = value
}
</script>