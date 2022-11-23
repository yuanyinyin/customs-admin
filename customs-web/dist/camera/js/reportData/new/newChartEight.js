// 基于准备好的dom，初始化echarts实例
var newChartEight = echarts.init(document.getElementById('newChartEight'));
var newOptionEight  = {
      legend: {
        x:'center',
        padding:[0,0,0,0],
        icon:'rect',
        itemHeight:16,
        itemWidth:30,
        textStyle: { 
            color: '#fff',
            fontSize: 24
        },
        data: [ 
          { name:'两步申报',itemStyle: {color: '#40FFDA'} },
          { name:'提前申报',itemStyle: {color: '#FEFE41'} }
        ]
      },
      grid: { 
        top: '18%',
        left: '8%',
        right: '2%',
        bottom: '10%',
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
          data :  ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
          axisLine:{
              lineStyle: {
                  color:'#eef7f9',
                  fontSize: 18
              }
          },
          axisLabel: {
              show: true,
              textStyle: {
                  color: 'rgba(255,255,255,0.8)',
                  fontSize: 18
              }
          }
      }
      ],
      yAxis : [
          {
              name: '单位',
              nameTextStyle: {
                  color: "rgba(48,194,255,1)", 
                  fontSize:18,
                  align: 'left'
              }, 
              type : 'value', 
              scale:true,
              min:0,
              splitLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(44,166,255,.5)',
                    type: 'dashed',
                }
              },
              axisLabel: {
                  margin: 15,
                  color: '#ffffff63',
                  fontSize: 18
              },
              axisLine:{
                  show: true,
                  lineStyle: {
                      color:'#eef7f9',
                  }
              },
              
          }
      ],
      series : [
      {
          name:'两步申报',
          type:'bar',
          barWidth:20,
          barGap:0.2,
          yAxisIndex: 0,
          itemStyle: {
            emphasis: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#40FFDA'
              }, {
                  offset: 1,
                  color: 'rgba(44,231,255,0.12)'
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
                    color: '#40FFDA'
                }, {
                    offset: 1,
                    color: 'rgba(44,231,255,0.12)'
                }]),
                
            }
          },
          data:[69, 45, 95, 62, 12,38,40,50,86,61,70,46]
      },
      {
        name:'提前申报',
        type:'bar',
        barWidth:20,
        barGap:0.2,
        yAxisIndex: 0,
        itemStyle: {
          emphasis: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: '#FEFE41'
            }, {
                offset: 1,
                color: 'rgba(44,231,255,0.12)'
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
                  color: '#FEFE41'
              }, {
                  offset: 1,
                  color: 'rgba(44,231,255,0.12)'
              }]),
              
          }
        },
        data:[36, 27, 88, 52, 39,67,80,32,71,39,36,56]
    }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
newChartEight.setOption(newOptionEight);