/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.ConvertationHistory;
import emc.brousegame.repository.ConvertaitonHistoryRepository;
import emc.brousegame.service.ConvertationHistoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emerio-PC
 */
@Service
public class ConvertationHistoryServiceImpl implements ConvertationHistoryService{
    
    @Autowired
    private ConvertaitonHistoryRepository convertaitonHistoryRepository;

    @Override
    public ConvertationHistory save(ConvertationHistory convertationHistory) {
        return convertaitonHistoryRepository.save(convertationHistory);
    }

    @Override
    public Page<ConvertationHistory> list(Pageable pageable) {
        return convertaitonHistoryRepository.findAll(pageable);
    }

    @Override
    public Optional<ConvertationHistory> findByTicketNo(Long ticketNp) {
        return convertaitonHistoryRepository.findByTicketNo(ticketNp);
    }
    
}
