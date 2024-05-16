package com.sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.sdk.modal.User;
import com.sdk.utils.SignUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO  客户端
 * @date 2024/5/5 10:29
 */
public class JunClient {

    private String accessKey;
    private String secretKey;
    public static final String BASE_API="http://localhost:7788";

    public JunClient() {
    }

    public JunClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String   GetNameByGET(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.get(BASE_API+"/api/user/", paramMap);
        return  result;
    }


    public String   GetNameByPost( String name){

        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post(BASE_API+"/api/user/", paramMap);
        return  result;

    }



   public Map<String,String> getHeaderMap(String body){

        //对body进行编码 encode   解决在请求头中的  乱码
       String encodeBody=null;
       try {
           encodeBody= URLEncoder.encode(body,"utf-8");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }

       Map<String, String> HeaderMap = new HashMap<>();
       //秘钥不传递给Header
//       HeaderMap.put("secretKey",secretKey);
       HeaderMap.put("body", encodeBody);
       HeaderMap.put("accessKey",accessKey);

       //使用加密技术
       HeaderMap.put("sign", SignUtils.getSign(body,secretKey));
       HeaderMap.put("nonce", RandomUtil.randomNumbers(4));
       HeaderMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));

       return HeaderMap;
   }

    public String  GetNameByPostAndBody(User user){

        String UserJson = JSONUtil.toJsonStr(user);//参数 进行json封装传递给后端

        HttpResponse response = HttpRequest.post(BASE_API+"/api/user/name")
                .addHeaders(getHeaderMap(UserJson))
                 .body(UserJson)
                .execute();

        return response.body();
    }


}
