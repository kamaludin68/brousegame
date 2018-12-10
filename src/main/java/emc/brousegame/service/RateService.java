/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.Currency;
import emc.brousegame.domain.Rates;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kamal
 */
public interface RateService {
    public List<Rates> findAll();
    public Rates save(Rates rates);
    public Optional<Rates> findById(Long id);
    public void delete(Long id);
    public List<Rates> findByTimeRate(Integer timeRate);
    public void upload(MultipartFile file) throws Exception;
    public List<Rates> findByCcyTo(String code);
}
