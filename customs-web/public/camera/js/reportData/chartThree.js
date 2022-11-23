// 基于准备好的dom，初始化echarts实例
var chartThree = echarts.init(document.getElementById('chartThree'));
var optionThree  = {
  legend: {
    x:'center',
    padding:[0,0,0,0],
    icon:'rect',
    itemHeight:4,
    itemWidth:30,
    textStyle: { 
        color: '#fff',
        fontSize: 24
    }
  },
  grid: { 
    top: '16%',
    left: '6%',
    right: '2%',
    bottom: '8%',
  },
  tooltip: {
    trigger: 'axis',
    textStyle: {
        fontSize: 24,
    },
  },
  xAxis : [
  {
      type : 'category',
      data :  ['2020年','2021年','2022年'],
      axisLine:{
          lineStyle: {
              color:'#eef7f9',
              fontSize: 20
          }
      },
      axisLabel: {
          show: true,
          textStyle: {
              color: 'rgba(255,255,255,0.8)',
              fontSize: 20
          }
      }
  }
  ],
  yAxis : [
  {
      name: '单位',
      nameTextStyle: {
          color: "rgba(48,194,255,1)",
          fontSize: 20
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
          fontSize: 20,
      },
      axisLine:{
        show: false,
        lineStyle: {
            color:'#eef7f9',
            fontSize: 20
        }
      },
  }
  ],
  series : [
    {
      name:'棉花',
      type:'line',
      barGap: 0.2,
      itemStyle: {
        normal: {
            barBorderRadius: 0,
            label: {
                show: false, 
                position: 'top', 
                textStyle: { 
                    color: '#49E3DD',
                    fontSize: 28
                }
            },
            color: "#FFD64D",
            lineStyle:{
              width: 3
            }
        }
    },
    data:[68.47,31.63,25.31]
  },
  {
        name:'机械',
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
                color: "#2C9DFF",
                lineStyle:{
                  width: 3
                }
            }
        },
        data:[20.36,26.26,18.20]
    }
]
};
// 使用刚指定的配置项和数据显示图表。
chartThree.setOption(optionThree);