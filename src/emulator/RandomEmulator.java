package emulator;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/29 03-29 15:29
 * @Description: day8
 * @Version 1.0
 */
public class RandomEmulator extends AbstractEmulator {

    @Override
    public RandomFieldTask createFiledTask(List<String> fieldParams) {
        return this.process(fieldParams);
    }

    @Override
    public RandomFieldTask process(List<String> fieldParams) {
        if (fieldParams == null ) return null;

        if (fieldParams.size() == 1) {
            String startValue = fieldParams.get(0);
            return processData(Integer.parseInt(startValue), null, null, true, false);
        }else {
            String interval = fieldParams.get(0);
            String startValue = fieldParams.get(1);
            String endValue = fieldParams.get(2);
            if(CommonUtils.isDouble(startValue) || CommonUtils.isDouble(endValue)) {
                return processData(Double.parseDouble(startValue), Double.parseDouble(endValue), Integer.parseInt(interval), false, false);
            }
            return processData(Integer.parseInt(startValue), Integer.parseInt(endValue), Integer.parseInt(interval), false, true);
        }
    }

    private RandomFieldTask processData(Object startValue, Object endValue, Integer interval,
                                        Boolean isBooleanType, Boolean isPositive){
        Executor executor = Executors.newCachedThreadPool();

        RandomFieldTask randomFieldTask = new RandomFieldTask();
        Field field = new Field();
        if (!isBooleanType) {
            field.setStartValue(startValue);
            field.setEndValue(endValue);
            field.setInterval(interval);
            field.setPositive(isPositive);
            field.setValue(startValue);
        }else {
            field.setInterval((Integer)startValue);
            field.setValue(false);
        }
        randomFieldTask.setField(field);
        executor.execute(randomFieldTask);

        return randomFieldTask;
    }
}
