package com.mstore;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mstore.controller.AuthController;
import com.mstore.dto.LoginDto;
import com.mstore.service.UserService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    
    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer(); 
    

    LoginDto RECORD_1 = new LoginDto("test@gmail.com ","12345");
    LoginDto RECORD_2 = new LoginDto("test2@gmail.com", "123");

    @BeforeAll
    public void setUp(){
     MockitoAnnotations.openMocks(this);
     this.mockMvc = MockMvcBuilders.standaloneSetup(AuthController.class).build();  
    }

    // @Test
    // public void login_Success(){

    //     GeneralResponse generalResponse = new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
    //     .setMessage("Login SuccessFull ;)").build();

    //     when(userService.login(RECORD_1)).thenReturn(generalResponse);

    //     ResponseEntity<GeneralResponse> mockResponseEntity = authController.login(RECORD_1);

    //     assertEquals(HttpStatus.ACCEPTED, mockResponseEntity.getStatusCode());
    // }
}   
