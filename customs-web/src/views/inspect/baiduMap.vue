<!-- vue 3 引入百度api -->
<template>
  <div id="allmap"></div>
</template>

<script>
import { defineComponent, onMounted } from "vue";
import {getDetail} from "@/api/dailyRecord";
var list = []
export default defineComponent({
  props: ['detailId'],
  created(){
    this.getPosition()
  },
  methods:{
    getPosition(){
      if(this.detailId) {
        getDetail(this.detailId).then(res =>{
          for (var i=0;i<res.data.length;i++) {
            let lng = parseFloat(res.data[i].position.split(',')[0])
            let lat = parseFloat(res.data[i].position.split(',')[1])
            list.push({lng:lng,lat:lat})
          }
        })
      }
    },
  },
  setup() {
    onMounted(() => {
      loadMapScript(); // 加载百度地图资源
    });
    // 初始化地图
    const init = () => {
      let Bmap = window.BMap; // 注意要带window，不然会报错（注意官方api,会有改动，之前是Bmap,后面3.0版本改为了BMap,最好查文档或者打印一下window）
      var map = new Bmap.Map("allmap"); // allmap必须和dom上的id一直
      map.centerAndZoom(
        new Bmap.Point(list[0].lng,list[0].lat),
        15
      ); // 初始化地图,设置中心点坐标和地图级别
      map.enableScrollWheelZoom(true);
      var pointList = []
      for (var i = 0; i < list.length; i++) {
        var point = new BMap.Point(list[i].lng,list[i].lat);
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
        pointList.push(point)
      }
      //点位连线
      var polyline =  new BMap.Polyline(pointList, {strokeColor: "blue", strokeWeight: 6, strokeOpacity: 0.5});
      map.addOverlay(polyline);
      list = [];
    };
    const loadMapScript = () => {
      //将百度api设置为在https协议下也能访问
      window.HOST_TYPE='2'
      // 此处在所需页面引入资源就是，不用再public/index.html中引入
      var script = document.createElement("script");
      script.type = "text/javascript";
      script.className = "loadmap"; // 给script一个类名
      script.src =
        "https://api.map.baidu.com/getscript?v=3.0&ak=OaGr78TjxBO0bS6dxptYXom91MGzFQuT&s=1";
        // 此处需要注意：申请ak时，一定要应用类别一定要选浏览器端，不能选服务端，不然地图会报ak无效
      script.onload = () => {
        // 使用script.onload，待资源加载完成，再初始化地图
        setTimeout(init,100)
      };
      let loadmap = document.getElementsByClassName("loadmap");
      if (loadmap) {
        // 每次append script之前判断一下，避免重复添加script资源标签
        for (var i = 0; i < loadmap.length; i++) {
          document.body.removeChild(loadmap[i]);
        }
      }
      document.body.appendChild(script);
    };
  },
});
</script>
<style lang="scss" scoped>
#allmap {
  // 注意给dom宽高，不然地图不出来
  width: 100%;
  height: 100%;
}
</style>
