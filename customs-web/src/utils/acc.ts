/**
 *  ** 乘法函数，用来得到精确的乘法结果
 ** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
 ** 调用：accMul(arg1,arg2)
 ** 返回值：arg1乘以 arg2的精确结果
 * @param arg1 
 * @param arg2 
 * @returns 
 */
 export function accMul (arg1, arg2) {
	let m = 0,
		  s1 = arg1.toString(),
		  s2 = arg2.toString();
	  try {
		  m += s1.split(".")[1].length;
	  } catch (e) {}
	  try {
		  m += s2.split(".")[1].length;
	  } catch (e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
  
  }