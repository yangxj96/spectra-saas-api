package io.github.yangxj96.starter.remote.fallback;

import io.github.yangxj96.starter.remote.clients.DemoFeignClient;

public class DemoFeignClientFallback implements DemoFeignClient {


    @Override
    public String get() {
        return "熔断";
    }

}
