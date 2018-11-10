/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.AppParams;
import emc.brousegame.repository.AppParamsRepository;
import emc.brousegame.service.AppParamsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Emerio-PC
 */
@Service
@Transactional
public class AppParamsServiceImpl implements AppParamsService{
    @Autowired
    private AppParamsRepository appParamsRepository;
    
    @Override
    public AppParams save(AppParams appParams){
        return appParamsRepository.save(appParams);
    }

    @Override
    public List<AppParams> list() {
        return appParamsRepository.findAll();
    }

    @Override
    public Optional<AppParams> findById(Long id) {
        return appParamsRepository.findById(id);
    }

    @Override
    public Optional<AppParams> findByCode(String code) {
        return appParamsRepository.findByCode(code);
    }

    @Override
    public List<AppParams> findAllByParentCode(String parentCode) {
        return appParamsRepository.findAllByParentCode(parentCode);
    }

    @Override
    public void delete(Long id) {
        appParamsRepository.deleteById(id);
    }
}
