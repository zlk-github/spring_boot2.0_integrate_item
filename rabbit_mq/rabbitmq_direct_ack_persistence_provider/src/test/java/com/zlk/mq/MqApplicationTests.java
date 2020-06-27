package com.zlk.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MqApplicationTests {


    @Autowired
    private Sender sender;

    @Test
    public void testSender() {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(100);
                    sender.send(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

}
