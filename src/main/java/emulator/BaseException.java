package emulator;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 20:34
 * @Description: com.emulator.plcemulator.generator
 * @Version 1.0
 */
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private ExceptionEnum errMsg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;

    public ExceptionEnum getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(ExceptionEnum errMsg) {
        this.errMsg = errMsg;
    }

    public BaseException(ExceptionEnum errMsg, Object object) {
        super(String.format("%s,具体值为%s+", errMsg.getName() , object.toString()));
    }

}
