package com.inventory.nike.common.em;

public enum ErrorCodeEnum {
    SUCCESS("0", "请求成功"),
    ERROR("1", "请求失败"),
    NO_LOGIN("9", "未登录或会话超时，请重新登录！"),
    BAD_REQUEST("400", "参数解析失败"),
    METHOD_NOT_ALLOWED("405", "不支持当前请求方法"),
    BUSINESS("1000", "业务异常"),
    SYS_ERROR("1001", "系统异常，请联系管理员！"),
    NETWORK("1002", "网络异常"),
    ENCRYPTION("1003", "加密异常"),
    RESUBMIT("1004", "请勿重复提交请求"),
    NOT_FOUND("1005", "请求资源不存在"),
    NO_SEARCH("1006", "无查询权限"),
    NO_PERMISSIONS("1007", "对不起，您无  {module} {type} 权限，不能进行此操作！"),
    LOGIN_SECONDARY("1008", "您的账户在异地登录,如不是本人操作,请及时修改密码！"),
    LOGIN_NULL_USER("1009", "帐号或密码不能为空"),
    LOGIN_USER("1010", "帐号或密码错误"),
    LOGIN_ACCOUNT("1011", "帐号不存在"),
    LOGIN_INACTIVE("1012", "帐号未激活，请联系管理员"),
    LOGIN_PASSWORD("1013", "密码错误或加密异常"),
    ACCOUNT_LOCK("1014", "30分钟内连续5次登录错误，您的帐号暂时被锁定"),
    SQL_ERROR("1015", "SQL执行异常,请联系管理员，请确认SQL参数或语句是否异常"),
    QUERY_ERROR("1016", "查询条件异常，请修改查询条件后重试！"),
    QUERY_ERROR_TIME("1017", "时间查询条件异常不支持此关系查询！"),
    UPDATE_VES("1018", "更新失败，操作的记录已被更新"),
    UPDATE_NULL("1019", "【更新失败，{filed}字段不允许为空】"),
    UPDATE_PK("1020", "主键未标记"),
    ADD("1021", "新增数据异常"),
    UPDATE("1022", "修改数据异常"),
    DELETE("1023", "删除数据异常"),
    NOT_AUTH_TOKEN("1024", "身份认证票据authToken不允许为空"),
    LOGIN_SSO("1025", "单点登录认证失败"),
    SQL_ERROR_INFO("1026", "SQL执行异常,{info}"),
    UPDATE_VES_NOT("1027", "乐观锁不允许为空"),
    NOT_SUBMIT_KEY("1028", "表单防重submitKey不能为空"),
    FILE_TYPE_OUT("1029", "文件类型超出限制"),
    NOT_BIND("1030", "账号未绑定"),
    PARAMS_PARSE("1031", "参数转化异常"),
    NO_SEARCH_AREA("1032", "无所属区域，不能进行此操作"),
    SUBMIT_PARAMS("1033", "非法操作，请求参数异常"),
    MAX_UPLOAD_SIZE("1034", "上传文件大小超出限制"),
    RSA_FAILURE("1035", "RSA公钥失效,请刷新后重试"),
    RSA_GEN_ERROR("1036", "生成RSA异常"),
    NO_SEARCH_ORG("1037", "无所属单位，不能进行此操作"),
    NO_SEARCH_DEPT("1038", "无所属部门，不能进行此操作"),
    UPDATE_NOT_PK("1039", "更新失败，主键id不允许为空"),
    AUTH_CODE_FAILURE("1040", "authCode已失效，DDUserId查询不到用户"),
    DD_ID_USER("1041", "未查询到绑定该钉钉账号的用户信息"),
    LOGIN_EXIST("1042", "帐号已存在");

    private String code;
    private String msg;

    private ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}