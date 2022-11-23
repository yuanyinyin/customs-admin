<template>
  <div>
    <!-- <iframe src="/ycj/big/big.html" style="width:100%;height:100%;" frameborder="0" allowFullScreen = "true" ref="fIframe"></iframe> -->
  </div>
</template>
<script lang="ts" setup>
import {queryToday} from "@/api/tide";
import {getToken} from "@/utils/auth";

const fIframe = ref<any>();

  onMounted(() => {
    //查询今日潮汐
    // queryTodayTide()
    // window.addEventListener("message", handleMessage);
    // window.location.href ="/ycj/big/big.html";
    window.open("/ycj/big/big.html?token="+getToken());
  })

  const handleMessage = (event) =>{
    console.log('-----iframe传值过来了')
    console.log(event)
    // 获取从iframe页面中传过来的值
    var cmd = event.data.cmd;
    var params = event.data.params;
 }

const queryTodayTide = () =>{
  queryToday()
    .then((response) => {
      fIframe.value.onload = function () {
        fIframe.value.contentWindow.setTodayTide(response.data);
      }
    })
    .catch((response) => {
    })
}

</script>
<style scoped>

</style>
