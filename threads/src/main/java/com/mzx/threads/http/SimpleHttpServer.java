package com.mzx.threads.http;

import com.mzx.threads.threadpool.DefaultThreadPool;
import com.mzx.threads.threadpool.ThreadPool;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的Http服务器.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-27 22:48 周日.
 */
public class SimpleHttpServer {

    /**
     * 开启一个线程池用来处理所有请求.
     */
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(10);

    static String basePath = "";
    static ServerSocket serverSocket;
    static int port = 8080;
    static volatile boolean serverRunning = true;

    /**
     * 存放了所有标记有@RestController注解的全路径.
     */
    private List<String> className = new ArrayList<>();

    /**
     * 存放了所有标记@RestControoler注解的实例.
     * 该容器时由上面的类路劲容器实现并且加载.
     */
    private Map<String, Object> IOC = new HashMap<>();

    /**
     * key是@RequestMappint中value的值,如果类上没有添加注解,那么就使用方法上的注解@RequestMappint中的value的值作为key.
     * 如果类上和方法上同时含有注解,那么就是用两者拼接起来的作为一个完整的key.
     */
    private final ConcurrentHashMap<String, Method> handlerMapping = new ConcurrentHashMap<>();

    public static void stPort(int port) {

        if (port > 1024 && port < 65535) {

            SimpleHttpServer.port = port;

        }

    }

    public static void setBasePath(String basePath) {

        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {

            SimpleHttpServer.basePath = basePath;

        }

    }

    /**
     * 以后这里用NIO进行改造下.
     *
     * @throws Exception
     */
    public static void start() throws Exception {

        serverSocket = new ServerSocket(port);
        Socket socket = null;
        // 对socket进行判断.
        System.out.println("自定义简单的Tomcat服务启动成功,监听端口: " + port);
        // TODO: 2020/9/28 能在static静态代码块内初始化一个该对象的实例吗?应该是可以的.
        SimpleHttpServer server = new SimpleHttpServer();
        // 将@RestControoler注解和@RequestMapping注解进行解析.
        server.init();
        while (serverRunning) {

            socket = serverSocket.accept();
            // 接受一个客户端Socket,生成一个HttpRequestHandler放入线程池中.
            threadPool.execute(new HttpRequestHandler(socket));

        }

        // 当服务进行关闭的时候,进行关闭退出.
        serverSocket.close();

    }

    /**
     * 关闭当前服务.
     */
    public static void close() {

        SimpleHttpServer.serverRunning = false;

    }


    static class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {

            this.socket = socket;

        }

        /**
         * Http服务器接受请求之后进行处理.
         */
        @Override
        public void run() {

            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {

                // TODO: 2020/9/27 浏览器发送的请求到底是什么格式?
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // 读取请求头?
                String header = reader.readLine();
                // 根路径下的路径.
                // 那么base就没有用?
                String path = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                // 如果读取资源是文本,那么就输出.
                // 打开用户请求路径所表示的文件.
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Molly");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println("");
                if ("/test".equals(path)) {

                    String test = this.test();
                    out.println(test);
                    // 每次写完之后都需要进行flush.
                    out.flush();

                }

            } catch (Exception e) {

                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();

            } finally {

                // 请求完成,关闭资源.
                socketClose(socket, br, out, reader);

            }

        }

        private String test() {

            return "Hello Word private String test()";

        }


    }

    private static void socketClose(Closeable... closeables) {

        if (closeables != null) {

            for (Closeable closeable : closeables) {

                try {

                    closeable.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }

    /**
     * 初始化添加了注解@RestController和@RequestMapping的类的对象.
     */
    public void init() {

        this.scanAnnotation();

    }

    /**
     *
     */
    private void scanAnnotation() {

        // 应该拿到的是类路径.
        // com.mzx.threads.http.controller
        // D:\Study\Java\IDEACode\java-base\threads\target\classes\com\mzx\threads\http
        // TODO: 2020/9/28 尽量最早完成.
        String path = this.getClass().getClassLoader().getResource("/com/mzx").getPath();
        System.out.println(path);

    }


    public static void main(String[] args) throws Exception {

        SimpleHttpServer.stPort(9999);
        SimpleHttpServer.start();

    }


}
