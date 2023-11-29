package com.mstore.RepoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mstore.model.Address;
import com.mstore.model.User;
import com.mstore.repo.AddressRepo;
import com.mstore.repo.UserRepo;

@SpringBootTest
class AddressRepoTest{

    @Autowired
    private  UserRepo userRepo;

    @Autowired
    private  AddressRepo addressRepo;

    User expectedUser_1 ;
    User expectedUser_2;

    // Before all test cases saving the data into db 

    @BeforeEach
    public void setUp(){
        expectedUser_1 = new User(1l, "manu", "b k", null, null, null, null, null, null, null, null, null);
        Address address = new Address(1l, "Manu", "B K", "Peenya", "Bangalore", "karnataka", "560054", expectedUser_1,"9999999999   ", null, null);
       
        expectedUser_2 = new User(2l, "manu", "b k", null, null, null, null, new ArrayList<Address>(), null, null, null, null);
        
        expectedUser_1.setAddress(Arrays.asList(address));

        userRepo.save(expectedUser_1);
        userRepo.save(expectedUser_2);

        addressRepo.save(address);
    }


    @Test
    public void findByUserTest(){
        List<Address> actuayAddresses = addressRepo.findByUser(expectedUser_1);
        assertEquals(expectedUser_1.getAddress().size() , actuayAddresses.size());
    }

    @Test
    public void findByUserTest_NoAddrestPresent(){
        List<Address> actuAddresses = addressRepo.findByUser(expectedUser_2);
        assertEquals(expectedUser_2.getAddress().size(), actuAddresses.size());
    }

    @AfterEach
    public void afterAll(){
        //Deleting all the address from database
        addressRepo.deleteAll(expectedUser_1.getAddress());
        userRepo.delete(expectedUser_1);
    }

}