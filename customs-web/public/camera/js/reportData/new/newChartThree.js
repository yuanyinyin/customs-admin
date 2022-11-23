// 基于准备好的dom，初始化echarts实例
var newChartThree = echarts.init(document.getElementById('newChartThree'));
var newOptionThree  = {
      grid: { 
        top: '18%',
        left: '11%',
        right: '2%',
        bottom: '12%',
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
          data :  ['01','02','03','04','05','06','07','08','09','10','11','12'],
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
              name: '票数',
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
              
          },
          {
            name: '查验率',
            nameTextStyle: {
                color: "rgba(48,194,255,1)",
                fontSize:18,
                align: 'right'
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
                show: false,
                interval: 'auto',//居中显示
                formatter: '{value} %',//以百分比显示
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
          name:'票数',
          type:'bar',
          barWidth:20,
          barGap:0.2,
          yAxisIndex: 0,
          itemStyle: {
            emphasis: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#FFAF2E'
              }, {
                  offset: 1,
                  color: 'rgba(255,175,46,0.01)'
              }]),
            },
            normal: {
                barBorderRadius: 0,
                label: {
                    show: true, 
                    position: 'top', 
                    textStyle: { 
                        color: '#fff',
                        fontSize: 20
                    }
                },
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#00B7FF'
                }, {
                    offset: 1,
                    color: 'rgba(44,231,255,0.12)'
                }]),
                
            }
          },
          data:[22000, 80000, 10800, 29008, 59006,10000,62000,96000,28500,16988,12569,69008]
      },
      {
        name:'查验率',
        type:'line',
        yAxisIndex: 1,
        itemStyle: {
            normal: {
                barBorderRadius: 0,
                label: {
                    show: false, 
                    position: 'top', 
                    textStyle: { 
                        color: '#FFFF2B',
                        fontSize: 28
                    }
                },
                color: "#FFD64D",
                lineStyle:{
                    width: 3
                }
            }
        },
        data: [96,98,80,73,99,60,70,98,90,88,80,70]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
newChartThree.setOption(newOptionThree);