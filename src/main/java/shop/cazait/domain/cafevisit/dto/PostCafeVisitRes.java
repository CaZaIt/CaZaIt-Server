package shop.cazait.domain.cafevisit.dto;

import javax.persistence.Access;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeVisitRes {

    private Long cafeVisitId;

    private String userName;

    private String cafeName;

    @Builder
    public PostCafeVisitRes(Long cafeVisitId, String userName, String cafeName) {
        this.cafeVisitId = cafeVisitId;
        this.userName = userName;
        this.cafeName = cafeName;
    }

}
