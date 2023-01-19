package shop.cazait.domain.cafemenu.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public class PostCafeMenuRes {

    private Long menuId;
    private String name;
    private int price;
    private String imageUrl;

    public static List<PostCafeMenuRes> of() {

    }

}
