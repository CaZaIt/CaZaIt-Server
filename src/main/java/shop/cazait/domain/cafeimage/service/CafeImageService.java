package shop.cazait.domain.cafeimage.service;

import static shop.cazait.global.error.status.ErrorStatus.*;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.dto.GetCafeImageRes;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.repository.CafeImageRepository;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.common.service.AwsS3Service;
import shop.cazait.global.error.status.ErrorStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImageService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;
    private final MasterRepository masterRepository;
    private final AwsS3Service awsS3Service;

    public void addCafeImage(Long cafeId, Long masterId, List<MultipartFile> imageFiles) throws CafeException {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));
        Master master = masterRepository.findById(masterId).orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));
        if (!(master.getCafe().getId().equals(cafe.getId()))) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }

        if (imageFiles != null) {
            List<String> imageUrl = getImageUrl(imageFiles);
            saveImageUrl(cafe, imageUrl);
        }
    }

    private List<String> getImageUrl(List<MultipartFile> imageFiles) {
        List<String> imageUrl = imageFiles.stream()
                .map(imageFile -> {
                    try {
                        return awsS3Service.uploadImage(imageFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return imageUrl;
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

    public List<GetCafeImageRes> readCafeImageList(Long cafeId) {
        List<CafeImage> cafeImageList = cafeImageRepository.findByCafeId(cafeId);
        List<GetCafeImageRes> getCafeImageResList = new ArrayList<>();
        for (CafeImage cafeImage : cafeImageList) {
            GetCafeImageRes getCafeImageRes = GetCafeImageRes.of(cafeImage);
            getCafeImageResList.add(getCafeImageRes);
        }
        return getCafeImageResList;
    }

    public void deleteCafeImage(Long cafeImageId, Long masterId) throws CafeImageException {
        CafeImage cafeImage = cafeImageRepository.findById(cafeImageId).orElseThrow(() -> new CafeImageException(ErrorStatus.NOT_EXIST_IMAGE));
        Master master = masterRepository.findById(masterId).orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));
        if (!(master.getCafe().getId().equals(cafeImage.getCafe().getId()))) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }
        cafeImageRepository.deleteById(cafeImageId);
    }
}
