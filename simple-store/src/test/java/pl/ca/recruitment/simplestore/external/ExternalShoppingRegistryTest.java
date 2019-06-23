package pl.ca.recruitment.simplestore.external;

import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;
import pl.ca.recruitment.simplestore.Randomize;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-integration.properties")
public class ExternalShoppingRegistryTest {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalApi externalShoppingRegistry;

    private MockRestServiceServer mockServer;

    @Before
    public void before() {
        final RestGatewaySupport gateway = new RestGatewaySupport();
        gateway.setRestTemplate(restTemplate);

        mockServer = MockRestServiceServer.createServer(gateway);
    }

    @Test
    public void externalServerRespondWithSuccess() {
        // given
        mockServer.expect(once(), requestTo("http://localhost:8081/api/notification/purchase"))
                .andRespond(withSuccess());

        // when
        final Try<Void> result = externalShoppingRegistry
                .sendNotification(Randomize.random(PurchaseItemsRequestDTO.class), Randomize.random(PurchaseItemsResponseDTO.class));

        // and
        mockServer.verify();

        // then
        assertTrue(result.isSuccess());
    }

    @Test
    public void externalServerRespondWithBadRequest() {
        // given
        mockServer.expect(once(), requestTo("http://localhost:8081/api/notification/purchase"))
                .andRespond(withBadRequest());

        // when
        final Try<Void> result = externalShoppingRegistry
                .sendNotification(Randomize.random(PurchaseItemsRequestDTO.class), Randomize.random(PurchaseItemsResponseDTO.class));

        // and
        mockServer.verify();

        // then
        assertTrue(result.isFailure());
    }

    @Test
    public void externalServerRespondWithServerError() {
        // given
        mockServer.expect(once(), requestTo("http://localhost:8081/api/notification/purchase"))
                .andRespond(withServerError());

        // when
        final Try<Void> result = externalShoppingRegistry
                .sendNotification(Randomize.random(PurchaseItemsRequestDTO.class), Randomize.random(PurchaseItemsResponseDTO.class));

        // and
        mockServer.verify();

        // then
        assertTrue(result.isFailure());
    }

}