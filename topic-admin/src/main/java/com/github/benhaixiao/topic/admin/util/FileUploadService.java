package com.github.benhaixiao.topic.admin.util;

import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 *
 */
@Service
public class FileUploadService {
    public String uploadPicToBs2(byte[] bytes, String fileName) {
            String filePath ="http://screenshot.dwstatic.com/weibo/wb-bdf03c51-2ed4-443f-8b2c-3b6e5d05e8d7?imageview/4/1/w/560/h/560";

        return filePath;
    }

}
