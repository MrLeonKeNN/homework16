package epam.ilyankov.service;

import epam.ilyankov.domain.Product;
import epam.ilyankov.repository.PriceList;
import epam.ilyankov.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class BasketServiceTest {

    @Mock
    private PriceList priceList;

    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpServletRequest req;
    @InjectMocks
    private BasketService basketService;

    @Test
    void getAllSum_Test_Positive() {
        List<Product> productList = List.of(
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Cup").cell(13.5).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Soldering iron").cell(17.7).build()
        );
        Double expected = 668.7;

        Double actual;

        try (MockedStatic<ProductRepository> mockedStatic = Mockito.mockStatic(ProductRepository.class);) {
            mockedStatic.when(ProductRepository::getProductList).thenReturn(productList);
            actual = basketService.getAllSum();
        }

        assertEquals(expected, actual);
    }

    @Test
    void getAllSum_Test_Negative() {
        try (MockedStatic<ProductRepository> mockedStatic = Mockito.mockStatic(ProductRepository.class);) {
            mockedStatic.when(ProductRepository::getProductList).thenReturn(null);

            assertThrows(NullPointerException.class, () -> {
                basketService.getAllSum();
            });
        }
    }

    @Test
    void getBasketProduct_Test_Positive() {
        List<Product> productList = List.of(
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Cup").cell(13.5).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Soldering iron").cell(17.7).build()
        );

        String expected = "<p>Book 12.5 $</p>\n" +
                "<p>Book 12.5 $</p>\n" +
                "<p>Book 12.5 $</p>\n" +
                "<p>Cup 13.5 $</p>\n" +
                "<p>Bicycle 300.0 $</p>\n" +
                "<p>Bicycle 300.0 $</p>\n" +
                "<p>Soldering iron 17.7 $</p>\n";

        String actual;

        try (MockedStatic<ProductRepository> mockedStatic = Mockito.mockStatic(ProductRepository.class);) {
            mockedStatic.when(ProductRepository::getProductList).thenReturn(productList);
            actual = basketService.getBasketProduct();
        }

       assertEquals(expected,actual);
    }

    @Test
    void getNameUser_Test_Positive_Req_GetName() {
        when(req.getParameter(any())).thenReturn("Alisa");

        String expected = "Alisa";

        String actual = basketService.getNameUser(req, resp);

        assertEquals(expected, actual);
    }

    @Test
    void getNameUser_Test_Positive_Cookie() {
        Cookie cookie = new Cookie("name", "Alisa");

        Cookie[] cookies = {cookie};

        when(req.getParameter(any())).thenReturn(null);

        when(req.getCookies()).thenReturn(cookies);

        String expected = "Alisa";

        String actual = basketService.getNameUser(req, resp);

        assertEquals(expected, actual);
    }
}