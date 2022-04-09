package emulator;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/29 03-29 21:03
 * @Description: day8
 * @Version 1.0
 */
public class Field {

    private volatile Object startValue;
    private volatile Object endValue;
    private volatile Integer interval;
    private  Object defaultValue;
    private volatile Object offset;

    public boolean getPositive() {
        return isPositive;
    }

    public void setPositive(Boolean positive) {
        isPositive = positive;
    }

    public Boolean isPositive;

    public Object getOffset() {
        return offset;
    }

    public void setOffset(Object offset) {
        this.offset = offset;
    }

    public Object getStartValue() {
        return startValue;
    }

    public void setStartValue(Object startValue) {
        this.startValue = startValue;
    }

    public Object getEndValue() {
        return endValue;
    }

    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }


    public Object getValue() {
        return defaultValue;
    }

    public void setValue(Object value) {
        this.defaultValue = value;
    }
}
