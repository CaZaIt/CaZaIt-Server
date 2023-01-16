package shop.cazait.domain.cafemenu.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PutCafeMenuRes {

    private Long cafeMenuId;
    private Long cafeId;
    private String name;
    private int price;
    private String imageUrl;

    @Builder
    public PutCafeMenuRes(Long cafeMenuId, Long cafeId, String name, int price, String imageUrl) {
        this.cafeMenuId = cafeMenuId;
        this.cafeId = cafeId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
