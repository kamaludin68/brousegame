/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.ConvertationHistory;
import emc.brousegame.domain.Transaction;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.payload.Position;
import emc.brousegame.repository.ConvertaitonHistoryRepository;
import emc.brousegame.repository.TransactionRepository;
import emc.brousegame.service.TransactionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emerio-PC
 */
@Service
public class TrasactionServiceImpl implements TransactionService{
    
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ConvertaitonHistoryRepository convertaitonHistoryRepository;

    @Override
    public Transaction save(Transaction transaction) {
        if(transaction.getTransactionType().equalsIgnoreCase("BUY")){
            transaction.setAmount(- transaction.getAmount());
            transaction.setCountraAmount(- transaction.getCountraAmount());
        }
        Optional<ConvertationHistory> convertationHistory = convertaitonHistoryRepository.findByTicketNo(transaction.getTicketNo());
        if(!convertationHistory.isPresent())
            throw new ResourceNotFoundException("ticketNo not found in convertation history");
        ConvertationHistory ch = convertationHistory.get();
        ch.setTransactional(Boolean.FALSE);
        convertaitonHistoryRepository.save(ch);
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Position> positionDetail(String transactionType) {
        return transactionRepository.positionDetail(transactionType);
    }

    @Override
    public List<Position> position() {
        return transactionRepository.position();
    }

    @Override
    public List<Position> transacionLog() {
        return transactionRepository.transactionLog();
    }

    
}
