package com.sdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO  签名工具
 * @date 2024/5/5 17:03
 */
public class SignUtils {

    //生成秘钥
    public static String getSign(String body,String secretKey){


        String key=body+"."+secretKey;

        Digester md5 = new Digester(DigestAlgorithm.MD5);

        String digestHex = md5.digestHex(key);


        System.out.println("加密后的字符串："+digestHex);
        return digestHex;
    }



}
