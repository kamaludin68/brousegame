/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.repository;

import emc.brousegame.domain.Currency;
import emc.brousegame.domain.Rates;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Emerio-PC
 */
public interface RateRepository extends JpaRepository<Rates, Long>{
    public List<Rates> findAllByTimeRate(Integer timeRate);
    public List<Rates> findAllByCcyTo(Currency currency);
}
