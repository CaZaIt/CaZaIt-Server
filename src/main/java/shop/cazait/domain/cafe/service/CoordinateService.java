package shop.cazait.domain.cafe.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.cazait.domain.cafe.dto.CoordinateVO;

@Service
public class CoordinateService {

    @Value("${rest_api_key}")
    private String API_KEY;
    private String baseUrl = "https://dapi.kakao.com";
    private String uri = "/v2/local/search/address.json";

    public CoordinateVO getCoordinateFromAddress(String address) {

        CoordinateVO coordinateVO = WebClient.builder()
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(builder -> builder.path(uri)
                        .queryParam("query", address)
                        .build()
                )
                .header("Authorization", "KakaoAK " + API_KEY)
                .retrieve()
                .bodyToMono(CoordinateVO.class)
                .block();

        return coordinateVO;

    }
}
