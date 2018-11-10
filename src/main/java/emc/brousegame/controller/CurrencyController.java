/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.Currency;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.service.CurrencyService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author kamal
 */
@RestController
@RequestMapping("/api/v1/")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;
    
    @GetMapping("currency")
    public ResponseEntity<List<Currency>> listCurrency(){
        List<Currency> currencyList =  currencyService.findAll();
        return ResponseEntity.ok(currencyList);
    }
    
    @GetMapping("currency/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable Long id){
        Optional<Currency> currency = currencyService.findById(id);
        if(!currency.isPresent())
            throw new ResourceNotFoundException("id:"+id);
        return ResponseEntity.ok(currency.get());
    }
    
    @PostMapping("currency")
    public ResponseEntity<Object> createCurrency(@RequestBody Currency currency){
        if(currency.getId() !=null)
            return ResponseEntity.badRequest().body("A new Currency cannot already have an ID");
        Currency result = currencyService.save(currency);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(currency.getId()).toUri();
        return ResponseEntity.created(location).body(currency);
    }
    
    @PutMapping("currency")
    public ResponseEntity<Currency> updateCurrency(@RequestBody Currency currency){
        Currency result = currencyService.save(currency);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("currency/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        currencyService.delete(id);
        return ResponseEntity.ok("data has been delete");
    }
}
