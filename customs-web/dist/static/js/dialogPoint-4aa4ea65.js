import{d as e,p as a,r as l,a as t,g as o,f as d,w as r,j as u,b as m,y as p,i as f,ag as i,K as s,E as n}from"./vendor-78a6dd30.js";import{g as b,u as y,q as D}from"./index-30d780dc.js";import v from"./baiduMap-0b00d2a7.js";const h={style:{height:"500px"}},V={class:"dialog-footer"},c=s("审核通过"),_=s("审核不通过"),g=s("取 消"),w=e({props:{dialogPoint:{require:!0,default:null,type:Object},formData:{require:!0,default:null,type:Object}},emits:["audit"],setup(e,{emit:s}){const w=e,k=a("https://www.nteport.com/ycjAdmin"),x=a(""),U=a(!1),P=a({authToken:b()}),I=a([]),j=a(null),L=a([]),N=e=>{2==e&&y(w.formData.id,e).then((e=>{w.dialogPoint.show=!1,n({message:"审核已通过",type:"success"}),s("audit")})),3==e&&y(w.formData.id,e).then((e=>{w.dialogPoint.show=!1,n({message:"已更新审核状态",type:"success"}),s("audit")}))},B=(e,a,l)=>{w.formData.photoId?w.formData.photoId+=e.data+",":w.formData.photoId=e.data+","},F=e=>{x.value=e.url,U.value=!0},M=(e,a)=>{void 0===e.response?w.formData.photoId=w.formData.photoId.replace(e.name+",",""):w.formData.photoId=w.formData.photoId.replace(e.response.data+",","")},C=()=>{if(w.formData.id&&(w.formData.pointId?D({Id:w.formData.pointId}).then((e=>{L.value.push({name:e.data.name,id:e.data.id})})):L.value=[],w.formData.photoId)){let a=w.formData.photoId.split(",");for(var e=0;e<a.length;e++)""!=a[e]&&I.value.push({name:a[e],url:k.value+"/file/downLoad/"+a[e]})}},T=()=>{w.dialogPoint.show=!1,j.value.resetFields(),I.value=[],L.value=[]};return(a,s)=>{const n=l("el-form-item"),b=l("el-input"),y=l("el-option"),D=l("el-select"),w=l("el-icon"),x=l("el-upload"),U=l("el-table-column"),L=l("el-table"),q=l("el-form"),G=l("el-scrollbar"),O=l("el-button"),R=l("el-dialog");return t(),o("div",null,[d(R,{modelValue:e.dialogPoint.show,"onUpdate:modelValue":s[21]||(s[21]=a=>e.dialogPoint.show=a),title:e.dialogPoint.title,width:"40%",center:"",onOpen:C,onClose:T,"destroy-on-close":""},{footer:r((()=>[u("div",V,[1==e.formData.status?(t(),m(O,{key:0,type:"primary",onClick:s[19]||(s[19]=e=>N(2))},{default:r((()=>[c])),_:1})):p("v-if",!0),1==e.formData.status?(t(),m(O,{key:1,type:"info",onClick:s[20]||(s[20]=e=>N(3))},{default:r((()=>[_])),_:1})):p("v-if",!0),d(O,{onClick:T},{default:r((()=>[g])),_:1})])])),default:r((()=>[u("div",h,[d(G,null,{default:r((()=>[u("div",null,[d(q,{ref_key:"refPointForm",ref:j,model:e.formData,"label-width":"100px"},{default:r((()=>[d(n,{label:"点位坐标",prop:"position"},{default:r((()=>[d(v,{msg:e.formData.position,style:{width:"400px",height:"200px"}},null,8,["msg"])])),_:1}),d(n,{label:"点位名称",prop:"name"},{default:r((()=>[d(b,{modelValue:e.formData.name,"onUpdate:modelValue":s[0]||(s[0]=a=>e.formData.name=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1}),d(n,{label:"点位类型",prop:"type"},{default:r((()=>[d(D,{modelValue:e.formData.type,"onUpdate:modelValue":s[1]||(s[1]=a=>e.formData.type=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"码头",value:"1"}),d(y,{label:"渔港",value:"2"}),d(y,{label:"船闸",value:"3"}),d(y,{label:"船舶临时停靠点",value:"4"}),d(y,{label:"其他走私风险点",value:"5"})])),_:1},8,["modelValue"])])),_:1}),d(n,{label:"现场图片",prop:"photoId"},{default:r((()=>[d(x,{action:k.value+"/file/upload?type="+e.formData.id,"list-type":"picture-card",accept:".jpg,.jpeg,.png,.bmp,.JPG,.JPEG,.PBG,.BMP","on-success":B,"on-preview":F,"on-remove":M,"auto-upload":!0,headers:P.value,"file-list":I.value,disabled:""},{default:r((()=>[d(w,null,{default:r((()=>[d(f(i))])),_:1})])),_:1},8,["action","headers","file-list"])])),_:1}),d(n,{label:"所在地",prop:"area"},{default:r((()=>[d(b,{modelValue:e.formData.area,"onUpdate:modelValue":s[2]||(s[2]=a=>e.formData.area=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1}),d(n,{label:"沿江/沿海",prop:"along"},{default:r((()=>[d(D,{modelValue:e.formData.along,"onUpdate:modelValue":s[3]||(s[3]=a=>e.formData.along=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"沿江",value:"1"}),d(y,{label:"沿海",value:"2"})])),_:1},8,["modelValue"])])),_:1}),1==e.formData.type?(t(),m(n,{key:0,label:"性质",prop:"properties"},{default:r((()=>[d(D,{modelValue:e.formData.properties,"onUpdate:modelValue":s[4]||(s[4]=a=>e.formData.properties=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"民营",value:"1"}),d(y,{label:"国有",value:"2"}),d(y,{label:"合资",value:"3"}),d(y,{label:"个体",value:"4"})])),_:1},8,["modelValue"])])),_:1})):p("v-if",!0),1==e.formData.type?(t(),m(n,{key:1,label:"类别",prop:"category"},{default:r((()=>[d(D,{modelValue:e.formData.category,"onUpdate:modelValue":s[5]||(s[5]=a=>e.formData.category=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"危化品",value:"1"}),d(y,{label:"通用散杂货",value:"2"})])),_:1},8,["modelValue"])])),_:1})):p("v-if",!0),2==e.formData.type?(t(),m(n,{key:2,label:"类别",prop:"category"},{default:r((()=>[d(D,{modelValue:e.formData.category,"onUpdate:modelValue":s[6]||(s[6]=a=>e.formData.category=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"开放式",value:"1"}),d(y,{label:"封闭式",value:"2"})])),_:1},8,["modelValue"])])),_:1})):p("v-if",!0),3==e.formData.type?(t(),m(n,{key:3,label:"类别",prop:"category"},{default:r((()=>[d(D,{modelValue:e.formData.category,"onUpdate:modelValue":s[7]||(s[7]=a=>e.formData.category=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"水闸",value:"1"}),d(y,{label:"船闸",value:"2"})])),_:1},8,["modelValue"])])),_:1})):p("v-if",!0),2==e.formData.type?(t(),m(n,{key:4,label:"码头数",prop:"dockNum"},{default:r((()=>[d(b,{modelValue:e.formData.dockNum,"onUpdate:modelValue":s[8]||(s[8]=a=>e.formData.dockNum=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1})):p("v-if",!0),1==e.formData.type?(t(),m(n,{key:5,label:"泊位数",prop:"berthNum"},{default:r((()=>[d(b,{modelValue:e.formData.berthNum,"onUpdate:modelValue":s[9]||(s[9]=a=>e.formData.berthNum=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1})):p("v-if",!0),2==e.formData.type||3==e.formData.type?(t(),m(n,{key:6,label:"可停靠船舶数",prop:"berthNum"},{default:r((()=>[d(b,{modelValue:e.formData.berthNum,"onUpdate:modelValue":s[10]||(s[10]=a=>e.formData.berthNum=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1})):p("v-if",!0),1==e.formData.type||2==e.formData.type||4==e.formData.type?(t(),m(n,{key:7,label:"吊机情况",prop:"crane"},{default:r((()=>[d(D,{modelValue:e.formData.crane,"onUpdate:modelValue":s[11]||(s[11]=a=>e.formData.crane=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"有吊机",value:"1"}),d(y,{label:"无吊机",value:"2"})])),_:1},8,["modelValue"])])),_:1})):p("v-if",!0),1==e.formData.type||3==e.formData.type||4==e.formData.type?(t(),m(n,{key:8,label:"最大吨级",prop:"maxTon"},{default:r((()=>[d(b,{modelValue:e.formData.maxTon,"onUpdate:modelValue":s[12]||(s[12]=a=>e.formData.maxTon=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1})):p("v-if",!0),3==e.formData.type?(t(),m(n,{key:9,label:"是否通航",prop:"whetherSail"},{default:r((()=>[d(D,{modelValue:e.formData.whetherSail,"onUpdate:modelValue":s[13]||(s[13]=a=>e.formData.whetherSail=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"是",value:"1"}),d(y,{label:"否",value:"2"})])),_:1},8,["modelValue"])])),_:1})):p("v-if",!0),3==e.formData.type?(t(),m(n,{key:10,label:"日均进闸船舶数量",prop:"averageBoats"},{default:r((()=>[d(b,{modelValue:e.formData.averageBoats,"onUpdate:modelValue":s[14]||(s[14]=a=>e.formData.averageBoats=a),disabled:"",style:{width:"200px"}},null,8,["modelValue"])])),_:1})):p("v-if",!0),4==e.formData.type?(t(),m(n,{key:11,label:"用途",prop:"useFor"},{default:r((()=>[d(b,{modelValue:e.formData.useFor,"onUpdate:modelValue":s[15]||(s[15]=a=>e.formData.useFor=a),disabled:"",type:"textarea"},null,8,["modelValue"])])),_:1})):p("v-if",!0),d(n,{label:"走私风险等级",prop:"riskLevel"},{default:r((()=>[d(D,{modelValue:e.formData.riskLevel,"onUpdate:modelValue":s[16]||(s[16]=a=>e.formData.riskLevel=a),disabled:"",style:{width:"200px"}},{default:r((()=>[d(y,{label:"低",value:"1"}),d(y,{label:"中",value:"2"}),d(y,{label:"高",value:"3"})])),_:1},8,["modelValue"])])),_:1}),1!=e.formData.riskLevel?(t(),m(n,{key:12,label:"风险原因",prop:"riskReason"},{default:r((()=>[d(b,{modelValue:e.formData.riskReason,"onUpdate:modelValue":s[17]||(s[17]=a=>e.formData.riskReason=a),disabled:"",type:"textarea"},null,8,["modelValue"])])),_:1})):p("v-if",!0),1!=e.formData.riskLevel?(t(),m(n,{key:13,label:"监管举措",prop:"manageMeasure"},{default:r((()=>[d(b,{modelValue:e.formData.manageMeasure,"onUpdate:modelValue":s[18]||(s[18]=a=>e.formData.manageMeasure=a),disabled:"",type:"textarea"},null,8,["modelValue"])])),_:1})):p("v-if",!0),d(n,{label:"主要负责人",prop:"maintainPersonList"},{default:r((()=>[d(L,{data:e.formData.maintainPersonList,"show-header":!1,style:{width:"50%"},border:!0},{default:r((()=>[d(U,{prop:"name",label:"姓名"}),d(U,{prop:"job",label:"职务"}),d(U,{prop:"telephone",label:"电话"})])),_:1},8,["data"])])),_:1}),1!=e.formData.riskLevel?(t(),m(n,{key:14,label:"监管负责人",prop:"supervisePersonList"},{default:r((()=>[d(L,{data:e.formData.supervisePersonList,"show-header":!1,style:{width:"50%"},border:!0},{default:r((()=>[d(U,{prop:"name",label:"姓名"}),d(U,{prop:"job",label:"职务"}),d(U,{prop:"telephone",label:"电话"})])),_:1},8,["data"])])),_:1})):p("v-if",!0)])),_:1},8,["model"])])])),_:1})])])),_:1},8,["modelValue","title"])])}}});export{w as default};
