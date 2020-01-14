package pl.bets365mj.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.bets365mj.SportsBet2Application;
import pl.bets365mj.bet.BetServiceImpl;
import pl.bets365mj.coupon.Coupon;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.coupon.CouponServiceImpl;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.bet.BetRepository;
import pl.bets365mj.coupon.CouponRepository;
import pl.bets365mj.bet.BetService;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.user.User;
import pl.bets365mj.wallet.Wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
public class CouponServiceImplTest {
    private static final Logger log= LoggerFactory.getLogger(CouponServiceImplTest.class);

    @TestConfiguration
    static class CouponServiceImplTestContextConfig{
        @Bean
        public CouponService couponService(){
            return new CouponServiceImpl();
        }

        @Bean
        public BetService betService(){
            return new BetServiceImpl();
        }
    }

    @MockBean
    private CouponRepository couponRepository;

    @MockBean
    private BetRepository betRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private BetService betService;

    @Before
    public void setUp(){
        User u1=new User();
        u1.setUsername("user");

        Coupon c1=new Coupon();
        c1.setUser(u1);

        Coupon c2=new Coupon();
        c2.setUser(u1);

        List<Coupon> userCoupons=new ArrayList<>();
        userCoupons.add(c1);
        userCoupons.add(c2);

        Mockito.when(couponRepository.findAllByUser(u1.getUsername())).thenReturn(userCoupons);
    }

    @Test
    //given user find all cuopons
    public void findAllByUser() {
        //given
        String name="user";
        List<Coupon> storedCoupons=couponService.findAllByUser(name);

        //then
        assert(storedCoupons.size()==2);
        assertEquals(storedCoupons.get(0).getUser().getUsername(),name);
    }

    @Test
    public void resolveCoupons__given_all_fixtures_are_finished() {

        String username="u3";
        User u3=new User();
        u3.setUsername(username);

        Wallet wallet=new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));
        u3.setWallet(wallet);

        //1.2creating new fixtures to put it inside bet
        Fixture fixture1 = new Fixture();
        fixture1.setMatchStatus("finished");

        Fixture fixture2 = new Fixture();
        fixture2.setMatchStatus("finished");

        //1.3creating new coupon and coupon list
        Coupon c3=new Coupon();
        List<Coupon> coupons=new ArrayList<>();
        Date date= new Date();

        //1.4 creating new bets to add into coupon
        Bet b1=new Bet();
        b1.setFixture(fixture1);
        b1.setWon(true);
        b1.setDateCreated(date);
        b1.setBetPrice(BigDecimal.valueOf(2.5));
        b1.setPlacedBet("H");
        b1.setCoupon(c3);
        List<Bet> bets=new ArrayList<>();
        bets.add(b1);

        Bet b2=new Bet();
        b2.setFixture(fixture2);
        b2.setWon(true);
        b2.setDateCreated(date);
        b2.setBetPrice(BigDecimal.valueOf(2));
        b2.setPlacedBet("H");
        b2.setCoupon(c3);
        bets.add(b2);

        //1.5 updating coupon and coupon list
        c3.setBets(bets);
        c3.setUser(u3);
        c3.setActive(true);
        c3.setBetValue(BigDecimal.valueOf(10.0));
        c3.setWinValue(BigDecimal.valueOf(50.0));
        coupons.add(c3);

        Mockito.when(couponRepository.findAll()).thenReturn(coupons);

        //2 WHEN
        couponService.resolveCoupons(coupons);
        List<Coupon> storedCoupons=couponRepository.findAll();

        //3 THEN
        BigDecimal winValue=BigDecimal.valueOf(50.0);
        BigDecimal betValue=BigDecimal.valueOf(10.0);
        assert(coupons.size()==storedCoupons.size());
        assert(storedCoupons.contains(c3));
        assertEquals(storedCoupons.get(0).getWinValue(), winValue);
        assertEquals(storedCoupons.get(0).getBetValue(),betValue);
        assertEquals(storedCoupons.get(0).isActive(), false);
        assertEquals(storedCoupons.get(0).isWon(),true);


    }


    @Test
    public void resolveCoupons__given_one_fixture_is_active() {

        String username="u3";
        User u3=new User();
        u3.setUsername(username);

        Wallet wallet=new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));
        u3.setWallet(wallet);

        //1.2creating new fixtures to put it inside bet
        Fixture fixture1 = new Fixture();
        fixture1.setMatchStatus("finished");

        Fixture fixture2 = new Fixture();
        fixture2.setMatchStatus("active");

        //1.3creating new coupon and coupon list
        Coupon c3=new Coupon();
        List<Coupon> coupons=new ArrayList<>();
        Date date= new Date();

        //1.4 creating new bets to add into coupon
        Bet b1=new Bet();
        b1.setFixture(fixture1);
        b1.setWon(true);
        b1.setDateCreated(date);
        b1.setBetPrice(BigDecimal.valueOf(2.5));
        b1.setPlacedBet("H");
        b1.setCoupon(c3);
        List<Bet> bets=new ArrayList<>();
        bets.add(b1);

        Bet b2=new Bet();
        b2.setFixture(fixture2);
        b2.setWon(false);
        b2.setDateCreated(date);
        b2.setBetPrice(BigDecimal.valueOf(2));
        b2.setPlacedBet("H");
        b2.setCoupon(c3);
        bets.add(b2);

        //1.5 updating coupon and coupon list
        c3.setBets(bets);
        c3.setUser(u3);
        c3.setActive(true);
        c3.setBetValue(BigDecimal.valueOf(10.0));
        c3.setWinValue(BigDecimal.valueOf(50.0));
        coupons.add(c3);

        Mockito.when(couponRepository.findAll()).thenReturn(coupons);

        //2 WHEN
        couponService.resolveCoupons(coupons);
        List<Coupon> storedCoupons=couponRepository.findAll();

        //3 THEN
        BigDecimal winValue=BigDecimal.valueOf(50.0);
        BigDecimal betValue=BigDecimal.valueOf(10.0);
        assert(coupons.size()==storedCoupons.size());
        assert(storedCoupons.contains(c3));
        assertEquals(storedCoupons.get(0).getWinValue(), winValue);
        assertEquals(storedCoupons.get(0).getBetValue(),betValue);
        assertEquals(storedCoupons.get(0).isActive(), true);
        assertEquals(storedCoupons.get(0).isWon(),false);


    }

}