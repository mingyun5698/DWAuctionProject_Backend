package com.example.AuctionProject;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auction.service.AuctionService;
import com.example.AuctionProject.auth.security.UserRoleEnum;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionService auctionService;

    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {

        User user0 = new User();
        user0.setUserId("admin");
        user0.setPassword(passwordEncoder.encode("123"));
        user0.setUserType(UserRoleEnum.ADMIN);
        user0.setUsername("김민균");
        user0.setGender("Male");
        user0.setContact("010-555-5555");
        user0.setEmail("gpvkxhekek@naver.com");
        LocalDate localDate0 = LocalDate.now(); // 현재 날

        // DateTimeFormatter를 사용하여 yyyy-MM-dd 형식으로 변환
        DateTimeFormatter formatter0 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate0 = localDate0.format(formatter0);
        user0.setBirthdate(formattedDate0); // birthdate를 String으로 설정

        userRepository.save(user0);




        for (int i=1; i<10; i++) {
            User user = new User();
            user.setUserId("user"+i);
            user.setPassword(passwordEncoder.encode("123"));
            user.setUserType(UserRoleEnum.USER);
            user.setUsername("김민균" + i);
            user.setGender("Male");
            user.setContact("010-555-5555");
            user.setEmail("gpvkxhekek@naver.com");
            LocalDate localDate = LocalDate.now(); // 현재 날

            // DateTimeFormatter를 사용하여 yyyy-MM-dd 형식으로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = localDate.format(formatter);
            user.setBirthdate(formattedDate); // birthdate를 String으로 설정

            userRepository.save(user);
        }

            Auction auction1 = new Auction();
            auction1.setUser(userRepository.findById(1L).get());
            auction1.setTitle("후라이팬 팝니다.");
            auction1.setContents("후라이팬 팝니다 3개월밖에 사용 안했습니다. 상태매우 좋습니다.");
            auction1.setCreateTime(LocalDateTime.now());
            auction1.setDeadline(LocalDateTime.now().plusDays(7));
            auction1.setBidFee(10000);
            auction1.setProductName("후라이팬");
            auction1.setProductCategory("주방용품");
            auction1.setImagePath("주방용품1.png");
            auctionRepository.save(auction1);

        Auction auction2 = new Auction();
        auction2.setUser(userRepository.findById(1L).get());
        auction2.setTitle("가스레인지 팝니다.");
        auction2.setContents("가스레인지 팝니다 2년 사용 했습니다. 상태매우 좋습니다.");
        auction2.setCreateTime(LocalDateTime.now());
        auction2.setDeadline(LocalDateTime.now().plusDays(7));
        auction2.setBidFee(10000);
        auction2.setProductName("가스레인지");
        auction2.setProductCategory("주방용품");
        auction2.setImagePath("주방용품2.png");

        auctionRepository.save(auction2);

        Auction auction3 = new Auction();
        auction3.setUser(userRepository.findById(1L).get());
        auction3.setTitle("의자 팝니다.");
        auction3.setContents("의자 팝니다. 상태매우 좋습니다.");
        auction3.setCreateTime(LocalDateTime.now());
        auction3.setDeadline(LocalDateTime.now().plusDays(7));
        auction3.setBidFee(10000);
        auction3.setProductName("의자");
        auction3.setProductCategory("가구");
        auction3.setImagePath("가구1.png");

        auctionRepository.save(auction3);


        Auction auction4 = new Auction();
        auction4.setUser(userRepository.findById(1L).get());
        auction4.setTitle("책상 팝니다.");
        auction4.setContents("책상 팝니다. 상태는 안좋습니다.");
        auction4.setCreateTime(LocalDateTime.now());
        auction4.setDeadline(LocalDateTime.now().plusDays(7));
        auction4.setBidFee(10000);
        auction4.setProductName("책상");
        auction4.setProductCategory("가구");
        auction4.setImagePath("가구2.png");

        auctionRepository.save(auction4);

        Auction auction5 = new Auction();
        auction5.setUser(userRepository.findById(1L).get());
        auction5.setTitle("모자 팝니다.");
        auction5.setContents("모자 팝니다. 상태는 좋습니다.");
        auction5.setCreateTime(LocalDateTime.now());
        auction5.setDeadline(LocalDateTime.now().plusDays(7));
        auction5.setBidFee(10000);
        auction5.setProductName("모자");
        auction5.setProductCategory("의류");
        auction5.setImagePath("의류1.png");

        auctionRepository.save(auction5);

        Auction auction6 = new Auction();
        auction6.setUser(userRepository.findById(1L).get());
        auction6.setTitle("코트 팝니다.");
        auction6.setContents("코트 팝니다. 상태는 매우 좋습니다.");
        auction6.setCreateTime(LocalDateTime.now());
        auction6.setDeadline(LocalDateTime.now().plusDays(7));
        auction6.setBidFee(10000);
        auction6.setProductName("코트");
        auction6.setProductCategory("의류");
        auction6.setImagePath("의류2.png");

        auctionRepository.save(auction6);

        Auction auction7 = new Auction();
        auction7.setUser(userRepository.findById(1L).get());
        auction7.setTitle("카메라 팝니다.");
        auction7.setContents("카메라 팝니다. 상태는 매우 좋습니다. 5년넘게 사용했습니다.");
        auction7.setCreateTime(LocalDateTime.now());
        auction7.setDeadline(LocalDateTime.now().plusDays(7));
        auction7.setBidFee(10000);
        auction7.setProductName("카메라");
        auction7.setProductCategory("전자기기");
        auction7.setImagePath("전자기기1.png");

        auctionRepository.save(auction7);

        Auction auction8 = new Auction();
        auction8.setUser(userRepository.findById(1L).get());
        auction8.setTitle("핸드폰 팝니다.");
        auction8.setContents("핸드폰 팝니다. 상태는 매우 좋습니다. 5년넘게 사용했습니다.");
        auction8.setCreateTime(LocalDateTime.now());
        auction8.setDeadline(LocalDateTime.now().plusDays(7));
        auction8.setBidFee(10000);
        auction8.setProductName("핸드폰");
        auction8.setProductCategory("전자기기");
        auction8.setImagePath("전자기기2.png");

        auctionRepository.save(auction8);

        Auction auction9 = new Auction();
        auction9.setUser(userRepository.findById(1L).get());
        auction9.setTitle("로션 팝니다.");
        auction9.setContents("로션 팝니다. 상태는 매우 좋습니다. 한번도 사용 안했습니다.");
        auction9.setCreateTime(LocalDateTime.now());
        auction9.setDeadline(LocalDateTime.now().plusDays(7));
        auction9.setBidFee(10000);
        auction9.setProductName("로션");
        auction9.setProductCategory("화장품");
        auction9.setImagePath("화장품1.png");

        auctionRepository.save(auction9);

        Auction auction10 = new Auction();
        auction10.setUser(userRepository.findById(1L).get());
        auction10.setTitle("립스틱 팝니다.");
        auction10.setContents("립스틱 팝니다. 상태는 매우 좋습니다. 한번도 사용 안했습니다.");
        auction10.setCreateTime(LocalDateTime.now());
        auction10.setDeadline(LocalDateTime.now().plusDays(7));
        auction10.setBidFee(10000);
        auction10.setProductName("립스틱");
        auction10.setProductCategory("화장품");
        auction10.setImagePath("화장품2.png");

        auctionRepository.save(auction10);







    }
}
