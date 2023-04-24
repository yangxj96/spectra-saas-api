package io.github.yangxj96.server.auth.controller;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * demo
 */
@Slf4j
@RestController
@RequestMapping("/Demo")
public class DemoController {

    @GetMapping
    public void get(String k1, String k2, Integer k3) {
        log.info("k1:{},k2:{},k3:{}", k1, k2, k3);
    }

    @DeleteMapping
    public void delete(String id1, String id2, Integer id3) {
        log.info("id1:{},id2:{},id3:{}", id1, id2, id3);
    }

    /**
     * 只处理json
     */
    @PostMapping(path = "/json", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void post1(@RequestBody Json param) {
        log.info("application/json:" + param.toString());
    }

    @PostMapping(path = "/fromdata", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void post2(Json param) {
        log.info("multipart/form-data:" + param);
    }

    @Data
    @ToString
    static class Json {
        private Integer id;
        private String name;
        private Integer age;
    }

}
