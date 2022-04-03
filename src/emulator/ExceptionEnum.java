package emulator;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 11:40
 * @Description: day8
 * @Version 1.0
 */
public enum ExceptionEnum {

    VALUE_TOO_LONG("输入的数值超出了指定的范围!"),
    NOT_FOUND_FUNCTION_EXCEPTION("输入的函数名称不在给定范围!"),
    NOT_FOUND_FORMATION("输入的函数格式不正确!"),
    NOT_AVAILABLE_PARAMS_FORMATION("输入的函数参数不符合要求!");

    public String getName() {
        return message;
    }

    public void setName(String message) {
        this.message = message;
    }

    private String message;


    ExceptionEnum(String message) {
        this.message = message;
    }
}
