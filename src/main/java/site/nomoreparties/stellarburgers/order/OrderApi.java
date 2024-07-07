package site.nomoreparties.stellarburgers.order;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.constants.ApiEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderApi {
    public List<IngredientData> getIngredient() {
        ValidatableResponse response = given().get(ApiEnum.INGREDIENTS_PATH).then();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonData = jsonParser.parse(response.extract().asPrettyString())
                .getAsJsonObject()
                .get("data");
        Gson gson = new Gson();
        return gson.fromJson(jsonData, new TypeToken<List<IngredientData>>(){}.getType());
    }

    public ValidatableResponse orderCreate(List<String> ingredientIds) {
        Map<String, List<String>> requestParams = new HashMap<>();
        requestParams.put("ingredients", ingredientIds);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams)
                .when()
                .post(ApiEnum.ORDER_PATH).then();
    }

    public ValidatableResponse orderGet(String authToken) {
        return given()
                .header("Authorization", authToken)
                .get(ApiEnum.ORDER_PATH).then();
    }

    public ValidatableResponse fiftyOrderGet(String authToken) {
        return given()
                .header("Authorization", authToken)
                .get(ApiEnum.ALL_ORDERS_PATH).then();
    }
}