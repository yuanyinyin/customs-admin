<template>
  <div id="allmap"></div>
</template>

<script>
import { BMPGL } from "@/bmp.ts"
import {getAllPoints} from "../../api/point";

const fileList = ref([])
const baseUrl = ref(import.meta.env.VITE_APP_BASE_URL)
export default {
  data() {
    return {
      ak: "OaGr78TjxBO0bS6dxptYXom91MGzFQuT", // 百度的地图密钥
      myMap: null,
      seaList:"",
      riverList:""
    };
  },
  created(){
    this.getPoints()
  },
  mounted() {
    this.initMap()
  },
  methods: {
    initMap() {
      // 传入密钥获取地图回调。
      BMPGL(this.ak).then((BMapGL) => {
        // 创建地图实例
        let map = new BMapGL.Map("allmap");
        // 创建点坐标 axios => res 获取的初始化定位坐标
        let point = new BMapGL.Point(121.02084, 32.216411)
        // 初始化地图，设置中心点坐标和地图级别
        map.centerAndZoom(point, 11)
        //开启鼠标滚轮缩放
        map.enableScrollWheelZoom(true)
        var bdary = new BMapGL.Boundary();
        bdary.get('南通市', function (rs) {
          // 绘制行政区
          for (var i = 0; i < rs.boundaries.length; i++) {
            var path = [];
            var xyArr = rs.boundaries[i].split(';');
            var ptArr = [];
            for (var j = 0; j < xyArr.length; j++) {
              var tmp = xyArr[j].split(',');
              var pt = new BMapGL.Point(tmp[0], tmp[1]);
              ptArr.push(pt);
            }
            var mapmask = new BMapGL.MapMask(ptArr, {
              isBuildingMask: true,
              isPoiMask: true,
              isMapMask: true,
              showRegion: 'inside',
              topFillColor: '#009dff',
              topFillOpacity: 1,
              sideFillColor: '#009dff',
              sideFillOpacity: 1
            });
            map.addOverlay(mapmask);
            var border = new BMapGL.Polyline(ptArr, {
              strokeColor: '#39ffff',
              strokeWeight: 3,
              strokeOpacity: 1
            });
            map.addOverlay(border);
            // --- 添加行政区划 ---
            var dist = new BMapGL.DistrictLayer({
              name: '(南通市)',
              kind: 1,
              fillColor: '#009dff',
              fillOpacity: 0.2,
              strokeColor: '#39ffff',
              viewport: true
            });
            map.addDistrictLayer(dist);

            // --- 行政区划添加鼠标事件 ---
            dist.addEventListener('mouseover', function (e) {
              e.currentTarget.setFillColor('#0059ff');
              e.currentTarget.setFillOpacity(0);
            });
            dist.addEventListener('mouseout', function (e) {
              e.currentTarget.setFillColor(e.currentTarget.style.fillColor);
              e.currentTarget.setFillOpacity(e.currentTarget.style.fillOpacity);
            });
          }
        });
        setTimeout(()=>{
          this.seaList.forEach(val=>{
            var lng = val.position.split(",")[0]
            var lat = val.position.split(",")[1]
            var riskLevel;
            var type;
            var properties;
            var category;
            var crane;
            switch (val.riskLevel) {
              case "1":riskLevel = "低";break;
              case "2":riskLevel = "中";break;
              case "3":riskLevel = "高";break;
            }
            switch (val.type){
              case "1":type = "码头";break;
              case "2":type = "渔港";break;
              case "3":type = "船闸";break;
              case "4":type = "船舶临时停靠点";break;
              case "5":type = "其他走私风险点";break;
            }
            var content = '<div>' +
              '<span>点位类型：'+type+'</span><br/>' +
              '<span>风险等级：'+riskLevel+'</span><br/>' +
              '<span>所在地：'+val.area+'</span><br/>' +
              '<span>地址：'+val.address+'</span><br/>' +
              '<span>坐标：'+val.position+'</span><br/>'
            if(val.type == "1"){
              switch(val.properties){
                case "1":properties = "民企";break;
                case "2":properties = "国有";break;
                case "3":properties = "合资";break;
                case "4":properties = "个体";break;
              }
              switch(val.category){
                case "1":category = "危化品";break;
                case "2":category = "通用散杂货";break;
              }
              switch (val.crane) {
                case "1":crane = "有";break;
                case "2":crane = "无";
              }
              content += '<span>性质：'+properties+'</span><br/>' +
                '<span>类别：'+category+'</span><br/>' +
                '<span>泊位数：'+val.berthNum+'</span><br/>' +
                '<span>有无吊机：'+crane+'</span><br/>' +
                '<span>最大吨级：'+val.maxTon+'</span>'
            }else if(val.type == "2"){
              switch (val.category) {
                case "1":category = "开放式";break;
                case "2":category = "封闭式";break;
              }
              switch (val.crane) {
                case "1":crane = "有";break;
                case "2":crane = "无";
              }
              content += '<span>类别：'+category+'</span><br/>' +
                '<span>码头数：'+val.dockNum+'</span>' +
                '<span>可停靠船舶数：'+val.berthNum+'</span>' +
                '<span>有无吊机：'+crane+'</span><br/>' +
                '<span>最大吨级：'+val.maxTon+'</span>'
            }else if(val.type == "3"){
              switch (val.category) {
                case "1":category = "水闸";break;
                case "2":category = "船闸";break;
              }
              content += '<span>类别：'+category+'</span><br/>' +
                '<span>是否通航：'+(val.weatherSail == "1" ? "是" : "否")+'</span><br/>' +
                '<span>最大吨级：'+val.maxTon+'</span><br/>' +
                '<span>日均进闸船舶数量：'+val.averageBoats+'</span>'
            }else if(val.type == "4"){
              switch (val.crane) {
                case "1":crane = "有";break;
                case "2":crane = "无";
              }
              content += '<span>用途：'+val.useFor+'</span><br/>'+
                '<span>最大吨级：'+val.maxTon+'</span><br/>' +
                '<span>有无吊机：'+crane+'</span><br/>'
            }
            if(val.photoId){
              content += '<div style="overflow-x: auto;overflow-y: hidden;white-space: nowrap;" >'
              let photoids = val.photoId.split(',');
              for(var i = 0;i<photoids.length;i++){
                if(""!=photoids[i]){
                  content += '<img style="height: 150px" src="'+baseUrl.value + '/file/downLoad/' + photoids[i] + '"/>'
                }
              }
            }
            content += '</div></div>'
            // 将标注添加到地图
            var pt = new BMapGL.Point(lng,lat);
            if(val.riskLevel == "1"){
              var marker = new BMapGL.Circle(pt, 50, {
                strokeColor:"#00ff2c",
                strokeWeight:2,
                size: 50,
                fillColor: '#00ff2c',
                fillOpacity: 0.6
              });
              map.addOverlay(marker);
              // 创建小车图标
              var myIcon = new BMapGL.Icon("/src/views/screen/icons/low.png", new BMapGL.Size(20, 20));
              // 创建Marker标注，使用小车图标
              var marker = new BMapGL.Marker(pt, {
                icon: myIcon
              });
              // 将标注添加到地图
              map.addOverlay(marker);
            }else if(val.riskLevel == "2"){
              var marker = new BMapGL.Circle(pt, 50, {
                strokeColor:"#ffb700",
                strokeWeight:2,
                size: 50,
                fillColor: '#ffb700',
                fillOpacity: 0.6
              });
              map.addOverlay(marker);
              // 创建小车图标
              var myIcon = new BMapGL.Icon("/src/views/screen/icons/middle.png", new BMapGL.Size(20, 20));
              // 创建Marker标注，使用小车图标
              var marker = new BMapGL.Marker(pt, {
                icon: myIcon
              });
              // 将标注添加到地图
              map.addOverlay(marker);
            }else{
              var marker = new BMapGL.Circle(pt, 50, {
                strokeColor:"#ff0000",
                strokeWeight:2,
                size: 50,
                fillColor: '#ff0000',
                fillOpacity: 0.6
              });
              map.addOverlay(marker);
              // 创建小车图标
              var myIcon = new BMapGL.Icon("/src/views/screen/icons/high.png", new BMapGL.Size(20, 20));
              // 创建Marker标注，使用小车图标
              var marker = new BMapGL.Marker(pt, {
                icon: myIcon
              });
              // 将标注添加到地图
              map.addOverlay(marker);
            }
            // 创建信息窗口
            var opts = {
              width: 500,
              height:500,
              title: val.name
            };
            var infoWindow = new BMapGL.InfoWindow(content,opts);
            // 点标记添加点击事件
            marker.addEventListener('click', function () {
              map.openInfoWindow(infoWindow,pt); // 开启信息窗口
            });
          })
          this.riverList.forEach(val=>{
            var lng = val.position.split(",")[0]
            var lat = val.position.split(",")[1]
            var riskLevel;
            var type;
            var properties;
            var category;
            var crane;
            switch (val.riskLevel) {
              case "1":riskLevel = "低";break;
              case "2":riskLevel = "中";break;
              case "3":riskLevel = "高";break;
            }
            switch (val.type){
              case "1":type = "码头";break;
              case "2":type = "渔港";break;
              case "3":type = "船闸";break;
              case "4":type = "船舶临时停靠点";break;
              case "5":type = "其他走私风险点";break;
            }
            var content = '<div>' +
              '<span>点位类型：'+type+'</span><br/>' +
              '<span>风险等级：'+riskLevel+'</span><br/>' +
              '<span>所在地：'+val.area+'</span><br/>' +
              '<span>地址：'+val.address+'</span><br/>' +
              '<span>坐标：'+val.position+'</span><br/>'
            if(val.type == "1"){
              switch(val.properties){
                case "1":properties = "民企";break;
                case "2":properties = "国有";break;
                case "3":properties = "合资";break;
                case "4":properties = "个体";break;
              }
              switch(val.category){
                case "1":category = "危化品";break;
                case "2":category = "通用散杂货";break;
              }
              switch (val.crane) {
                case "1":crane = "有";break;
                case "2":crane = "无";
              }
              content += '<span>性质：'+properties+'</span><br/>' +
                '<span>类别：'+category+'</span><br/>' +
                '<span>泊位数：'+val.berthNum+'</span><br/>' +
                '<span>有无吊机：'+crane+'</span><br/>' +
                '<span>最大吨级：'+val.maxTon+'</span>'
            }else if(val.type == "2"){
              switch (val.category) {
                case "1":category = "开放式";break;
                case "2":category = "封闭式";break;
              }
              switch (val.crane) {
                case "1":crane = "有";break;
                case "2":crane = "无";
              }
              content += '<span>类别：'+category+'</span><br/>' +
                '<span>码头数：'+val.dockNum+'</span><br/>' +
                '<span>可停靠船舶数：'+val.berthNum+'</span><br/>' +
                '<span>有无吊机：'+crane+'</span><br/>' +
                '<span>最大吨级：'+val.maxTon+'</span>'
            }else if(val.type == "3"){
              switch (val.category) {
                case "1":category = "水闸";break;
                case "2":category = "船闸";break;
              }
              content += '<span>类别：'+category+'</span><br/>' +
                '<span>是否通航：'+(val.weatherSail == "1" ? "是" : "否")+'</span><br/>' +
                '<span>最大吨级：'+val.maxTon+'</span><br/>' +
                '<span>日均进闸船舶数量：'+val.averageBoats+'</span>'
            }else if(val.type == "4"){
              switch (val.crane) {
                case "1":crane = "有";break;
                case "2":crane = "无";
              }
              content += '<span>用途：'+val.useFor+'</span><br/>'+
                '<span>最大吨级：'+val.maxTon+'</span><br/>' +
                '<span>有无吊机：'+crane+'</span><br/>'
            }
            if(val.photoId){
              content += '<div style="overflow-x: auto;overflow-y: hidden;white-space: nowrap;" >'
              let photoids = val.photoId.split(',');
              for(var i = 0;i<photoids.length;i++){
                if(""!=photoids[i]){
                  content += '<img style="height: 150px" src="'+baseUrl.value + '/file/downLoad/' + photoids[i] + '"/>'
                }
              }
            }
            content += '</div></div>'
            // 将标注添加到地图
            var pt = new BMapGL.Point(lng,lat)
            var marker = new BMapGL.Marker(pt)
            map.addOverlay(marker);
            // 创建信息窗口
            var opts = {
              width: 500,
              height:500,
              title: val.name
            };
            var infoWindow = new BMapGL.InfoWindow(content,opts);
            // 点标记添加点击事件
            marker.addEventListener('click', function () {
              map.openInfoWindow(infoWindow,pt); // 开启信息窗口
            });
          })
        },500)
      })
      .catch((err)=>{
        console.log(err)
      })
    },
    getPoints(){
      getAllPoints().then(res=>{
        this.seaList = res.data.sea
        this.riverList = res.data.river
      })
    }
  }
};
</script>
<style lang="scss" scoped>
#allmap {
  // 注意给dom宽高，不然地图不出来
  width: 100%;
  height: 100%;
}
</style>
