function loadChartOne(param){
    var names = [];
    var nums = [];
    param.forEach(element=>{
        names.push(element.name)
        nums.push(element.num)
    });
    // 基于准备好的dom，初始化echarts实例
var chartOne = echarts.init(document.getElementById('chartOne'));
var readeOption  = {
      grid: { 
        top: '16%',
        left: '8%',
        right: '0%',
        bottom: '20%', 
      },
      tooltip: {
        trigger: 'axis',
        textStyle: {
            fontSize: 20,
        },
      },
      xAxis : [
      {
          type : 'category',
          data :  names,
          axisLine:{
              lineStyle: {
                  color:'#1FFCDA',
                  fontSize: 18
              }
          },
          axisLabel: {
              show: true,
              textStyle: {
                  color: 'rgba(255,255,255,0.6)',
                  fontSize: 18,
                  lineHeight: 26
              },
              formatter: function (params) {
                var newParamsName = "";
                var paramsNameNumber = params.length;
                var provideNumber = 4;  //一行显示几个字
                var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                if (paramsNameNumber > provideNumber) {
                    for (var p = 0; p < rowNumber; p++) {
                        var tempStr = "";
                        var start = p * provideNumber;
                        var end = start + provideNumber;
                        if (p == rowNumber - 1) {
                            tempStr = params.substring(start, paramsNameNumber);
                        } else {
                            tempStr = params.substring(start, end) + "\n";
                        }
                        newParamsName += tempStr;
                    }

                } else {
                    newParamsName = params;
                }
                return newParamsName
            }
          }
      }
      ],
      yAxis : [
      {
          name: '个',
          nameTextStyle: {
              color: "rgba(48,194,255,1)",
              fontSize:18,
              align:'left'
          }, 
          type : 'value',
          scale:true,
          min:0,
          splitLine: {
            show: false,
            lineStyle: {
                color: 'rgba(44,166,255,.5)',
                type: 'dashed',
            }
          },
          axisLabel: {
              margin: 15,
              color: 'rgba(255,255,255,0.6)',
              fontSize: 18
          },
          axisLine:{
              show:true,
              lineStyle: {
                  color:'#1FFCDA'
              }
          },
          
      }
      ],
      series : [
      {
          name:'点位数',
          type:'bar',
          barWidth: '20%',
          barGap:0.2,
          itemStyle: {
            emphasis: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#fcf65a'
              }, {
                  offset: 1,
                  color: 'rgba(255,175,46,0.01)'
              }]),
            },
            normal: {
                barBorderRadius: 0,
                label: {
                    show: false, 
                    position: 'top', 
                    textStyle: { 
                        color: '#fff',
                        fontSize: 20
                    }
                },
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#4DB2FF'
                }, {
                    offset: 1,
                    color: '#3357CE'
                }]),
                
            }
          },
          data:nums
        //   data:[280,{
        //     value: 196,
        //     itemStyle: {
        //       normal: {
        //         barBorderRadius: 0,
        //         label: {
        //             show: false, 
        //             position: 'top', 
        //             textStyle: { 
        //                 color: '#fff',
        //                 fontSize: 20
        //             }
        //         },
        //         color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
        //             offset: 0,
        //             color: '#66FBF9'
        //         }, {
        //             offset: 1,
        //             color: '#0FC2A7'
        //         }])
        //       }
        //     }
        //   },{
        //     value: 160,
        //     itemStyle: {
        //       normal: {
        //         barBorderRadius: 0,
        //         label: {
        //             show: false, 
        //             position: 'top', 
        //             textStyle: { 
        //                 color: '#fff',
        //                 fontSize: 20
        //             }
        //         },
        //         color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
        //             offset: 0,
        //             color: '#66FBF9'
        //         }, {
        //             offset: 1,
        //             color: '#0FC2A7'
        //         }])
        //       }
        //     }
        //   },360,{
        //     value: 220,
        //     itemStyle: {
        //       normal: {
        //         barBorderRadius: 0,
        //         label: {
        //             show: false, 
        //             position: 'top', 
        //             textStyle: { 
        //                 color: '#fff',
        //                 fontSize: 20
        //             }
        //         },
        //         color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
        //             offset: 0,
        //             color: '#66FBF9'
        //         }, {
        //             offset: 1,
        //             color: '#0FC2A7'
        //         }])
        //       }
        //     }
        //   }]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
chartOne.setOption(readeOption);
}
