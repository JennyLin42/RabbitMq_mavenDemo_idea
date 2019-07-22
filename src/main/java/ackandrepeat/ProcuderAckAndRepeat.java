package ackandrepeat;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class ProcuderAckAndRepeat {
    public static void main(String[] args) throws Exception{

        //1 创建一个工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.127.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2 通过工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3通过connection创建channel
        Channel channel = connection.createChannel();
        //4通过channel发生数据
        String exchangeName = "test_ackandrepeat_exchange";
        String routingKey = "ackandrepeat.save";
        String msg = "hello";

        Map<String,Object> beaders = new HashMap<String,Object>();
        beaders.put("num" ,1);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .headers(beaders)
                .build();
        //第一个参数是交换机 第二个是 ruodingkey 第三个mandatory 监听没有收到应答的消息 第四个是附加属性
        channel.basicPublish(exchangeName,routingKey,true,properties,msg.getBytes());
    }
}
