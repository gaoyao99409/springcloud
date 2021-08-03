package jbsdemo.Enums;

/**
 * @ClassName ThemeEnum
 * @Description ThemeEnum
 * @Author gaoyao
 * @Date 2021/7/22 2:50 PM
 * @Version 1.0
 */
public enum  ThemeEnum {
    RIBEN(1, "日本"),
    OUSHI(2, "欧式"),
    KONGBU(3, "恐怖"),
    GUFENG(4, "古风"),
    t_1(5, "1"),
    t_2(6, "2"),
    t_3(7, "3"),
    t_4(8, "4"),

    ;
    int code;
    String desc;

    ThemeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
