package com.org.emprunt.controller;

import org.springframework.web.bind.annotation.*;


import com.org.emprunt.entities.Emprunter;
import com.org.emprunt.service.EmpruntService;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService service;

    public EmpruntController(EmpruntService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/{bookId}")
    public Emprunter emprunterBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {

        return service.createEmprunt(userId, bookId);
    }
}
