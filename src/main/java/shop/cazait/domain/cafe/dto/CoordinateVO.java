package shop.cazait.domain.cafe.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordinateVO {

    private List<Documents> documents = new ArrayList<>();

}
