package shop.cazait.domain.cafeimage.service;

import static shop.cazait.global.error.status.ErrorStatus.*;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.repository.CafeImageRepository;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.error.status.ErrorStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import shop.cazait.infra.s3.service.S3Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImageService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;
    private final MasterRepository masterRepository;
    private final S3Service S3Service;

    public void createCafeImage(Long cafeId, UUID masterId, List<MultipartFile> imageFiles) throws CafeException {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));

        if (!(master.getCafe().getId().equals(cafe.getId()))) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }

    }
    private void saveImageUrl(Cafe cafe, List<String> imageUrl) {

        List<CafeImage> cafeImages = imageUrl.stream()
                .map(url -> {
                    return CafeImage.builder()
                            .cafe(cafe)
                            .imageUrl(url)
                            .build();
                }).collect(Collectors.toList());

        cafeImages.forEach(cafeImage -> {
            System.out.println("Save Image Url : " + cafeImage.getImageUrl());
            cafeImageRepository.save(cafeImage);
        });

    }

    public List<String> getCafeImages(Long cafeId) {

        List<CafeImage> cafeImages = cafeImageRepository.findByCafeId(cafeId);
        List<String> result = cafeImages.stream().
                map(image -> image.getImageUrl())
                .collect(Collectors.toList());

        return result;

    }

    public void deleteCafeImage(Long cafeImageId, UUID masterId) throws CafeImageException {

        CafeImage cafeImage = cafeImageRepository.findById(cafeImageId)
                .orElseThrow(() -> new CafeImageException(ErrorStatus.NOT_EXIST_IMAGE));

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));

        if (!(master.getCafe().getId().equals(cafeImage.getCafe().getId()))) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }

        cafeImageRepository.deleteById(cafeImageId);

    }
}
