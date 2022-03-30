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
    public RAMFieldTask process(List<String> params) {
        String interval = params.get(0);
        String startValue = params.get(1);
        String endValue = params.get(2);
        String offset = params.get(3);
        if (CommonUtils.isDouble(startValue)
                || CommonUtils.isDouble(endValue)
                || CommonUtils.isDouble(offset)) {
            return processData(Double.parseDouble(startValue), Double.parseDouble(offset), Double.parseDouble(endValue), Integer.parseInt(interval), false);
        }
        return processData(Long.parseLong(startValue), Long.parseLong(offset), Long.parseLong(endValue), Integer.parseInt(interval), true);
    }

    @Override
    public RAMFieldTask createFiledTask(List<String> params) {
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
