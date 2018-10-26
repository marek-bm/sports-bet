package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.model.Coupon;
import pl.coderslab.sportsbet2.model.Fixture;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {



}
