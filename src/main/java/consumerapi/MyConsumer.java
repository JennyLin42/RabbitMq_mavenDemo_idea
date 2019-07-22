package consumerapi;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer  {
    public MyConsumer(Channel channel) {
        super(channel);
    }

    //1 消费的标签
    //2 保存重要信息 比如交换机 routingkey
    //3 额外的属性
    //4 msg内容
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        System.out.println("-------MyConsumer--------");
        System.out.println("-------consumerTag--------"+consumerTag);
        System.out.println("-------envelope--------"+envelope);
        System.out.println("-------basicProperties--------"+basicProperties);
        System.out.println("-------bytes--------"+new String(bytes));

    }
}
