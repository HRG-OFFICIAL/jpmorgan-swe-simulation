package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserRecord sender;
    
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserRecord recipient;
    
    @Column(nullable = false)
    private Float amount;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column
    private Float incentive;
    
    protected TransactionRecord() {
    }
    
    public TransactionRecord(UserRecord sender, UserRecord recipient, Float amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }
    
    public TransactionRecord(UserRecord sender, UserRecord recipient, Float amount, Float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
        this.timestamp = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public UserRecord getSender() {
        return sender;
    }
    
    public UserRecord getRecipient() {
        return recipient;
    }
    
    public Float getAmount() {
        return amount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public Float getIncentive() {
        return incentive;
    }
    
    public void setIncentive(Float incentive) {
        this.incentive = incentive;
    }
    
    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", sender=" + sender.getName() +
                ", recipient=" + recipient.getName() +
                ", amount=" + amount +
                ", incentive=" + incentive +
                ", timestamp=" + timestamp +
                '}';
    }
}
