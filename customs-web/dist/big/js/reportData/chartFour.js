function loadChartFour(param){
  // 基于准备好的dom，初始化echarts实例
var chartFour = echarts.init(document.getElementById('chartFour'));
var data = param;
var optionFour  = {
  tooltip: {
    show: true,
    trigger: 'item',
    textStyle: {
        fontSize: 22, 
    },
    formatter: '{b}：{c}件',
  },
  legend: {
    x: 'right',
    y: 'center',
    orient: 'vertical',
    padding:[0,50,0,0],
    icon: "circle",
    itemHeight:16,
    itemWidth:16,
    align: "left",
    textStyle: {
        color: '#fff',
        fontSize: 22,
        lineHeight: 28
    },
    formatter(name) { 
      //找到data中name和文本name值相同的对象
      const val = data.filter(item => {
        return item.name === name
      })
      return name + '  ' +  val[0].value+ ' 件'
    }
  },
  series: [ 
    {
      name: '',
      type: 'pie',
      radius: [40, 110],
      center: ['30%', '50%'],
      // roseType: 'area',
      // itemStyle: {
      //   borderRadius: 8 
      // },
      labelLine: {
        show: true,
      },
      itemStyle:{
        normal:{
          color: function(params) {
            var colorList = [
           {
               c1: '#24F0D1',
               c2: '#E6FF5C'                                                                   
           },
           {
               c1: '#4DB2FF',
               c2: '#3357CE'
           }]
            return new echarts.graphic.LinearGradient(0, 0, 0, 1, [{ //颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
                   offset: 0,
                   color: colorList[params.dataIndex].c1
               }, {
                   offset: 1,
                   color: colorList[params.dataIndex].c2
               }])                                    
             
           },
        }
      },
      label:{
        normal: {
            // formatter: '{b|{d}%}\n{hr|}\n{c|{c}单}',
            formatter: '{b|{d}%}',
            position: 'inner',
            rich: {
                hr: {
                    backgroundColor: '#ade8ff',
                    width: '100%',
                    height: 1,
                },
                a: {
                    color:'#36ffff',
                    fontSize:22,
                    align: 'center',
                    height: 40,
                    fontWeight:'bold',
                    padding:[0,0,0,5]
                  },
                b: {
                  color:'#fff',
                  fontSize:22,
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
      data: data
    }
  ]
};  
// 使用刚指定的配置项和数据显示图表。
chartFour.setOption(optionFour);
}
