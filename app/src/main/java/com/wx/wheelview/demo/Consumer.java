package com.wx.wheelview.demo;

/**
 * @author William
 * @date 2019/2/26
 * class introduction:
 */
public class Consumer implements Runnable{

    private PublicBox box;

    public Consumer(PublicBox box){
        this.box = box;
    }

    @Override
    public void run() {
        for (int i=0; i<10;i++){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            box.decrease();
        }
    }
}
