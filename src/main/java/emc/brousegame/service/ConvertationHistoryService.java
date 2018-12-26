/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.ConvertationHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Emerio-PC
 */
public interface ConvertationHistoryService {
    public ConvertationHistory save(ConvertationHistory convertationHistory);
    public Page<ConvertationHistory> list(Pageable pageable);
    public Optional<ConvertationHistory> findByTicketNo(Long ticketNo);
    
}
