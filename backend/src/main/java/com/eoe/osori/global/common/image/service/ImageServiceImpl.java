package com.eoe.osori.global.common.image.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eoe.osori.global.advice.error.exception.ImageException;
import com.eoe.osori.global.advice.error.info.ImageErrorInfo;
import com.eoe.osori.global.common.image.dto.ImagePathElement;
import com.eoe.osori.global.common.image.dto.PostImageRemovalRequestDto;
import com.eoe.osori.global.common.image.dto.PostImageUploadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    /**
     * S3 bucket 이름
     */
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;

    /**
     * S3에 파일 업로드
     * @param multipartFiles List<MultipartFile>
     * @return PostImageUploadResponseDto
     * @see AmazonS3Client
     */
    @Override
    public PostImageUploadResponseDto uploadFiles(List<MultipartFile> multipartFiles) {

        if (multipartFiles.size() == 0) {
            throw new ImageException(ImageErrorInfo.IMAGE_NOT_FOUND_ERROR);
        }

        // 파일의 정보를 담을 리스트 만들고
        List<ImagePathElement> path = new ArrayList<>();

        // 업로드하는 파일 경로 만들기
        // getFolderName에서 폴더 이름을 만듬
        String uploadFilePath = getFolderName();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();

            // 파일이름을 기반으로 uuid로 변환 => 즉 uploadFileName은 uuid
            String uploadFileName = getUuidFileName(originalFileName);

            // 로컬에 파일을 저장하지 않고 업로드 하기
            ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentType(multipartFile.getContentType());

            objectMetadata.setContentLength(multipartFile.getSize());

            String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

            // try() 안에 있는 inputStream이 PutObjectRequest의 세번째에 들어가는 값
            try(InputStream inputStream = multipartFile.getInputStream()) {

                // TODO : 외부에 공개하는 파일인 경우 Public Read 권한을 추가, ACL 확인
                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

            } catch (IOException e) {
                // 파일 읽어오기 에러
                throw new ImageException(ImageErrorInfo.IMAGE_FILE_IO_ERROR);
            }catch (AmazonS3Exception e) {
                // Amazon S3 서비스에서 반환한 예외 처리
                throw new ImageException(ImageErrorInfo.AMAZON_S3_SERVICE_UPLOAD_ERROR);
            } catch (AmazonClientException e) {
                // Amazon S3 클라이언트에서 발생한 예외 처리
                throw new ImageException(ImageErrorInfo.AMAZON_S3_CLIENT_UPLOAD_ERROR);
            }
            ImagePathElement imagePathElement = ImagePathElement.from(keyName);

            path.add(imagePathElement);
        }

        return PostImageUploadResponseDto.from(path);
    }

    /**
     *
     * @param postImageRemovalRequestDto PostImageRemovalRequestDto
     * @return void
     * @see AmazonS3Client
     */
    @Override
    public void deleteFiles(PostImageRemovalRequestDto postImageRemovalRequestDto) {
        List<String> imageUrls = postImageRemovalRequestDto.getImageUrls();

        if (imageUrls.size() == 0) {
            throw new ImageException((ImageErrorInfo.IMAGE_URL_NOT_FOUND_ERROR));
        }

        for (String imageUrl : imageUrls) {
            try {
                // S3 버킷에 해당 파일 경로에 파일이 있는지 확인
                boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, imageUrl);

                if (isObjectExist) {
                    amazonS3Client.deleteObject(bucketName, imageUrl);
                } else {
                    log.info("S3에 해당 이미지가 존재하지 않습니다. imageUrl = " + imageUrl);
                    continue;
                }
            } catch (AmazonS3Exception e) {
                // Amazon S3 서비스에서 반환한 예외 처리
                throw new ImageException(ImageErrorInfo.AMAZON_S3_SERVICE_DELETE_ERROR);
            } catch (AmazonClientException e) {
                // Amazon S3 클라이언트에서 발생한 예외 처리
                throw new ImageException(ImageErrorInfo.AMAZON_S3_CLIENT_DELETE_ERROR);
            }
        }


    }

    /**
     * UUID 파일명 반환
     * @param fileName String
     */
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

    /**
     * 년/월/일 폴더명 반환
     */
    public String getFolderName() {
        // SimpleDateFormat을 사용하면 날짜를 원하는 형식으로 얻어 올 수 있다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }
}
