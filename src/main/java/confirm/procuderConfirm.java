package confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class procuderConfirm {
    public static void main(String[] args) throws Exception{
        //1 创建一个工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.127.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //创建连接
        Connection connection = connectionFactory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //设置连接方式为确认模式
        channel.confirmSelect();

        //设置属性
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        String msg = "hello";
        //发送消息
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        //添加确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //成功
            //1 标志消息是哪个
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("-------Ack-------------");
            }

            //失败
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("-------no Ack-------------");
            }
        });

        //不能关闭channel 和connetion
    }
}
