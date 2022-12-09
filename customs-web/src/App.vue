<template>
  <router-view />
</template>
<script setup lang="ts">
import { setToken } from '@/utils/auth'

const store = useStore()
const settings = computed(() => {
  return store.state.app.settings
})
//提醒
import {ElNotification} from "element-plus";
import {getListPage} from "@/api/warning";
import { useRouter } from 'vue-router';
const timer = ref(null);
const router = useRouter()

onMounted(() => {
    timer.value = setInterval(async () => {
    handleCurrentChange();
    }, 3000000);
  });

onUnmounted(() => {
    clearInterval(timer.value);
  });

const handleCurrentChange = () => {
    let params={
        page: 1,
        limit:5,
        flag:0
    }
    store
    .dispatch('info/getListPage',params)
    .then((response) => {
      const tableData = response.items
      const pageTotal = response.total
      if(pageTotal){
       
        for(var i=0;i<tableData.length;i++){
          const obj = tableData[i] 
          const  message =    obj.content + '<br/>' + "<div class='app-message-right'>" + obj.createTime + '</div>'

          // ElNotification({
          //   title: '回执信息提醒',
          //   message: message,
          //   position: 'bottom-right',
          //    dangerouslyUseHTMLString:true,
          //   // offset:36,
          //   duration:30000,
          //   onClick:function(){
          //     linkTodo(
          //       obj
          //     );
          //   },
          // })

          window.setTimeout(() => {
          ElNotification({
            title: '回执信息提醒',
            message: message,
            position: 'bottom-right',
            dangerouslyUseHTMLString:true,
            // offset:36,
            duration:30000,
            // type:type,
            onClick:function(){
              linkTodo(
                obj
              );
            },
          })
      }, 0)



        }
         
      }
    })
    .catch((response) => {})
 }

 //跳转我的信息
const linkTodo = (params:any) =>{

  const path = '/noticeInfoManage/info'
  const routeData = router.resolve({ //使用resolve
    name: '',
    path: path,
    query: params,
  })
  window.open(routeData.href, '_self')

}




onBeforeMount(() => {
  //set tmp token when setting isNeedLogin false
  if (!settings.value.isNeedLogin) setToken(settings.value.tmpToken)
})
</script>


<style lang="scss" scoped>
.app-message-right {
  display: flex; 
  justify-content: flex-end;
}

</style>