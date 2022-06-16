import Ajv2019 from "ajv/dist/2019";

const ajv = new Ajv2019({
  allErrors: true,
  removeAdditional: true,
  useDefaults: true,
});

const keywords = {
  meta: {
    ui: {
      title: "field title",
      description: "field description",
      placeholder: "field placeholder",
      visible: "field visible",
    },
  },
};

const schemaKeywordsPriority = {
  $defs: 1,
};

const isObject = (obj) =>
  typeof obj === "object" && !Array.isArray(obj) && obj !== null;

const isArray = Array.isArray;

const parseKeywords = (obj, prefix) => {
  if (isObject(obj)) {
    return Object.entries(obj).flatMap((v) => {
      return parseKeywords(v[1], prefix ? prefix + ":" + v[0] : v[0]);
    });
  } else {
    return [prefix];
  }
};

parseKeywords(keywords).forEach((keyword) => {
  ajv.addKeyword({
    keyword: keyword,
  });
});

export const createValidater = (schema) => {
  return ajv.compile(schema);
};

export const validate = (schema, data) => {
  return ajv.validate(schema, data);
};

const getRefSchema = (refKey, schema) => {
  try {
    const routers = refKey.split("/");
    let result = schema;
    for (let i = 1; i < routers.length; i++) {
      result = result[routers[i]];
    }
    return result;
  } catch (e) {
    console.log(refKey, schema);
    throw e;
  }
};

export const tileSchema = (schema, root) => {
  if (isArray(schema)) {
    return schema.map((v) => tileSchema(v, root));
  } else if (isObject(schema)) {
    // eslint-disable-next-line no-prototype-builtins
    if (schema.hasOwnProperty("$ref")) {
      const refValue = getRefSchema(schema["$ref"], root);
      const { $ref, ...others } = schema;
      return { ...refValue, ...others };
    } else {
      return Object.entries(schema)
        .map((v) => [v[0], tileSchema(v[1], root)])
        .map((v) => Object.fromEntries([v]))
        .reduce((all, item) => {
          return { ...all, ...item };
        }, {});
    }
  } else {
    return schema;
  }
};

export const tileSchemaOnlyDefs = (schema) => {
  // eslint-disable-next-line no-prototype-builtins
  if (schema.hasOwnProperty("$defs")) {
    const { $defs, ...others } = schema;
    return { $defs: tileSchema($defs, schema), ...others };
  }
  return schema;
};

export const tileSchemaRef = (schema) => tileSchema(schema, schema);

const schemaToTreeNode = (schema) => {
  const { properties, items, id, ...others } = schema;
  if (schema.type === "object") {
    const children = Object.entries(properties || {}).map((v, index) => {
      return {
        $key: v[0],
        id: `${id}-${index + 1}`,
        ...schemaToTreeNode(v[1]),
      };
    });
    return { id, children, ...others };
  } else if (schema.type === "array") {
    const children = [
      {
        $key: "",
        id: `${id}-1`,
        ...items,
      },
    ];
    return { id, children, ...others };
  }
  return schema;
};

export const schemaToTree = (schema) => {
  schema.id = "1";
  return schemaToTreeNode(tileSchemaRef(schema));
};

const treeNodeToSchema = (treeNode) => {
  const { $key, type, children, notValid, ...others } = treeNode;
  if ("object" === type) {
    const properties = Object.fromEntries(
      children.map((node) => [node.$key, treeNodeToSchema(node)])
    );
    return { properties, type, $key, ...others };
  } else if ("array" === type) {
    const items = children.length === 1 ? treeNodeToSchema(children[0]) : {};
    return { items, type, $key, ...others };
  } else {
    return { type, $key, ...others };
  }
};

export const treeToSchema = (tree) => treeNodeToSchema(tree[0]);
