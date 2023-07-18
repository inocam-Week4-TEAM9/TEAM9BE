package com.example.inobao.global.utils.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3FileUpload {

    private final AwsUtils awsUtils;

    public List<String> uploadImage(List<MultipartFile> files) {

        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            fileUrls.add(awsUtils.uploadFileV1(multipartFile));
        }

        return fileUrls;
    }
}
