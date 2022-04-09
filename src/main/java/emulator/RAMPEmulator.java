package emulator;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/29 03-29 22:02
 * @Description: day8
 * @Version 1.0
 */
public class RAMPEmulator extends AbstractEmulator {

    @Override
    public RAMFieldTask process(List<Object> params) {
        Object interval = params.get(0);
        Object startValue = params.get(1);
        Object endValue = params.get(2);
        Object offset = params.get(3);
        if (startValue instanceof Double
                || endValue instanceof Double
                || offset instanceof Double) {
            return processData(startValue, offset, endValue, Integer.valueOf(interval.toString()), false);
        }
        return processData(startValue, offset, endValue, Integer.valueOf(interval.toString()), true);
    }

    @Override
    public RAMFieldTask createFiledTask(List<Object> params) {
       return this.process(params);
    }

    private RAMFieldTask processData(Object startValue, Object offset, Object endValue, Integer interval, Boolean isPositive){

        Executor executor = Executors.newCachedThreadPool();

        RAMFieldTask randomFieldTask = new RAMFieldTask();
        Field field = new Field();
        field.setStartValue(startValue);
        field.setEndValue(endValue);
        field.setInterval(interval);
        field.setValue(startValue);
        field.setOffset(offset);
        field.setPositive(isPositive);
        randomFieldTask.setField(field);
        executor.execute(randomFieldTask);
        return randomFieldTask;
    }

}
