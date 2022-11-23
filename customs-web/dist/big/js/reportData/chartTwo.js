function loadChartTwo(param){
  // 基于准备好的dom，初始化echarts实例
var chartTwo = echarts.init(document.getElementById('chartTwo'));
var xdata = param;
var optionTwo  = {
      title : {
        show:true, 
        text: '巡查统计',
        padding: [0,0,15,0],
        x:'24%', 
        y: 'bottom',
        textAlign: 'left',
        textStyle: {
            fontSize: 26,
            color: '#ffffff',
            fontStyle: 'normal',
            fontWeight: 'normal',
        },
    },
  tooltip: {
    show: true,
    trigger: 'item',
    textStyle: {
        fontSize: 22, 
    },
    formatter: '{b}：{c}次',
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
      const val = xdata.filter(item => {
        return item.name === name
      })
      console.log('------->',name)
      return name + '  ' +  val[0].value+ ' 次'
    }
  },
  series: [ 
    {
      name: '',
      type: 'pie',
      radius: [32, 96],
      center: ['30%', '45%'],
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
               c1: '#66FBF9',
               c2: '#0FC2A7'
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
      data: xdata
    }
  ]
};  
// 使用刚指定的配置项和数据显示图表。
chartTwo.setOption(optionTwo);
}
