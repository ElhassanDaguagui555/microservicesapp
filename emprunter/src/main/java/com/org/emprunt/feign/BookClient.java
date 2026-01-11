package com.org.emprunt.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "http://book-service:8081")
public interface BookClient {

    @GetMapping("/api/books/{id}")
    Object getBook(@PathVariable Long id);
}
