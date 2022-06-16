const basicSchema = {
  type: "object",
  required: ["$key", "type"],
  "meta:ui:title": "字段",
  "meta:ui:description": "字段",
  "meta:ui:placeholder": "字段",
  properties: {
    $key: {
      type: "string",
      pattern: "^[a-zA-Z]\\w{0,15}$",
      "meta:ui:title": "字段KEY",
      "meta:ui:description": "字段KEY",
      "meta:ui:placeholder": "字段KEY",
    },
    type: {
      $ref: "#/$defs/simpleTypes",
      "meta:ui:title": "类型",
      "meta:ui:description": "数据类型",
      "meta:ui:placeholder": "请选择数据类型",
    },
    "meta:ui:title": {
      type: "string",
      "meta:ui:title": "字段名称",
      "meta:ui:description": "字段名称",
      "meta:ui:placeholder": "请输入字段名称",
    },
    "meta:ui:description": {
      type: "string",
      "meta:ui:title": "字段描述",
      "meta:ui:description": "字段描述",
      "meta:ui:placeholder": "请输入字段描述",
    },
  },
  $defs: {
    nonNegativeInteger: {
      type: "integer",
      minimum: 0,
    },
    nonNegativeIntegerDefault0: {
      $ref: "#/$defs/nonNegativeInteger",
      default: 0,
    },
    simpleTypes: {
      type: "string",
      enum: ["string", "integer", "number", "boolean", "object", "array"],
    },
    stringArray: {
      type: "array",
      items: { type: "string" },
      uniqueItems: true,
      default: [],
    },
  },
};
export default basicSchema;
