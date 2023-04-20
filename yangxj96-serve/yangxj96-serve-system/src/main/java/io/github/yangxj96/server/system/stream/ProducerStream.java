/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-20 15:17:41
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.server.system.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 生产者
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 15:17
 */
@Slf4j
@Component
public class ProducerStream {

    @Resource
    private ObjectMapper om;

//    @Bean
//    public Supplier<StreamModel> tenantCreate() {
//        return () -> {
//            try {
//                var model = new StreamModel();
//                model.setType("new");
//                TenantNew obj = new TenantNew();
//                obj.setId(100000001L);
//                obj.setName("测试发送");
//                model.setMessage(om.writeValueAsString(obj));
//                return model;
//            } catch (JsonProcessingException e) {
//                log.error("格式化错误", e);
//                throw new RuntimeException(e);
//            }
//        };
//    }

}
