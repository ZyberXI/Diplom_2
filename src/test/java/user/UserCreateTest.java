package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
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
@DisplayName("Тесты на пользователя")
public class UserCreateTest {
    private UserApi userApi;
    private UserAsserts userAsserts;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiEnum.BASE_URL;
        userApi = new UserApi();
        userAsserts = new UserAsserts();
    }

    @Test
    @DisplayName("Проверка раз")
    public void userCreateTest() {
        UserData userData = UserRandom.createRandomUser();
        ValidatableResponse response = userApi.createUser(userData);
        String authToken = response.extract().path("accessToken");
        ValidatableResponse response1 = userApi.deleteUser(userData, authToken);
        userAsserts.assertUserDeleteCorrect(response1);
    }

    @Test
    @DisplayName("Проверка два")
    public void userCreateAlreadyExistTest11() {
        UserData userData = UserRandom.createRandomUser();
        ValidatableResponse response = userApi.createUser(userData);
        ValidatableResponse response1 = userApi.createUser(userData);
        userAsserts.assertUserCreateFail(response1);
        String authToken = response.extract().path("accessToken");
        ValidatableResponse response2 = userApi.deleteUser(userData, authToken);
        userAsserts.assertUserDeleteCorrect(response2);
    }

    @Test
    @DisplayName("Проверка три")
    public void userCreateWithoutEmailTest11() {
        UserData userData = UserRandom.createRandomUser();
        userData.setEmail(null);
        ValidatableResponse response = userApi.createUser(userData);
        userAsserts.assertUserCreateFail(response);
    }

    @Test
    @DisplayName("Проверка четыре")
    public void userCreateWithoutPasswordTest11() {
        UserData userData = UserRandom.createRandomUser();
        userData.setPassword(null);
        ValidatableResponse response = userApi.createUser(userData);
        userAsserts.assertUserCreateFail(response);
    }

    @Test
    @DisplayName("Проверка пять")
    public void userCreateTestWithoutNameTset11() {
        UserData userData = UserRandom.createRandomUser();
        userData.setName(null);
        ValidatableResponse response = userApi.createUser(userData);
        userAsserts.assertUserCreateFail(response);
    }
}
