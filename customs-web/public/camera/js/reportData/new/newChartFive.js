// 基于准备好的dom，初始化echarts实例
var newChartFive = echarts.init(document.getElementById('newChartFive'));
var newOptionFive  = {
      grid: { 
        top: '14%',
        left: '11%',
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
          data :  ['202201','202202','202203','202204','202205','202206'],
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
          name: '万元',
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
          name:'销售额（万元）',
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
          data:[80800,96000,120900,36960,368000,96800]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
newChartFive.setOption(newOptionFive);