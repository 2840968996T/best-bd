package org.best.bd.controller;

import org.best.bd.util.BaiduApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class DownloadController {
    //疑问：未指写produces的时候 通过浏览器查看返回头是 text 就会导致json Formatter 无法正常工作
    private BaiduApi api;
    public DownloadController(BaiduApi api){
        this.api = api;
    }
    @GetMapping(value = "/getFile",produces = "application/json")
    public String getFile() {
        return api.getFileList("/");
    }
    @GetMapping(value = "/getFileAll",produces = "application/json")
    public String getFileAll() {
        return api.getFileListAll("/",0);
    }
}
