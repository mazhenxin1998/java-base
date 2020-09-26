package com.mzx.threads.pool;

import com.mysql.jdbc.Connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-25 23:15 周五.
 */
public class ConnectionDriver {

    public static final Connection createConnection() {

        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[]{Connection.class}, new ConnectionHandler());

    }


    static class ConnectionHandler implements InvocationHandler {

        /**
         * 模拟数据库连接的伪代码.
         *
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (SqlConstant.METHOD_NAME.equals(method.getName())) {

                // 如果是提交方法,那么就伪代码休眠.
                TimeUnit.MILLISECONDS.sleep(100);

            }

            return null;

        }

    }


}
