/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.Currency;
import emc.brousegame.domain.Rates;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.repository.CurrencyRepository;
import emc.brousegame.repository.RateRepository;
import emc.brousegame.service.RateService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kamal
 */
@Service
@Transactional
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    public Rates save(Rates rates) {
        return rateRepository.save(rates);
    }

    @Transactional(readOnly = true)
    public List<Rates> findAll() {
        return rateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Rates> findById(Long id) {
        return rateRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Rates> findByTimeRate(Integer timeRate) {
        return rateRepository.findAllByTimeRate(timeRate);
    }
    
    public List<Rates> findByCcyTo(String code){
        Currency currency = currencyRepository.findByCode(code);
        return rateRepository.findAllByCcyTo(currency);
    }

    public void delete(Long id) {
        rateRepository.deleteById(id);
    }

    @Override
    public void upload(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Iterator<Sheet> it = workbook.iterator();
        DataFormatter dataFormatter = new DataFormatter();
        List<Rates> rateList = new ArrayList<>();
        while (it.hasNext()) {
            Sheet sheet = it.next();
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                try {
                    Rates rates = new Rates();
                    rates.setTimeRate(Integer.valueOf(dataFormatter.formatCellValue(row.getCell(0))));
                    Currency from = currencyRepository.findByCode(dataFormatter.formatCellValue(row.getCell(1)));
                    if(from == null)
                        throw new ResourceNotFoundException("Currency with code :"+dataFormatter.formatCellValue(row.getCell(1))+" not found at master currency");
                    rates.setCcyFrom(from);
                    Currency to = currencyRepository.findByCode(dataFormatter.formatCellValue(row.getCell(2)));
                    if(to == null)
                        throw new ResourceNotFoundException("Currency with code :"+dataFormatter.formatCellValue(row.getCell(2))+" not found at master currency");
                    rates.setCcyTo(to);
                    rates.setBuy(Integer.valueOf(dataFormatter.formatCellValue(row.getCell(3))));
                    rates.setSell(Integer.valueOf(dataFormatter.formatCellValue(row.getCell(4))));
                    rates.setMaxRate(Integer.valueOf(dataFormatter.formatCellValue(row.getCell(5))));
                    rates.setMinRate(Integer.valueOf(dataFormatter.formatCellValue(row.getCell(6))));
                    rateList.add(rates);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Number format exception :"+sheet.getSheetName()+", row : "+row.getRowNum()+", "+e.getMessage());
                }
            }
        }
        rateRepository.saveAll(rateList);
    }
}
