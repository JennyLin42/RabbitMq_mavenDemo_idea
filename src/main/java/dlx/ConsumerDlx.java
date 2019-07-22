package dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class ConsumerDlx {
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
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        Map<String,Object> argument = new HashMap<String ,Object>();
        argument.put("x-dead-letter-exchange","dlx.exchange");
        //声明交换机 队列 然后绑定路由key
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare(queueName,true,false,false,argument);
        channel.queueBind(queueName,exchangeName,routingKey);

        //设置死信队列
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,argument);
        channel.queueBind("dlx.queue","dlx.exchange","#");

        //设置channel 中间的参数是authConfirm 自动签收
        channel.basicConsume(queueName,true,new MyConsumerDlx(channel));

        //即可
    }
}
