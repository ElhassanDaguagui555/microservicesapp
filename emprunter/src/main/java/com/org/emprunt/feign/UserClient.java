package com.org.emprunt.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microservice", url = "http://user-service:8082")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    Object getUser(@PathVariable Long id);
}
