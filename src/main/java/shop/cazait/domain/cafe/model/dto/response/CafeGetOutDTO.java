package shop.cazait.domain.cafe.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.congestion.entity.CongestionStatus;

import java.util.List;
import shop.cazait.domain.favorites.entity.FavoritesStatus;

@Schema(name = "특정 카페 정보 조회 Response", description = "카페 조회 시 얻을 수 있는 정보")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CafeGetOutDTO {

    @Schema(description = "카페 ID", example = "1")
    private UUID cafeId;

    @Schema(description = "혼잡도 상태", example = "FREE")
    private CongestionStatus congestionStatus;

    @Schema(description = "이름", example = "롬곡")
    private String name;

    @Schema(description = "위치", example = "서울특별시 광진구 군자동 광나루로17길 18")
    private String address;

    @Schema(description = "경도", example = "127.543215")
    private String longitude;

    @Schema(description = "위도", example = "36.987561")
    private String latitude;

    @Schema(description = "즐겨찾기", example = "ACTIVE")
    private FavoritesStatus favoritesStatus;

    @Schema(description = "이미지 url")
    private List<String> cafeImages;

    public static CafeGetOutDTO of(Cafe cafe, FavoritesStatus favoritesStatus, List<String> cafeImages) {
        return CafeGetOutDTO.builder()
                .cafeId(cafe.getId())
                .congestionStatus(cafe.getCongestion().getCongestionStatus())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .longitude(cafe.getCoordinate().getLongitude())
                .latitude(cafe.getCoordinate().getLatitude())
                .favoritesStatus(favoritesStatus)
                .cafeImages(cafeImages)
                .build();
    }

}

