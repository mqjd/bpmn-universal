import{h as u,q as p,r,o as h,e as d,v as _,a as f,b as m,u as g,w as v,F as x,p as C}from"./index.a5a61868.js";const L={props:["schema","a"],emits:["a"],setup(e,{emit:o}){const t=e,n=u("");p(t);const l=a=>{o("a",a)};return(a,s)=>{const c=r("el-input");return h(),d(c,{modelValue:n.value,"onUpdate:modelValue":s[0]||(s[0]=i=>n.value=i),onChange:l,maxLength:e.schema.maxLength,minLength:e.schema.minLength,placeholder:e.schema.placeholder},null,8,["modelValue","maxLength","minLength","placeholder"])}}},k=C("Default"),b={setup(e){const o=_({"meta:ui:title":"\u5B57\u6BB5\u540D\u79F0","meta:ui:description":"\u5B57\u6BB5\u540D\u79F0","meta:ui:placeholder":"\u8BF7\u8F93\u5165\u5B57\u6BB5\u540D\u79F0"}),t=u("asd"),n=()=>{console.log(t.value)},l=a=>{t.value=a};return(a,s)=>{const c=r("el-button");return h(),f(x,null,[m(L,{schema:g(o),a:t.value,onA:l},null,8,["schema","a"]),m(c,{onClick:n},{default:v(()=>[k]),_:1})],64)}}};export{b as default};