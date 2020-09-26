package com.mzx.threads.join;

/**
 * thread.join() 当前线程等待thread执行结束才会从thread.join()状态返回.
 *
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦?如梦之梦.
 * @create 2020-09-23 09:58 周三.
 */
public class ThreadJoin {


    public static void main(String[] args) {

        Thread student = new Thread(new Student(), "Student");
        Thread teacher = new Thread(new Teacher(student), "Teacher");
        teacher.start();
        student.start();

    }

    static class Student implements Runnable {

        /**
         * 模拟现在距离上课还有10秒,当前线程同学还在操场玩耍,没有进入教室上课.
         * 那么老师线程需要等学生进入教室之后才开始上课.
         */
        @Override
        public void run() {

            try {

                // 玩耍的时间.
                System.out.println(Thread.currentThread().getName() + " 老师,我去操场玩耍了，现在还没有上课了 ");
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName() + "   老师, 我来上课了. ");

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }

    static class Teacher implements Runnable {

        private Thread thread;

        public Teacher(Thread thread) {

            this.thread = thread;

        }

        @Override
        public void run() {

            // 老师上课之前，一定要确保学生来了.
            try {

                // 老师先等待学生到齐.
                // 如果学生不到齐,那么老师线程将的状态将永远是等待状态.
                System.out.println(Thread.currentThread().getName() + "教室还没有人,我先去办公室喝一会茶去.  等待学生中. ");
                // 可以使用join进行超时控制等.
                thread.join(5000);

                System.out.println(Thread.currentThread().getName() + "     同学们,开始上课了.");

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }

}
