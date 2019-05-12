package cn.judgchen.cisp.common.code;

public class ConstanCode {
    public static final ErrorCode SYSTEM_ERROR = new ErrorCode(-1, "服务器繁忙,请稍后再试");
    public static final ErrorCode PARAMETER_CHECK_FAILURE = new ErrorCode(1, "参数校验失败");

    public static final ErrorCode EMAIL_SUFFIX_NONSUPPORT = new ErrorCode(1000, "邮箱后缀不支持");
    public static final ErrorCode USER_HAS_REGISTERED = new ErrorCode(1001, "用户已经注册");
    public static final ErrorCode SEND_MAIL_FAILED = new ErrorCode(1002, "发送邮件失败");
    public static final ErrorCode IDENTITY_CHECK_FAILED = new ErrorCode(1003, "用户未注册");
    public static final ErrorCode WRONG_PASSWORD = new ErrorCode(1004, "密码不正确");
    public static final ErrorCode FREQUENT_OPERATION = new ErrorCode(1005, "操作频繁，");
    public static final ErrorCode RECORD_DOES_NOT_EXIST = new ErrorCode(1006,"记录不存在");
    public static final ErrorCode ORDER_ALREADY_EXISTS = new ErrorCode(1007,"订单已存在");
    public static final ErrorCode RECORD_HAS_EXISTED = new ErrorCode(1007,"记录已存在");
}
