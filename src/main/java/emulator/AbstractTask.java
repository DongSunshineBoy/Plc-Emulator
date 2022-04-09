package emulator;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 17:01
 * @Description: com.emulator.plcemulator.generator
 * @Version 1.0
 */
public interface AbstractTask {
     void processField(Boolean isPositive);
     Object getValue();
}
