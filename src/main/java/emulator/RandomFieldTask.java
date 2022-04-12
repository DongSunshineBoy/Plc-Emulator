package emulator;

import org.apache.commons.lang3.ClassUtils;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/29 03-29 15:30
 * @Description: day8
 * @Version 1.0
 */

public class RandomFieldTask extends Thread implements AbstractTask {



    private Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public  void processBoolean() {
        try {
            TimeUnit.MILLISECONDS.sleep(field.getInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int random = (int) (Math.random() * 2) + 1;
        this.field.valueQueue.add(random != 1);
    }


    @Override
    public void run() {
        while (true) {
            if (this.field.getValue() instanceof Boolean) {
                this.processBoolean();
            }else {
              processField(this.field.isPositive);
            }
        }

    }


    public void processField(Boolean isPositive) {

        while (true) {

            Random random = new Random();

            if (isPositive) {
                String endValue = this.field.getEndValue().toString();
                String startValue = this.field.getStartValue().toString();

                boolean isShort = (CommonUtils.isShort(endValue) && CommonUtils.isShort(startValue))
                        && !CommonUtils.isInteger(endValue) || !CommonUtils.isInteger(startValue)
                        && !CommonUtils.isLong(endValue) || !CommonUtils.isLong(startValue);

                boolean isInteger = (CommonUtils.isInteger(endValue) && CommonUtils.isInteger(startValue))
                        && !CommonUtils.isShort(endValue) || !CommonUtils.isShort(startValue)
                        && !CommonUtils.isLong(endValue) || !CommonUtils.isLong(startValue);

                if (isShort) {

                    int max = Integer.parseInt(endValue);
                    int min = Integer.parseInt(startValue);
                    max  = Math.max(max, min);
                    short value = (short) ((short) ThreadLocalRandom.current().nextInt(min, max + 1) & 0x7FFF);
                    this.field.valueQueue.add(value);


                }else if(isInteger){

                    int max = Integer.parseInt(endValue);
                    int min = Integer.parseInt(startValue);
                    max  = Math.max(max, min);
                    int value = ThreadLocalRandom.current().nextInt(min, max + 1);

                    this.field.valueQueue.add(value);

                }else {

                    long max = Long.parseLong(endValue);
                    long min = Long.parseLong(startValue);
                    max  = Math.max(max, min);
                    long value = ThreadLocalRandom.current().nextLong(max - min) + min;

                    this.field.valueQueue.add(value);

                }

            }else {
                double max = Double.parseDouble(this.field.getEndValue().toString());
                double min = Double.parseDouble(this.field.getStartValue().toString());
                double value = min + random.nextDouble() * (max - min);
                Integer maxDecimalBit = CommonUtils.getMaxDecimalBit(max, min);
                this.field.numberFormat.setMaximumFractionDigits(maxDecimalBit);

                double formatValue = Double.parseDouble(this.field.numberFormat.format(value));

                this.field.valueQueue.add(formatValue);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(field.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getValue()  {
        Object value = null;
        try {
            value = this.getField().getValueQueue().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (value == null) {
            return new Object();
        }
        return value;
    }

}

