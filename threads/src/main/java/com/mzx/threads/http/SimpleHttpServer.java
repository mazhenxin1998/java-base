package com.mzx.threads.http;

import com.mzx.threads.http.annotation.RequestMapping;
import com.mzx.threads.http.annotation.RestController;
import com.mzx.threads.threadpool.DefaultThreadPool;
import com.mzx.threads.threadpool.ThreadPool;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
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
    static List<String> className = new ArrayList<>();

    /**
     * 存放了所有标记@RestControoler注解的实例.
     * 该容器时由上面的类路劲容器实现并且加载.
     */
    static Map<String, Object> IOC = new HashMap<>();

    /**
     * key是@RequestMappint中value的值,如果类上没有添加注解,那么就使用方法上的注解@RequestMappint中的value的值作为key.
     * 如果类上和方法上同时含有注解,那么就是用两者拼接起来的作为一个完整的key.
     */
    static final ConcurrentHashMap<String, Method> handlerMapping = new ConcurrentHashMap<>();

    private String packagePath = "/com/mzx/threads/http/controller/";

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

                // 1. 需要先判断当前请求的方法是否存在. /test/main/t?
                String ps = path;
                // TODO: 2020/9/29 默认第一个就是controller.
                String[] cs = ps.split("/");
                String[] split = path.split("\\?");

                if (handlerMapping.containsKey(split[0])) {

                    // /t?name=马振鑫&age=18
                    Method method = handlerMapping.get(split[0]);
                    RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                    String paramTrue = mapping.param();
                    if (paramTrue == null || paramTrue.equals("")) {

                        // 如果是空字符串.

                    } else {

                        // 现在只处理方法中不带参数的.
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        Object[] args = new Object[parameterTypes.length];
                        int i = 0;
                        String[] names = split[1].split("&");
                        for (Class<?> param : parameterTypes) {

                            String[] ns = names[i].split("=");
                            if (ns[0].equals(paramTrue)) {

                                args[i] = ns[1];

                            }

                            i++;

                        }

                        Object o = IOC.get("/" + cs[1]);
                        // 第一个应该是类对象调用的方法.
                        Object invoke = method.invoke(o, args);
                        out.println(invoke.toString());
                        out.flush();

                    }

                } else {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("code", 500);
                    out.println(map);
                    out.flush();

                }

            } catch (Exception e) {

                // 出现异常了.
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
                e.printStackTrace();

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
        this.doInstance();
        this.urlMapping();


    }

    /**
     * 扫描所有添加了Controller注解.
     */
    private void scanAnnotation() {

        // 应该拿到的是类路径.
        // com.mzx.threads.http.controller
        // D:\Study\Java\IDEACode\java-base\threads\target\classes\com\mzx\threads\http
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String s = path.substring(1, path.length() - 1);
        String annotationPath = s + packagePath;
        this.scan(annotationPath);

    }

    /**
     * 扫描指定包下的全类名.
     *
     * @param path
     */
    private void scan(String path) {

        // 应该是一个目录.
        File file = new File(path);
        String[] list = file.list();
        Arrays.stream(list).forEach(item -> {

            String filePath = path + item;
            File f = new File(filePath);
            if (f.isDirectory()) {

                // 如果是目录. 就递归.
                this.scan(filePath);

            } else {

                // 代码能执行到这里的都是一个.class文件.
                // D:/Study/Java/IDEACode/java-base/threads/target/classes/com/mzx/threads/http/controller/MainController.class
                System.out.println(filePath);
                String[] split = filePath.split("/");
                String classPath = "";
                // TODO: 2020/9/28 固定.
                for (int i = 8; i < split.length; i++) {

                    classPath += split.length - 1 == i ? split[i] : split[i] + ".";

                }

                className.add(classPath.replaceAll(".class", ""));

            }

        });

    }

    /**
     * 根据className集合通过反射将其对应的对象放入到伪IOC容器中.
     */
    private void doInstance() {

        if (this.className != null && this.className.size() > 0) {

            this.className.forEach(item -> {

                try {

                    Class<?> cla;
                    // 通过反射为每一个添加了@RestControoler注解的类生成一个对象放入到IOC容器中.
                    // 这个cla通过反射创建之后是一个cla.
                    cla = Class.forName(item);
                    // 判断当前类上含有什么注解.
                    // 1. @RestController 2. @RequestMapping
                    // 如果当前类上添加了@RestController那么就放入Map容器中.
                    if (cla.isAnnotationPresent(RestController.class)) {

                        try {

                            Object o = cla.newInstance();
                            // @RestController只是一个标识符.
                            // key是类上添加了@RequestMapping中的value的值.
                            // RequestMapping是null.
                            RequestMapping requestMapping = cla.getAnnotation(RequestMapping.class);
                            if (requestMapping != null) {

                                // @RequestMapping 和 @RestController应该是配对的.
                                String key = requestMapping.value();
                                IOC.put(key, o);

                            }

                        } catch (InstantiationException e) {

                            e.printStackTrace();

                        } catch (IllegalAccessException e) {

                            e.printStackTrace();

                        }

                    }

                } catch (ClassNotFoundException e) {

                    System.out.println("加载Bean失败: " + e.getMessage());

                }

            });

        }

    }

    /**
     * 做路径映射匹配.
     */
    private void urlMapping() {

        if (this.IOC != null && this.IOC.size() > 0) {

            // 现在需要往 handlerMapping 增加key---method方法进行映射.
            this.IOC.forEach((key, value) -> {

                // value是一个Object对象: 每一个Object对象都是一个Controller.
                Class<?> cla = value.getClass();
                // 这个分支没有进去.
                if (cla.isAnnotationPresent(RestController.class)) {

                    RequestMapping requestMapping = cla.getAnnotation(RequestMapping.class);
                    String mapping = requestMapping.value();
                    // 获取当前类下所有的方法.
                    Method[] methods = cla.getMethods();
                    Arrays.stream(methods).forEach(method -> {

                        // 现在需要对每一个方法进行处理.
                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                        if (annotation != null) {

                            String v = annotation.value();
                            // 所以说每一个方法最后对应的key都应该是类上的Mapping和方法上的Mapping的总称.
                            String methodKey = key + v;
                            // 这里应该是
                            handlerMapping.put(methodKey, method);

                        }

                    });

                }

            });

        }

    }


    public static void main(String[] args) throws Exception {

        SimpleHttpServer.stPort(9999);
        SimpleHttpServer.start();

    }


}
