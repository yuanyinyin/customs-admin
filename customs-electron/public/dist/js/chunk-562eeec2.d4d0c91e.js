(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-562eeec2"],{"107c":function(e,t,n){var r=n("d039"),o=n("da84"),a=o.RegExp;e.exports=r((function(){var e=a("(?<a>b)","g");return"b"!==e.exec("b").groups.a||"bc"!=="b".replace(e,"$<a>c")}))},1276:function(e,t,n){"use strict";var r=n("2ba4"),o=n("c65b"),a=n("e330"),i=n("d784"),c=n("44e7"),l=n("825a"),u=n("1d80"),s=n("4840"),p=n("8aa5"),d=n("50c4"),f=n("577e"),v=n("dc4a"),x=n("4dae"),g=n("14c3"),h=n("9263"),m=n("9f7f"),b=n("d039"),w=m.UNSUPPORTED_Y,I=4294967295,k=Math.min,S=[].push,y=a(/./.exec),C=a(S),E=a("".slice),R=!b((function(){var e=/(?:)/,t=e.exec;e.exec=function(){return t.apply(this,arguments)};var n="ab".split(e);return 2!==n.length||"a"!==n[0]||"b"!==n[1]}));i("split",(function(e,t,n){var a;return a="c"=="abbc".split(/(b)*/)[1]||4!="test".split(/(?:)/,-1).length||2!="ab".split(/(?:ab)*/).length||4!=".".split(/(.?)(.?)/).length||".".split(/()()/).length>1||"".split(/.?/).length?function(e,n){var a=f(u(this)),i=void 0===n?I:n>>>0;if(0===i)return[];if(void 0===e)return[a];if(!c(e))return o(t,a,e,i);var l,s,p,d=[],v=(e.ignoreCase?"i":"")+(e.multiline?"m":"")+(e.unicode?"u":"")+(e.sticky?"y":""),g=0,m=new RegExp(e.source,v+"g");while(l=o(h,m,a)){if(s=m.lastIndex,s>g&&(C(d,E(a,g,l.index)),l.length>1&&l.index<a.length&&r(S,d,x(l,1)),p=l[0].length,g=s,d.length>=i))break;m.lastIndex===l.index&&m.lastIndex++}return g===a.length?!p&&y(m,"")||C(d,""):C(d,E(a,g)),d.length>i?x(d,0,i):d}:"0".split(void 0,0).length?function(e,n){return void 0===e&&0===n?[]:o(t,this,e,n)}:t,[function(t,n){var r=u(this),i=void 0==t?void 0:v(t,e);return i?o(i,t,r,n):o(a,f(r),t,n)},function(e,r){var o=l(this),i=f(e),c=n(a,o,i,r,a!==t);if(c.done)return c.value;var u=s(o,RegExp),v=o.unicode,x=(o.ignoreCase?"i":"")+(o.multiline?"m":"")+(o.unicode?"u":"")+(w?"g":"y"),h=new u(w?"^(?:"+o.source+")":o,x),m=void 0===r?I:r>>>0;if(0===m)return[];if(0===i.length)return null===g(h,i)?[i]:[];var b=0,S=0,y=[];while(S<i.length){h.lastIndex=w?0:S;var R,_=g(h,w?E(i,S):i);if(null===_||(R=k(d(h.lastIndex+(w?S:0)),i.length))===b)S=p(i,S,v);else{if(C(y,E(i,b,S)),y.length===m)return y;for(var O=1;O<=_.length-1;O++)if(C(y,_[O]),y.length===m)return y;S=b=R}}return C(y,E(i,b)),y}]}),!R,w)},"14c3":function(e,t,n){var r=n("da84"),o=n("c65b"),a=n("825a"),i=n("1626"),c=n("c6b6"),l=n("9263"),u=r.TypeError;e.exports=function(e,t){var n=e.exec;if(i(n)){var r=o(n,e,t);return null!==r&&a(r),r}if("RegExp"===c(e))return o(l,e,t);throw u("RegExp#exec called on incompatible receiver")}},"44e7":function(e,t,n){var r=n("861d"),o=n("c6b6"),a=n("b622"),i=a("match");e.exports=function(e){var t;return r(e)&&(void 0!==(t=e[i])?!!t:"RegExp"==o(e))}},"4dae":function(e,t,n){var r=n("da84"),o=n("23cb"),a=n("07fa"),i=n("8418"),c=r.Array,l=Math.max;e.exports=function(e,t,n){for(var r=a(e),u=o(t,r),s=o(void 0===n?r:n,r),p=c(l(s-u,0)),d=0;u<s;u++,d++)i(p,d,e[u]);return p.length=d,p}},"4ec2":function(e,t,n){"use strict";n("a414")},8418:function(e,t,n){"use strict";var r=n("a04b"),o=n("9bf2"),a=n("5c6c");e.exports=function(e,t,n){var i=r(t);i in e?o.f(e,i,a(0,n)):e[i]=n}},"8aa5":function(e,t,n){"use strict";var r=n("6547").charAt;e.exports=function(e,t,n){return t+(n?r(e,t).length:1)}},9263:function(e,t,n){"use strict";var r=n("c65b"),o=n("e330"),a=n("577e"),i=n("ad6d"),c=n("9f7f"),l=n("5692"),u=n("7c73"),s=n("69f3").get,p=n("fce3"),d=n("107c"),f=l("native-string-replace",String.prototype.replace),v=RegExp.prototype.exec,x=v,g=o("".charAt),h=o("".indexOf),m=o("".replace),b=o("".slice),w=function(){var e=/a/,t=/b*/g;return r(v,e,"a"),r(v,t,"a"),0!==e.lastIndex||0!==t.lastIndex}(),I=c.BROKEN_CARET,k=void 0!==/()??/.exec("")[1],S=w||k||I||p||d;S&&(x=function(e){var t,n,o,c,l,p,d,S=this,y=s(S),C=a(e),E=y.raw;if(E)return E.lastIndex=S.lastIndex,t=r(x,E,C),S.lastIndex=E.lastIndex,t;var R=y.groups,_=I&&S.sticky,O=r(i,S),V=S.source,M=0,A=C;if(_&&(O=m(O,"y",""),-1===h(O,"g")&&(O+="g"),A=b(C,S.lastIndex),S.lastIndex>0&&(!S.multiline||S.multiline&&"\n"!==g(C,S.lastIndex-1))&&(V="(?: "+V+")",A=" "+A,M++),n=new RegExp("^(?:"+V+")",O)),k&&(n=new RegExp("^"+V+"$(?!\\s)",O)),w&&(o=S.lastIndex),c=r(v,_?n:S,A),_?c?(c.input=b(c.input,M),c[0]=b(c[0],M),c.index=S.lastIndex,S.lastIndex+=c[0].length):S.lastIndex=0:w&&c&&(S.lastIndex=S.global?c.index+c[0].length:o),k&&c&&c.length>1&&r(f,c[0],n,(function(){for(l=1;l<arguments.length-2;l++)void 0===arguments[l]&&(c[l]=void 0)})),c&&R)for(c.groups=p=u(null),l=0;l<R.length;l++)d=R[l],p[d[0]]=c[d[1]];return c}),e.exports=x},"9f7f":function(e,t,n){var r=n("d039"),o=n("da84"),a=o.RegExp,i=r((function(){var e=a("a","y");return e.lastIndex=2,null!=e.exec("abcd")})),c=i||r((function(){return!a("a","y").sticky})),l=i||r((function(){var e=a("^r","gy");return e.lastIndex=2,null!=e.exec("str")}));e.exports={BROKEN_CARET:l,MISSED_STICKY:c,UNSUPPORTED_Y:i}},a15b:function(e,t,n){"use strict";var r=n("23e7"),o=n("e330"),a=n("44ad"),i=n("fc6a"),c=n("a640"),l=o([].join),u=a!=Object,s=c("join",",");r({target:"Array",proto:!0,forced:u||!s},{join:function(e){return l(i(this),void 0===e?",":e)}})},a358:function(e,t,n){"use strict";n.d(t,"a",(function(){return i})),n.d(t,"c",(function(){return c})),n.d(t,"b",(function(){return l}));n("a15b"),n("ac1f"),n("1276");var r=n("cff8"),o=n.n(r),a=n("b775"),i={test:"controller.example.test",messageShow:"controller.example.messageShow",messageShowConfirm:"controller.example.messageShowConfirm",selectFolder:"controller.example.selectFolder",openDirectory:"controller.example.openDirectory",loadViewContent:"controller.example.loadViewContent",removeViewContent:"controller.example.removeViewContent",createWindow:"controller.example.createWindow",sendNotification:"controller.example.sendNotification",initPowerMonitor:"controller.example.initPowerMonitor",getScreen:"controller.example.getScreen",openSoftware:"controller.example.openSoftware",autoLaunch:"controller.example.autoLaunch",setTheme:"controller.example.setTheme",getTheme:"controller.example.getTheme",checkForUpdater:"controller.example.checkForUpdater",downloadApp:"controller.example.downloadApp",dbOperation:"controller.example.dbOperation",sqlitedbOperation:"controller.example.sqlitedbOperation",uploadFile:"controller.example.uploadFile",checkHttpServer:"controller.example.checkHttpServer",doHttpRequest:"controller.example.doHttpRequest",doSocketRequest:"controller.example.doSocketRequest",ipcInvokeMsg:"controller.example.ipcInvokeMsg",ipcSendSyncMsg:"controller.example.ipcSendSyncMsg",ipcSendMsg:"controller.example.ipcSendMsg",hello:"controller.example.hello"},c={appUpdater:"app.updater"},l=function(e,t){var n=o.a.get("httpServiceConfig"),r=n.server||"http://127.0.0.1:7071",i=e.split(".").join("/");return i=r+"/"+i,console.log("url:",i),Object(a["b"])({url:i,method:"post",data:t,params:{},timeout:6e4})}},a414:function(e,t,n){},a640:function(e,t,n){"use strict";var r=n("d039");e.exports=function(e,t){var n=[][e];return!!n&&r((function(){n.call(null,t||function(){return 1},1)}))}},ac1f:function(e,t,n){"use strict";var r=n("23e7"),o=n("9263");r({target:"RegExp",proto:!0,forced:/./.exec!==o},{exec:o})},ad6d:function(e,t,n){"use strict";var r=n("825a");e.exports=function(){var e=r(this),t="";return e.global&&(t+="g"),e.ignoreCase&&(t+="i"),e.multiline&&(t+="m"),e.dotAll&&(t+="s"),e.unicode&&(t+="u"),e.sticky&&(t+="y"),t}},ae87:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app-base-window-view"}},[e._m(0),n("div",{staticClass:"one-block-2"},[n("a-space",[n("a-button",{on:{click:function(t){return e.loadViewContent(0)}}},[e._v("加载百度页面")]),n("a-button",{on:{click:function(t){return e.removeViewContent(0)}}},[e._v("移除百度页面")])],1)],1),e._m(1),n("div",{staticClass:"one-block-2"},[n("a-space",[n("a-button",{on:{click:function(t){return e.loadViewContent(1)}}},[e._v("加载html页面")]),n("a-button",{on:{click:function(t){return e.removeViewContent(1)}}},[e._v("移除html页面")])],1)],1)])},o=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"one-block-1"},[n("span",[e._v(" 1. 嵌入web内容 ")])])},function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"one-block-1"},[n("span",[e._v(" 2. 嵌入html内容 ")])])}],a=n("a358"),i={data:function(){return{views:[{type:"web",content:"https://www.baidu.com/"},{type:"html",content:"/public/html/view_example.html"}]}},methods:{loadViewContent:function(e){var t=this;t.$ipcInvoke(a["a"].loadViewContent,this.views[e]).then((function(e){console.log(e)}))},removeViewContent:function(e){var t=this;t.$ipcInvoke(a["a"].removeViewContent,t.views[e]).then((function(e){console.log(e)}))}}},c=i,l=(n("4ec2"),n("2877")),u=Object(l["a"])(c,r,o,!1,null,"113436ad",null);t["default"]=u.exports},d784:function(e,t,n){"use strict";n("ac1f");var r=n("e330"),o=n("6eeb"),a=n("9263"),i=n("d039"),c=n("b622"),l=n("9112"),u=c("species"),s=RegExp.prototype;e.exports=function(e,t,n,p){var d=c(e),f=!i((function(){var t={};return t[d]=function(){return 7},7!=""[e](t)})),v=f&&!i((function(){var t=!1,n=/a/;return"split"===e&&(n={},n.constructor={},n.constructor[u]=function(){return n},n.flags="",n[d]=/./[d]),n.exec=function(){return t=!0,null},n[d](""),!t}));if(!f||!v||n){var x=r(/./[d]),g=t(d,""[e],(function(e,t,n,o,i){var c=r(e),l=t.exec;return l===a||l===s.exec?f&&!i?{done:!0,value:x(t,n,o)}:{done:!0,value:c(n,t,o)}:{done:!1}}));o(String.prototype,e,g[0]),o(s,d,g[1])}p&&l(s[d],"sham",!0)}},fce3:function(e,t,n){var r=n("d039"),o=n("da84"),a=o.RegExp;e.exports=r((function(){var e=a(".","s");return!(e.dotAll&&e.exec("\n")&&"s"===e.flags)}))}}]);
//# sourceMappingURL=chunk-562eeec2.d4d0c91e.js.map