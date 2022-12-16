package com.nteport.admin.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by syf on 2017/3/10.
 */
public class HttpClientUtil {

    /**
     * httpClient post请求
     * @param url
     * @param paramMap
     * @return
     */
    public static String post(String url, Map<String,String> paramMap ) {
        String context = "";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if(paramMap!=null){
            for (Map.Entry<String, String> m : paramMap.entrySet()) {
                formparams.add(new BasicNameValuePair(m.getKey(), m.getValue()));
            }
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    context= EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return context;
    }

    public static List<String> threadPost(List<String> reqList) {
        List<String> results = new ArrayList<String>();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //设置线程数最大100,如果超过100为请求个数
        cm.setMaxTotal(reqList.size() > 100 ? reqList.size() : 100);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        try {
            HttpClientUtil.PostThread[] postThreads = new HttpClientUtil.PostThread[reqList.size()];
            for (int i = 0; i < reqList.size(); i++) {
                HttpPost post = new HttpPost((String) reqList.get(i));
                postThreads[i] = new HttpClientUtil.PostThread(httpclient, post, i + 1);
            }
            //执行线程
            for (HttpClientUtil.PostThread pt : postThreads) {
                pt.start();
            }
            //设置所有线程执行完毕之后再执行后续代码
            for (HttpClientUtil.PostThread pt : postThreads) {
                pt.join();
            }
            for (int i = 0; i < reqList.size(); i++) {
                results.add(postThreads[i].call());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("多线程post方法异常：" + e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 提取列表页面
     * @param billNo
     * @param page
     * @param first
     * @return
     */
    public List<Map> clientPage(String billNo,int page,boolean first,String domainUrl){
        List<Map> list = new ArrayList<Map>();
        String url = domainUrl+"/public_cntr_cargo.asp";
        Map<String, String> map = new HashMap<String, String>();
        map.put("bill_cntr", "bn");
        map.put("qrystr", billNo);
        map.put("page", page+"");
        String firstPage = HttpClientUtil.post(url, map);
        Pattern p = Pattern.compile("bnctr_list\\(\\'(.+?)\\'\\)");
        Matcher m = p.matcher(firstPage);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(domainUrl+"/public_cntr_cargo_list.asp?cntrno="+m.group(1));
        }
        List<String> detailStringList = HttpClientUtil.threadPost(strs);
        if(detailStringList!=null&&detailStringList.size()>0){
            for(String detailString:detailStringList){
                list.addAll(this.clientDetail(detailString));
            }
        }
        if(first){
            Pattern totalPattern = Pattern.compile("第<font color=#ff0000>1</font>/(.+?)页");
            Matcher totalMatcher = totalPattern.matcher(firstPage);
            while (totalMatcher.find()) {
                String totalPage = totalMatcher.group(1);
                if(totalPage!=null&&!"".equals(totalPage)){
                    int totalPageInt = Integer.parseInt(totalPage.trim());
                    if(totalPageInt>1){
                        for(int i = 2; i<=totalPageInt; i++ ){
                            list.addAll(this.clientPage(billNo,i,false,domainUrl));
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 提取详细页面
     * @param detailPage
     * @return
     */
    public List<Map> clientDetail(String detailPage){
        List<Map> list = new ArrayList<Map>();
        Pattern detailPattern = Pattern.compile("bordercolordark=\\\"#FFFFFF\\\">([\\s\\S]+?)</table>");
        Matcher detailMatcher = detailPattern.matcher(detailPage);
        ArrayList<String> detailStrings = new ArrayList<String>();
        while (detailMatcher.find()) {
            String detailString = detailMatcher.group(1);
            Pattern TdPattern = Pattern.compile(">(.+?)</td>");
            Matcher Tdmatcher = TdPattern.matcher(detailString);
            int i = 1;
            Map detailMap = new HashMap();
            String containerIdString = "";
            String sizeTdString = "";
            String emptyHeavyTdString = "";
            String billNoTdString = "";
            String stateTdString = "";
            while (Tdmatcher.find()) {
                switch (i) {
                    case 2:
                        containerIdString = Tdmatcher.group(1);
                    case 4:
                        sizeTdString = Tdmatcher.group(1);
                    case 8:
                        emptyHeavyTdString = Tdmatcher.group(1);
                    case 12:
                        billNoTdString = Tdmatcher.group(1);
                    case 16:
                        stateTdString = Tdmatcher.group(1);
                }
                i++;
            }
            if (sizeTdString != null && !"".equals(sizeTdString)) {
                if ("20".equals(this.trimEmptyChar(sizeTdString))) {
                    detailMap.put("containerMd", "21");
                    detailMap.put("containerMd_value", "普通标准箱（S）");
                } else if ("40".equals(this.trimEmptyChar(sizeTdString))) {
                    detailMap.put("containerMd", "11");
                    detailMap.put("containerMd_value", "普通2*标准箱（L）");
                }
            }
            detailMap.put("containerId", containerIdString == null ? "" : this.trimEmptyChar(containerIdString));
            detailMap.put("containerSize", sizeTdString == null ? "" : this.trimEmptyChar(sizeTdString));
            detailMap.put("emptyHeavy", emptyHeavyTdString == null ? "" : this.trimEmptyChar(emptyHeavyTdString));
            detailMap.put("billNo", billNoTdString == null ? "" : this.trimEmptyChar(billNoTdString));
            detailMap.put("state", stateTdString == null ? "" : this.trimEmptyChar(stateTdString));
            list.add(detailMap);
        }
        return list;
    }

    public String trimEmptyChar(String s){
        if(s!=null&&!"".equals(s)){
            while (s.startsWith("　")) {//这里判断是不是全角空格
                s = s.substring(1, s.length()).trim();
            }
            while (s.endsWith("　")) {
                s = s.substring(0, s.length() - 1).trim();
            }
        }
        return s;
    }

    /**
     * 实现Callable回调接口
     */
    static class PostThread extends Thread implements Callable<String> {

        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpPost httppost;
        private final int id;
        private String result = null;

        public PostThread(CloseableHttpClient httpClient, HttpPost httppost, int id) throws UnsupportedEncodingException {
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(60000)
                    .setConnectTimeout(60000)
                    .setSocketTimeout(60000)
                    .build();
            httppost.setConfig(requestConfig);
            List<NameValuePair> pairs = null;
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httppost = httppost;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                CloseableHttpResponse response = httpClient.execute(httppost, context);
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        result = EntityUtils.toString(entity);
                    }
                } finally {
                    response.close();
                }
            } catch (Exception e) {
                System.out.println(id + " - error: " + e);
            }
        }

        @Override
        public String call() throws Exception {
            return result;
        }
    }

    public static Map HttpPost(String param, String url) {
        Map resMap = new HashMap();
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).setConnectionRequestTimeout(20000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new StringEntity(param, "UTF-8"));
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                result = jsonString;
                resMap.put("res","success");
                resMap.put("data",jsonString);
            } else {
                resMap.put("res","fail");
                resMap.put("info","请求失败:" + state + ",请求发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                System.out.println("请求失败:" + state + ",请求发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("res","fail");
            resMap.put("info","错误详情:" + e.getMessage());
            System.out.println("请求出错" + ",请求发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println("错误详情:" + e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resMap;
    }
    //测试数据SNKO010161112413
    public static void main(String[] args) {
       /* HttpClientUtil httpClientUtil = new HttpClientUtil();
        List list = httpClientUtil.clientPage("SNKO010161112413",1,true);
        System.out.println("1111");*/
        // String url = "https://api.haohaozhu.com/index.php/Home/Recommend/getSpecialBanner2_0";
        String url= "https://api.haohaozhu.com/index.php/Home/Dynamic/index2_0";
        // String url="https://api.haohaozhu.com/index.php/Home/Search/photos2_0";
        Map<String, String> map = new HashMap<String, String>();
        map.put("hhz_token", "d51d48bbeaa05365f3de4e38550e8fa2");
        map.put("page","500000");
        String firstPage = HttpClientUtil.post(url, map);
        System.out.println(firstPage);

    }
}
