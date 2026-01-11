package com.org.emprunt.service;


import com.org.emprunt.entities.Emprunter;
import com.org.emprunt.events.EmpruntEvent;
import com.org.emprunt.feign.BookClient;
import com.org.emprunt.feign.UserClient;
import com.org.emprunt.repositories.EmpruntRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmpruntService {

    private final EmpruntRepository repo;
    private final UserClient userClient;
    private final BookClient bookClient;
    private final KafkaTemplate<String, EmpruntEvent> kafkaTemplate;

    @Value("${app.kafka.topic.emprunt-created}")
    private String empruntCreatedTopic;

    public EmpruntService(EmpruntRepository repo,
                          UserClient userClient,
                          BookClient bookClient,
                          KafkaTemplate<String, EmpruntEvent> kafkaTemplate) {
        this.repo = repo;
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Emprunter createEmprunt(Long userId, Long bookId) {

        // 1. Vérifier user existe
        userClient.getUser(userId);

        // 2. Vérifier book existe
        bookClient.getBook(bookId);

        // 3. Créer l’emprunt
        Emprunter b = new Emprunter();
        b.setUserId(userId);
        b.setBookId(bookId);

        Emprunter saved = repo.save(b);

        EmpruntEvent event = new EmpruntEvent(
            saved.getId(),
            userId,
            bookId,
            "EMPRUNT_CREATED",
            Instant.now()
        );

        kafkaTemplate.send(empruntCreatedTopic, String.valueOf(saved.getId()), event);

        return saved;
    }
}


