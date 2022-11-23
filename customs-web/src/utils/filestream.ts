/**
 * base64转成图片
 * @param dataurl 
 * @param filename 
 * @returns 
 */
export default function base64toFile (dataurl:any, filename = 'file') {
    const arr = dataurl.split(',')
    const mime = arr[0].match(/:(.*?);/)[1]
    const suffix = mime.split('/')[1]
    const bstr = atob(arr[1])
    let n = bstr.length
    const u8arr = new Uint8Array(n)
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n)
    }
    return new File([u8arr], `${filename}.${suffix}`, {
      type: mime
    })
}