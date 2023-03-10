package com.nahwasa.study.howtosolveconcurrencyissues.service;

import com.nahwasa.study.howtosolveconcurrencyissues.domain.Stock;
import com.nahwasa.study.howtosolveconcurrencyissues.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decreaseForNamedLock(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}

