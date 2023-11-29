package com.mstore.RepoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mstore.model.Cart;
import com.mstore.model.CartItem;
import com.mstore.model.Product;
import com.mstore.model.User;
import com.mstore.repo.CartRepo;
import com.mstore.repo.UserRepo;

@SpringBootTest
public class CartRepoTest {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    User userRecord_1;
    Cart cartRecord_1;

    @BeforeEach
    public void setUP(){
        userRecord_1 = new User(1l, "manu", "b k", "test@gmail.com", "123", null, null, null,new ArrayList<Product>(), null, null, null);
        userRecord_1.setRole("ROLE_USER");
        userRecord_1.getProducts().add(new Product());
        userRepo.save(userRecord_1);

        cartRecord_1 = new Cart(1l, userRecord_1, new ArrayList<CartItem>(), 100 , 2, 500, 50);
        cartRepo.save(cartRecord_1);
    }

    @Test
    public void findByUserIdTest_ifPresent(){
       Cart actualCartRecord = cartRepo.findByUserId(1l);

       assertEquals(cartRecord_1.getTotalPrice(), actualCartRecord.getTotalPrice());
       assertEquals(cartRecord_1.getTotalItem(), actualCartRecord.getTotalItem());
       assertEquals(cartRecord_1.getTotalDiscountedPrice(), actualCartRecord.getTotalDiscountedPrice());
       assertEquals(cartRecord_1.getDiscount(), actualCartRecord.getDiscount());

    }

     @Test
    public void findByUserIdTest_ifNotPresent(){
       Cart actualCartRecord = cartRepo.findByUserId(100l);
            assertNull(actualCartRecord);
    }

    @AfterEach
    public void afterEach(){
        cartRepo.delete(cartRecord_1);
        userRepo.delete(userRecord_1);
    }
    
}
