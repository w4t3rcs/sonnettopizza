package org.sonnetto.product.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class IngredientClientStub {
    public static void stub(Long id) {
        String ingredientUrl = "api/v1.0/ingredient/" + id;
        stubFor(head(urlEqualTo(ingredientUrl))
                .willReturn(aResponse()
                        .withStatus(200)));
    }
}
