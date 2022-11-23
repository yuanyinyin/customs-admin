import{d as e,u as a,p as l,l as t,r,ah as o,a as s,g as n,j as i,y as u,f as d,w as p,ad as c,X as m,b as v,k as g,K as f,i as b,Y as h,J as y,E as k}from"./vendor-78a6dd30.js";import{p as w}from"./dateTime-1b081a2f.js";import _ from"./dialogPoint-4aa4ea65.js";import L from"./dialogRisk-b6a0d8e9.js";import{d as x}from"./index-30d780dc.js";import"./baiduMap-0b00d2a7.js";const C={class:"LogContainer scroll-y"},P={class:"head-container m-1 mr-3 rowSS"},V={class:"filter-item"},K=f("查询"),N=i("div",{class:"clearfix"},[i("span",null,"点位采集列表")],-1),T=f("查看详情"),j=f("修改风险等级"),S=f("删除"),z={class:"block columnCC mt-2"},B=e({setup(e){const B=a(),U=l(null),M=l(null),R=l({});let F=l({});const I=l({});let A=l({});const E=l({page:1,limit:10});t((()=>{J()}));const J=()=>{B.dispatch("point/getPointsList",E._rawValue).then((e=>{U.value=e.data.items,M.value=e.data.total})).catch((e=>{}))},X=()=>{E.page=1,J()},Y=e=>{switch(e.type){case"1":return"码头";case"2":return"渔港";case"3":return"船闸";case"4":return"船舶临时停靠点";case"5":return"其他走私风险点"}},$=e=>{switch(e.status){case"0":return"暂存";case"1":return"待审核";case"2":return"审核通过";case"3":return"审核未通过"}},q=e=>{switch(e.riskLevel){case"1":return"低";case"2":return"中";case"3":return"高"}};return(e,a)=>{const l=r("el-input"),t=r("el-form-item"),B=r("el-option"),D=r("el-select"),G=r("el-button"),H=r("el-form"),O=r("el-table-column"),Q=r("el-button-group"),W=r("el-table"),Z=r("el-pagination"),ee=r("el-card"),ae=o("loading");return s(),n("div",C,[i("div",P,[u(" 查询工具条 "),d(H,{inline:!0,model:E.value,class:"demo-form-inline"},{default:p((()=>[d(t,{label:"点位名称"},{default:p((()=>[d(l,{modelValue:E.value.name,"onUpdate:modelValue":a[0]||(a[0]=e=>E.value.name=e),placeholder:"点位名称",class:"filter-item",onKeyup:c(X,["enter"]),clearable:""},null,8,["modelValue","onKeyup"])])),_:1}),d(t,{label:"点位类型"},{default:p((()=>[d(D,{modelValue:E.value.type,"onUpdate:modelValue":a[1]||(a[1]=e=>E.value.type=e),placeholder:"点位类型",class:"filter-item",onKeyup:c(X,["enter"]),clearable:""},{default:p((()=>[d(B,{label:"码头",value:"1"}),d(B,{label:"渔港",value:"2"}),d(B,{label:"船闸",value:"3"}),d(B,{label:"船舶临时停靠点",value:"4"}),d(B,{label:"其他走私风险点",value:"5"})])),_:1},8,["modelValue","onKeyup"])])),_:1}),d(t,{label:"点位所在地区"},{default:p((()=>[d(D,{modelValue:E.value.area,"onUpdate:modelValue":a[2]||(a[2]=e=>E.value.area=e),placeholder:"请选择地区",class:"filter-item",onKeyup:c(X,["enter"]),clearable:""},{default:p((()=>[d(B,{label:"崇川区",value:"崇川区"}),d(B,{label:"通州区",value:"通州区"}),d(B,{label:"如皋市",value:"如皋市"}),d(B,{label:"海安市",value:"海安市"}),d(B,{label:"海门区",value:"海门区"}),d(B,{label:"如东县",value:"如东县"}),d(B,{label:"启东市",value:"启东市"})])),_:1},8,["modelValue","onKeyup"])])),_:1}),d(t,{label:"点位风险等级"},{default:p((()=>[d(D,{modelValue:E.value.riskLevel,"onUpdate:modelValue":a[3]||(a[3]=e=>E.value.riskLevel=e),placeholder:"点位风险等级",class:"filter-item",onKeyup:c(X,["enter"]),clearable:""},{default:p((()=>[d(B,{label:"低",value:"1"}),d(B,{label:"中",value:"2"}),d(B,{label:"高",value:"3"})])),_:1},8,["modelValue","onKeyup"])])),_:1}),d(t,null,{default:p((()=>[i("span",V,[d(G,{type:"success",icon:e.Search,onClick:X},{default:p((()=>[K])),_:1},8,["icon"])])])),_:1})])),_:1},8,["model"])]),d(ee,{class:"m-1"},{header:p((()=>[N])),default:p((()=>[m((s(),v(W,{data:U.value,stripe:"",style:{width:"100%"}},{default:p((()=>[d(O,{fixed:"",type:"index",label:"序号",align:"center"},{default:p((e=>[i("span",null,g((E.value.page-1)*E.value.limit+e.$index+1),1)])),_:1}),d(O,{prop:"name",label:"点位名称",align:"center"}),d(O,{prop:"type",label:"点位类型",align:"center",formatter:Y}),d(O,{prop:"area",label:"点位所在区域",align:"center"}),d(O,{prop:"riskLevel",label:"点位风险等级",align:"center",formatter:q}),d(O,{prop:"createTime",label:"创建时间",align:"center"},{default:p((e=>[f(g(b(w)(e.row.createTime)),1)])),_:1}),d(O,{prop:"creatorName",label:"创建人",align:"center"}),d(O,{prop:"status",label:"审核状态",align:"center",formatter:$}),d(O,{label:"操作",align:"center","show-overflow-tooltip":"","min-width":"100%"},{default:p((e=>[d(Q,null,{default:p((()=>[d(G,{type:"primary",onClick:a=>{return l=e.row,R.value={show:!0,title:"详细信息"},void(F.value={id:l.id,type:l.type,along:l.along,name:l.name,position:l.position,properties:l.properties,category:l.category,berthNum:l.berthNum,crane:l.crane,maxTon:l.maxTon,dockNum:l.dockNum,whetherSail:l.whetherSail,averageBoats:l.averageBoats,useFor:l.useFor,area:l.area,photoId:l.photoId,riskLevel:l.riskLevel,riskReason:l.riskReason,manageMeasure:l.manageMeasure,memo:l.memo,status:l.status,maintainPersonList:l.maintainPersonList,supervisePersonList:l.supervisePersonList});var l}},{default:p((()=>[T])),_:2},1032,["onClick"]),2==e.row.status?(s(),v(G,{key:0,type:"warning",onClick:a=>{return l=e.row,I.value={show:!0,title:"修改风险等级"},void(A.value={id:l.id,type:l.type,along:l.along,name:l.name,position:l.position,properties:l.properties,category:l.category,berthNum:l.berthNum,crane:l.crane,maxTon:l.maxTon,dockNum:l.dockNum,whetherSail:l.whetherSail,averageBoats:l.averageBoats,useFor:l.useFor,area:l.area,photoId:l.photoId,riskLevel:l.riskLevel,riskReason:l.riskReason,manageMeasure:l.manageMeasure,memo:l.memo,status:l.status,maintainPersonList:l.maintainPersonList,supervisePersonList:l.supervisePersonList});var l}},{default:p((()=>[j])),_:2},1032,["onClick"])):u("v-if",!0),d(G,{type:"danger",onClick:a=>{return l=e.row,void y.confirm("是否确认删除该条数据, 是否继续?",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{x(l.id).then((e=>{k({message:"删除成功！",type:"success"}),J()})).catch((e=>{}))})).catch((()=>{}));var l}},{default:p((()=>[S])),_:2},1032,["onClick"])])),_:2},1024)])),_:1})])),_:1},8,["data"])),[[ae,false]]),i("div",z,[m(d(Z,{currentPage:E.value.page,"onUpdate:currentPage":a[4]||(a[4]=e=>E.value.page=e),"page-size":E.value.limit,"onUpdate:page-size":a[5]||(a[5]=e=>E.value.limit=e),"page-sizes":[10,20,50,100],layout:"total, sizes, prev, pager, next, jumper",total:M.value,onSizeChange:X,onCurrentChange:J},null,8,["currentPage","page-size","total"]),[[h,M.value>0]])])])),_:1}),d(_,{"dialog-point":R.value,"form-data":b(F),onAudit:J},null,8,["dialog-point","form-data"]),d(L,{"dialog-risk":I.value,"form-data":b(A),onAudit:J},null,8,["dialog-risk","form-data"])])}}});export{B as default};
