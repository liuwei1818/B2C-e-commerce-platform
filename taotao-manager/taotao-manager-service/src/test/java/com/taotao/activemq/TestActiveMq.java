package com.taotao.activemq;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;



public class TestActiveMq {
    //queue
    //Producer
    @Test
    public void testQueueProdecer() throws Exception {
        //1.创建连接工厂对象ConnectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        //2.使用ConnectionFactory创建一个连接Connection对象                       
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.使用Connection对象创建一个session对象
        //第一个参数是否开启事务，一般不使用事务。保证数据最终一至，使用消息队列实现。
        //如果第一个参数为true第二个参数自动忽略，第二个参数为消息应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个Destination对象，两种形式queue、topic、
        Queue queue = session.createQueue("test-queue");
        //6.使用session对象创建一个Producer对象
        MessageProducer producer = session.createProducer(queue);
        //7.创建一个TextMessage对象
        //TextMessage testMessage = new ActiveMQTextMessage();
        TextMessage textMessage = session.createTextMessage("hello activemq111");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }
    @Test
    public void testQueueConsumer() throws Exception {
        //1.创建连接工厂对象ConnectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        //2.使用ConnectionFactory创建一个连接Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.使用Connection对象创建一个session对象
        //第一个参数是否开启事务，一般不使用事务。保证数据最终一至，使用消息队列实现。
        //如果第一个参数为true第二个参数自动忽略，第二个参数为消息应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个Destination对象，与消息发送端一致
        Queue queue = session.createQueue("test-queue");
        //6.使用session对象创建一个Consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7.向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            
            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                //8.取消息内容
                if(message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                
            }
        });
        //系统等待接受信息
        /*while(true) {
            Thread.sleep(100);
        }*/
        System.in.read();
        //9.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
    
    //topic
    //producer
    @Test
    public void testTopicProducer() throws Exception {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建一个session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建destination，应该使用topic
        Topic topic = session.createTopic("test-topic");
        //创建一个producer对象
        MessageProducer producer = session.createProducer(topic);
        //创建一个TextNessage对象
        TextMessage textMessage = session.createTextMessage("hello activemq topic");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }
    
    @Test
    public void testTopicConsumer() throws Exception {
        //1.创建连接工厂对象ConnectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        //2.使用ConnectionFactory创建一个连接Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.使用Connection对象创建一个session对象
        //第一个参数是否开启事务，一般不使用事务。保证数据最终一至，使用消息队列实现。
        //如果第一个参数为true第二个参数自动忽略，第二个参数为消息应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个Destination对象，与消息发送端一致
        Topic topic = session.createTopic("test-topic");
        //6.使用session对象创建一个Consumer对象
        MessageConsumer consumer = session.createConsumer(topic);
        //7.向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            
            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                //8.取消息内容
                if(message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                
            }
        });
        //系统等待接受信息
        /*while(true) {
            Thread.sleep(100);
        }*/
        System.out.println("topic消费者1");
        System.in.read();
        //9.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
