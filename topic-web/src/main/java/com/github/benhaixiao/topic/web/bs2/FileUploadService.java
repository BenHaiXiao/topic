package com.github.benhaixiao.topic.web.bs2;

import com.github.benhaixiao.topic.web.exception.FileUploadException;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaobenhai
 *
 */
@Service
public class FileUploadService {

    private static ExecutorService uploadThreadPool = Executors.newFixedThreadPool(50);

    /**
     * 上传图片
     * @param fileMap
     * @return
     * @throws FileUploadException
     */
    public Map<String,String> uploadToBs2(Map<String,MultipartFile> fileMap) throws FileUploadException {
        Map<String,String> ret = Maps.newHashMap();
        return ret;
    }

}
