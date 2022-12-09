<template>
  <div class="report" ref="lineChart"></div>
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
    const myChart = echarts.init(currentInstance.ctx.$refs.lineChart);
    //series数据
    let seriesData = [];

    props.list.map((item) => {
      seriesData.push({
        name: item.title,
        type: 'line',
        smooth: true,
        symbol: 'circle',
        symbolSize: 6,
        itemStyle: { 
            normal: {
              color: item.colors[1],
              borderColor: "#ffffff",
              borderType: "solid",
              borderWidth: 1,
              lineStyle: {
                color: item.colors[1],
                width:1
              },
              areaStyle: { 
                color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                offset: 0,
                color: item.colors[0]
              }, {
                offset: 1,
                color: item.colors[1]
              }]),
            }
          }
        },
        data: item.data
      })
    })
    // 创建图标
    myChart.setOption({
      tooltip: {
          trigger: 'axis'
      },
      grid: {
          top:'30',
          left: '0',
          right: '10',
          bottom: '0',
          containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,//坐标轴两边留白
        data: ['01月', '02月', '03月','04月','05月','06月','07月', '08月', '09月','10月','11月','12月'],
        axisLabel: { //坐标轴刻度标签的相关设置。
          interval: 0,//设置为 1，表示『隔一个标签显示一个标签』
          textStyle: {
            fontStyle: 'normal',
            fontFamily: '微软雅黑',
            fontSize: 12,
            color: "#999",
          }
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
      },
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