package com.mstore.RepoTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mstore.model.PayMentInformation;
import com.mstore.model.User;
import com.mstore.repo.PaymentInformationRepo;
import com.mstore.repo.UserRepo;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


@SpringBootTest
public class PaymentInformationRepoTest {
    
    @Autowired
    private PaymentInformationRepo paymentInformationRepo;

    @Autowired
    private UserRepo userRepo;

    private User userRecord_1;
    private List<PayMentInformation> payMentInformationRecords = new ArrayList<PayMentInformation>();

    @BeforeEach
    public void setUp(){

        //Creating a user record 
        userRecord_1 = new User(1l, "manu", "b k", "test@gmail.com", "123", null, null, null, null, null, null, null);
        userRepo.save(userRecord_1);

       //Creating and saving PaymentInformation records for User
       PayMentInformation payMentInformationRecord_1 = new PayMentInformation(1l, "card", userRecord_1.getFirstName(), "2222 2222 2222", null, "234", null, userRecord_1, null, null);
       PayMentInformation payMentInformationRecord_2 = new PayMentInformation(2l, "upi", userRecord_1.getFirstName(), null, null, "234", "2323232323@upi", userRecord_1, null, null);
       
       paymentInformationRepo.save(payMentInformationRecord_1);
       paymentInformationRepo.save(payMentInformationRecord_2);
    
       //Creating payment information List
       payMentInformationRecords.add(payMentInformationRecord_1);
       payMentInformationRecords.add(payMentInformationRecord_2);
    }

    @Test
    public void findByUserTest_IfPresent(){
        List<PayMentInformation> actualPayMentInformations = paymentInformationRepo.findByUser(userRecord_1);

        assertEquals(payMentInformationRecords.size(), actualPayMentInformations.size());
    }

    @AfterEach
    private void afterEach(){
        //Delete payment details from database
        paymentInformationRepo.deleteAll(payMentInformationRecords);

        //Deleting User form database
        userRepo.delete(userRecord_1);
    }
}
