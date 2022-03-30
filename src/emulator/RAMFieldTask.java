package emulator;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 9:18
 * @Description: day8
 * @Version 1.0
 */
public class RAMFieldTask extends Thread implements AbstractTask {
    private Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


    @Override
    public void run() {
        while (true) {
           this.processField(this.field.isPositive);
        }
    }

    @Override
    public synchronized void processField(Boolean isPositive) {
        this.field.setStartValue(this.field.getStartValue());
        if (isPositive) {
            this.field.setValue((Long)this.field.getValue() + (Long) this.field.getOffset());
            Long defaultValue = (Long) this.field.getValue();
            Long endValue = (Long) this.field.getEndValue();
            if (defaultValue >= endValue) {
                this.field.setValue(this.getField().getStartValue());
            }
        }else {
            this.field.setValue((Double)this.field.getValue() + (Double) this.field.getOffset());
            Double defaultValue = (Double) this.field.getValue();
            Double endValue = (Double) this.field.getEndValue();
            if (defaultValue >= endValue) {
                this.field.setValue(this.getField().getStartValue());
            }
        }
        try {
            Thread.sleep(this.field.getInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValue() {
        return this.getField().getValue();
    }

}
