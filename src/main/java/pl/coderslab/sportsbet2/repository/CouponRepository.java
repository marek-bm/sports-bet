package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.Coupon;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    List<Coupon> findAllByUser(String userName);
    List<Coupon> findAllByUserUsername(String userName);
    Coupon findById(int id);


}
