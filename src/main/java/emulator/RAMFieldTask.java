package emulator;

import java.text.NumberFormat;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 9:18
 * @Description: day8
 * @Version 1.0
 */
public class RAMFieldTask extends Thread implements AbstractTask {

    private Field field;

    private NumberFormat numberFormat = NumberFormat.getInstance();

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

    private static Integer getMaxDecimalBit(Double... doubles) {
        int maxBit = 0;
        for (int i = 0; i < doubles.length; i++) {
           String value =  doubles[i].toString();
           int bitLength = value.substring(value.indexOf(".")).length() - 1;
           if (bitLength > maxBit) {
               maxBit = bitLength;
           }
        }
        return maxBit;
    }



    @Override
    public synchronized void processField(Boolean isPositive) {
        this.field.setStartValue(this.field.getStartValue());
        if (isPositive) {
            Long value = Long.valueOf(this.field.getValue().toString());
            Long offset = Long.valueOf(this.field.getOffset().toString());
            Long startValue = Long.valueOf(this.field.getStartValue().toString());

            Long defaultValue = Long.valueOf(this.field.getValue().toString());
            Long endValue = Long.valueOf(this.field.getEndValue().toString());

            long result = value + offset;

            Object resultValue = CommonUtils.getObjectType(Long.toString(result));

            this.field.setValue(resultValue);

            if (startValue > endValue) {
                if (defaultValue <= endValue) {
                    this.field.setValue(this.getField().getStartValue());
                }
            }else {
                if (defaultValue >= endValue) {
                    this.field.setValue(this.getField().getStartValue());
                }
            }

        }else {
            Double offset = (Double) this.field.getOffset();
            Double endValue = (Double) this.field.getEndValue();
            Double startValue = (Double) this.getField().getStartValue();
            Double defaultValue = (Double) this.field.getValue();

            //取最大的小数位数作为保留计算完后的结果位数
            Integer maxDecimalBit = getMaxDecimalBit(offset, endValue, startValue);
            numberFormat.setMaximumFractionDigits(maxDecimalBit);
            this.field.setValue(Double.valueOf(numberFormat.format(defaultValue + offset)));

            if (startValue > endValue) {
                if (defaultValue <= endValue) {
                    this.field.setValue(this.getField().getStartValue());
                }
            }else {
                if (defaultValue >= endValue) {
                    this.field.setValue(this.getField().getStartValue());
                }
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
