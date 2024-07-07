package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class OrderAsserts {
    @Step("Валидация статус кода = 200. Проверка тела ответа success = true")
    public void assertOrderRecievedCorrect(ValidatableResponse response) {
        response
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Step("Валидация статус кода = 200. Проверка тела ответа success = true. Проверка тела ответа orders.size() > 50")
    public void assertOrderRecievedFiftyCountsCorrect(ValidatableResponse response) {
        response
                .assertThat()
                .body("success", equalTo(true))
                .body("orders.size()", greaterThanOrEqualTo(50))
                .statusCode(200);
    }

    @Step("Валидация статус кода = 401. Проверка тела ответа success = false")
    public void assertOrderRecievedFailed(ValidatableResponse response) {
        response
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(401);
    }

    @Step("Валидация статус кода = 200. Проверка тела ответа success = true")
    public void assertOrderCreateCorrect(ValidatableResponse response) {
        response
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Step("Валидация статус кода = 400. Проверка тела ответа success = false")
    public void assertOrderCreateBadRequest(ValidatableResponse response) {
        response
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(400);
    }

    @Step("Валидация статус кода = 500. Проверка тела ответа success = false")
    public void assertOrderCreateServerError(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(500);
    }
}
