package io.github.yangxj96.server.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Resource
    private ObjectMapper om;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/d1")
    public Mono<String> d1() {
        return Mono.just("Hello World");
    }

    @PreAuthorize("hasRole('ROLE_USER1')")
    @GetMapping("/d2")
    public Mono<JsonNode> d2() throws JsonProcessingException {
        String str = """
                {
                   "code" : 1
                }
                """;
        JsonNode node = om.readTree(str);
        return Mono.just(node);
    }

}
