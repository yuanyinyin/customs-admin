import{c as r}from"./index-30d780dc.js";function t(t){return r({url:"/risk/listPage",method:"get",data:t,bfLoading:!0,isParams:!0,isAlertErrorMsg:!0})}function s(t,s){return r({url:`/risk/update/${t}`,data:s,method:"post",bfLoading:!0,isAlertErrorMsg:!0})}function e(t){return r({url:"/risk/create",data:t,method:"post",bfLoading:!0,isAlertErrorMsg:!0})}function o(t){return r({url:`/risk/delete/${t}`,method:"post",bfLoading:!0,isAlertErrorMsg:!0})}function i(){return r({url:"/risk/statRisks",method:"get",bfLoading:!0,isAlertErrorMsg:!0})}export{e as a,o as d,t as g,i as s,s as u};
