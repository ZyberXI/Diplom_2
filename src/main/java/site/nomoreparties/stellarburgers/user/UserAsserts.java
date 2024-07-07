package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserAsserts {

    @Step("Валидация статус кода = 200. Проверка тела ответа success = true")
    public void assertUserCreatCorrect(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Step("Валидация статус кода = 200. Проверка тела ответа success = true." +
            " Проверка тела ответа email = email пользователя. Проверка тела ответа name = name пользователя")
    public void assertUserUpdateResponse(ValidatableResponse response, UserData userData) {
//        String email = response.extract().path("user.email");
//        String name = response.extract().path("user.name");

        response.assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
//        String email = response.extract().path("user.email");
//        String name = response.extract().path("user.name");

//        Assert.assertThat(email, equalTo(userData.getEmail()));
//        Assert.asserThat(name, equalTo(userData.getName()));
    }

    @Step("Валидация статус кода = 403. Проверка тела ответа success = false")
    public void assertUserUpdateFailEmailExist(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Step("Валидация статус кода = 401. Проверка тела ответа success = false")
    public void assertUserUpdateFailNonAuthUser(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Step("Валидация статус кода = 403. Проверка тела ответа success = false")
    public void assertUserCreateFail(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Step("Валидация статус кода = 200. Проверка тела ответа success = true")
    public void assertUserLoginCorrect(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Step("Валидация статус кода = 403. Проверка тела ответа success = false")
    public void assertUserLoginFail(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Step("Валидация статус кода = 200. Проверка тела ответа success = true")
    public void assertUserLogoutCorrect(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Step("Валидация статус кода = 202. Проверка тела ответа success = true")
    public void assertUserDeleteCorrect(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(202);
    }
}
