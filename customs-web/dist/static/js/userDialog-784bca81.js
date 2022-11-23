import{d as e,p as l,l as a,r,a as t,g as o,f as d,w as u,j as s,b as i,y as p,X as m,K as n,Y as f}from"./vendor-78a6dd30.js";import{H as D}from"./index-30d780dc.js";import{g as b}from"./dept-5dd4a664.js";const h={class:"dialog-footer"},w=n("取 消"),c=n("确 定"),v=n("确 定"),y=e({props:{userDialog:{require:!0,default:null,type:Object},formData:{require:!0,default:null,type:Object}},emits:["userHandleSubmitAdd","userHandleSubmitEdit"],setup(e,{emit:n}){const y=e,g=l(null),V={label:"deptName",value:"id"},_=l([]);a((()=>{E()}));const E=()=>{b().then((e=>{_.value=e.data})).catch((e=>{}))},j=l({realName:[{required:!0,validator:(e,l,a)=>{if(""===l)a(new Error("请输入姓名"));else{/^[\u4E00-\u9FA5]{2,10}(·[\u4E00-\u9FA5]{2,10}){0,2}$/.test(l)?a():a(new Error("不符合姓名格式"))}},trigger:"blur"}],userName:[{required:!0,validator:(e,l,a)=>{""===l?a(new Error("请输入登录账号")):D(y.formData).then((e=>{"exists"===e.data?a(new Error("该登录用户名已经存在，不能重复")):a()}))},trigger:"blur"}],telephone:[{required:!0,validator:(e,l,a)=>{if(""===l)a(new Error("请输入手机号码"));else{/^(13[0-9]|14[014-9]|15[0-35-9]|16[25-7]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(l)?a():a(new Error("不符合手机号码格式"))}},trigger:"blur"}]}),k=()=>{y.userDialog.show=!1,g.value.resetFields()};return(l,a)=>{const D=r("el-form-item"),b=r("el-input"),E=r("el-tree-select"),N=r("el-form"),U=r("el-button"),q=r("el-dialog");return t(),o("div",null,[d(q,{modelValue:e.userDialog.show,"onUpdate:modelValue":a[8]||(a[8]=l=>e.userDialog.show=l),title:e.userDialog.title,width:"30%",center:""},{footer:u((()=>[s("span",h,[d(U,{onClick:k},{default:u((()=>[w])),_:1}),"add"===e.userDialog.option?(t(),i(U,{key:0,type:"primary",onClick:a[6]||(a[6]=e=>{g.value.validate((e=>{if(!e)return!1;"add"===y.userDialog.option&&n("userHandleSubmitAdd")}))})},{default:u((()=>[c])),_:1})):p("v-if",!0),"edit"===e.userDialog.option?(t(),i(U,{key:1,type:"primary",onClick:a[7]||(a[7]=e=>{g.value.validate((e=>{if(!e)return!1;"edit"===y.userDialog.option&&n("userHandleSubmitEdit")}))})},{default:u((()=>[v])),_:1})):p("v-if",!0)])])),default:u((()=>[d(N,{ref_key:"refUserForm",ref:g,model:e.formData,rules:j.value,"label-width":"80px"},{default:u((()=>["edit"===e.userDialog.option?m((t(),i(D,{key:0,label:"用户id",prop:"id"},null,512)),[[f,!1]]):p("v-if",!0),d(D,{label:"姓名",prop:"realName"},{default:u((()=>[d(b,{modelValue:e.formData.realName,"onUpdate:modelValue":a[0]||(a[0]=l=>e.formData.realName=l),placeholder:"姓名",style:{width:"90%"}},null,8,["modelValue"])])),_:1}),d(D,{label:"登录账号",prop:"userName"},{default:u((()=>[d(b,{modelValue:e.formData.userName,"onUpdate:modelValue":a[1]||(a[1]=l=>e.formData.userName=l),placeholder:"登录账号",style:{width:"90%"}},null,8,["modelValue"])])),_:1}),p("密码编辑框显示条件"),"add"===e.userDialog.option?(t(),i(D,{key:1,label:"登录密码",prop:"userPasswordAdd"},{default:u((()=>[d(b,{modelValue:e.formData.userPassword,"onUpdate:modelValue":a[2]||(a[2]=l=>e.formData.userPassword=l),type:"password",placeholder:"登录密码",style:{width:"90%"}},null,8,["modelValue"])])),_:1})):p("v-if",!0),p(' <el-form-item v-if="userDialog.option === \'edit\'" label="登录密码" prop="userPasswordEdit">\r\n          <el-input v-model="formData.userPassword" type="password" placeholder="********" style="width: 90%" />\r\n        </el-form-item> '),d(D,{label:"手机号码",prop:"telephone"},{default:u((()=>[d(b,{modelValue:e.formData.telephone,"onUpdate:modelValue":a[3]||(a[3]=l=>e.formData.telephone=l),placeholder:"手机号码",type:"phone",style:{width:"90%"}},null,8,["modelValue"])])),_:1}),d(D,{label:"部门",prop:"deptId"},{default:u((()=>[d(E,{style:{width:"90%"},modelValue:e.formData.deptId,"onUpdate:modelValue":a[4]||(a[4]=l=>e.formData.deptId=l),data:_.value,props:V,label:V.label,value:V.value,"check-strictly":"","render-after-expand":!1},null,8,["modelValue","data","label","value"])])),_:1}),d(D,{label:"职务",prop:"job"},{default:u((()=>[d(b,{modelValue:e.formData.job,"onUpdate:modelValue":a[5]||(a[5]=l=>e.formData.job=l),placeholder:"职务",style:{width:"90%"}},null,8,["modelValue"])])),_:1})])),_:1},8,["model","rules"])])),_:1},8,["modelValue","title"])])}}});export{y as default};
