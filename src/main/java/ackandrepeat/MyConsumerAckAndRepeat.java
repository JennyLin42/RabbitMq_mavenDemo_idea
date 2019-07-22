package ackandrepeat;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumerAckAndRepeat extends DefaultConsumer {
    private Channel channel;
    public MyConsumerAckAndRepeat(Channel channel) {
        super(channel);
        channel = this.channel;
    }

    //1 消费的标签
    //2 保存重要信息 比如交换机 routingkey
    //3 额外的属性
    //4 msg内容
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        System.out.println("-------MyConsumer--------");

        if((Integer) basicProperties.getHeaders().get("num") == 1){
            //1 deliveryTag
            //2.是否批量签收 如果前面perfetchCount是大于1的 可以设置为true
            //3.是否重回队列
            channel.basicNack(envelope.getDeliveryTag(),false,false);
        }

        //1 deliveryTag
        //2.是否批量签收 如果前面perfetchCount是大于1的 可以设置为true
        channel.basicAck(envelope.getDeliveryTag(),false);//回送消息给MQ

    }
}