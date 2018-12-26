/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.Transaction;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.payload.Position;
import emc.brousegame.service.TransactionService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Emerio-PC
 */
@RestController
@RequestMapping("/api/v1/")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("transaction")
    public ResponseEntity<?> create(@RequestBody Transaction transaction){
        if(null != transaction.getId()){
            return ResponseEntity.badRequest().body("A new Transaction cannot already have an id");
        }
        Transaction res = transactionService.save(transaction);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(location).body(res);
    }
    
    @GetMapping("transaction/{id}")
    public Transaction getTransaction(@PathVariable Long id){
        return transactionService.findById(id).map(
               transaction -> transaction
        ).orElseThrow(()->new ResourceNotFoundException("transaction not found"));
    }
    
    @GetMapping("transaction/log")
    public List<Position> transactionLog(){
        return transactionService.transacionLog();
    }
    
    @GetMapping("transaction/positions")
    public List<Position> positions(){
        return transactionService.position();
    }
    
    @GetMapping("transaction/position/detail")
    public List<Position> transactionDeatil(@RequestParam String transactionType){
        return transactionService.positionDetail(transactionType);
    }
}
