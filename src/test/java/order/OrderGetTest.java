package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.constants.ApiEnum;
import site.nomoreparties.stellarburgers.order.OrderApi;
import site.nomoreparties.stellarburgers.order.OrderAsserts;
import site.nomoreparties.stellarburgers.user.UserApi;
import site.nomoreparties.stellarburgers.user.UserAsserts;
import site.nomoreparties.stellarburgers.user.UserData;
import site.nomoreparties.stellarburgers.user.UserRandom;
/**
 * ToDo:Описание + вывод для красоты
 */
@DisplayName("Test Order")
public class OrderGetTest {
    private OrderApi orderApi;
    private UserApi userApi;
    private UserAsserts userAsserts;
    private OrderAsserts orderAsserts;
    private UserData userData;
    private String authToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiEnum.BASE_URL;
        orderApi = new OrderApi();
        userApi = new UserApi();
        userAsserts = new UserAsserts();
        orderAsserts = new OrderAsserts();

        userData = UserRandom.createRandomUser();
        ValidatableResponse response = userApi.createUser(userData);
        userAsserts.assertUserCreatCorrect(response);
        authToken = response.extract().path("accessToken");
    }

    @After
    public void cleanData() {
        if (authToken != null && userData != null) {
            ValidatableResponse response = userApi.deleteUser(userData, authToken);
            userAsserts.assertUserDeleteCorrect(response);
        }
    }

    @Test
    @DisplayName("Проверка раз")
    public void getOrdersAuthTest11() {
        ValidatableResponse response = orderApi.orderGet(authToken);
        orderAsserts.assertOrderRecievedCorrect(response);
    }


    @Test
    @DisplayName("Проверка два")
    public void getFifty11() {
        ValidatableResponse response = orderApi.fiftyOrderGet(authToken);
        orderAsserts.assertOrderRecievedFiftyCountsCorrect(response);
    }


    @Test
    @DisplayName("Проверка три")
    public void getOrderNonAuthTest11() {
        ValidatableResponse response = orderApi.orderGet("mistake");
        orderAsserts.assertOrderRecievedFailed(response);
    }
}
