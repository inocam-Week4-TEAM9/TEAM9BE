package com.example.inobao.global.utils.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class S3Utils {

    private final AwsUtils awsUtils;

    public List<String> uploadFile(List<MultipartFile> files) {

        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            fileUrls.add(awsUtils.uploadFileV1(multipartFile));
        }

        return fileUrls;
    }

    public Map<String, Boolean> deleteFile(List<String> urls) {
        Map<String, Boolean> result = urls
                .stream()
                .collect(Collectors.toMap(
                        url -> url,
                        v -> Boolean.FALSE
                ));

        for (String url : urls) {
            result.put(url, deleteFile(url));
        }
        return result;
    }

    public boolean deleteFile(String url) {
        String fileName = url.split("\\/")[3];
        return awsUtils.deleteFile(fileName);
    }
}
