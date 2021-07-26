package com.yxkong.common.exception;

/**
 * 具体的异常枚举类
 *
 * @author navyzhung
 * @date 2019/5/27-19:38
 */
public enum BizExceptionEnum implements BaseResult {

    /**
     * 未实名
     */
    NO_REAL_NAME("1100", "用户未实名"),

    /**
     * 期限缓存表数据为空
     */
    PERIOD_MAP_IS_NULL("1000", "期限缓存表数据为空"),
    /**
     * 引导贷款超市
     */
    CAPITAL_NAME_IS_NULL("0002", "贷款超市"),

    /**
     * bnh引导额度分期
     */
    BNH_CAPITAL_NAME_IS_NULL("9880", "引导额度分期"),

    GUIDE_FAIL("1000", "引导规则未通过"),

    FORBID_STATUS("1003", "停留在当前页面"),

    STATUS_TOKEN_INVALID("1008", "token失效，请重新登录!"),

    AUTH_VALIDATE_ERROR("1001", "授信项校验异常"),

    PAYMENT_METHOD_IS_NULL("1000", "该机构没有支付方式"),

    AUTH_TASK_CALLBACK("0", "风控已回流"),

    DO_NOT_REPEAT("0", "请勿重复操作!"),
    ;

    BizExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    @Override
    public String getStatus() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
