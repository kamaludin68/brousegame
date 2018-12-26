/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.Transaction;
import emc.brousegame.payload.Position;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Emerio-PC
 */
public interface TransactionService {
    public Transaction save(Transaction transaction);
    public List<Position> transacionLog();
    public List<Position> positionDetail(String transactionType);
    public List<Position> position();
    public Optional<Transaction> findById(Long id);
   
}