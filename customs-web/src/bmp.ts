export function BMPGL(ak) {
  return new Promise(function (resolve, reject) {
    window.init = function () {
      // eslint-disable-next-line
      resolve(BMapGL)
    }
    const script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = `https://api.map.baidu.com/api?v=1.0&type=webgl&ak=OaGr78TjxBO0bS6dxptYXom91MGzFQuT&callback=init&s=1`
    script.onerror = reject
    document.head.appendChild(script)
  })
}
