package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Kafka message listener that processes incoming transaction messages.
 * This component listens to the configured Kafka topic and processes
 * each transaction through the database conduit for validation and storage.
 */
@Component
public class KafkaTransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTransactionListener.class);
    private final DatabaseConduit databaseConduit;

    public KafkaTransactionListener(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    /**
     * Processes incoming transaction messages from Kafka.
     * Each transaction is validated and processed through the database conduit.
     * 
     * @param transaction The transaction message received from Kafka
     */
    @KafkaListener(topics = "${general.kafka-topic}")
    public void onTransaction(@Payload Transaction transaction) {
        logger.info("Received transaction: {}", transaction);
        
        // Process the transaction through the database conduit
        boolean processed = databaseConduit.processTransaction(transaction);
        
        if (processed) {
            logger.info("Transaction processed successfully: {}", transaction);
        } else {
            logger.info("Transaction validation failed, discarded: {}", transaction);
        }
    }
}


