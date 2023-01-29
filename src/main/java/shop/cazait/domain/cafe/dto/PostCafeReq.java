package shop.cazait.domain.cafe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "카페 정보 등록 및 수정 Request", description = "카페 등록 및 수정 시 필요한 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeReq {

    @ApiModelProperty(value = "이름", example = "롬곡")
    @NotBlank(message = "카페 이름을 입력해주세요.")
    private String name;
    @ApiModelProperty(value = "위치", example = "서울시 중구")
    @NotBlank(message = "카페 위치를 입력해주세요.")
    private String location;
    @ApiModelProperty(value = "경도", example = "127.543215")
    private double longitude;
    @ApiModelProperty(value = "위도", example = "36.987561")
    private double latitude;

    @Builder
    public PostCafeReq(String name, String location, double longitude, double latitude) {
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
