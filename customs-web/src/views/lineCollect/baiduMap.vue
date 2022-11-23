<!-- vue 3 引入百度api -->
<template>
  <div id="allmap"></div>
</template>

<script>
import { defineComponent, onMounted } from "vue";
let list = []
export default defineComponent({
  props: ['msg'],
  created(){
    this.getPosition()
  },
  methods:{
    getPosition(){
      if(this.msg.startPosition && this.msg.endPosition) {
        const startPosition = this.msg.startPosition.split(",")
        const endPosition = this.msg.endPosition.split(",")
        list.push({
          startLng:startPosition[0],
          startLat:startPosition[1],
          endLng:endPosition[0],
          endLat:endPosition[1],
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
      var startLng = list[0].startLng
      var startLat = list[0].startLat
      var endLng = list[0].endLng
      var endLat = list[0].endLat
      map.centerAndZoom(
        new Bmap.Point(startLng,startLat),
        14
      ); // 初始化地图,设置中心点坐标和地图级别
      map.enableScrollWheelZoom(true);
      var startPoint = new BMap.Point(startLng,startLat);
      var startMarker = new BMap.Marker(startPoint);
      map.addOverlay(startMarker);
      var endPoint = new BMap.Point(endLng,endLat);
      var endMarker = new BMap.Marker(endPoint);
      map.addOverlay(endMarker);
      var line = new BMap.Polyline([
        startPoint,endPoint
      ])
      map.addOverlay(line)
      list=[]
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
  }
});
</script>
<style lang="scss" scoped>
#allmap {
  // 注意给dom宽高，不然地图不出来
  width: 100%;
  height: 100%;
}
</style>
