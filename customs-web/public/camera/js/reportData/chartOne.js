// 基于准备好的dom，初始化echarts实例
var chartOne = echarts.init(document.getElementById('chartOne'));
var readeOption  = {
      grid: { 
        top: '14%',
        left: '10%',
        right: '0%',
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
          data :  ['徐州','盐城','连云港','淮安','宿迁'],
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
          name: '亿元',
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
              lineStyle: {
                  color:'#eef7f9',
              }
          },
          
      }
      ],
      series : [
      {
          name:'GDP',
          type:'bar',
          barWidth:20,
          barGap:0.2,
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
          data:[80,96,120,36,68]
      },
      {
        name:'增速',
        type:'line',
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
        data: [22, 80, 108, 29, 59]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
chartOne.setOption(readeOption);