package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.constants.ApiEnum;
import site.nomoreparties.stellarburgers.user.UserApi;
import site.nomoreparties.stellarburgers.user.UserAsserts;
import site.nomoreparties.stellarburgers.user.UserData;
import site.nomoreparties.stellarburgers.user.UserRandom;
/**
 * ToDo:Описание + вывод для красоты
 */
@DisplayName("Тесты на логин пользователя")
public class UserLoginTest {
    private UserApi userApi;
    private UserAsserts userAsserts;
    private UserData userData;
    private UserData userLogin;
    private String authToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiEnum.BASE_URL;
        userAsserts = new UserAsserts();
        userApi = new UserApi();
        userData = UserRandom.createRandomUser();

        ValidatableResponse response = userApi.createUser(userData);
        userAsserts.assertUserCreatCorrect(response);

        authToken = response.extract().path("accessToken");
        userLogin = new UserData(userData.getEmail(), userData.getPassword(), userData.getName());
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
    public void userLoginTest11() {
        ValidatableResponse response = userApi.loginUser(userLogin);
        userAsserts.assertUserLoginCorrect(response);
    }

    @Test
    @DisplayName("Проверка два")
    public void userLoginWithWrongEmailTest11() {
        userLogin.setEmail(userLogin.getEmail() + "mistake");
        ValidatableResponse response = userApi.loginUser(userLogin);
        userAsserts.assertUserLoginFail(response);
    }

    @Test
    @DisplayName("Проверка два")
    public void userLoginWithWrongPasswordTest11() {
        userLogin.setPassword(userLogin.getPassword() + "mistake");
        ValidatableResponse response = userApi.loginUser(userLogin);
        userAsserts.assertUserLoginFail(response);
    }

    @Test
    @DisplayName("Проверка четыре")
    public void userLogouttest11() {
        ValidatableResponse response =userApi.loginUser(userLogin);
        userAsserts.assertUserLogoutCorrect(response);
        String refreshToken = response.extract().path("refreshToken");
        ValidatableResponse response1 = userApi.logoutUser(refreshToken);
        userAsserts.assertUserLogoutCorrect(response1);
    }
}
