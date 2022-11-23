// 基于准备好的dom，初始化echarts实例
var newChartOne = echarts.init(document.getElementById('newChartOne'));
var newOptionOne  = { 
  tooltip: {
    show: true,
    trigger: 'item',
    textStyle: {
        fontSize: 20, 
    },
    formatter: '{b}：{c}例',
  },
  legend: {
    x: 'left',
    y: 'center',
    orient: 'vertical',
    padding:[0,0,0,0],
    icon: "circle",
    itemHeight:16,
    itemWidth:16,
    align: "left",
    textStyle: {
        color: '#fff',
        fontSize: 20
    }
  },
  color:['#3EA1FF','#F06C6C','#F0C76C','#DCF06C','#85F06C','#5FFFD4','#5FC8FF'],
  series: [ 
    {
      name: '',
      type: 'pie',
      // radius: [40, 120],
      center: ['60%', '50%'],
      // roseType: 'area',
      // itemStyle: {
      //   borderRadius: 8 
      // },
      labelLine: {
        show: true,
      },
      label:{
        normal: {
            // formatter: '{b|{d}%}\n{hr|}\n{c|{c}单}',
            formatter: '{b|{d}%}',
            rich: {
                hr: {
                    backgroundColor: '#ade8ff',
                    width: '100%',
                    height: 1,
                },
                a: {
                    color:'#36ffff',
                    fontSize:20,
                    align: 'center',
                    height: 40,
                    fontWeight:'bold',
                    padding:[0,0,0,5]
                  },
                b: {
                  color:'#fff',
                  fontSize:20,
                  fontWeight:'bold',
                  height:40
                },
                c: {
                  color:'#36ffff',
                  fontSize:25,
                  fontWeight:'bold',
                  align: 'center',
                  height: 40,
                  padding:[0,0,0,5]
                },
            }
        },
      },
      data: [
        { value: 2022, name: '健康' },
        { value: 120, name: '发热' },
        { value: 360, name: '其他' }
      ]
    }
  ]
};  
// 使用刚指定的配置项和数据显示图表。
newChartOne.setOption(newOptionOne);