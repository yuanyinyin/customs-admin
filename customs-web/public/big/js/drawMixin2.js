// 屏幕适配 mixin 函数

// * 默认缩放值
const scale = { 
  width: '1',
  height: '1',
}

let drawTiming = null

// * 设计稿尺寸（px）
const baseWidth = 3840
const baseHeight = 2160

// * 需保持的比例
const baseProportion = parseFloat((baseWidth / baseHeight).toFixed(5))
console.log(baseProportion)

function calcRate(){
  const appRef = $("#container")
      if (!appRef) return
      // 当前宽高比
      const currentRate = parseFloat((window.innerWidth / window.innerHeight).toFixed(5))
      console.log('屏幕比率',currentRate)
      if (appRef) {
        if (currentRate > baseProportion) {
          // 表示更宽
          console.log('更宽')
          scale.width = ((window.innerHeight * baseProportion) / baseWidth).toFixed(5)
          scale.height = (window.innerHeight / baseHeight).toFixed(5)
          appRef.css({"transform":`scale(${scale.width}, ${scale.height})`})
        } else {
          // 表示更高
          console.log('更高')
          scale.height = ((window.innerWidth / baseProportion) / baseHeight).toFixed(5)
          scale.width = (window.innerWidth / baseWidth).toFixed(5)
          appRef.css({"transform":`scale(${scale.width}, ${scale.height})`})
        }
      }

}

function resize() {
  console.log('执行')
  clearTimeout(drawTiming)
  drawTiming = setTimeout(() => {
    calcRate()
  }, 200)
}

$(function(){
  calcRate()
  $(window).resize(function() {
    resize()
  })
})
