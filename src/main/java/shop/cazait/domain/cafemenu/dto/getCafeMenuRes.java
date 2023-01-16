package shop.cazait.domain.cafemenu.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class getCafeMenuRes {

    private String name;
    private int price;
    private String imageUrl;

    @Builder
    public getCafeMenuRes(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}
