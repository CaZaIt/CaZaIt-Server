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
    @ApiModelProperty(value = "주소", example = "서울시 중구")
    @NotBlank(message = "카페 위치를 입력해주세요.")
    private String address;

    @Builder
    public PostCafeReq(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
