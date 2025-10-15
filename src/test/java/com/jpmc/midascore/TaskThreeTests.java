package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private DatabaseConduit databaseConduit;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        
        // Wait for Kafka consumer to be ready
        Thread.sleep(10000);
        
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(5000);


        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what waldorf's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");
        
        // Debug: Find waldorf's balance
        UserRecord waldorf = databaseConduit.findUserByName("waldorf");
        if (waldorf != null) {
            logger.info("DEBUG: waldorf's balance is: " + waldorf.getBalance());
            logger.info("DEBUG: waldorf's balance (rounded down): " + (int) Math.floor(waldorf.getBalance()));
        } else {
            logger.info("DEBUG: waldorf not found");
        }
        
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}
