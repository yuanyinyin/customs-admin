import{d as a,p as e,l,r as s,ah as r,a as n,g as t,f as o,w as d,X as p,b as i,j as c,k as u}from"./vendor-78a6dd30.js";import{s as m}from"./risk-af0d91d4.js";import"./index-30d780dc.js";const b={class:"LogContainer scroll-y"},f=c("div",{class:"clearfix"},[c("span",null,"隐患统计列表")],-1),g=a({setup(a){const g=e(null);l((()=>{v()}));const v=()=>{m().then((a=>{g.value=a.data})).catch((a=>{}))};return(a,e)=>{const l=s("el-table-column"),m=s("el-table"),v=s("el-card"),j=r("loading");return n(),t("div",b,[o(v,{class:"m-1"},{header:d((()=>[f])),default:d((()=>[p((n(),i(m,{data:g.value,stripe:"",style:{width:"100%"}},{default:d((()=>[o(l,{fixed:"",type:"index",label:"序号",align:"center"},{default:d((a=>[c("span",null,u(a.$index+1),1)])),_:1}),o(l,{prop:"area",label:"地区",align:"center"}),o(l,{prop:"sum",label:"当月隐患上报数",align:"center"}),o(l,{prop:"solvedSum",label:"当月隐患已处理数",align:"center"}),o(l,{prop:"normal",label:"当月一般隐患上报数",align:"center"}),o(l,{prop:"solvedNormal",label:"当月一般隐患已处理数",align:"center"}),o(l,{prop:"major",label:"当月较大风险隐患已处理数",align:"center"}),o(l,{prop:"solvedMajor",label:"当月较大风险隐患已处理数",align:"center"})])),_:1},8,["data"])),[[j,false]])])),_:1})])}}});export{g as default};
