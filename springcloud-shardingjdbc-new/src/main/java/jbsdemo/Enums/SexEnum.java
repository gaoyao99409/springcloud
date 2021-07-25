package jbsdemo.Enums;

public enum SexEnum {
    BOY(1, "男"),
    GIRL(2, "女"),
    ;

    SexEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    int code;
    String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
