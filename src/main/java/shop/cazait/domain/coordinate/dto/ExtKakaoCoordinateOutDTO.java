package shop.cazait.domain.coordinate.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.coordinate.entity.Documents;

@Data
@NoArgsConstructor
public class ExtKakaoCoordinateOutDTO {

    private List<Documents> documents = new ArrayList<>();

}
