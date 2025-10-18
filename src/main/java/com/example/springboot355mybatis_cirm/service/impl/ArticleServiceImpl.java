package com.example.springboot355mybatis_cirm.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot355mybatis_cirm.entity.Article;
import com.example.springboot355mybatis_cirm.entity.Attachment;
import com.example.springboot355mybatis_cirm.dto.ArticleInfo;
import com.example.springboot355mybatis_cirm.util.TagLibrary;
import com.example.springboot355mybatis_cirm.mapper.ArticleMapper;
import com.example.springboot355mybatis_cirm.mapper.AttachmentMapper;
import com.example.springboot355mybatis_cirm.service.ArticleService;
import com.example.springboot355mybatis_cirm.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    private TagLibrary tagLibrary;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllArticle(Integer authorId,Boolean isDelete, Integer status){
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<ArticleInfo> articles = articleMapper.getAllArticleInfo(authorId,isDelete,status);
        if(articles.isEmpty()){
            return new ResponseEntity<>(mapList, HttpStatus.NO_CONTENT);
        }
        for (ArticleInfo article : articles) {
            Map<String, Object> map = new HashMap<>();
            map.put("articleId", article.getArticleId());
            map.put("title", article.getTitle());
            map.put("content", article.getContent());
            map.put("summary", article.getSummary());
            map.put("categoryId", article.getCategoryId());
            map.put("authorId", article.getAuthorId());
            map.put("status", article.getStatus());
            map.put("isTop", article.getIsTop());
            map.put("publishTime", article.getPublishTime());
            map.put("createTime", article.getCreateTime());
            map.put("updateTime", article.getUpdateTime());
            map.put("categoryName", article.getCategoryName());
            map.put("userName", article.getUserName());
            map.put("realName", article.getRealName());
            map.put("tag", article.getTag());
            map.put("pageViews", article.getPageViews());
            map.put("coverImagePath", article.getCoverImagePath());
            mapList.add(map);
        }
        return ResponseEntity.ok(mapList);
    }

    @Override
    public ResponseEntity<Map<String, Object>> publishArticle(@RequestPart("article") Article article,
                                                              @RequestPart(required = false) MultipartFile coverImage,
                                                              @RequestPart(required = false) List<MultipartFile> files){
        Map<String, Object> map = new HashMap<>();
        Article newArticle = new Article();

        // 复制文章信息
        newArticle.setTitle(article.getTitle());
        newArticle.setContent(article.getContent());
        newArticle.setSummary(article.getSummary());
        newArticle.setCategoryId(article.getCategoryId());
        newArticle.setAuthorId(article.getAuthorId());
        newArticle.setStatus(article.getStatus());

        String tags = tagLibrary.calcTags(
                article.getTitle(),
                article.getSummary(),
                article.getContent());
        newArticle.setTag(tags);

        /* -------- ① 封面图：由 String base64 改为 MultipartFile 处理 -------- */
        if (coverImage != null && !coverImage.isEmpty()) {
            // 直接从文件名截取扩展名（省去之前 MIME→后缀的 switch）
            String originalName = coverImage.getOriginalFilename();
            String ext = originalName == null ? "jpg"
                    : originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
            String uploadPath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "image";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                String uniqueFileName = UUID.randomUUID() + "." + ext;
                File imageFile = new File(uploadPath + File.separator + uniqueFileName);
                coverImage.transferTo(imageFile);          // 简单落盘

                newArticle.setCoverImagePath(uploadPath + File.separator + uniqueFileName);
                newArticle.setCoverImageType(ext);             // 保存后缀即可
            } catch (IOException e) {
                System.out.println("封面上传失败: " + e.getMessage());
                map.put("success", false);
                map.put("message", "封面文件上传失败");
                return ResponseEntity.badRequest().body(map);
            }
        }
        else {
            /* ---------- 就地生成默认封面图（不抽方法） ---------- */
            try {
                String defaultDir = System.getProperty("user.dir") + File.separator
                        + "uploads" + File.separator + "image";
                File dir = new File(defaultDir);
                if (!dir.exists()) dir.mkdirs();

                File defaultFile = new File(dir, "cover_default.png");
                if (!defaultFile.exists()) {          // 只生成一次
                    // 16:9 纯色图
                    int width = 640, height = 360;
                    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(new Color(0, 188, 212));   // 主色调
                    g2d.fillRect(0, 0, width, height);
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("SansSerif", Font.BOLD, 48));
                    FontMetrics fm = g2d.getFontMetrics();
                    String txt = "封面";
                    int sw = fm.stringWidth(txt);
                    g2d.drawString(txt, (width - sw) / 2, height / 2 + 18);
                    g2d.dispose();

                    ImageIO.write(image, "png", defaultFile);
                }

                newArticle.setCoverImagePath(defaultFile.getAbsolutePath() + File.separator + defaultFile.getName());
                newArticle.setCoverImageType("png");
            } catch (Exception e) {
                // 生成失败就抛运行时异常，事务会回滚
                throw new RuntimeException("默认封面生成失败", e);
            }
        }
        /* ------------------------------------------------------------------ */



        System.out.println("insert:"+newArticle);
        int rows = articleMapper.insert(newArticle);
        /* -------- ② 附件：新增 List<MultipartFile> 处理逻辑 -------------- */
        attachmentService.saveAttachments(newArticle.getArticleId(),files,newArticle.getAuthorId());
        /* ------------------------------------------------------------------ */
        if(rows != 1){
            map.put("success", false);
            map.put("message", "发布文章失败");
            return ResponseEntity.badRequest().body(map);
        }
        map.put("success", true);
        return ResponseEntity.ok(map);
    }


    @Override
    public ResponseEntity<Map<String, Object>> updateArticle(@RequestPart("article") Article dto,
                                                              @RequestPart(required = false) MultipartFile coverImage,
                                                              @RequestPart(required = false) List<MultipartFile> attachments){
        Map<String, Object> map = new HashMap<>();
        /* 1. 查旧数据，取旧文件路径（后面清理用） */
        Article old = articleMapper.selectById(dto.getArticleId());
        if (old == null) {
            map.put("success", false);
            map.put("message", "文章不存在");
            return ResponseEntity.badRequest().body(map);
        }
        String oldCoverPath = old.getCoverImagePath();

        /* 2. 复制新字段（前端只传需要改的） */
        Article entity = new Article();
        entity.setArticleId(dto.getArticleId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setSummary(dto.getSummary());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAuthorId(dto.getAuthorId());
        entity.setStatus(dto.getStatus());
        entity.setTag(tagLibrary.calcTags(dto.getTitle(), dto.getSummary(), dto.getContent()));

        /* 3. 处理封面（只有上传了新图才覆盖） */
        if (coverImage != null && !coverImage.isEmpty()) {
            // 3-1 删除旧封面（非默认图才删）
            if (StrUtil.isNotBlank(oldCoverPath) && !oldCoverPath.contains("default")) {
                FileUtil.del(oldCoverPath);
            }

            // 3-2 保存新图（就地写，不抽方法）
            try {
                String originalName = coverImage.getOriginalFilename();
                String ext = originalName == null ? "jpg"
                        : originalName.substring(originalName.lastIndexOf(".") + 1);
                String dir = System.getProperty("user.dir") + File.separator
                        + "uploads" + File.separator + "image";
                FileUtil.mkdir(dir);
                String fileName = UUID.randomUUID() + "." + ext.toLowerCase();
                File target = new File(dir, fileName);
                coverImage.transferTo(target);

                entity.setCoverImagePath(target.getAbsolutePath());
                entity.setCoverImageType(ext);
            } catch (IOException e) {
                throw new RuntimeException("封面保存失败", e);
            }
        } else {
            // 未传图 → 保留原路径
            entity.setCoverImagePath(oldCoverPath);
            entity.setCoverImageType(old.getCoverImageType());
        }

        /* 4. 更新文章表 */
        int rows = articleMapper.updateById(entity);
        if (rows != 1) {
            map.put("success", false);
            map.put("message", "更新文章失败");
            return ResponseEntity.badRequest().body(map);
        }

        /* 5. 处理附件（前端传了新文件就“全量替换”） */
        // 5-1 查旧附件路径
        List<Attachment> oldAtts = attachmentMapper.selectList(new QueryWrapper<Attachment>().eq("article_id", dto.getArticleId()));
        List<String> oldAttPaths = oldAtts.stream().map(Attachment::getFilePath).toList();

        // 5-2 先删旧附件文件（非事务，失败只记日志）
        oldAttPaths.forEach(p -> {
            try { FileUtil.del(p); } catch (Exception e) {
                log.warn("删除旧附件失败: " + p + ',' + e.getMessage());
            }
        });

        // 5-3 删旧记录
        attachmentMapper.delete(new QueryWrapper<Attachment>().eq("article_id", dto.getArticleId()));
        if (attachments != null && !attachments.isEmpty()) {
            // 5-4 保存新附件
            attachmentService.saveAttachments(entity.getArticleId(), attachments,entity.getAuthorId());
        }

        map.put("success", true);
        return ResponseEntity.ok(map);
    }


    @Override
    public ResponseEntity<Map<String, Object>> deleteArticle(Integer articleId){
        Map<String,Object> map = new HashMap<>();
        /* 1. 读文章，取封面路径 */
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            map.put("success", false);
            map.put("message", "文章不存在");
            return ResponseEntity.badRequest().body(map);
        }
        int rows = attachmentMapper.update(null,new UpdateWrapper<Attachment>().eq("article_id", articleId).set("is_delete", 1));
        System.out.println(rows);
        rows = articleMapper.update(null,new UpdateWrapper<Article>().eq("article_id", articleId).set("is_delete", 1));
        if(rows != 1){
            map.put("success", false);
            map.put("message", "删除文章失败");
            return ResponseEntity.badRequest().body(map);
        }
        map.put("success", true);
        return ResponseEntity.ok(map);
    }


    @Override
    public ResponseEntity<Map<String, Object>> submitArticle(Integer articleId,Integer status){
        Map<String, Object> map = new HashMap<>();
        if(status == null){
            status= 1;
        }
        int rows = articleMapper.update(
                null,                       // 实体不用传
                new UpdateWrapper<Article>()
                        .eq("article_id", articleId)
                        .set("status", status)
        );
        if (rows != 1) {
            map.put("success", false);
            map.put("message", "更新文章状态失败");
        }
        map.put("success", true);
        return ResponseEntity.ok(map);
    }
}
