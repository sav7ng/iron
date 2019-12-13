package run.aquan.iron.controller;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.core.Result;
import run.aquan.iron.core.ResultGenerator;
import run.aquan.iron.model.params.WebhookParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @Class MlxyTestController
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/12 18:08
 * @Version 1.0
 **/
@Slf4j
@RequestMapping("webhook")
@Api(value = "马来西亚支付接口回调", tags = {"马来西亚支付接口回调"})
@RestController
public class MlxyTestController {

    // TODO: 2019/12/13 S-caHZmB_KjGJRLsgJ4cHjCA
    @PostMapping("webhook")
    @ApiOperation("回调接口")
    public Result webhook(WebhookParam webhookParam) {
        log.warn("________________________" + LocalDateTime.now().toString() + "________________________");
        Boolean check = check(webhookParam);
        if (check) {
            log.warn("数据一致");
            log.warn(webhookParam.toString());
        } else {
            log.error("数据给修改过");
        }
        // String id = request.getParameter("id");
        // String collection_id = request.getParameter("collection_id");
        // String paid = request.getParameter("paid");
        // String state = request.getParameter("state");
        // String amount = request.getParameter("amount");
        // String paid_amount = request.getParameter("paid_amount");
        // String due_at = request.getParameter("due_at");
        // String email = request.getParameter("email");
        // String mobile = request.getParameter("mobile");
        // String name = request.getParameter("name");
        // String url = request.getParameter("url");
        // String paid_at = request.getParameter("paid_at");
        // String x_signature = request.getParameter("x_signature");
        // log.info("id：" + id);
        // log.info("collection_id：" + collection_id);
        // if (paid.equals("false")) {
        //     log.error("paid：" + paid);
        // } else {
        //     log.warn("paid：" + paid);
        // }
        // log.info("state：" + state);
        // log.info("amount：" + amount);
        // log.info("paid_amount：" + paid_amount);
        // log.info("due_at：" + due_at);
        // log.info("email：" + email);
        // log.info("mobile：" + mobile);
        // log.info("name：" + name);
        // log.info("url：" + url);
        // log.info("paid_at：" + paid_at);
        // log.info("x_signature：" + x_signature);
        log.warn("________________________________________________________________________");
        return null;

    }


    @GetMapping
    @ApiOperation("创建订单")
    public Result createBill() {

        HttpClient httpclient = HttpClientBuilder.create().build();

        Base64.Encoder encoder = Base64.getEncoder();
        String encoding = encoder.encodeToString(("cdc4f58c-1a46-433c-ac9f-eb2de906c171:").getBytes());//fc821d48-5f13-4929-97c2-31c57fd33f4f:

        // HttpPost httppost = new HttpPost("https://www.billplz.com/api/v3/bills");
        HttpPost httppost = new HttpPost("https://www.billplz-sandbox.com/api/v3/bills");
        httppost.setHeader("Authorization", "Basic " + encoding);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(getData()));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));

            String line = null;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                result.append(line);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            log.error(ex.getMessage());
        }
        JSONObject jsonObject = new JSONObject(result);
        String url = jsonObject.getStr("url");
        return ResultGenerator.genSuccessResult(url);

    }

    public static List<NameValuePair> getData() {
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("collection_id", "v3qcsqjm"));
        urlParameters.add(new BasicNameValuePair("description", "Test callback"));
        // urlParameters.add(new BasicNameValuePair("email", "853029827@qq.com"));
        urlParameters.add(new BasicNameValuePair("mobile", "60123079699"));
        urlParameters.add(new BasicNameValuePair("name", "Michael API V3"));
        urlParameters.add(new BasicNameValuePair("amount", "100"));
        urlParameters.add(new BasicNameValuePair("callback_url", "http://8kv7kc.natappfree.cc/meow/webhook/webhook"));
        urlParameters.add(new BasicNameValuePair("redirect_url", "https://www.baidu.com/"));
        return urlParameters;
    }

    public static Boolean check(WebhookParam webhookParam) {
        String data = webhookParam.toString();
        String key = "S-caHZmB_KjGJRLsgJ4cHjCA";
        try {
            String secret = HMACSHA256(data, key);
            log.warn("secret：" + secret);
            log.warn("X_signature：" + webhookParam.getX_signature());
            if (secret.equals(webhookParam.getX_signature())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return true;
        }
    }

    public static String HMACSHA256(String data, String key) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();

    }

}
