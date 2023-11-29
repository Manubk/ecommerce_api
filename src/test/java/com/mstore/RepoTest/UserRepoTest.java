package com.mstore.RepoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mstore.model.Product;
import com.mstore.model.User;
import com.mstore.repo.UserRepo;

@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    User user = null;

    @BeforeEach
    public void setUp(){
        user = new User(1l, "manu", "b k", "test@gmail.com", "123", null, null, null,new ArrayList<Product>(), null, null, null);
        user.setRole("ROLE_USER");
        user.getProducts().add(new Product());
        userRepo.save(user);
 }
    
    @Test
    public void findByEmailTest(){
       User actualUser = userRepo.findByEmail(user.getEmail());

       assertEquals(user.getEmail(), actualUser.getEmail());

    }

    // If the user with email is not present in database the it user should be null
    @Test
    public void findByEmailTest_IfEmailNotPresetn(){
        User actualUser = userRepo.findByEmail("nomail@gmail.com");
        assertEquals(null, actualUser);
    }

    @Test
    public void findByEmailAndPassword(){
        User actualUser = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPassword(), actualUser.getPassword());
    }

    @Test
    public void findByEmailAndPassword_EmailOrPassNotPresent(){
        User actualUser = userRepo.findByEmailAndPassword(user.getEmail(), "fakePass");
        assertNull(actualUser);
    }

    @Disabled
    @Test
    public void findByEmailAndPassword_null(){
        User actualUser = userRepo.findByEmailAndPassword(null, null);
        assertNull(actualUser);
    }

    @AfterEach
    public void afterTest(){
        userRepo.delete(user);
    }
}
