package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final RestTemplate restTemplate;

    public DatabaseConduit(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.restTemplate = restTemplate;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    @Transactional
    public boolean processTransaction(Transaction transaction) {
        System.out.println("Processing transaction: " + transaction);
        
        // Find sender and recipient
        Optional<UserRecord> senderOpt = userRepository.findById(transaction.getSenderId());
        Optional<UserRecord> recipientOpt = userRepository.findById(transaction.getRecipientId());
        
        System.out.println("Sender found: " + senderOpt.isPresent());
        System.out.println("Recipient found: " + recipientOpt.isPresent());
        
        // Validate that both users exist
        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) {
            System.out.println("Transaction failed: User not found");
            return false;
        }
        
        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();
        
        System.out.println("Sender: " + sender.getName() + " (balance: " + sender.getBalance() + ")");
        System.out.println("Recipient: " + recipient.getName() + " (balance: " + recipient.getBalance() + ")");
        System.out.println("Amount: " + transaction.getAmount());
        
        // Validate that sender has sufficient balance
        if (sender.getBalance() < transaction.getAmount()) {
            System.out.println("Transaction failed: Insufficient balance");
            return false;
        }
        
        // Call incentive API
        Incentive incentive = callIncentiveAPI(transaction);
        System.out.println("Incentive received: " + incentive.getAmount());
        
        // Process the transaction
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentive.getAmount());
        
        System.out.println("New sender balance: " + sender.getBalance());
        System.out.println("New recipient balance: " + recipient.getBalance());
        
        // Save updated user balances
        userRepository.save(sender);
        userRepository.save(recipient);
        
        // Create and save transaction record with incentive
        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount(), incentive.getAmount());
        transactionRecordRepository.save(transactionRecord);
        
        System.out.println("Transaction processed successfully");
        return true;
    }
    
    private Incentive callIncentiveAPI(Transaction transaction) {
        try {
            // Set up headers for JSON request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Create HTTP entity with transaction
            HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);
            
            // Call the incentive API
            Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive", 
                request, 
                Incentive.class
            );
            
            return incentive != null ? incentive : new Incentive(0.0f);
        } catch (Exception e) {
            System.out.println("Error calling incentive API: " + e.getMessage());
            // Return zero incentive if API call fails
            return new Incentive(0.0f);
        }
    }
    
    public UserRecord findUserByName(String name) {
        // Since we don't have a findByName method, we'll need to get all users and find by name
        // This is not efficient but works for the test scenario
        Iterable<UserRecord> allUsers = userRepository.findAll();
        for (UserRecord user : allUsers) {
            if (name.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }
    
    public UserRecord findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
