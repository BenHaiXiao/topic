package com.github.benhaixiao.topic.admin.controller;

import com.github.benhaixiao.topic.admin.util.FileUploadService;
import com.github.benhaixiao.topic.admin.util.SlothResponse;
import com.github.benhaixiao.topic.shared.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author xiaobenhai
 */
@Controller
@RequestMapping("/sloth")
public class Fsb2Controller {
    private static final Logger LOG = LoggerFactory.getLogger(Fsb2Controller.class);
    @Autowired
    FileUploadService bs2UploadService;
    /**
     * 小文件上传
     *
     * @param file
     * @return
     * @throws IOException
     * @throws Exception
     */
    @RequestMapping(value = "fsbs2", method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse saveFsBs2(@RequestParam("file") MultipartFile file) throws IOException, Exception {
        String filePath = null;
        String fileName = null;
        long size = file.getSize();
        fileName = file.getOriginalFilename();
        filePath = bs2UploadService.uploadPicToBs2(file.getBytes(), fileName);
        return new SlothResponse().code(Status.SUCCESS).add("filePath",filePath).add("fileSize",Long.toString(size)).add("fileName", fileName);
    }
}
