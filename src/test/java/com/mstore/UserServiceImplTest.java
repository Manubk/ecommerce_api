package com.mstore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;

import com.mstore.model.Address;
import com.mstore.model.User;
import com.mstore.util.ApplicationUtils;

public class UserServiceImplTest {

    @Mock
    private ApplicationUtils applicationUtils;
    
    int length = 10;
    List<User> users ;

    @BeforeAll
    public void setUp(){
        for(int i=0;i<length;i++){
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setActivated(true);
            user.setEmail("email"+1+"@gmail.com");
            user.setFirstName("manu"+1);
            user.setLastName("B");
            user.setMobile("1234567899");
            user.setPassword("password");
            user.setRole("ROLE_USER");

            Address address = new Address();
            address.setId(Long.valueOf(i));
            address.setFirstName(user.getFirstName());
            address.setLastName(user.getLastName());
            address.setMobile(user.getMobile());
            address.setCity("Bangalore");
            address.setState("Karnataka");
            address.setStreetAddress("9th cross ");
            address.setUser(user);
            address.setZipCode("112345");
            address.setUpdatedAt(LocalDateTime.now());
            List<Address> addresses = new ArrayList<Address>();
                addresses.add(address);

            user.setAddress(addresses);
            user.setUpdatedDate(LocalDateTime.now());
            user.setCreateDate(LocalDateTime.now());
        }
    }


    // @Test
    // public void findLogedInUserTest(){

    //     when(ApplicationUtils.getLogedInUser()).thenReturn(users.get(0));

    //     UserDto userDto = new UserDto();
    //     BeanUtils.copyProperties(users.get(0), userDto);

    //     User userMock = ApplicationUtils.getLogedInUser();
    //     UserDto userDtoMock = new UserDto();
    //     BeanUtils.copyProperties(userMock, userDtoMock);
        
    //     assertEquals(userDto, userDtoMock);

    // }
}
