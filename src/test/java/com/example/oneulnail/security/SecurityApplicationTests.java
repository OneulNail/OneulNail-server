//package com.example.oneulnail.security;
//
//import com.example.oneulnail.domain.user.entity.User;
//import com.example.oneulnail.domain.user.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//class SecurityApplicationTests {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    public void testUser() {
//        User user = new User(1L,"윤서영","admin","1234","01040282129","활동중");
//        User savedUser = userRepository.save(user);
//
//        User findUser = userRepository.findById(savedUser.getId()).get();
//
//        Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
////        Assertions.assertThat(findUser.getName()).isEqualTo(user.getName());
//        Assertions.assertThat(findUser).isEqualTo(user);
//
//    }
//
//}