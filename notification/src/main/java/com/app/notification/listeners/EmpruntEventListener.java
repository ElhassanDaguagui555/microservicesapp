package com.app.notification.listeners;

import com.app.notification.events.EmpruntEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmpruntEventListener {

    private static final Logger log = LoggerFactory.getLogger(EmpruntEventListener.class);

    @KafkaListener(topics = "${app.kafka.topic.emprunt-created}", groupId = "notification-service")
    public void onEmpruntCreated(EmpruntEvent event) {
        log.info("[NOTIFICATION] Emprunt created: empruntId={}, userId={}, bookId={}, type={}, ts={}",
                event.getEmpruntId(), event.getUserId(), event.getBookId(), event.getEventType(), event.getTimestamp());
    }
}
