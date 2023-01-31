package shop.cazait.domain.cafe.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.cazait.domain.cafe.dto.CoordinateVO;

@Service
public class CoordinateService {

    private String API_KEY = "3537aa85353d26f92022e7c3b311e2a6";
    private String baseUrl = "https://dapi.kakao.com";
    private String uri = "/v2/local/search/address.json";

    public CoordinateVO getCoordinateFromAddress(String address) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonString = WebClient.builder()
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(builder -> builder.path(uri)
                        .queryParam("query", address)
                        .build()
                )
                .header("Authorization", "KakaoAK " + API_KEY)
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                }).block();

        CoordinateVO coordinateVO  = mapper.readValue(jsonString, CoordinateVO.class);
        return coordinateVO;

    }

}
