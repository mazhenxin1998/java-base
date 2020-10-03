package com.mzx.threads.lock;

import sun.misc.Unsafe;

/**
 * 简单版的AQS.
 * <p>
 * 自己实现的一把锁，不是通过继承AQS实现的.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-04 00:18 周日.
 */
public class Mutex {

    public void lock() {

    }

    public void unlock() {

    }

    /**
     * Sync内部实现了对锁的获取和释放的所有操作.
     */
    static class Sync {

        /**
         * 线程同步标志位.
         * 0表示锁没有被占用.
         */
        static volatile int state = 0;

        /**
         * 这个值不能表示成final.
         */
        static Long stateOffset;

        /**
         * 表示当前锁是被哪个线程获取.
         */
        private Thread lockOwnerThread;

        /**
         * 使用Unsafe提供的CAS方法进行原子操作.
         */
        static final Unsafe UNSAFE = Unsafe.getUnsafe();

        static {

            try {

                stateOffset = UNSAFE.objectFieldOffset(Sync.class.getDeclaredField("state"));

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        public void lock() {

            // 说明下这里为什么需要进行取反: 如果当前线程能正常获取到锁，那么返回的是true.
            // 但是当前线程已经获取到了锁，也就是说其已经没有必要在接着往下执行力.
            // 方法退出即可.
            // 而要方法退出，那么if判断就应该结束.
            // 而if判断结束的条件就是遇见false. 但是tryLock加锁成功返回的是true，所以需要进行取反操作.
            if (!tryLock()) {

            }

        }

        public boolean tryLock() {

            // 尝试获取锁.
            int c = this.getState();
            Thread current = Thread.currentThread();
            if (c == 0) {

                // 表示当前锁的状态是没有被获取的.
                // 尝试利用CAS进行对锁的状态进行更改.
                // 这个CAS操作可能会失败.
                if (this.compareAndSwapState(0, 1)) {

                    // CAS修改成功.
                    return true;

                }

            } else if (current == this.getLockOwnerThread()) {

                // 可重入锁.
                return true;

            }

            return false;

        }

        /**
         * 当前线程释放锁.
         */
        public void release() {


        }

        public int getState() {

            return state;

        }

        public void setLockOwnerThread(Thread thread) {

            this.lockOwnerThread = thread;

        }

        public Thread getLockOwnerThread() {

            return this.lockOwnerThread;

        }

        /**
         * 第一个参数表示修改的是哪个对象里面的那个属性.
         * 第二个参数表示修改的是第一个参数表示的对象的偏移.
         * 第三个参数表示要修改的属性的期望的值. 如果在比较的过程期望的值和属性现在的值相等才进行下一步的修改操作.
         * 第四个参数表示要修改之后的值.
         *
         * @return 返回CAS操作状态.
         */
        public boolean compareAndSwapState(int expect, int update) {

            return UNSAFE.compareAndSwapInt(this, stateOffset, expect, update);

        }

    }


}
