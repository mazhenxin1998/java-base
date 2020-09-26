package com.mzx.threads.pipe;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦?如梦之梦.
 * @create 2020-09-23 09:36 周三.
 */
public class PipeWriteReadStream {

    public static void main(String[] args) throws IOException {

        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        // 将输入流和输出流进行绑定, 否则在传输的时候会出现错误.
        outputStream.connect(inputStream);
        new Thread(new Print(inputStream)).start();

        try {

            byte[] cache = new byte[1024];
            int len = -1;
            // 从系统外读取数据是一个阻塞活动.
            while ((len = System.in.read(cache)) != -1) {

                // 每次读入的内容都放在字节数组中.
                // 向绑定的管道进行消息传输.
                System.out.println(Thread.currentThread().getName() + "开始发送消息");
                outputStream.write(cache);

            }

        } catch (Exception e) {

            System.out.println("发送数据的时候出现异常了: " + e.getMessage());

        } finally {

            outputStream.close();

        }


    }

    static class Print implements Runnable {

        private PipedInputStream inputStream;

        public Print(PipedInputStream inputStream) {

            this.inputStream = inputStream;

        }

        @Override
        public void run() {

            int receive = 0;
            try {

                // 1m的读取缓存.
                byte[] cache = new byte[1024];
                int len = -1;
                Thread.sleep(10);
                // 如果这个while循环的条件是false,那么这个里面的内容不是就不会进行读取了吗?
                // 从线程外读取数据是一个阻塞线程的行为.
                while ((len = inputStream.read(cache)) != -1) {

                    // 每次读取的内容都存在在cache缓冲字节数组中.
                    System.out.println(Thread.currentThread().getName() + "接受到了消息: " + new String(cache));

                }

            } catch (Exception e) {

                System.out.println("在使用管道进行线程之间的通信发生了异常: " + e.getMessage());

            }

        }
    }


}
