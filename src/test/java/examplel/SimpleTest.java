package examplel;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SimpleTest {

    @BeforeClass
    public void setUp() {
        // code that will be invoked when this test is instantiated
    }

    //每次运行前打印当前时间
    @BeforeMethod
    public void getTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("当前时间是："+formatter.format(date));
    }

    @Test(groups = {"fast"})
    public void aFastTest() {
        System.out.println("Fast test");
        Assert.assertEquals("1","2");
    }

    @Test(groups = {"slow"})
    public void aSlowTest() {
        System.out.println("Slow test");
    }

    //参数化，在testing.xml中配置
    @Parameters("url")
    @Test
    public void test1(String url) throws IOException {
        // 准备url
        //String url=url;
        //请求方法
        HttpGet get = new HttpGet(url);
        //请求头
        String tokenV = null;
        //get.setHeader("Authorization",tokenV);
        //准备一个发送请求的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 发送请求得到响应报文-java中封装为对象
        CloseableHttpResponse response = httpClient.execute(get);
        System.out.println(response);
    }

    //数据驱动
    @DataProvider(name="provider")
    public Object[][] provider(){
        Object [][] provider = new Object [5][2];
        for (int i = 0; i < provider.length; i++) {
            provider[i][0] = (int)(Math.random()*100) + 1;//随机数生成
            provider[i][1] = (int)(Math.random()*100) + 1;
        }
        return provider;
    }
    @Test(dataProvider="provider")
    public void test2(int name,int age){
        System.out.println(name+"_"+age);

    }

    //json操作
    @Test
    public void test4() throws IOException{
        JSONObject jsonResult = null;
        String Url = "https://jisutqybmf.market.alicloudapi.com/weather/query?city=丽江&citycode=citycode&cityid=cityid&ip=ip&location=location";
        HttpGet get = new HttpGet(Url);
        get.setHeader("Authorization","appcode 8d54e478bbcc4364ab46a2a0a9f1b8de");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        System.out.println("响应状态为:" + response.getStatusLine());
        HttpEntity responseEntity = response.getEntity();
        String str = EntityUtils.toString(responseEntity);
        jsonResult = JSONObject.parseObject(str);
        System.out.println(jsonResult.get("result"));

        if (responseEntity != null) {
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + str);
        }
    }

    //天气预报查询
    @Test
    public void test3() {
        String host = "https://jisutqybmf.market.alicloudapi.com";
        String path = "/weather/query";
        String method = "GET";//GET/POST 任意
        String appcode = "8d54e478bbcc4364ab46a2a0a9f1b8de";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", "安顺");
        querys.put("citycode", "citycode");
        querys.put("cityid", "cityid");
        querys.put("ip", "ip");
        querys.put("location", "location");


        /**try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */

            /**System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
