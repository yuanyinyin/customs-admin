// 基于准备好的dom，初始化echarts实例
var newChartSeven = echarts.init(document.getElementById('newChartSeven'));
var newOptionSeven  = {
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
    top: '18%',
    left: '8%',
    right: '2%',
    bottom: '12%',
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
      data :  ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
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
      name: '平均放行时长（分钟）',
      min:0,
      max:100,
      nameTextStyle: {
          color: "rgba(48,194,255,1)",
          fontSize: 20,
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
          fontSize: 20,
      },
      axisLine:{
        show: true,
        lineStyle: {
            color:'#eef7f9',
            fontSize: 20
        }
      },
  }
  ],
  series : [
    {
      name:'',
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
    data:[52,31,25,36,45,45,40,38,48,56,22,32]
  }
]
};
// 使用刚指定的配置项和数据显示图表。
newChartSeven.setOption(newOptionSeven);