// 采用 promise 形式的API
const amqp = require('amqplib');


let serverConfig = {
  protocol: 'amqp',
  // hostname: '172.16.22.11',
  hostname: '58.221.159.202',
  port: 5672,
  username: '',
  password: '',

  locale: 'en_US',
  frameMax: 0,
  heartbeat: 0,
  vhost: '/customs',
}
let sendConfig={
  queue:'bgd.queue',
  exchange:'bgd.direct',
  exchange_type:'direct',
}
// 发送信息的方法
function sendQueueMsg(params, callbackfun) {
  // console.log(queue)//队列名
  // console.log(params)
  var queue=sendConfig.queue;//队列名
  var exchange=sendConfig.exchange;//交换机名称
  var exchange_type=sendConfig.exchange_type;//交换机类型
  var body=params.body;
  var headers=params.headers;
  serverConfig.username=params.username;
  serverConfig.password=params.password;

  amqp.connect(serverConfig).then(function(conn) {
    return conn.createChannel().then(function(ch) {
      var ex = exchange;
      var ok = ch.assertExchange(ex, exchange_type, {durable: true});//初始化和持久化交换机
      // 交换器类型direct、fanout、topic、headers
      return ok.then(function() {
        /*
          消息持久化  参考：nodemodules源码(网上基本没有资料) 实现了直接通过队列发送持久化消息 E:\git_new_workspace\customs\customs-electron\node_modules\amqplib\examples\tutorials\new_task.js
          下例代码实现通过交换机发送持久化消息
          特别注意：rabbitmq控制台中持久化的数据  http://172.16.22.11:15672/
            message_id:	消息ID
            priority:	0
            delivery_mode:	2     包括java代码使用   特别注意:但是vue中使用{deliveryMode: true}
            content_type:	text/plain
            消息优先级priority字段定义为一个无符号byte,所以实际的优先级应该在0到255之间；消息如果没有设置priority优先级字段，那么priority字段值默认为0；如果优先级队列priority属性被设置为比x-max-priority大，那么priority的值被设置为x-max-priority的值。
       */
        ch.publish(ex, '', Buffer.from(body), { //注意第一个参数为交换机名称  第二个参数为Routing key(1、当交换器类型为direct时 该值只能为'' 不能为null  2、当对应交换机类型绑定queue时有Routing key配置时才需要填写)
          headers: headers, //null '' {}  三者效果一样
          priority:	0, //优先级 数值越大优先级越高   0-255
          deliveryMode:true,  //消息持久化
          contentType:'text/plain',  //注意属性写法和java及rabbitmq结果集不一样  自己尝试摸索出来 网上几乎没有相关文档
          messageId:guid() //注意属性写法和java及rabbitmq结果集不一样  自己尝试摸索出来 网上几乎没有相关文档
        });
        if(callbackfun)callbackfun('发送成功!');
        return ch.close();
      });
    }).finally(function() { conn.close(); });
  }).catch(console.warn);
}
// 接收信息的方法
function receiveQueueMsg(queue, callback) {
  var open = amqp.connect(serverConfig);
  // Consumer
  open.then(function (conn) {
    return conn.createChannel();
  }).then(function (ch) {
    return ch.assertQueue(queue).then(function (ok) {
      return ch.consume(queue, function (msg) {
        if (msg !== null) {
          callback(msg.content.toString(), msg.properties.headers)
          ch.ack(msg);
        }
      });

    });
  }).catch(console.warn);
}
// module.exports ={
//   sendQueueMsg,
//   receiveQueueMsg
//
// }

function guid() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    var r = Math.random() * 16 | 0,
        v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

exports.rabbitmq = {
  sendQueueMsg: sendQueueMsg,
  receiveQueueMsg:receiveQueueMsg,
}