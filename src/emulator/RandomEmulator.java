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
    public RandomFieldTask createFiledTask(List<Object> fieldParams) {
        return this.process(fieldParams);
    }

    @Override
    public RandomFieldTask process(List<Object> fieldParams) {
        if (fieldParams == null ) return null;

        if (fieldParams.size() == 1) {
            Object startValue = fieldParams.get(0);
            return processData(startValue, null, null, true, false);
        }else {
            Object interval = fieldParams.get(0);
            Object startValue = fieldParams.get(1);
            Object endValue = fieldParams.get(2);
            if(startValue instanceof Double || endValue instanceof Double) {
                return processData(startValue, endValue, Integer.valueOf(interval.toString()), false, false);
            }
            return processData(startValue, endValue, Integer.valueOf(interval.toString()), false, true);
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
            field.setInterval(Integer.valueOf(startValue.toString()));
            field.setValue(false);
        }
        randomFieldTask.setField(field);
        executor.execute(randomFieldTask);

        return randomFieldTask;
    }
}
