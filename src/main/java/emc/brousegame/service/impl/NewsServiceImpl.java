/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.News;
import emc.brousegame.domain.Rates;
import emc.brousegame.repository.NewsRepository;
import emc.brousegame.service.NewsService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Emerio-PC
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService{
    
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void upload(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Iterator<Sheet> it = workbook.iterator();
        DataFormatter dataFormatter = new DataFormatter();
        List<News> newsList = new ArrayList<>();
       // while (it.hasNext()) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                News news = new News();
                news.setHeadline(dataFormatter.formatCellValue(row.getCell(0)));
                news.setDetail(dataFormatter.formatCellValue(row.getCell(1)));
                newsList.add(news);
            }
       // }
        newsRepository.saveAll(newsList);
    }
    
    
    
}
