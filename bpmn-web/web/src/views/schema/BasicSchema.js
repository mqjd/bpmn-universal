const basicSchema = {
    "type": "object",
    "required": ["type", "meta:ui:title"],
    "meta:ui:title": "字段",
    "meta:ui:description": "字段",
    "meta:ui:placeholder": "字段",
    "properties": {
        "$key": {
            "type": "string",
            "meta:ui:title": "字段KEY",
            "meta:ui:description": "字段KEY",
            "meta:ui:placeholder": "字段KEY"
        },
        "type": {
            "$ref": "#/$defs/simpleTypes",
            "meta:ui:title": "类型",
            "meta:ui:description": "数据类型",
            "meta:ui:placeholder": "请选择数据类型"
        },
        "meta:ui:title": {
            "type": "string",
            "meta:ui:title": "字段名称",
            "meta:ui:description": "字段名称",
            "meta:ui:placeholder": "请输入字段名称"
        },
        "meta:ui:description": {
            "type": "string",
            "meta:ui:title": "字段描述",
            "meta:ui:description": "字段描述",
            "meta:ui:placeholder": "请输入字段描述"
        }
    },
    "allOf": [{
        "if": {
            "type": "object",
            "required": ["type"],
            "properties": { "type": { "const": "string" } }
        },
        "then": {
            "properties": {
                "maxLength": {
                    "$ref": "#/$defs/nonNegativeInteger",
                    "meta:ui:title": "最大长度",
                    "meta:ui:description": "最大长度",
                    "meta:ui:placeholder": "请输入允许的最大长度"
                },
                "minLength": {
                    "$ref": "#/$defs/nonNegativeIntegerDefault0",
                    "meta:ui:title": "最小长度",
                    "meta:ui:description": "最小长度",
                    "meta:ui:placeholder": "请输入允许的最小长度"
                },
                "pattern": {
                    "type": "string",
                    "meta:ui:title": "正则",
                    "meta:ui:description": "字符串正则校验",
                    "meta:ui:placeholder": "请输入正则表达式"
                },
                "format": {
                    "type": "string",
                    "meta:ui:title": "规则",
                    "meta:ui:description": "字符串内置规则校验",
                    "meta:ui:placeholder": "请选择规则"
                },
                "enum": {
                    "type": "array",
                    "uniqueItems": true,
                    "items": {
                        "type": "string"
                    },
                    "meta:ui:title": "字典",
                    "meta:ui:description": "字符串可选值",
                    "meta:ui:placeholder": "请选择"
                },
                "default": {
                    "type": "string",
                    "meta:ui:title": "默认值",
                    "meta:ui:description": "默认值",
                    "meta:ui:placeholder": "默认值"
                }
            }
        }
    }, {
        "if": {
            "type": "object",
            "required": ["type"],
            "properties": { "type": { "const": "integer" } }
        },
        "then": {
            "properties": {
                "maximum": {
                    "type": "integer",
                    "exclusiveMinimum": 0,
                    "meta:ui:title": "最大值(包含)",
                    "meta:ui:description": "最大值，包括该值(<=)",
                    "meta:ui:placeholder": "请输入最大值"
                },
                "minimum": {
                    "type": "integer",
                    "meta:ui:title": "最小值(包含)",
                    "meta:ui:description": "最小值，包括该值(>=)",
                    "meta:ui:placeholder": "请输入最小值"
                },
                "exclusiveMaximum": {
                    "type": "integer",
                    "meta:ui:title": "最大值(不包含)",
                    "meta:ui:description": "最大值，不包括该值(<)",
                    "meta:ui:placeholder": "请输入最大值"
                },
                "exclusiveMinimum": {
                    "type": "integer",
                    "meta:ui:title": "最小值(不包含)",
                    "meta:ui:description": "最小值，不包括该值(>=)",
                    "meta:ui:placeholder": "请输入最小值"
                },
                "multipleOf": {
                    "type": "integer",
                    "exclusiveMinimum": 0,
                    "meta:ui:title": "倍数",
                    "meta:ui:description": "数据类型校验规则，数值必须为输入值的整数倍",
                    "meta:ui:placeholder": "请输入"
                },
                "enum": {
                    "type": "array",
                    "uniqueItems": true,
                    "items": { "type": "integer" },
                    "meta:ui:title": "字典",
                    "meta:ui:description": "可选值",
                    "meta:ui:placeholder": "请选择"
                },
                "default": {
                    "type": "integer",
                    "meta:ui:title": "默认值",
                    "meta:ui:description": "默认值",
                    "meta:ui:placeholder": "默认值"
                }
            }
        }
    }, {
        "if": {
            "type": "object",
            "required": ["type"],
            "properties": { "type": { "const": "number" } }
        },
        "then": {
            "properties": {
                "maximum": {
                    "type": "number",
                    "exclusiveMinimum": 0,
                    "meta:ui:title": "最大值(包含)",
                    "meta:ui:description": "最大值，包括该值(<=)",
                    "meta:ui:placeholder": "请输入最大值"
                },
                "minimum": {
                    "type": "number",
                    "meta:ui:title": "最小值(包含)",
                    "meta:ui:description": "最小值，包括该值(>=)",
                    "meta:ui:placeholder": "请输入最小值"
                },
                "exclusiveMaximum": {
                    "type": "number",
                    "meta:ui:title": "最大值(不包含)",
                    "meta:ui:description": "最大值，不包括该值(<)",
                    "meta:ui:placeholder": "请输入最大值"
                },
                "exclusiveMinimum": {
                    "type": "number",
                    "meta:ui:title": "最小值(不包含)",
                    "meta:ui:description": "最小值，不包括该值(>=)",
                    "meta:ui:placeholder": "请输入最小值"
                },
                "precision": {
                    "type": "integer",
                    "minimum": -1,
                    "meta:ui:title": "小数位数",
                    "meta:ui:description": "小数位数)",
                    "meta:ui:placeholder": "小数位数"
                },
                "multipleOf": {
                    "type": "number",
                    "exclusiveMinimum": 0,
                    "meta:ui:title": "倍数",
                    "meta:ui:description": "数据类型校验规则，数值必须为输入值的整数倍",
                    "meta:ui:placeholder": "请输入"
                },
                "enum": {
                    "type": "array",
                    "uniqueItems": true,
                    "items": { "type": "number" },
                    "meta:ui:title": "字典",
                    "meta:ui:description": "可选值",
                    "meta:ui:placeholder": "请选择"
                },
                "default": {
                    "type": "number",
                    "meta:ui:title": "默认值",
                    "meta:ui:description": "默认值",
                    "meta:ui:placeholder": "默认值"
                }
            }
        }
    }, {
        "if": {
            "type": "object",
            "required": ["type"],
            "properties": { "type": { "const": "boolean" } }
        },
        "then": {
            "properties": {
                "enum": {
                    "type": "array",
                    "uniqueItems": true,
                    "items": { "type": "boolean" },
                    "meta:ui:title": "字典",
                    "meta:ui:description": "可选值",
                    "meta:ui:placeholder": "请选择"
                },
                "default": {
                    "type": "boolean",
                    "meta:ui:title": "默认值",
                    "meta:ui:description": "默认值",
                    "meta:ui:placeholder": "默认值"
                }
            }

        }
    }, {
        "if": {
            "type": "object",
            "required": ["type"],
            "properties": { "type": { "const": "array" } }
        },
        "then": {
            "required": ["items"],
            "properties": {
                "maxItems": {
                    "$ref": "#/$defs/nonNegativeInteger",
                    "meta:ui:title": "最大个数",
                    "meta:ui:description": "最大个数",
                    "meta:ui:placeholder": "请输入最大个数"
                },
                "minItems": {
                    "$ref": "#/$defs/nonNegativeIntegerDefault0",
                    "meta:ui:title": "最小个数",
                    "meta:ui:description": "最小个数",
                    "meta:ui:placeholder": "请输入最小个数"
                }
            }
        }
    }],
    "$defs": {
        "nonNegativeInteger": {
            "type": "integer",
            "minimum": 0
        },
        "nonNegativeIntegerDefault0": {
            "$ref": "#/$defs/nonNegativeInteger",
            "default": 0
        },
        "simpleTypes": {
            "type": "string",
            "enum": [
                "string",
                "integer",
                "number",
                "boolean",
                "object",
                "array"
            ]
        },
        "stringArray": {
            "type": "array",
            "items": { "type": "string" },
            "uniqueItems": true,
            "default": []
        }
    }
}
export default basicSchema