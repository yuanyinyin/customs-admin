// 基于准备好的dom，初始化echarts实例
var chartFour = echarts.init(document.getElementById('chartFour'));
var optionFour  = {
      grid: { 
        top: '14%',
        left: '8%',
        right: '8%',
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
          data :  ['报关企业','报关企业','报关企业','报关企业','报关企业','报关企业','报关企业'],
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
              name: '金额',
              nameTextStyle: {
                  color: "rgba(48,194,255,1)", 
                  fontSize:18
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
            name: '同比',
            nameTextStyle: {
                color: "rgba(48,194,255,1)",
                fontSize:18
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
                show: true,
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
          name:'报税',
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
          data:[800,620,580,360,980,280,600]
      },
      {
        name:'同比',
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
        data: [22, 80, 108, 29, 59,100,62]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
chartFour.setOption(optionFour);