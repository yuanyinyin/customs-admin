var myChart = echarts.init(document.getElementById('map')); 
var geoCoordMap = {
    淮安: [119.119766, 33.556668], 
    美国: [-118.24311, 34.052713], 
    巴西: [-48.678945, -10.493623],
    澳大利亚: [150.993137, -33.675509],  
    加拿大: [-123.023921, 49.311753],
    英国: [-1.657222, 51.886863],
    德国: [10.01959, 54.38474],
    俄罗斯: [89.116876, 67.757906],      
    墨西哥: [-99.094092, 19.365711] 
};
var ExData = [
    [{
        name: "美国",
        value: '食品、电子产品'
    }, {
        name: "淮安"
    }],

    [{
        name: "巴西",
        value: '粮食、哈根纽崔莱'
    }, {
        name: "淮安"
    }],
    
    [{
        name: "澳大利亚",
        value: '奶制品、数码产品'
    }, {
        name: "淮安"
    }],
    
    [{
        name: "加拿大",
        value: '奶制品、水果'
    }, {
        name: "淮安"
    }]
];

var InData = [
    [{
        name: "英国",
        value: '船舶、钢材'
    }, {
        name: "淮安"
    }],

    [{
        name: "德国",
        value: '服装、加工产品'
    }, {
        name: "淮安"
    }],
    
    [{
        name: "俄罗斯",
        value: '钢材、生活用品'
    }, {
        name: "淮安"
    }],
    
    [{
        name: "墨西哥",
        value: '大豆、大米'
    }, {
        name: "淮安"
    }]
];


var convertData = function(data,flag) {
    var res = []; 
    for (var i = 0; i < data.length; i++) {
        var dataItem = data[i];
        if(flag){
            var fromCoord = geoCoordMap[dataItem[0].name];
            var toCoord = geoCoordMap[dataItem[1].name];
        }else{
            var fromCoord = geoCoordMap[dataItem[1].name];
            var toCoord = geoCoordMap[dataItem[0].name];
        }
        if (fromCoord && toCoord) {
            res.push([{
                    coord: toCoord,
                    value: dataItem[0].value
                },
                {
                    coord: fromCoord
                }
            ]);
        }
    }
    return res;
};

var series1 = [];
var series2 = [];

[
    ["淮安", ExData]
].forEach(function(item, i) {
    series1.push({
            type: "lines",
            zlevel: 2,
            effect: {
                show: true,
                period: 6, //箭头指向速度，值越小速度越快
                trailLength: 0.1, //特效尾迹长度[0,1]值越大，尾迹越长重
                symbol: "arrow", //箭头图标
                symbolSize: 16 //图标大小
            },
            lineStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: '#FFD64D'
                    }, {
                        offset: 1,
                        color: '#FFD64D'
                    }], false),
                    width: 5,
                    opacity: 0.2,
                    curveness: 0.2
                }
            },

            data: convertData(item[1],true)
        }, {
            type: "effectScatter",
            coordinateSystem: "geo",
            zlevel: 1,
            label: {
                normal: {
                    show: true,
                    formatter: function(params) { 
                        return '{fline|'+ params.data.name +'：'+'主要出口国'+'}\n{tline|商品：'+params.data.value[2]+'}';
                    },
                    position: 'top',
                    backgroundColor: 'rgba(254,174,33,.8)',
                    padding: [20, 20],
                    borderRadius: 6,
                    lineHeight: 40,
                    color: '#f7fafb',
                    rich:{
                        fline:{
                            padding: [10, 0, 10, 10],
                            color:'#ffffff',
                            fontSize:30,
                            fontWeight:'bold'
                        },
                        tline:{
                            padding: [10, 10, 0, 10],
                            color:'#ffffff',
                            fontSize:30,
                            fontWeight:'bold'
                        }
                    }
                },
                emphasis: {
                    show: true
                }
            },
            data: item[1].map(function(dataItem) {
                return {
                    name: dataItem[0].name,
                    value: geoCoordMap[dataItem[0].name].concat([dataItem[0].value]),
                    
                };
            })
        },
        //方向
        {
            type: "scatter",
            coordinateSystem: "geo",
            zlevel: 2,
            rippleEffect: {
                period: 4,
                brushType: "stroke",
                scale: 4
            },
            label: {//字体
                normal: {
                    show: true,
                    position: "right",
                    formatter: "{b}",
                    textStyle: {
                        color: "#fff",
                        fontSize:40,
                        fontWeight:'bold'
                    }
                },
                emphasis: {
                    show: true
                }
            },
            symbol: "pin",//图标
            symbolSize: 60,
            itemStyle: {
                normal: {
                    show: true,
                    color: "#FFD64D"
                }
            },
            data: [{
                name: item[0],
                value: geoCoordMap[item[0]].concat([100])
            }]
        }
    );
});



[
    ["淮安", InData]
].forEach(function(item, i) {
    series2.push({
            type: "lines",
            zlevel: 2,
            effect: {
                show: true,
                period: 6, //箭头指向速度，值越小速度越快
                trailLength: 0.1, //特效尾迹长度[0,1]值越大，尾迹越长重
                symbol: "arrow", //箭头图标
                symbolSize: 16 //图标大小
            },
            lineStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: '#FFD64D'
                    }, {
                        offset: 1,
                        color: '#FFD64D'
                    }], false),
                    width: 5,
                    opacity: 0.2,
                    curveness: 0.2
                }
            },

            data: convertData(item[1],true)
        }, {
            type: "effectScatter",
            coordinateSystem: "geo",
            zlevel: 1,
            label: {
                normal: {
                    show: true,
                    formatter: function(params) {
                        return '{fline|'+ params.data.name +'：'+'主要进口国'+'}\n{tline|商品：'+params.data.value[2]+'}';
                    },
                    position: 'top',
                    backgroundColor: 'rgba(254,174,33,.8)',
                    padding: [20, 20],
                    borderRadius: 6,
                    lineHeight: 40,
                    color: '#f7fafb',
                    rich:{
                        fline:{
                            padding: [10, 0, 10, 10],
                            color:'#ffffff'
                        },
                        tline:{
                            padding: [10, 10, 0, 10],
                            color:'#ffffff'
                        }
                    }
                },
                emphasis: {
                    show: true
                }
            },
            data: item[1].map(function(dataItem) {
                return {
                    name: dataItem[0].name,
                    value: geoCoordMap[dataItem[0].name].concat([dataItem[0].value]),
                    
                };
            })
        },
        //方向
        {
            type: "scatter",
            coordinateSystem: "geo",
            zlevel: 2,
            rippleEffect: {
                period: 4,
                brushType: "stroke",
                scale: 4
            },
            label: {
                normal: {
                    show: true,
                    position: "right",
                    formatter: "{b}",
                    textStyle: {
                        color: "#fff",
                        fontSize: 40,
                        fontWeight:'bold'
                    }
                },
                emphasis: {
                    show: true
                }
            },
            symbol: "pin",
            symbolSize: 60,
            itemStyle: {
                normal: {
                    show: true,
                    color: "#FFD64D"
                }
            },
            data: [{
                name: item[0],
                value: geoCoordMap[item[0]].concat([100])
            }]
        }
    );
});

var option = {
    title: {
        text: '',
        left: 'center',
        textStyle: {
            color: '#fff'
        }
    },
    color:'rgba(233,63,66,.9)',
    geo: {
        map: "world", 
        label: {
            emphasis: {
                show: false
            } 
        },
        roam: true, //是否允许缩放
        layoutCenter: ["50%", "50%"], //地图位置
        layoutSize: "180%",
        itemStyle: {
            normal: {
                areaColor: '#00caff',
                borderColor: '#39ffff',
                borderWidth:1,
            },
            emphasis: {
                areaColor: '#00caff'
            },
        },
    },

    series: series1
};
myChart.setOption(option);

$(function(){
  //切换
  $(".mapLinks a").click(function(){
      $(this).siblings().removeClass("current");
      $(this).addClass("current");
      console.log(option);
      if($(this).index() == 1){
          series2[0]['data'] = convertData(InData,false);
          option.series = series2;
      }else{
          series1[0]['data'] = convertData(ExData,true);
          option.series = series1;
      }
      myChart.setOption(option);
      myChart.resize(); 
      return false;
  });

    window.addEventListener("resize",function(){
        myChart.resize();
    });
});