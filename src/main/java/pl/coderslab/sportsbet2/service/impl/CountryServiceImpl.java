package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.repository.CountryRepository;
import pl.coderslab.sportsbet2.service.CountryService;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
   
    @Autowired
    CountryRepository countryRepository;
    
    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

}
