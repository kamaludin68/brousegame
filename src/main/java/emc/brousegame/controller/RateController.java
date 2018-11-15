/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.Rates;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.service.RateService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author kamal
 */

@RestController
@RequestMapping("/api/v1/")
public class RateController {
    @Autowired
    RateService rateService;
    
    @GetMapping("rates")
    public List<Rates> listRate(){
        return rateService.findAll();
    }
    
    @GetMapping("rates/findBytime")
    public List<Rates> findBytimeRate(@RequestParam Integer timeRate){
        return rateService.findByTimeRate(timeRate);
    }
    
    @GetMapping("rates/{id}")
    public Rates getRate(@PathVariable Long id){
        Optional<Rates> rate = rateService.findById(id);
        if(!rate.isPresent())
            throw new ResourceNotFoundException("id:"+id);
        return rate.get();
    }
    
    @GetMapping("rates/uploadTemplate")
    public ResponseEntity<InputStreamResource> getUploadTemplate(){
        try {
            InputStreamResource resource = new InputStreamResource(new ClassPathResource("templates/RATE_TEMPLATE.xls").getInputStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=RATE_TEMPLATE.xls")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("rates")
    public ResponseEntity<Object> createRate(@RequestBody Rates rates){
        if(rates.getId() !=null)
            return ResponseEntity.badRequest().body("A new Rates cannot already have an ID");
        Rates rate = rateService.save(rates);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rate.getId()).toUri();
        return ResponseEntity.created(location).body(rate);
    }
    
    @PutMapping("rates")
    public ResponseEntity<Rates> updateRate(@RequestBody Rates rates){
        Rates rate = rateService.save(rates);
        return ResponseEntity.ok(rate);
    }
    
    @DeleteMapping("rates/{id}")
    public ResponseEntity<String> deleteRate(@PathVariable Long id){
        rateService.delete(id);
        return ResponseEntity.ok("data has been delete");
    }
    
    @PostMapping("rates/upload")
    public ResponseEntity<String> uploadRate(@RequestParam MultipartFile file){
        try {
            rateService.upload(file);
            return ResponseEntity.ok("upload success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("upload fail cause : "+ex.getMessage());
        }
    }
}
