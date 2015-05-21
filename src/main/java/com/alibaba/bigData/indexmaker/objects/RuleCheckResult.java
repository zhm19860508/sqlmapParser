package com.alibaba.bigData.indexmaker.objects;


/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-12
 * Time: 下午6:32
 * rule检查结果
 */
public class RuleCheckResult {

    private String rule;

    private boolean success;

    private String detail;

    private String advice;

    private IndexAdviceResult indexMakerResult;

    public RuleCheckResult(String ruleName, boolean b) {
        this.rule = ruleName;
        this.success = b;
    }

    private String addIndexScript;

    private String dropIndexScript;

    public String getAddIndexScript() {
        if (indexMakerResult != null && indexMakerResult.getSqlIndexScriptResult() != null) {
            return indexMakerResult.getSqlIndexScriptResult().getAddIndexScript();
        } else {
            return "";
        }
    }

    public String getDropIndexScript() {
        if (indexMakerResult != null && indexMakerResult.getSqlIndexScriptResult() != null) {
            return indexMakerResult.getSqlIndexScriptResult().getDropIndexScript();
        } else {
            return "";
        }
    }


    public RuleCheckResult(String ruleName, boolean b, String detail, String advice) {
        this.rule = ruleName;
        this.success = b;
        this.detail = detail;
        this.advice = advice;
    }

    public RuleCheckResult(String ruleName, boolean b, String detail) {
        this.rule = ruleName;
        this.success = b;
        this.detail = detail;
    }


    public IndexAdviceResult getIndexMakerResult() {
        return indexMakerResult;
    }

    public void setIndexMakerResult(IndexAdviceResult indexMakerResult) {
        this.indexMakerResult = indexMakerResult;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
