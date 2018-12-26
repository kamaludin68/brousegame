/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.repository;

import emc.brousegame.domain.Transaction;
import emc.brousegame.payload.Position;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Emerio-PC
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(" SELECT"
      + "    new emc.brousegame.payload.Position(t.currency, t.transactionType, t.rate, SUM(t.amount))"
      + "  FROM"
      + "    Transaction t"
      + "  GROUP BY t.currency, t.transactionType, t.rate")
    public List<Position> transactionLog();
    
    @Query(" SELECT"
      + "    new emc.brousegame.payload.Position(t.currency, '', t.rate, SUM(t.amount))"
      + "  FROM"
      + "    Transaction t"
      + "  GROUP BY t.currency, t.rate")
    public List<Position> position();
    
    @Query(" SELECT"
      + "    new emc.brousegame.payload.Position(t.currency, t.transactionType, t.rate, SUM(t.amount))"
      + "  FROM"
      + "    Transaction t"
      + "  WHERE t.transactionType IS :transactionType"
      + "  GROUP BY t.currency, t.rate, t.transactionType")
    public List<Position> positionDetail(@Param("transactionType") String transactionType);
}
