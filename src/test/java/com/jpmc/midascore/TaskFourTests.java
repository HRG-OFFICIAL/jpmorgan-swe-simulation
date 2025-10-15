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
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private DatabaseConduit databaseConduit;

    @Test
    void task_four_verifier() throws InterruptedException {
        userPopulator.populate();
        
        // Wait for Kafka consumer to be ready
        Thread.sleep(10000);
        
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(5000);


        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what wilbur's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");
        
        // Debug: Find wilbur's balance
        UserRecord wilbur = databaseConduit.findUserByName("wilbur");
        if (wilbur != null) {
            logger.info("DEBUG: wilbur's balance is: " + wilbur.getBalance());
            logger.info("DEBUG: wilbur's balance (rounded down): " + (int) Math.floor(wilbur.getBalance()));
        } else {
            logger.info("DEBUG: wilbur not found");
        }
        
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}
