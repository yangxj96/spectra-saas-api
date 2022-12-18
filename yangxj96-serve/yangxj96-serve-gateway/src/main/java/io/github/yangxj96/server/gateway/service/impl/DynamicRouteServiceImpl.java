package io.github.yangxj96.server.gateway.service.impl;

import jakarta.annotation.Resource;
import org.apache.ibatis.javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource
    private DBRouteServiceImpl routeService;


    @Override
    public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public int add(RouteDefinition definition) {
        routeService.save(Mono.just(definition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
        return 1;
    }

    public int update(RouteDefinition definition) {
        routeService.delete(Mono.just(definition.getId()));
        return add(definition);
    }

    public Mono<ResponseEntity<Object>> add(String id) {
        return routeService
                .delete(Mono.just(id))
                .then(
                        Mono.defer(() ->
                                Mono.just(ResponseEntity.ok().build())
                                        .onErrorResume(
                                                NotFoundException.class::isInstance,
                                                t -> Mono.just(ResponseEntity.notFound().build())
                                        )
                        )
                );
    }
}
