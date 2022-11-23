// 基于准备好的dom，初始化echarts实例
var newChartTwo = echarts.init(document.getElementById('newChartTwo'));
var newOptionTwo  = { 
  tooltip: {
    show: true,
    trigger: 'item',
    textStyle: {
        fontSize: 20, 
    },
    formatter: '{b}：{c}例',
  },
  legend: {
    x: 'right',
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
  color:['#3EA1FF','#3fcbcc','#50ca73','#fad443','#f4637b','#ca1bb7','#5FC8FF'],
  series: [ 
    {
      name: '',
      type: 'pie',
      radius: [50, 100],
      center: ['40%', '50%'],
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
        { value: 6520, name: '水果' },
        { value: 2628, name: '水产品' },
        { value: 8860, name: '肉类' },
        { value: 2268, name: '伴侣动物' },
        { value: 3996, name: '花卉盆景' },
        { value: 5020, name: '其他' },
      ]
    }
  ]
};  
// 使用刚指定的配置项和数据显示图表。
newChartTwo.setOption(newOptionTwo);