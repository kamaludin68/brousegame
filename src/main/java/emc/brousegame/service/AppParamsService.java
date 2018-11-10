/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.AppParams;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Emerio-PC
 */
public interface AppParamsService {
    public AppParams save(AppParams appParams);
    public List<AppParams> list();
    public Optional<AppParams> findById(Long id);
    public Optional<AppParams> findByCode(String code);
    public List<AppParams> findAllByParentCode(String parentCode);
    public void delete(Long id);
}
