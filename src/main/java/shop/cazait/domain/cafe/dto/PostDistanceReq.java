package shop.cazait.domain.cafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "카페와의 거리, 정렬 기준, 제한 거리 Request : 카페와의 거리 계산, 정렬, 제한 거리 내 카페 조회 시 필요한 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDistanceReq {

    @Schema(description = "경도", example = "127.543215")
    private String longitude;

    @Schema(description = "위도", example = "36.987561")
    private String latitude;

    @Schema(description = "정렬 기준(congestion: 혼잡도순, distance: 거리순)", example = "congestion")
    private String sort;

    @Schema(description = "제한 거리(0일 때는 모든 카페 출력), 해당 거리 내에 있는 카페 전달, 단위는 m(미터)", example = "500")

    private String limit;

    @Builder
    public PostDistanceReq(String longitude, String latitude, String sort, String limit) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.sort = sort;
        this.limit = limit;
    }
}
