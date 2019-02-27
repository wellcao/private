package com.wx.wheelview.demo;

/**
 * @author William
 * @date 2019/2/26
 * class introduction: 多线程  生产消费者模型
 */
public class PublicBox {
    private int apple = 0;


    //  wait和notify实现
    public synchronized void increase(){
        while (apple == 5){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apple++;
        System.out.println("____生产苹果成功");
        notify();
    }


    //  wait和notify实现
    public synchronized void decrease(){
        while (apple == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apple--;
        System.out.println("____消费苹果成功");
        notify();
    }
}
