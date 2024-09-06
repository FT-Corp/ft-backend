package com.spring.ftbackend.openAI.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class S3UploadService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3에 파일 업로드
     *
     * @param file 업로드할 MultipartFile
     * @return 업로드된 파일의 S3 URL
     * @throws IOException 파일 업로드 실패 시 발생
     */
    // S3 에 파일 업로드하고 업로드된 파일의 URL을 반환(String)
    public String uploadFileFromUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream()) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(connection.getContentLengthLong());
            metadata.setContentType(connection.getContentType());

            // 파일을 S3 버킷에 업로드
            s3Client.putObject(bucketName, fileName, inputStream, metadata);

            // 업로드된 파일의 URL 반환
            return s3Client.getUrl(bucketName, fileName).toString();
        }
    }
}
