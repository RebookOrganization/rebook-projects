//package com.crawler.utils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GsonUtils {
//
//  private static final Gson gson;
//
//  static {
//    GsonBuilder gsonBuilder = new GsonBuilder();
//    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    gson = gsonBuilder.disableHtmlEscaping().create();
//  }
//
//  public static String toJsonString(Object obj) {
//    return gson.toJson(obj);
//  }
//
//  public static <T> T fromJsonString(String sJson, Class<T> t) {
//    return gson.fromJson(sJson, t);
//  }
//
//  public static <T> T json2Collection(String sJson, Type t) {
//    return gson.fromJson(sJson, t);
//  }
//
//  public static void main(String[] args) {
//    String data = "{\"callbackUrl\":\"http://localhost:9327/miservice/notify\",\"reqData\":\"{\\\"data\\\":\\\"{\\\\\\\"appid\\\\\\\":133,\\\\\\\"zptransid\\\\\\\":180613000000007,\\\\\\\"apptransid\\\\\\\":\\\\\\\"180613micode1528855028\\\\\\\",\\\\\\\"apptime\\\\\\\":1528855276073,\\\\\\\"appuser\\\\\\\":\\\\\\\"Nguyễn Văn A\\\\\\\",\\\\\\\"item\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"amount\\\\\\\":100000,\\\\\\\"embeddata\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"servertime\\\\\\\":1528855318919,\\\\\\\"channel\\\\\\\":36,\\\\\\\"merchantuserid\\\\\\\":\\\\\\\"6yHuLa-2KZfxc6RXxkQqyg\\\\\\\",\\\\\\\"userfeeamount\\\\\\\":0,\\\\\\\"discountamount\\\\\\\":0,\\\\\\\"bankcode\\\\\\\":\\\\\\\"CC\\\\\\\"}\\\",\\\"mac\\\":\\\"aac8a85abf94e3ca3e3bf448d2993c1f3936518626902e656bac26923ea80d22\\\"}\",\"respData\":\"\"}";
//    HashMap json = GsonUtils.fromJsonString(data, HashMap.class);
//    System.out.println(json.get("reqData"));
//  }
//}
