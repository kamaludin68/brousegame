/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.AppParams;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.service.AppParamsService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Emerio-PC
 */
@RestController
@RequestMapping("/api/v1/")
public class AppParamController {
    @Autowired
    private AppParamsService appParamsService;
    
    @GetMapping("parameters")
    public List<AppParams> list(@RequestParam(required = false) String parentCode){
        if(null == parentCode)
            return appParamsService.list();
        return appParamsService.findAllByParentCode(parentCode);
    }
    
    @GetMapping("parameters/{id}")
    public AppParams getParameter(@PathVariable Long id){
        Optional<AppParams> param = appParamsService.findById(id);
        if(!param.isPresent())
            throw new ResourceNotFoundException("id:"+id+" not found");
        return param.get();
    }
    
    @GetMapping("paramters")
    public AppParams getParameterByCode(@RequestParam String code){
        Optional<AppParams> param = appParamsService.findByCode(code);
        if(!param.isPresent())
            throw new ResourceNotFoundException("code:"+code+" not found");
        return param.get();
    }
    
    @PostMapping("parameters")
    public ResponseEntity<Object> create(@RequestBody AppParams appParams){
        if(null != appParams.getId()){
            return ResponseEntity.badRequest().body("A new News cannot already have an ID");
        }
        AppParams res = appParamsService.save(appParams);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(location).body(res);
    }
    
    @PutMapping("parameters")
    public AppParams update(@RequestBody AppParams appParams){
        return appParamsService.save(appParams);
    }
    
    @DeleteMapping("parameters/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        appParamsService.delete(id);
        return ResponseEntity.ok("news "+id+" has been delete");
    }
}
