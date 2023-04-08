package org.best.bd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class BaiduApi {
    @Value("${key}")
    private String key;
    private String getFileListPath = "https://pan.baidu.com/rest/2.0/xpan/file?";
    private String getFileListAllPath = "https://pan.baidu.com/rest/2.0/xpan/multimedia?";

    private RestTemplate restTemplate;

    @Autowired
    public BaiduApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * desc:请求时，直接获取五层目录结构，兼顾体验与百度接口风控
     */
    public String getFileList(String dir) {

        String url = getFileListPath + "access_token={0}&method={1}&web={2}&dir={3}";
        Map<String, Object> map = new HashMap<>();
        map.put("0", key);
        map.put("1", "list");
        map.put("2", "1");
        map.put("3", dir);
        //第二个参数就是要将返回体反序列化成什么对象
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, map);
        return response.getBody();
    }

    public String getFileListAll(String dir, int start) {

        String url = getFileListAllPath + "access_token={0}&method={1}&recursion={2}&path={3}&start={4}";
        Map<String, Object> map = new HashMap<>();
        map.put("0", key);
        map.put("1", "listall");
        map.put("2", "1");
        map.put("3", dir);
        map.put("4", start);
        //第二个参数就是要将返回体反序列化成什么对象
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, map);
        return response.getBody();
    }
}
