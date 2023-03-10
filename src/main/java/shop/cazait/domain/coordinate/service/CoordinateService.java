package shop.cazait.domain.coordinate.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.cazait.domain.coordinate.dto.CoordinateVO;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.coordinate.entity.Coordinate;

@Service
public class CoordinateService {

    @Value("${api_key}")
    private String apiKey;
    private String baseUrl = "https://dapi.kakao.com";
    private String uri = "/v2/local/search/address.json";

    public Coordinate getCoordinate(PostCafeReq cafeReq) throws JsonProcessingException {
        CoordinateVO coordinateVO = getCoordinateFromAddress(cafeReq.getAddress());
        Coordinate coordinate = Coordinate.builder()
                .longitude(coordinateVO.getDocuments().get(0).getLongitude())
                .latitude(coordinateVO.getDocuments().get(0).getLatitude())
                .build();
        return coordinate;
    }

    private CoordinateVO getCoordinateFromAddress(String address) throws JsonProcessingException {

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
                .header("Authorization", "KakaoAK " + apiKey)
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                }).block();

        CoordinateVO coordinateVO  = mapper.readValue(jsonString, CoordinateVO.class);
        return coordinateVO;

    }
}
