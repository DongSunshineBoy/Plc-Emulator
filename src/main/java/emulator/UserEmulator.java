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
    public AbstractTask createFiledTask(List<Object> params) {
       return this.process(params);
    }

    @Override
    public AbstractTask process(List<Object> params) {

        Executor executor = Executors.newCachedThreadPool();

        UserFiledTask userFiledTask = new UserFiledTask();
        Object interval = params.get(0);
        List<Object> userParams = new LinkedList<>();
        for (int i = 1; i < params.size(); i++) {
            userParams.add(params.get(i));
        }
        Field field = new Field();
        field.setInterval(Integer.valueOf(interval.toString()));
        userFiledTask.setUserData(userParams);
        userFiledTask.setField(field);
        executor.execute(userFiledTask);
        return userFiledTask;
    }

}
