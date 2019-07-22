import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//生产者
public class procuder {
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
        String msg = "hello";
        //第一个参数是交换机 第二个是 ruodingkey 第三个是附加属性
        channel.basicPublish("","test001",null,msg.getBytes());

        //5记得要关闭相关的连接
        channel.close();
        connection.close();

        // 生产者发消息的时候必须要指定一个交换机，
        // 如果没有的话就会被引导到默认交换机中，默认交换机的路由规则是，ruodingkey 有没有相同名字的队列，有的话则发送给该队列
        // 如果有指定交换机的话 则根据ruodingkey=交换机的ruodingkey来路由
    }
}


