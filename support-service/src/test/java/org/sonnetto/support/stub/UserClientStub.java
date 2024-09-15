package org.sonnetto.support.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class UserClientStub {
    public static void stub(Long id) {
        String userUrl = "api/v1.0/users/" + id;
        String responseBody = """
                {
                    "id": %s,
                    "name": "test",
                    "email": "rousnagibator228@gmail.com"
                }
                """.formatted(id);
        stubFor(head(urlEqualTo(userUrl))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo(userUrl))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}
