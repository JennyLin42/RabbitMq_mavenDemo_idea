import com.rabbitmq.client.*;

//消费者
public class consumer {
    public static void main(String[] args) throws Exception{

        //1 创建一个工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.127.129");
        connectionFactory.setPort(5672);
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        //connectionFactory.setVirtualHost("/");
        //2 通过工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3通过connection创建channel
        Channel channel = connection.createChannel();

        //4声明一个队列
        String queuwName = "test001";
        // 参数1 队列名
        // 参数2 durable是否持久化 true的话下次重启队列还会在
        // 参数3 exclusive 独占 true的时候 就是这个队列只有这一个连接可以使用，保证了数据的顺序
        // 参数4 autoDelete 自动删除的队列 假如没有其他东西和这个队列有联系，例如没有交换机和这个队列绑定，那么他就会自动删除这个队列
        // 参数5 扩展参数
        channel.queueDeclare(queuwName,true,false,false,null);

        //5创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //设置channel 中间的参数是authConfirm 自动签收
        channel.basicConsume(queuwName,true,queueingConsumer);

        //6获取消息
        while (true){
            //可以一直获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端"+ msg );
            //Envelope envelope = delivery.getEnvelope();
        }


    }
}
