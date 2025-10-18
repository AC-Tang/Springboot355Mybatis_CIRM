package com.example.springboot355mybatis_cirm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot355mybatis_cirm.entity.Attachment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public interface AttachmentService extends IService<Attachment> {
    ResponseEntity<Map<String, Object>> saveAttachments(Integer articleId, List<MultipartFile> files,Integer userId);
    ResponseEntity<List<Map<String, Object>>> getAllAttachment(@RequestParam(required = false) Integer articleId);
}
