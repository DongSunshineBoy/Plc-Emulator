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
                Integer max = Integer.valueOf(this.field.getEndValue().toString());
                Integer min = Integer.valueOf(this.field.getStartValue().toString());
                this.field.setValue(random.nextInt(max - min) + min);
            }else {
                Double max = Double.valueOf(this.field.getEndValue().toString());
                Double min = Double.valueOf(this.field.getStartValue().toString());
                this.field.setValue((min + random.nextDouble() * (max - min)));
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

