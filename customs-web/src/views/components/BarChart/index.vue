<template>
  <div class="report" ref="barChart"></div>
</template>
<script lang="ts" setup>
  import * as echarts from 'echarts'

  const props = defineProps({
    list: {
      type: Array,
      default: () => []
    }
  })

  let currentInstance = ''
  onMounted(() => {
    // 获取DOM元素并且进行初始化
    currentInstance = getCurrentInstance()
    const myChart = echarts.init(currentInstance.ctx.$refs.barChart);
    //series数据
    let seriesData = [];
    let xAxisData = [];
    props.list.map((item) => {
      seriesData.push({
        name: item.title,
        type:'bar',
        barWidth: 10,
        barGap: 0.5,
        itemStyle: {
            normal: {
                barBorderRadius: [ 5, 5, 0, 0],
                label: {
                    show: false, 
                    position: 'top', 
                    textStyle: { 
                        color: item.colors[1],
                        fontSize: 12
                    }
                },
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: item.colors[0]
                }, {
                    offset: 1,
                    color:  item.colors[1]
                }]),
            }
        },
        data: item.data
      })
    })
    props.list.map((item0) => {
      xAxisData.push({
        type: 'category',
        // boundaryGap: false,//坐标轴两边留白
        // data: ['崇川区', '海门区', '通州区','海安县','启东市','如皋市','如东县','开发区', '苏锡通','通州湾'],
        data:item0.areaName,
        axisLabel: { //坐标轴刻度标签的相关设置。
          interval: 0,//设置为 1，表示『隔一个标签显示一个标签』
          textStyle: {
            fontStyle: 'normal',
            fontFamily: '微软雅黑',
            fontSize: 12,
            color: "#999",
          },
      },
      axisTick:{//坐标轴刻度相关设置。
        show: false,
      },
      axisLine:{//坐标轴轴线相关设置
        lineStyle:{
          color:'#E5E9ED',
          opacity:0.6
        }
      },
      splitLine: { //坐标轴在 grid 区域中的分隔线。
        show: true,
        lineStyle: {
          color: '#E5E9ED',
          opacity:0.6
        }
      }
      })
    })
 
    // 创建图标
    myChart.setOption({
      tooltip: {
          trigger: 'axis'
      },
       legend: {
        x:'center',
        padding:[5,0,0,0],
        icon:'rect',
        itemHeight:5,
        itemWidth:30,
        textStyle: { 
            color: '#333',
            fontSize: 12
        }
      },
      grid: {
          top:'30',
          left: '0',
          right: '10',
          bottom: '0',
          containLabel: true
      },
      xAxis: xAxisData,
      yAxis: [
      {
        name: '资产（万）',
        nameTextStyle: {
            color: "rgba(48,194,255,1)",
            fontSize:10,
            align: 'left'
        }, 
        type: 'value',
        // splitNumber: 2,
        // interval:5,
        axisLabel: {
          textStyle: {
            fontStyle: 'normal',
            fontFamily: '微软雅黑',
            fontSize: 12,
            color: "#999",
          }
        },
        axisLine:{
          show: false
        },
        axisTick:{
          show: false
        },
        splitLine: {
          show: true,
          lineStyle: {
            color: '#E5E9ED',
            opacity:0.6
          }
        }
      }
    ],
    series: seriesData
    })

    window.addEventListener("resize", () => {
      myChart && myChart.resize();
    })
  })
</script>
<style lang="scss" scoped>
  .report{
    width: 100%;
    height: 100%;
  }
</style>