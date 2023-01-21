package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.PostCafeImageReq;
import shop.cazait.domain.cafe.entity.CafeImage;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafe.repository.CafeImageRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImageService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;

    public void addCafeImage(Long id, PostCafeImageReq cafeImageReq) {
        CafeImage cafeImage = CafeImage.builder()
                .cafe(cafeRepository.findById(id).get())
                .imageUrl(cafeImageReq.getImageUrl())
                .build();

        cafeImageRepository.save(cafeImage);
    }

    public void deleteCafeImage(Long id) {
        cafeImageRepository.deleteById(id);
    }
}
