package emulator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 10:21
 * @Description: day8
 * @Version 1.0
 */
public class UserEmulator extends AbstractEmulator {

    @Override
    public AbstractTask createFiledTask(List<String> params) {
       return this.process(params);
    }

    @Override
    public AbstractTask process(List<String> params) {

        Executor executor = Executors.newCachedThreadPool();

        UserFiledTask userFiledTask = new UserFiledTask();
        String interval = params.get(0);
        List<Object> userParams = new LinkedList<>();
        for (int i = 1; i < params.size(); i++) {
            String value = params.get(i).trim();
            boolean isDouble = CommonUtils.isDouble(value);
            boolean isBoolean = CommonUtils.isBoolean(value);
            boolean isNumber = CommonUtils.isNumeric(value);
            if (isDouble) {
                userParams.add(Double.parseDouble(value));
            }else if (isBoolean) {
                userParams.add(Boolean.parseBoolean(value));
            }else if (isNumber) {
                userParams.add(Long.parseLong(value));
            }else {
                userParams.add(value);
            }
        }
        Field field = new Field();
        field.setInterval(Integer.parseInt(interval));
        userFiledTask.setUserData(userParams);
        userFiledTask.setField(field);
        executor.execute(userFiledTask);
        return userFiledTask;
    }

}
