package com.wx.wheelview.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author William
 * @date 2019/2/25
 * class introduction:
 */
public class HookTestActivity extends Activity{

    private TextView tvClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hooktest);

        tvClick = findViewById(R.id.tv_click);
        //  hookOnclickListener(tvClick);
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("___","onclick");
            }
        });
      //  hookOnclickListener(tvClick);
       // test1();
      //  doTest3();
        doTest4();
    }


    private void hookOnclickListener(View view){
        try {
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            // 得到view的listenerinfo对象
            Object listenerInfo  = getListenerInfo.invoke(view);
            // 得到原始的ListenerInfo对象
            Class<?> listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClazz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originalOnclickListener = (View.OnClickListener)mOnClickListener.get(listenerInfo);
            HookOnclickListener hookOnclickListener = new HookOnclickListener(originalOnclickListener);

            // 向ListenerInfo对象的mOnClickListener变量设置新值，最终目的（改变运行期变量，拦截原有的该OnclickListener对象的onClick方法，调用新对象的onClick）
            mOnClickListener.set(listenerInfo,hookOnclickListener);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void hookClick(View view) {
        hookOnclickListener(view);
    }

    public class HookOnclickListener implements View.OnClickListener{

        private View.OnClickListener originalOnClickListener;

        HookOnclickListener(View.OnClickListener originalOnClickListener){
            this.originalOnClickListener = originalOnClickListener;
        }
        @Override
        public void onClick(View view) {
            Log.e("___","before click");
            Toast.makeText(HookTestActivity.this,"hook click",Toast.LENGTH_SHORT).show();
            if (originalOnClickListener!=null){
                originalOnClickListener.onClick(view);
            }
            Log.e("___","after click");
        }
    }

    /**
     *  一个int二维数组，从左到右递增，从上到下递增，输入一个数k，判断k是否在这个数组里
     */
    private void test1(){
        int[][] arrays= {   {1,3,4,6,7}
                            ,{2,4,15,17,18}
                            ,{12,48,49,66,75}
                            ,{13,49,52,68,81}
                         };
        int k = 65;
        int m=0;
        int n= arrays[0].length-1;
        boolean containsKey = false;
        while (arrays[0].length>0 && m<arrays.length && n>=0){
            if (arrays[m][n] == k){
                containsKey = true;
                break;
            }else if (arrays[m][n]<k){
                m++;
            }else {
                n--;
            }
        }
        Log.e("___test1:  ","___contains key : "+containsKey);
    }


    /**
     *  java 多线程 生产消费者模型
     */
    private void doTest3(){
/*       wait和notify实现
        PublicBox publicBox = new PublicBox();
        Producer producer = new Producer(publicBox);
        Consumer consumer = new Consumer(publicBox);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();*/
        // blockingquene实现
        BlockingQueue blockingQueue = new LinkedBlockingQueue(5);
        ProducerQueue producerQueue = new ProducerQueue(blockingQueue);
        ConsumerQueue consumerQueue = new ConsumerQueue(blockingQueue);
        Thread producerThread = new Thread(producerQueue);
        Thread consumerThread = new Thread(consumerQueue);
        producerThread.start();
        consumerThread.start();
    }

    /**
     *  给定一个链表的节点，反向打印链表
     */
    private void doTest4(){
        ListNode root = new ListNode();
        root.nodeValue = 13;
        root.next = new ListNode();
        root.next.nodeValue = 14;
        root.next.next = new ListNode();
        root.next.next.nodeValue = 15;
        root.next.next.next = new ListNode();
        root.next.next.next.nodeValue = 16;
      //  printListInverseUseRecursion(root);
        printListInverseUseIterator(root);
    }

    private static class ListNode{
        int nodeValue; // 节点的值
        ListNode next;  // 下一个节点
    }

    /**
     *  用栈的方式的方式
     */
    private void printListInverseUseIterator(ListNode root){
        Stack<ListNode> nodes = new Stack<ListNode>();
        while (root!=null){
            nodes.push(root);
            root = root.next;
        }
        ListNode tempNode;
        while (!nodes.isEmpty()){
            tempNode = nodes.pop();
            System.out.println("____ current node value "+tempNode.nodeValue);
        }
    }

    /**
     *  用递归的方式
     */
    private void printListInverseUseRecursion(ListNode listNode){
        if (listNode!=null){
            System.out.println("____ current node value "+listNode.nodeValue);
            printListInverseUseRecursion(listNode.next);
        }
    }
}
