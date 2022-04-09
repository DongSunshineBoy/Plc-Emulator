package emulator;

import java.util.List;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 11:35
 * @Description: day8
 * @Version 1.0
 */
public class EmulatorFactory {

    private AbstractEmulator abstractEmulator;

    private static final EmulatorFactory EMULATOR_FACTORY = new EmulatorFactory();

    private final static String RANDOM = "random";
    private final static String RAMP = "ramp";
    private final static String USER = "user";

    private EmulatorFactory(){}

    public static EmulatorFactory getInstance() {
        return EMULATOR_FACTORY;
    }

    public AbstractTask builder(String userParams) {

        String functionName = CommonUtils.getFunctionName(userParams).toLowerCase();

        switch (functionName) {
            case RANDOM:
                this.abstractEmulator = new RandomEmulator();
                break;
            case RAMP:
                this.abstractEmulator = new RAMPEmulator();
                break;
            case USER:
                this.abstractEmulator = new UserEmulator();
                break;
            default:
                throw new BaseException(ExceptionEnum.NOT_FOUND_FUNCTION_EXCEPTION, functionName);
        }
        return this.builderTask(userParams);
    }

    private AbstractTask builderTask(String userFunction) {
        List<Object> params = CommonUtils.processParams(userFunction);
        return this.abstractEmulator.createFiledTask(params);
    }

}
