package io.github.yangxj96.starter.remote.fallback;

import io.github.yangxj96.starter.remote.clients.SystemFeignClient;

public class SystemFeignClientFallback implements SystemFeignClient {

    @Override
    public String get() {
        return "熔断1";
    }

    @Override
    public String get2() {
        return "熔断2";
    }
}
