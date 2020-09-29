package com.mzx.basemodel;

/**
 * 重载和重写.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-29 20:51 周二.
 */
public class Overload {

    public static void main(String[] args) {

        // TODO: 2020/9/29 重载和重写实现未完成.
        // 看看sun最终会输出什么.
        Sun sun = new Sun();

    }

}

class Father {

    public Father() {

        // 这个this是对重写的考验.
        m2();

    }

    public void m1() {

        System.out.println("父类中的方法1.");

    }

    public void m2() {

        System.out.println("父类中的方法2.");

    }

}

/**
 * 子类只重写方法一.
 */
class Sun extends Father {

    public Sun() {

        // 先调用父类的构造函数.
        m1();

    }

    @Override
    public void m1() {

        System.out.println("这是子类中的方法一.");

    }

    @Override
    public void m2() {

        System.out.println("这是子类中的方法二.");

    }
}



