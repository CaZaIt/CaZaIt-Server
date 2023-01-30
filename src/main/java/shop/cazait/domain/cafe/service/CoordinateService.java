package shop.cazait.domain.cafe.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.cazait.domain.cafe.dto.CoordinateVO;

@Service
public class CoordinateService {

    private final String API_KEY = "KakaoAK ";
    private final String BASE_URL = "https://dapi.kakao.com";
    private final String URI = "/v2/local/search/address.json";
    
    public CoordinateVO getCoordinateFromAddress(String address) {

        CoordinateVO coordinateVO = WebClient.builder()
                .baseUrl(BASE_URL)
                .build()
                .get()
                .uri(builder -> builder.path(URI)
                        .queryParam("query", address)
                        .build()
                )
                .header("Authorization", API_KEY)
                .retrieve()
                .bodyToMono(CoordinateVO.class)
                .block();

        return coordinateVO;

    }
}
