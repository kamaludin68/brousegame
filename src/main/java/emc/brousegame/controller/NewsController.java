/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.News;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.service.NewsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * @author Emerio-PC
 */
@RestController
@RequestMapping("/api/v1/")
public class NewsController {
    @Autowired
    NewsService newsService;
    
    @GetMapping("news")
    public Page<News> list(
        @RequestParam(required = false) Integer page, 
        @RequestParam(required = false) Integer size){
        Pageable paging = Pageable.unpaged();
        if(null!=page && null !=size){
            paging = PageRequest.of(page, size);
        }
        Page<News> result =  newsService.findAll(paging);
        return result;
    }
    
    @GetMapping("news/{id}")
    public News getNews(@PathVariable Long id){
        Optional<News> news = newsService.findById(id);
        if(!news.isPresent())
            throw new ResourceNotFoundException("id:"+id);
        return news.get();
    }
    
    @GetMapping("news/uploadTemplate")
    public ResponseEntity<InputStreamResource> getUploadTemplate(){
        try {
            InputStreamResource resource = new InputStreamResource(new ClassPathResource("templates/NEWS_TEMPLATE.xls").getInputStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=NEWS_TEMPLATE.xls")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("news")
    public ResponseEntity<Object> create(@RequestBody News news){
        if(news.getId() !=null)
            return ResponseEntity.badRequest().body("A new News cannot already have an ID");
        News res = newsService.save(news);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(location).body(res);
    }
    
    @PutMapping("news")
    public ResponseEntity<News> update(@RequestBody News news){
        News res = newsService.save(news);
        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping("news/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        newsService.delete(id);
        return ResponseEntity.ok("news "+id+" has been delete");
    }
    
    @PostMapping("news/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file){
        try {
            newsService.upload(file);
            return ResponseEntity.ok("upload success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("upload fail cause : "+ex.getMessage());
        }
    }
}
