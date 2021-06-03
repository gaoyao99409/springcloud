package com.springcloud.test.util;

import java.io.FileWriter;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Maps;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ClassName JsoupUtil
 * @Description JsoupUtil
 * @Author gaoyao
 * @Date 2021/6/3 11:16 AM
 * @Version 1.0
 */
public class JsoupUtil {

    private static String url = "https://hezuo.btime.com/question/getquestion";

    public static void main(String[] args) throws Exception {
        FileWriter fileWriter = new FileWriter("/Users/yaogao/morefun/a.txt");
        Map<String, Object> params = Maps.newHashMap();
        int total = 1;
        for(int page=1; page<=50; page++){
            params.put("page", page);
            String json = HttpClientUtil.httpGetRequest(url, params);
            JSONObject object = JSONObject.parseObject(json);
            JSONArray ja = object.getJSONObject("data").getJSONArray("data");
            for (int i=0; i<ja.size(); i++) {
                String title = ja.getJSONObject(i).getString("title");
                JSONArray answerArr = ja.getJSONObject(i).getJSONArray("answer");
                Map<String, String> answerMap = Maps.newHashMap();
                for (int ai=0; ai<answerArr.size();ai++) {
                    String anStr = answerArr.getString(ai);
                    answerMap.put(anStr.substring(0,1), anStr.substring(2));
                }

                String correct = ja.getJSONObject(i).getString("correct");
                System.out.println(total +"."+title);
                fileWriter.write(total +"."+title+"\n");
                total++;
                if (correct.contains(",")) {
                    String[] arr = correct.split(",");
                    for (String s : arr) {
                        System.out.println(answerMap.get(s));
                        fileWriter.write(answerMap.get(s)+"\n");
                    }
                } else {
                    System.out.println(answerMap.get(correct));
                    fileWriter.write(answerMap.get(correct)+"\n");
                }

                fileWriter.flush();
            }

        }
        fileWriter.flush();
    }

}
