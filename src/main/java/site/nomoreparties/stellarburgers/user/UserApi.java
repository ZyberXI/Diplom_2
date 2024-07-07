package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.constants.ApiEnum;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApi {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(UserData userData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .post(ApiEnum.USER_REGISTER_PATH).then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(UserData userData, String authToken) {
        return given()
                .header("Authorization", authToken)
                .and()
                .body(userData)
                .when()
                .delete(ApiEnum.USER_INF_PATH).then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(UserData userData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .post(ApiEnum.USER_LOGIN_PATH).then();
    }

    @Step("Логаут пользователя")
    public ValidatableResponse logoutUser(String authToken) {
        Map<String, String> params = new HashMap<>();
        params.put("token", authToken);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(params)
                .when()
                .post(ApiEnum.USER_LOGOUT_PATH).then();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse updateUser(UserData userData, String authToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", authToken)
                .and()
                .body(userData)
                .when()
                .patch(ApiEnum.USER_INF_PATH).then();
    }
}
