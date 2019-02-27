package com.wx.wheelview.demo;

import android.content.Intent;

import java.util.concurrent.BlockingQueue;

/**
 * @author William
 * @date 2019/2/26
 * class introduction: 消费者
 */
public class ConsumerQueue implements Runnable{

    private BlockingQueue blockingQueue;

    public ConsumerQueue(BlockingQueue blockingQueue){
        this.blockingQueue = blockingQueue;
    }


    @Override
    public void run() {

        for (int i=0; i<10;i++){
            try {
                System.out.println("____消费苹果成功");
                blockingQueue.put(i);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
