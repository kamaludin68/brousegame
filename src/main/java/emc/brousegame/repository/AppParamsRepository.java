/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.repository;

import emc.brousegame.domain.AppParams;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Emerio-PC
 */
public interface AppParamsRepository extends JpaRepository<AppParams, Long> {
    public Optional<AppParams> findByCode(String code);
    public List<AppParams> findAllByParentCode(String parentCode);
}
