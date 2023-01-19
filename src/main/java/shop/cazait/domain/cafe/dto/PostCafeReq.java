package shop.cazait.domain.cafe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import shop.cazait.domain.congestion.entity.Congestion;

@ApiModel(value = "PostCafeReq / 카페 정보", description = "카페 등록 시 필요한 dto")
@Getter
public class PostCafeReq {

    @ApiModelProperty(value = "카페 혼잡도")
    private final Congestion congestion;
    @ApiModelProperty(value = "이름", example = "롬곡")
    private final String name;
    @ApiModelProperty(value = "위치", example = "서울시 중구")
    private final String location;
    @ApiModelProperty(value = "경도", example = "127.543215")
    private final double longitude;
    @ApiModelProperty(value = "위도", example = "36.987561")
    private final double latitude;

    @Builder
    public PostCafeReq(Congestion congestion, String name, String location, double longitude, double latitude) {
        this.congestion = congestion;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
