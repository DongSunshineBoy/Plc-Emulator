package emulator;



import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

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

    public static void verifyOffsetArguments(double start, double end, double step) {
        if (start > end && step > 0) {
            throw new BaseException(ExceptionEnum.INPUT_VALUE_UNAVALIABLE, step);
        }
    }


    @Override
    public synchronized void processField(Boolean isPositive) {
        this.field.setStartValue(this.field.getStartValue());
        if (isPositive) {

            Long startValue = Long.valueOf(this.field.getStartValue().toString());

            Long defaultValue = Long.valueOf(this.field.getValue().toString());
            Long endValue = Long.valueOf(this.field.getEndValue().toString());

            Long value = Long.valueOf(this.field.getValue().toString());
            Long offset = Long.valueOf(this.field.getOffset().toString());
            long result = value + offset;

            Object resultValue = CommonUtils.getObjectType(Long.toString(result));

            verifyOffsetArguments(startValue, endValue, offset);

            this.field.valueQueue.add(resultValue);

            if (startValue > endValue && offset < 0) {

                if (defaultValue <= endValue) {
                    this.field.valueQueue.add(this.getField().getStartValue());
                }
            }else {
                if (defaultValue >= endValue) {
                    this.field.valueQueue.add(this.getField().getStartValue());
                }
            }

        }else {
            Double offset = Double.parseDouble(this.field.getOffset().toString());
            Double endValue = Double.parseDouble(this.field.getEndValue().toString());
            Double startValue = Double.parseDouble(this.getField().getStartValue().toString());
            Double defaultValue = Double.parseDouble(this.field.getValue().toString());

            //取最大的小数位数作为保留计算完后的结果位数
            Integer maxDecimalBit = CommonUtils.getMaxDecimalBit(offset, endValue, startValue);
            this.field.numberFormat.setMaximumFractionDigits(maxDecimalBit);

            verifyOffsetArguments(startValue, endValue, offset);

            Double value = Double.valueOf(this.field.numberFormat.format(defaultValue + offset));
            this.field.setValue(value);
            this.field.valueQueue.add(value);

            if (startValue > endValue && offset < 0) {
                if (defaultValue <= endValue) {
                    this.field.setValue(this.getField().getStartValue());
                    this.field.valueQueue.add(this.getField().getStartValue());
                }
            }else {
                if (defaultValue >= endValue) {
                    this.field.setValue(this.getField().getStartValue());
                    this.field.valueQueue.add(this.getField().getStartValue());
                }
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(field.getInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
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
