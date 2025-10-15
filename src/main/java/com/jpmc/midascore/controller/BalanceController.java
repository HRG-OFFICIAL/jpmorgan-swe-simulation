package com.jpmc.midascore.controller;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for querying user balances.
 * Provides a REST API endpoint to retrieve user account balances.
 */
@RestController
public class BalanceController {
    
    private final DatabaseConduit databaseConduit;
    
    public BalanceController(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }
    
    /**
     * Retrieves the balance for a specific user.
     * 
     * @param userId The ID of the user whose balance to retrieve
     * @return Balance object containing the user's current balance, or 0.0 if user doesn't exist
     */
    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        UserRecord user = databaseConduit.findUserById(userId);
        if (user != null) {
            return new Balance(user.getBalance());
        } else {
            return new Balance(0.0f);
        }
    }
}
