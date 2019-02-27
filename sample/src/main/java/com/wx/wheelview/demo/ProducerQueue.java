package com.wx.wheelview.demo;

import java.util.concurrent.BlockingQueue;

/**
 * @author William
 * @date 2019/2/26
 * class introduction:
 */
public class ProducerQueue implements Runnable{

    private BlockingQueue blockingDeque;

    public ProducerQueue(BlockingQueue blockingQueue){
        this.blockingDeque = blockingQueue;
    }

    @Override
    public void run() {
        for (int i=0;i<10;i++){
            //blockingDeque.remove(i);
            try {
                System.out.println("____生产苹果成功:  "+blockingDeque.take());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
