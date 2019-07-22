package dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumerDlx extends DefaultConsumer  {
    public MyConsumerDlx(Channel channel) {
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
