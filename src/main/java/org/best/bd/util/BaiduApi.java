package org.best.bd.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
@Component
public class BaiduApi {
    @Value("${key}")
    private String key;
    private String getFileListPath = "https://pan.baidu.com/rest/2.0/xpan/file?";
    private String getFileListAllPath = "https://pan.baidu.com/rest/2.0/xpan/multimedia?";
    private String getFileInfoPath = "https://pan.baidu.com/rest/2.0/xpan/multimedia?";
    private static final RestTemplate restTemplate = new RestTemplateBuilder()
            .requestFactory(NoRedirectSimpleClientHttpRequestFactory.class)
            .setConnectTimeout(Duration.ofMillis(3000))
            .setConnectTimeout(Duration.ofMillis(5000))
            .build();


    /**
     * desc:请求时，直接获取五层目录结构，兼顾体验与百度接口风控
     */
    public String getFileList(String dir){

        String url = getFileListPath + "access_token={0}&method={1}&web={2}&dir={3}";
        Map<String,Object> map = new HashMap<>();
        map.put("0", key);
        map.put("1", "list");
        map.put("2","1");
        map.put("3",dir);
        //第二个参数就是要将返回体反序列化成什么对象
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class,map);
        return response.getBody();
    }
    public String getFileListAll(String dir,int start){

        String url = getFileListAllPath + "access_token={0}&method={1}&recursion={2}&path={3}&start={4}";
        Map<String,Object> map = new HashMap<>();
        map.put("0", key);
        map.put("1", "listall");
        map.put("2","1");
        map.put("3",dir);
        map.put("4",start);
        //第二个参数就是要将返回体反序列化成什么对象
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class,map);
        return response.getBody();
    }
    public String getFileInfo(long[] fs_id){
        String url = getFileInfoPath + "access_token={0}&method={1}&dlink={2}&fsids=[{3}]";
        Map<String,Object> map = new HashMap<>();
        map.put("0", key);
        map.put("1", "filemetas");
        map.put("2","1");
        map.put("3",fs_id);
        //第二个参数就是要将返回体反序列化成什么对象
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class,map);
        System.out.println(response.getBody());
        return response.getBody();
    }
    public String getRealDLink(String link) throws UnsupportedEncodingException {
        String decodedLink = URLDecoder.decode(link, "UTF-8");
        URI uri = UriComponentsBuilder
                .fromUriString(decodedLink)
                .queryParam("access_token", key)
                .build().toUri();
        System.out.println(uri);
        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .header("User-Agent", "pan.baidu.com")
                .build();
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        return response.getHeaders().getLocation().toString();
    }
}
