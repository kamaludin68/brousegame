/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.ConvertationHistory;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.service.ConvertationHistoryService;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Emerio-PC
 */
@RestController
@RequestMapping("/api/v1/")
public class ConvertationHistoryController {
    
    @Autowired
    private ConvertationHistoryService convertationHistoryService;
            
    @GetMapping("convertationHistory")
    public Page<ConvertationHistory> list(
        @RequestParam(required = false) Integer page, 
        @RequestParam(required = false) Integer size){
        Pageable paging = Pageable.unpaged();
        if(null!=page && null !=size){
            paging = PageRequest.of(page, size);
        }
        Page<ConvertationHistory> result =  convertationHistoryService.list(paging);
        return result;
    }
    
    @GetMapping("convertationHistory/{ticketNo}")
    public ConvertationHistory getConvertationHistory(@PathVariable Long ticketNo){
        Optional<ConvertationHistory> result = convertationHistoryService.findByTicketNo(ticketNo);
        if(!result.isPresent())
            throw new ResourceNotFoundException("tickeNo : "+ ticketNo);
        return result.get();
    }
    
    @PostMapping("convertationHistory")
    public ResponseEntity<?> create(@RequestBody ConvertationHistory convertationHistory){
        if(null != convertationHistory.getTicketNo()){
            return ResponseEntity.badRequest().body("A new ConvertationHistory cannot already have an ticketNo");
        }
        ConvertationHistory res = convertationHistoryService.save(convertationHistory);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ticketNo}").buildAndExpand(res.getTicketNo()).toUri();
        return ResponseEntity.created(location).body(res);
    }
}
