package emulator;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 10:24
 * @Description: day8
 * @Version 1.0
 */
public class UserFiledTask extends Thread implements AbstractTask{
    private volatile Field field;
    private List<Object> userData;
    private AtomicInteger counter = new AtomicInteger(0);


    public AtomicInteger getCounter() {
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    public List<Object> getUserData() {
        return userData;
    }

    public void setUserData(List<Object> userData) {
        this.userData = userData;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


    @Override
    public synchronized void run() {
        while (true) {
            this.processField(true);
        }
    }

    @Override
    public  synchronized void processField(Boolean isPositive) {
        List<Object> defaultValue = this.getUserData();
        int end = defaultValue.size();
        int start = counter.get();
        if (start < end ) {
            this.field.setValue(defaultValue.get(counter.getAndIncrement()));
        }else {
            counter.set(0);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(field.getInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object getValue()  {
        return this.field.getValue();
    }
}
