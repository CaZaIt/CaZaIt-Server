package shop.cazait.global.common.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String directory;

    /**
     * S3 파일 upload
     * 로직 : convert(로컬에 파일 생성) -> putS3(S3 업로드) -> removeNewFile(생성한 파일 삭제)
     */
    public String uploadImage(MultipartFile multipartFile) throws IOException {

        // 로컬 파일 생성
        File file = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        // 파일 이름 생성
        String fileName = directory + "/" + UUID.randomUUID();

        // S3에 파일 업로드
        String uploadFileName = putS3(file, fileName);

        // 파일 삭제
        removeNewFile(file);

        return uploadFileName;

    }

    private Optional<File> convert(MultipartFile file) throws IOException {

        String pathName = System.getProperty("user.dir");
        log.info(pathName + "/" + file.getOriginalFilename());
        File convertFile = new File(file.getOriginalFilename());

        log.info(convertFile.getAbsolutePath());
        if (convertFile.createNewFile()) {

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }

            return Optional.of(convertFile);

        }

        return Optional.empty();

    }

    private String putS3(File uploadFile, String fileName) {

        try {
            log.info("Putting object " + fileName  +" into bucket "+ bucket);
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonS3Exception e) {
            log.info(e.getMessage());
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();

    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("업로드를 위해 생성한 파일 삭제 성공");
            return;
        }
        log.info("업로드를 위해 생성한 파일 삭제 실패");
    }


}
