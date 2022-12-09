// 采用 promise 形式的API
const amqp = require('amqplib');


let serverConfig = {
  protocol: 'amqp',
  hostname: '172.16.22.11',
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
      var ok = ch.assertExchange(ex, exchange_type, {durable: false});
      // 交换器类型direct、fanout、topic、headers
      return ok.then(function() {
        ch.publish(ex, '', Buffer.from(body), { //注意第一个参数为交换机名称  第二个参数为Routing key(1、当交换器类型为direct时 该值只能为'' 不能为null  2、当对应交换机类型绑定queue时有Routing key配置时才需要填写)
          headers: headers
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


exports.rabbitmq = {
  sendQueueMsg: sendQueueMsg,
  receiveQueueMsg:receiveQueueMsg,
}