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
@DisplayName("Обновление данных пользователя")
public class UserUpdateTest {
    private UserApi userApi;
    private UserAsserts userAsserts;
    private UserData userData;
    private String authToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiEnum.BASE_URL;
        userApi = new UserApi();
        userAsserts = new UserAsserts();
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
    public void updateLoggedUserTest11() {
        UserData userData1 = UserRandom.createRandomUser();
        ValidatableResponse response = userApi.updateUser(userData1, authToken);
        userAsserts.assertUserUpdateResponse(response, userData1);
        ValidatableResponse response1 = userApi.loginUser(userData1);
        userAsserts.assertUserLoginCorrect(response1);
    }

    @Test
    @DisplayName("Проверка два")
    public void updateLoggedUserWithEmailAlreadyExistTest11() {
        UserData userData1 = UserRandom.createRandomUser();
        ValidatableResponse response = userApi.createUser(userData1);
        userAsserts.assertUserCreatCorrect(response);
        UserData userData2 = new UserData(userData1.getEmail(), userData1.getPassword(), userData1.getName());
        ValidatableResponse response1 = userApi.updateUser(userData2, authToken);
        userAsserts.assertUserUpdateFailEmailExist(response1);
        String authToken2 = response.extract().path("accessToken");
        ValidatableResponse response2 = userApi.deleteUser(userData1, authToken2);
        userAsserts.assertUserDeleteCorrect(response2);
    }

    @Test
    @DisplayName("Проверка три")
    public void updateUnloggedUserTest11() {
        UserData userData1 = UserRandom.createRandomUser();
        ValidatableResponse response = userApi.updateUser(userData1, "mistake");
        userAsserts.assertUserUpdateFailNonAuthUser(response);
    }

}
