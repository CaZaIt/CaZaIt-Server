package shop.cazait.domain.cafevisit.dto;

import lombok.Data;
import shop.cazait.domain.cafe.entity.Cafe;

@Data
public class GetCafeVisitRes {

    private String name;
    private String imageUrl;

    public GetCafeVisitRes(Cafe cafe) {
        this.name = cafe.getName();
        this.imageUrl = cafe.getImageUrl();
    }

}
