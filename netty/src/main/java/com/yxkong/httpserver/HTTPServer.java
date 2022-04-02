package com.yxkong.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.yxkong.agent.NamedThreadFactory;
import com.yxkong.common.entity.dto.ResultBean;
import com.yxkong.common.util.JsonUtil;
import com.yxkong.httpserver.collector.CollectorRegistry;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 自定义httpserver
 * @Author: yxkong
 * @Date: 2021/8/18 3:19 下午
 * @version: 1.0
 */
@Slf4j
public class HTTPServer {

    static class  HTTPHandler implements HttpHandler{
        private  CollectorRegistry registry;
        private final static String HEALTHY_RESPONSE = "is Healthy";

        HTTPHandler(CollectorRegistry registry) {
            this.registry = registry;
        }


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String path = httpExchange.getRequestURI().getPath();
            String query = httpExchange.getRequestURI().getRawQuery();
            final Map<String, String> paramMap = parseQuery(query);
            try (OutputStream os = httpExchange.getResponseBody();){
                ResultBean resultBean = new ResultBean.Builder().success("healthy").build();
                if ("/healthy".equals(path)) {
                    os.write(HEALTHY_RESPONSE.getBytes());
                } else {
                    //默认直接以json响应
                    httpExchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
                    if(path.contains("/")){
                        path = path.substring(1);
                    }
                    try {
                        resultBean = registry.filteredCollector(path,paramMap);
                    } catch (Exception e) {
                        log.error("%s 接口异常",path,e);
                        resultBean = new ResultBean.Builder().fail(e.getMessage()).build();

                    }
                }
                String data = JsonUtil.toJson(resultBean);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, data.getBytes().length);
                os.write(data.getBytes());
            }
            httpExchange.close();
        }
        /**
         * 过滤查询条件，暂时用不到
         * @param query
         * @return
         * @throws IOException
         */
        protected static Map<String,String> parseQuery(String query) throws IOException {
            Map<String,String> map = new HashMap<>();
            if (query != null) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] arrays = pair.split("=");
                    map.put(arrays[0],arrays[1]);
                }
            }
            return map;
        }
    }
    protected HttpServer server;
    protected ExecutorService executorService;
    /**
     * 启动一个httpserver服务，并设置
     */
    public HTTPServer(HttpServer httpServer, CollectorRegistry registry, boolean daemon) throws IOException {
        if (httpServer.getAddress() == null) {
            throw new IllegalArgumentException("HttpServer hasn't been bound to an address");
        }

        server = httpServer;
        HttpHandler mHandler = new HTTPHandler(registry);
        server.createContext("/", mHandler);
        server.createContext("/metrics", mHandler);
        executorService = new ThreadPoolExecutor(5,10,1000, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        server.setExecutor(executorService);
        start(daemon);
    }

}