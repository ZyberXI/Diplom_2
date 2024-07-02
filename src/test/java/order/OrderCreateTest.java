package order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.constants.ApiEnum;
import site.nomoreparties.stellarburgers.order.IngredientData;
import site.nomoreparties.stellarburgers.order.OrderApi;
import site.nomoreparties.stellarburgers.order.OrderAsserts;

import java.util.List;
import java.util.ArrayList;

@DisplayName("Order Creation Test")
public class OrderCreateTest {
    private OrderApi orderApi;
    private OrderAsserts orderAsserts;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiEnum.BASE_URL;
        orderApi = new OrderApi();
        orderAsserts = new OrderAsserts();
    }

    @Step("Получение игредиентов")
    private List<String> getIngredient() {
        List<IngredientData> ingredient = orderApi.getIngredient();
        List<String> ingredientIds = new ArrayList<>();
        if (ingredient.size() >= 3) {
            ingredientIds.add(ingredient.get(0).get_id());
            ingredientIds.add(ingredient.get(1).get_id());
            ingredientIds.add(ingredient.get(2).get_id());
        }
        else {
            throw new RuntimeException("Недостаточно игредиентов");
        }
        return ingredientIds;
    }

    @Step("Создание заказа с игредиентами")
    private ValidatableResponse orderCreate(List<String> ingredientIds) {
        return orderApi.orderCreate(ingredientIds);
    }

    @Test
    @DisplayName("wde")
    public void orderCreateTest() {
        List<String> ingredientIds = getIngredient();
        ValidatableResponse response = orderCreate(ingredientIds);
        orderAsserts.assertOrderCreateCorrect(response);
    }

    @Test
    @DisplayName("wde")
    public void orderCreateWithoutIngredientTest() {
        List<String> ingredientIds = new ArrayList<>();
        ValidatableResponse response = orderCreate(ingredientIds);
        orderAsserts.assertOrderCreateBadRequest(response);
    }

    @Test
    @DisplayName("wde")
    public void orderCreateWithWrongIngredientTest() {
        List<String> ingredientIds = new ArrayList<>();
        ingredientIds.add("errorId");
        ValidatableResponse response = orderCreate(ingredientIds);
        orderAsserts.assertOrderCreateServerError(response);
     }
}
