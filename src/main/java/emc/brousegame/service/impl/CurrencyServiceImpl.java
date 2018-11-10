/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.Currency;
import emc.brousegame.repository.CurrencyRepository;
import emc.brousegame.service.CurrencyService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kamal
 */
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService{
    @Autowired
    private CurrencyRepository currencyRepository;
    
    @Override
    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        currencyRepository.deleteById(id);
    }
    
}
