package com.example.springboot355mybatis_cirm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot355mybatis_cirm.entity.Attachment;
import com.example.springboot355mybatis_cirm.mapper.AttachmentMapper;
import com.example.springboot355mybatis_cirm.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllAttachment(Integer articleId) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        System.out.println(articleId);
        List<Attachment> attachments = attachmentMapper.selectList(new QueryWrapper<Attachment>().eq("article_id", articleId));
        if(attachments.isEmpty()){
            return new ResponseEntity<>(mapList, HttpStatus.NO_CONTENT);
        }
        for (Attachment attachment : attachments) {
            Map<String, Object> map = new HashMap<>();
            map.put("file_path", attachment.getFilePath());
            map.put("file_name", attachment.getFileName());
            map.put("file_size", attachment.getFileSize());
            mapList.add(map);
        }
        return ResponseEntity.ok(mapList);
    }
    @Override
    public ResponseEntity<Map<String, Object>> saveAttachments(Integer articleId, List<MultipartFile> files,Integer userId) {
        Map<String, Object> map = new HashMap<>();

        /* 1. 空校验 */
        if (files == null || files.isEmpty()) {
            map.put("success", false);
            map.put("message", "附件列表不能为空");
            return ResponseEntity.badRequest().body(map);
        }

        /* 2. 准备目录：uploads/attach/{articleId} */
        String baseDir = System.getProperty("user.dir")
                + File.separator + "uploads"
                + File.separator + "attachment";
        File dir = new File(baseDir, String.valueOf(articleId));
        if (!dir.exists()) {
            dir.mkdirs();
        }

        /* 3. 循环处理每个 MultipartFile */
        List<Attachment> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
            String storeName = UUID.randomUUID() + "." + ext;
            File targetFile = new File(dir, storeName);

            try {
                file.transferTo(targetFile);   // 简单落盘
//                System.out.println("附件保存成功：" + targetFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("附件保存失败：" + e.getMessage());
                map.put("success", false);
                map.put("message", "附件保存失败");
                return ResponseEntity.badRequest().body(map);
            }

            /* 4. 封装对象 → MyBatis-Plus 一键插入 */
            Attachment att = new Attachment();
            att.setArticleId(articleId);
            att.setFileName(originalName);
            att.setFilePath(targetFile.getAbsolutePath());
            att.setFileSize((int) file.getSize());
            att.setFileType(ext);
            att.setUploaderId(userId);
            attachments.add(att);
        }
        if(!attachments.isEmpty()) {
            boolean ok = this.saveBatch(attachments);
            if(ok) {
                map.put("success", true);
            }
            else {
                map.put("success", false);
                map.put("message", "上传文章附件失败");
                return ResponseEntity.badRequest().body(map);
            }
        }
        return ResponseEntity.ok(map);
    }
}
