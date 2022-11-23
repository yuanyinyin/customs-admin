// 基于准备好的dom，初始化echarts实例
var newChartSix = echarts.init(document.getElementById('newChartSix'));
var newOptionSix  = {
      grid: { 
        top: '14%',
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
          name: '查获率',
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
          name:'查获率',
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
          data:[890,720,660,480,340,400,510,630,800,960,200,310]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
newChartSix.setOption(newOptionSix);