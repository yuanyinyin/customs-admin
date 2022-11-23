function loadChartThree(param){
  var names = [];
  var nums = [];
  param.forEach(element=>{
      names.push(element.name)
      nums.push(element.num)
  });

  // 基于准备好的dom，初始化echarts实例
var chartThree = echarts.init(document.getElementById('chartThree'));
var lineData = nums;
// var lineBg = [25,25,25,25,25,25];
var optionThree  = {
      grid: { 
        top: '16%',
        left: '8%',
        right: '0%',
        bottom: '14%',
      },
      tooltip: {
        trigger: 'item',
        textStyle: {
            fontSize: 20,
        },
        formatter:function(params){
          return '已上报<br>'+ params.name + '：' + params.value + ' 件'
        }
      },
      legend: {
        x: 'right',
        y: 'top',
        orient: 'vertical',
        padding:[0,0,0,0],
        icon: "circle",
        itemHeight:16,
        itemWidth:16,
        align: "left",
        textStyle: {
            color: '#fff',
            fontSize: 22,
            lineHeight: 28
        }
      },
      xAxis : [
      {
          type : 'category',
          data :  names,
          axisLine:{
              show: false,
              lineStyle: {
                  color:'#eef7f9',
                  fontSize: 18
              }
          },
          axisLabel: {
              show: true,
              textStyle: {
                  color: 'rgba(255,255,255,0.8)',
                  fontSize: 18,
                  lineHeight: 26
              },
              padding: [10,0,0,0]
          }
      }
      ],
      yAxis : [
      {
          name: '件数',
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
                color: 'rgba(44,166,255,.1)',
                type: 'dashed',
            }
          },
          axisLabel: {
              margin: 15,
              color: '#ffffff63',
              fontSize: 18
          },
          axisLine:{
              show: false,
              lineStyle: {
                  color:'#eef7f9',
              } 
          },
          
      }
      ],
      series : [
      // {//柱底圆片
      //     name:'已上报',
      //     type:'pictorialBar',
      //     symbolSize: [32, 18],//调整截面形状
      //     symbolOffset: [0, 10],
      //     z: 12,
      //     itemStyle: {
      //       'normal': {
      //         color: "rgba(30, 128, 228, 1)",

      //       }
      //     },
      //     data: lineBg
      // },
      // {//背景柱顶圆片
      //   name: "",
      //   type: "pictorialBar",
      //   symbolSize: [32, 18],//调整截面形状
      //   symbolOffset: [0, -10],
      //   z: 12,
      //   symbolPosition: "end",
      //   "itemStyle": {
      //     'normal': {
      //       color: 'rgba(22, 138, 237, 0.08)',
      //     }
      //   },
      //   data: lineBg
      // },
      {//柱顶圆片
        name: "",
        type: "pictorialBar",
        symbolSize: [32, 18],//调整截面形状
        symbolOffset: [0, -10],
        z: 12,
        symbolPosition: "end",
        "itemStyle": {
          'normal': {
            color: '#16BBED',
          }
        },
        data: lineData
      },
      {//柱体
        name: '',
        type: 'bar',
        barWidth: 32,
        barGap: '-100%',
        itemStyle: {
          normal: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(32, 93, 219, 1)'
            }, {
                offset: 1,
                color: 'rgba(27, 223, 252, 1)'
            }])
          }
        },
        showBackground: true,
        backgroundStyle: {
          color: 'rgba(18, 81, 210, 0.1)'
        },
        data: lineData
      }
    ]
  };
// 使用刚指定的配置项和数据显示图表。
chartThree.setOption(optionThree);
}
