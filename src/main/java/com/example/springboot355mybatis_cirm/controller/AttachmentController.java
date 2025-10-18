package com.example.springboot355mybatis_cirm.controller;

import com.example.springboot355mybatis_cirm.entity.Attachment;
import com.example.springboot355mybatis_cirm.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attach")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam(required = false) Integer articleId) {
        return attachmentService.getAllAttachment(articleId);
    }
}
