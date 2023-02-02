package shop.cazait.domain.checklog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.checklog.entity.CheckLog;

@Schema(description = "등록한 조회 기록 Response : 등록한 조회 기록 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostCheckLogRes {

    @Schema(description = "조회 기록 ID", example = "1")
    private Long cafeVisitId;

    @Schema(description = "사용자 닉네임", example = "root")

    private String nickName;

    @Schema(description = "조회한 카페 이름", example = "롬곡")
    private String cafeName;

    public static PostCheckLogRes of(CheckLog visitLog) {
        return PostCheckLogRes.builder()
                .cafeVisitId(visitLog.getId())
                .nickName(visitLog.getUser().getNickname())
                .cafeName(visitLog.getCafe().getName())
                .build();
    }

}
