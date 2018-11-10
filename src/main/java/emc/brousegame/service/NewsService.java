/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.News;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Emerio-PC
 */
public interface NewsService {
    public News save(News news);
    public Page<News> findAll(Pageable pageable);
    public Optional<News> findById(Long id);
    public void delete(Long id);
    public void upload(MultipartFile file) throws Exception;
}
