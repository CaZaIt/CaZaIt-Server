package shop.cazait.domain.cafemenu.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PutCafeMenuReq {

    private String name;
    private int price;
    private String imageUrl;

}
