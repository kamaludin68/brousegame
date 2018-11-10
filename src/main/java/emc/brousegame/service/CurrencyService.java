/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.Currency;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kamal
 */
public interface CurrencyService {
    public Currency save(Currency currency);
    public List<Currency> findAll();
    public Optional<Currency> findById(Long id);
    public void delete(Long id);
}
