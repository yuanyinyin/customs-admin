/*年-月-日- 时:分:秒*/
export function parseTime(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      const seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      // 拼接
      return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
    } else {
      return ''
    }
  }

  export function parseDate(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // 拼接
      return year + '-' + month + '-' + day
    } else {
      return ''
    }
  }

  // 月-日
  export function parseMonthDay(time) {
    if (time) {
      const date = new Date(time)
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // 拼接
      return month + '-' + day
    } else {
      return ''
    }
  }
  // 时-秒
  export function parseTimeOper(time) {
    if (time) {
      const date = new Date(time)
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      // 拼接
      return hours + ':' + minutes
    } else {
      return ''
    }
  }

  /*年-月-日- 时:分:秒*/
  export function parseTime_CN(time) {
    if (time) {
      let week
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      const seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      // 拼接
      if(date.getDay()==0) week="星期日"
      if(date.getDay()==1) week="星期一"
      if(date.getDay()==2) week="星期二"
      if(date.getDay()==3) week="星期三"
      if(date.getDay()==4) week="星期四"
      if(date.getDay()==5) week="星期五"
      if(date.getDay()==6) week="星期六"

      return year + '年' + month + '月' + day + '日 ' + hours + ':' + minutes + ':' + seconds + "\xa0\xa0" + week
    } else {
      return ''
    }
  }

  ///*中文:年-月-日- 时:分:秒*/
  export function parse_cn_time(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* *
       * 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      const seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      return year + '年' + month + '月' + day + '日 ' + hours + ':' + minutes + ':' + seconds + "\xa0\xa0"
    } else {
      return ''
    }
  }

  ///*中文:星期week*/
  export function parse_cn_week(time) {
    if (time) {
      let week
      const date = new Date(time)
      // 拼接
      if(date.getDay()==0) week="星期日"
      if(date.getDay()==1) week="星期一"
      if(date.getDay()==2) week="星期二"
      if(date.getDay()==3) week="星期三"
      if(date.getDay()==4) week="星期四"
      if(date.getDay()==5) week="星期五"
      if(date.getDay()==6) week="星期六"
      return week
    } else {
      return ''
    }
  }

  /*年-月-日 时:分*/
  export function parseTimeTwo(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      // 拼接
      return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes
    } else {
      return ''
    }
  }
  /*格式: 日/月 例如24/10*/
  export function parseDayMonth(time) {
    if (time) {
      const date = new Date(time)
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // 拼接
      return day + '/' + month
    } else {
      return ''
    }
  }
  /*年-月-日*/
  export function parseTimeThrid(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // 拼接
      return year + '-' + month + '-' + day
    } else {
      return ''
    }
  }
  /*年-月-日 时:分*/
  export function parseTimeFour(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      // 拼接
      return year + '年' + month + '月' + day + '日' + hours + '时'
    } else {
      return ''
    }
  }

  /*年-月-日*/
  export function parseTimeFive(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // 拼接
      return year + '年' + month + '月' + day+ '日'
    } else {
      return ''
    }
  }
  /*年/月/日 时:分*/
  export function parseTimeSix(time) {
    if (time) {
      const date = new Date(time)
      const year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      // 拼接
      return year + '/' + month + '/' + day + ' ' + hours + ':'+ minutes
    } else {
      return ''
    }
  }
  /*当前日期增加n天*/
  export function currentDateAddDate(num) {
    const d1 = new Date();
    const d2 = new Date(d1);
    d2.setDate(d1.getDate()+ num);
    return d2
  }

export function parseYear(time) {
  if (time) {
    const date = new Date(time)
    const year = date.getFullYear()
    // 拼接
    return year
  } else {
    const date = new Date()
    const year = date.getFullYear()
    // 拼接
    return year
  }
  
}
export function parseDateWithoutDay(time) {
  if (time) {
    const date = new Date(time)
    const year = date.getFullYear()
    /* 在日期格式中，月份是从0开始的，因此要加0
     * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
     * */
    const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
    // 拼接
    return year + '-' + month
  } else {
    return ''
  }
}
export function parseDateWithoutDayNew(time) {
  if (time) {
    const date = new Date(time)
    const year = date.getFullYear()
    /* 在日期格式中，月份是从0开始的，因此要加0
     * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
     * */
    const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
    // 拼接
    return year + ''+ month
  } else {
    return ''
  }
}