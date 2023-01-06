/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.remote.fallback;

import io.github.yangxj96.starter.remote.clients.DemoFeignClient;

public class DemoFeignClientFallback implements DemoFeignClient {


    @Override
    public String get() {
        return "熔断";
    }

}
