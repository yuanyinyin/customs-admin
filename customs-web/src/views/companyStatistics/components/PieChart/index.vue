<template>
  <div class="report" ref="chart"></div>
</template>
<script lang="ts" setup>
  import * as echarts from 'echarts'

  const props = defineProps({
    // summary: String,
    unit:{
      type: String,
      default: ''
    },
    summary: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    list: {
      type: Array,
      default: () => []
    },
    colors: {
      type: Array,
      default: () => []
    },
  })

  // alert('pie')
  let total = 0;
  let currentInstance = ''
  onMounted(() => {
    // 获取DOM元素并且进行初始化
    currentInstance = getCurrentInstance()

    total = props.list.reduce(( acc, cur ) => {
                  return acc + cur.value
              }, 0);
              
    const myChart = echarts.init(currentInstance.ctx.$refs.chart);
    // 创建图标
    myChart.setOption({
      color: props.colors,
      title: { 
        text: props.title,
        x:'right',
        y: '10',
        padding: [0,20,0,0],
        textStyle: {
            fontSize: 14,
            color: '#707070',
            fontWeight: 'normal'
        },
       },
      tooltip: {
        show: true,
        trigger: 'item',
        textStyle: {
            fontSize: 12, 
        },
        // formatter: '{b}：{c}',
        formatter: function(params){
          return `<b>${params.name}</b><br>占比：${params.percent || 0}%<br>数量：${params.value}${props.unit || ''}`;
        },
      },
      legend: {
        x: "right",
        y: "center",
        orient: 'vertical',
        padding:[0,20,0,0],
        icon: "circle",
        itemHeight:8,
        itemWidth:8,
        align: "left",
        textStyle: {
            fontSize: 12
        }
      },
      series: [ 
        {
          name: '',
          type: 'pie',
          radius: [40, 60],
          center: ['30%', '50%'],
          labelLine: {
            show: false,
          },
          label: {
            normal: {
                show: true,
                position: 'center',
                color:'#333333',
                formatter: props.summary ? `{total|${total}}{unit|${props.unit || ''}}\n\r{active|${props.summary}}` : `{total|${total}}{unit|${props.unit || ''}}`,
                rich: {
                    total:{
                        fontSize: 20,
                        fontWeight: 'bold',
                        lineHeight:34,
                    },
                    unit:{
                      fontSize: 12,
                      color:'#30300'
                    },
                    active: {
                        fontSize: 12,
                        color:'#6c7a89'
                    },
                }
            },
            emphasis: {//中间文字显示
                show: true
            }
        },
        data: props.list
      }]
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