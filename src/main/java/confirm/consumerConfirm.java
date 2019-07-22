package confirm;

import com.rabbitmq.client.*;

import java.io.IOException;

public class consumerConfirm {
    public static void main(String[] args)  throws Exception{
        //1 创建一个工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.127.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //创建连接
        Connection connection = connectionFactory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //设置属性
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";
        String queueName = "confirm_save_queue";

        //声明交换机 队列 然后绑定路由key
        channel.exchangeDeclare(exchangeName,"topic",true);

        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        //创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //设置channel 中间的参数是authConfirm 自动签收
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true){
            //可以一直获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端"+ msg );
            //Envelope envelope = delivery.getEnvelope();
        }

        //不能关闭channel 和connetion
    }
}
