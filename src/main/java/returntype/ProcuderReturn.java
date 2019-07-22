package returntype;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ProcuderReturn {
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
        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "aaa.aaa";
        String msg = "hello";

        channel.addReturnListener(new ReturnListener() {
            //1 replyCode 就是返回的状态码 成不成功
            //2 replytext 文本
            //3 exchange  交换机
            //4 routingkey
            //5 AMQP.basicproperties
            //6 body 实际的消息体内容
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println("---return--");
                System.out.println("---replyCode--"+ i);
                System.out.println("---replytext--"+ s);
                System.out.println("---exchange--"+ s1);
                System.out.println("---routingkey--"+ s2);
                System.out.println("---properties--"+ basicProperties);
                System.out.println("---body--"+ new String(bytes));
            }
        });


        //第一个参数是交换机 第二个是 ruodingkey 第三个mandatory 第四个是附加属性
        channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());




    }
}
