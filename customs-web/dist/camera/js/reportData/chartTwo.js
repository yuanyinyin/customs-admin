// 基于准备好的dom，初始化echarts实例
var chartTwo = echarts.init(document.getElementById('chartTwo'));
var optionTwo  = {
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
          data :  ['清江浦区','淮阴区','淮安区','洪泽区','涟水县','盱眙县','金湖县'],
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
          name: 'KG',
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
                  color: '#FF4D4D'
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
          data:[160,202,168,98,126,140,198]
      },
      {
        name:'折线名称',
        type:'line',
        smooth:true,  //这个是把线变成曲线
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
                color: "#8538FF",
                lineStyle:{
                    width: 3
                }
            }
        },
        data: [120, 80, 108, 162, 20,49,88]
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
chartTwo.setOption(optionTwo);