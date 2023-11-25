import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.mstore.util.ApplicationUtils;

public class ApplicationUtilisTest {

    @ParameterizedTest
    @CsvSource({
        "'HI' ,false",
        "'' , true"
    })
    public void isNullOrEmpty_Success(String value,boolean expectedValue){
        assertEquals(expectedValue, ApplicationUtils.isNotNullOrEmpty(value));
    }
    

    @ParameterizedTest
    @CsvSource({
        "100.0 , 50.0 , 50",
        "150.0 , 100.0 ,33",
        "0,0,0",
        "200,200,0"
    })
    public void getDiscountedPercent_Success(Double actualPrice , Double discountedPrice , int expectedDiscount){

        assertEquals(expectedDiscount, ApplicationUtils.getDiscountPercent(actualPrice, discountedPrice));
    }

    @ParameterizedTest
    @CsvSource({
        "100.0,50,50.0",
        "150.0,33,100.5",
        "100.0,0,100.0"
    })
    public void getDiscountedPriceTest(Double price , int discountedPercent,Double expectedDiscountPrice){
        assertEquals(expectedDiscountPrice, ApplicationUtils.getDiscountedPrice(price, discountedPercent));
    }
    
}
