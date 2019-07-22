package Limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import consumerapi.MyConsumer;

public class ConsumerLimit {
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
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";

        //声明交换机 队列 然后绑定路由key
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName, exchangeName, routingKey);


        //设置channel 中间的参数是authConfirm 自动签收
        //设置限流 autoAck是false
        channel.basicQos(0,1,false);
        channel.basicConsume(queueName,false,new MyConsumerLimit(channel));

        //即可
    }
}
