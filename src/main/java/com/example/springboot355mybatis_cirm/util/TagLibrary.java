package com.example.springboot355mybatis_cirm.util;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TagLibrary {

    // 关键词 -> 标签映射表
    private static final Map<String, String> KW_TAG = new HashMap<>();

    static {
        // AI/技术类
        KW_TAG.put("人工智能", "AI");
        KW_TAG.put("深度学习", "AI");
        KW_TAG.put("机器学习", "AI");
        KW_TAG.put("神经网络", "AI");
        KW_TAG.put("自然语言处理", "AI");
        KW_TAG.put("计算机视觉", "AI");
        KW_TAG.put("大数据", "AI");
        KW_TAG.put("算法", "AI");
        KW_TAG.put("编程", "技术");
        KW_TAG.put("代码", "技术");
        KW_TAG.put("开发", "技术");
        KW_TAG.put("软件工程", "技术");
        KW_TAG.put("前端", "技术");
        KW_TAG.put("后端", "技术");
        KW_TAG.put("数据库", "技术");
        KW_TAG.put("云计算", "技术");
        KW_TAG.put("物联网", "技术");
        KW_TAG.put("区块链", "技术");
        KW_TAG.put("网络安全", "技术");

        // 学术类
        KW_TAG.put("讲座", "学术讲座");
        KW_TAG.put("学术报告", "学术讲座");
        KW_TAG.put("研讨会", "学术讲座");
        KW_TAG.put("论文", "学术");
        KW_TAG.put("科研", "学术");
        KW_TAG.put("实验", "学术");
        KW_TAG.put("课题", "学术");
        KW_TAG.put("研究生", "学术");
        KW_TAG.put("博士", "学术");
        KW_TAG.put("学术会议", "学术");
        KW_TAG.put("期刊", "学术");
        KW_TAG.put("参考文献", "学术");

        // 体育类
        KW_TAG.put("运动会", "体育");
        KW_TAG.put("篮球", "体育");
        KW_TAG.put("足球", "体育");
        KW_TAG.put("排球", "体育");
        KW_TAG.put("乒乓球", "体育");
        KW_TAG.put("羽毛球", "体育");
        KW_TAG.put("田径", "体育");
        KW_TAG.put("游泳", "体育");
        KW_TAG.put("健身", "体育");
        KW_TAG.put("比赛", "体育");
        KW_TAG.put("冠军", "体育");
        KW_TAG.put("运动员", "体育");
        KW_TAG.put("裁判", "体育");
        KW_TAG.put("训练", "体育");

        // 就业/职业类
        KW_TAG.put("招聘", "就业");
        KW_TAG.put("求职", "就业");
        KW_TAG.put("面试", "就业");
        KW_TAG.put("简历", "就业");
        KW_TAG.put("实习", "就业");
        KW_TAG.put("职业规划", "就业");
        KW_TAG.put("薪资", "就业");
        KW_TAG.put("offer", "就业");
        KW_TAG.put("就业指导", "就业");
        KW_TAG.put("校园招聘", "就业");
        KW_TAG.put("职业生涯", "就业");

        // 党建/政治类
        KW_TAG.put("党课", "党建");
        KW_TAG.put("党组织", "党建");
        KW_TAG.put("党员", "党建");
        KW_TAG.put("党支部", "党建");
        KW_TAG.put("十九大", "党建");
        KW_TAG.put("习近平新时代中国特色社会主义思想", "党建");
        KW_TAG.put("爱国主义", "党建");
        KW_TAG.put("社会主义核心价值观", "党建");
        KW_TAG.put("党史", "党建");
        KW_TAG.put("党章", "党建");
        KW_TAG.put("入党", "党建");
        KW_TAG.put("共青团", "党建");

        // 财务/经费类
        KW_TAG.put("经费", "科研经费");
        KW_TAG.put("报销", "财务");
        KW_TAG.put("预算", "财务");
        KW_TAG.put("财务处", "财务");
        KW_TAG.put("发票", "财务");
        KW_TAG.put("拨款", "财务");
        KW_TAG.put("奖学金", "财务");
        KW_TAG.put("助学金", "财务");
        KW_TAG.put("学费", "财务");
        KW_TAG.put("补助", "财务");
        KW_TAG.put("基金", "财务");

        // 校园生活类
        KW_TAG.put("宿舍", "校园生活");
        KW_TAG.put("食堂", "校园生活");
        KW_TAG.put("图书馆", "校园生活");
        KW_TAG.put("选课", "校园生活");
        KW_TAG.put("考试", "校园生活");
        KW_TAG.put("成绩", "校园生活");
        KW_TAG.put("毕业", "校园生活");
        KW_TAG.put("开学", "校园生活");
        KW_TAG.put("寒假", "校园生活");
        KW_TAG.put("暑假", "校园生活");
        KW_TAG.put("班级", "校园生活");
        KW_TAG.put("辅导员", "校园生活");
        KW_TAG.put("社团", "校园生活");

        // 文艺类
        KW_TAG.put("音乐会", "文艺");
        KW_TAG.put("话剧", "文艺");
        KW_TAG.put("美术", "文艺");
        KW_TAG.put("舞蹈", "文艺");
        KW_TAG.put("摄影", "文艺");
        KW_TAG.put("书法", "文艺");
        KW_TAG.put("展览", "文艺");
        KW_TAG.put("演唱会", "文艺");
        KW_TAG.put("艺术", "文艺");
        KW_TAG.put("绘画", "文艺");
        KW_TAG.put("文学作品", "文艺");

        // 社会实践类
        KW_TAG.put("志愿者", "社会实践");
        KW_TAG.put("支教", "社会实践");
        KW_TAG.put("社会调查", "社会实践");
        KW_TAG.put("公益", "社会实践");
        KW_TAG.put("环保", "社会实践");
        KW_TAG.put("社区服务", "社会实践");
        KW_TAG.put("义工", "社会实践");
        KW_TAG.put("扶贫", "社会实践");

        // 创新创业类
        KW_TAG.put("创业", "创新创业");
        KW_TAG.put("创新", "创新创业");
        KW_TAG.put("创业计划", "创新创业");
        KW_TAG.put("孵化器", "创新创业");
        KW_TAG.put("融资", "创新创业");
        KW_TAG.put("商业模式", "创新创业");
        KW_TAG.put("创业大赛", "创新创业");
        KW_TAG.put("初创企业", "创新创业");

        // 国际交流类
        KW_TAG.put("留学", "国际交流");
        KW_TAG.put("交换生", "国际交流");
        KW_TAG.put("国际合作", "国际交流");
        KW_TAG.put("外教", "国际交流");
        KW_TAG.put("托福", "国际交流");
        KW_TAG.put("雅思", "国际交流");
        KW_TAG.put("国际会议", "国际交流");
        KW_TAG.put("海外学习", "国际交流");
        KW_TAG.put("外语", "国际交流");

        // 心理健康类
        KW_TAG.put("心理咨询", "心理健康");
        KW_TAG.put("心理健康", "心理健康");
        KW_TAG.put("心理辅导", "心理健康");
        KW_TAG.put("压力", "心理健康");
        KW_TAG.put("焦虑", "心理健康");
        KW_TAG.put("抑郁", "心理健康");
        KW_TAG.put("情绪管理", "心理健康");
    }

    /**
     * 根据标题+摘要+内容计算标签
     * @return 逗号分隔的标签串，最多 3 个
     */
    public String calcTags(String title, String summary, String content) {
        if (title == null) title = "";
        if (summary == null) summary = "";
        if (content == null) content = "";

        // 不同部分赋予不同权重：标题(3) > 摘要(2) > 内容(1)
        String weightedText = title + " " + title + " " + title + " " +  // 标题权重3
                summary + " " + summary + " " +              // 摘要权重2
                content;                                    // 内容权重1

        String text = weightedText.toLowerCase();
        Map<String, Integer> tagScore = new LinkedHashMap<>();

        // 统计关键词频率并累加到对应标签
        KW_TAG.forEach((kw, tag) -> {
            int freq = countWithWordBoundary(text, kw.toLowerCase());
            if (freq > 0) {
                tagScore.merge(tag, freq, Integer::sum);
            }
        });

        // 应用特殊规则处理标签冲突
        applySpecialRules(tagScore, text);

        // 按分数倒序取前3
        String tags = tagScore.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));

        // 如果没有匹配到标签，返回默认标签
        return tags.isEmpty() ? "其他" : tags;
    }

    /**
     * 使用正则表达式匹配完整词语，避免部分匹配
     */
    private int countWithWordBoundary(String text, String kw) {
        // 构建正则表达式，确保匹配完整词语
        String patternStr = "(?<![\\u4e00-\\u9fa5a-zA-Z0-9])" + Pattern.quote(kw) + "(?![\\u4e00-\\u9fa5a-zA-Z0-9])";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * 应用特殊规则处理标签冲突和优先级
     */
    private void applySpecialRules(Map<String, Integer> tagScore, String text) {
        // 规则1：如果同时有AI和技术标签，且AI分数更高，则移除技术标签
        if (tagScore.containsKey("AI") && tagScore.containsKey("技术")) {
            if (tagScore.get("AI") >= tagScore.get("技术")) {
                tagScore.remove("技术");
            }
        }

        // 规则2：党建标签优先级最高，如果有党建相关词汇，确保党建标签在前
        if (tagScore.containsKey("党建")) {
            int currentScore = tagScore.get("党建");
            tagScore.put("党建", currentScore + 5); // 增加权重确保优先级
        }

        // 规则3：学术讲座和学术标签的优先级调整
        if (tagScore.containsKey("学术讲座") && tagScore.containsKey("学术")) {
            if (tagScore.get("学术讲座") > tagScore.get("学术")) {
                tagScore.remove("学术");
            }
        }

        // 可以根据需要添加更多规则
    }

    /**
     * 获取所有可用的标签列表（用于前端展示）
     */
    public Set<String> getAllAvailableTags() {
        return new HashSet<>(KW_TAG.values());
    }

    /**
     * 获取标签对应的关键词列表
     */
    public Map<String, List<String>> getTagKeywords() {
        Map<String, List<String>> tagKeywords = new HashMap<>();
        KW_TAG.forEach((kw, tag) -> {
            tagKeywords.computeIfAbsent(tag, k -> new ArrayList<>()).add(kw);
        });
        return tagKeywords;
    }

    /**
     * 手动添加新的关键词-标签映射
     */
    public void addKeywordMapping(String keyword, String tag) {
        KW_TAG.put(keyword, tag);
    }

    /**
     * 获取关键词映射表的副本
     */
    public Map<String, String> getKeywordMappings() {
        return new HashMap<>(KW_TAG);
    }

    /**
     * 增强版标签计算，可自定义权重
     */
    public String calcTagsWithWeights(String title, String summary, String content,
                                      int titleWeight, int summaryWeight, int contentWeight) {
        if (title == null) title = "";
        if (summary == null) summary = "";
        if (content == null) content = "";

        // 构建加权文本
        StringBuilder weightedText = new StringBuilder();
        for (int i = 0; i < titleWeight; i++) {
            weightedText.append(title).append(" ");
        }
        for (int i = 0; i < summaryWeight; i++) {
            weightedText.append(summary).append(" ");
        }
        for (int i = 0; i < contentWeight; i++) {
            weightedText.append(content).append(" ");
        }

        String text = weightedText.toString().toLowerCase();
        Map<String, Integer> tagScore = new LinkedHashMap<>();

        KW_TAG.forEach((kw, tag) -> {
            int freq = countWithWordBoundary(text, kw.toLowerCase());
            if (freq > 0) tagScore.merge(tag, freq, Integer::sum);
        });

        applySpecialRules(tagScore, text);

        String tags = tagScore.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));

        return tags.isEmpty() ? "其他" : tags;
    }
}
