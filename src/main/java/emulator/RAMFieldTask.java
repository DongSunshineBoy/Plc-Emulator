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
    public synchronized void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(field.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

            Long offset = Long.valueOf(this.field.getOffset().toString());

            if (startValue > endValue && offset < 0) {
                if (defaultValue <= endValue) {
                    defaultValue = Long.parseLong(this.field.getStartValue().toString());
                }
            }

            if (startValue < endValue && offset > 0) {
                if (defaultValue >= endValue) {
                    defaultValue = Long.parseLong(this.field.getStartValue().toString());
                }
            }

            long result = defaultValue + offset;

            Object resultValue = CommonUtils.getObjectType(Long.toString(result));

            verifyOffsetArguments(startValue, endValue, offset);

            if (startValue > endValue && offset < 0) {
                if (result >= endValue){
                    this.field.setValue(resultValue);
                }
            }

            if (startValue < endValue && offset > 0) {
                if (result <= endValue){
                    this.field.setValue(resultValue);
                }
            }

        }else {

            Double offset = Double.parseDouble(this.field.getOffset().toString());
            Double endValue = Double.parseDouble(this.field.getEndValue().toString());
            Double startValue = Double.parseDouble(this.field.getStartValue().toString());
            Double defaultValue = Double.parseDouble(this.field.getValue().toString());


            if (startValue > endValue && offset < 0) {
                if (defaultValue <= endValue) {
                    defaultValue = Double.parseDouble(this.field.getStartValue().toString());
                }
            }

            if (startValue < endValue && offset > 0) {
                if (defaultValue >= endValue) {
                    defaultValue = Double.parseDouble(this.field.getStartValue().toString());
                }
            }

            //取最大的小数位数作为保留计算完后的结果位数
            Integer maxDecimalBit = CommonUtils.getMaxDecimalBit(offset, endValue, startValue);
            this.field.numberFormat.setMaximumFractionDigits(maxDecimalBit);

            verifyOffsetArguments(startValue, endValue, offset);

            double result = defaultValue + offset;

            Double value = Double.valueOf(this.field.numberFormat.format(result));

            if (value > endValue && offset < 0) {
                if (result <= endValue){
                    this.field.setValue(this.field.getStartValue());
                }else {
                    this.field.setValue(value);
                }
            }

            if (value < endValue && offset > 0) {
                if (result >= endValue){
                    this.field.setValue(this.field.getStartValue());
                }else {
                    this.field.setValue(value);
                }
            }
        }

    }

    @Override
    public Object getValue()  {
        return this.field.getDefaultValue();
    }

}
