package emulator;

import java.util.Random;

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

    public void processBoolean() {
        int random = (int) (Math.random() * 2) + 1;
        this.field.setValue(random != 1);
        try {
            Thread.sleep((field.getInterval()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                int max = (int) this.field.getEndValue();
                int min = (int) this.field.getStartValue();
                this.field.setValue((long)random.nextInt(max - min) + min);
            }else {
                double max = (double) this.field.getEndValue();
                double min = (double) this.field.getStartValue();
                this.field.setValue(((Double) min + random.nextDouble() * (max - min)));
            }

            try {
                Thread.sleep(this.field.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getValue() {
        return this.getField().getValue();
    }

}

