package emulator.test;
import emulator.AbstractTask;
import emulator.EmulatorFactory;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 20:48
 * @Description: com.emulator.plcemulator.test
 * @Version 1.0
 */
public class EmulatorTest {

    private EmulatorFactory emulatorFactory = new EmulatorFactory();
    private AbstractTask task;

    public void testRandomNegative() {
        String userParams = "random(1000,3.2,60.9)";
        task = emulatorFactory.builder(userParams);
        new Thread(()->{
            while (true) {
                Object value = task.getValue();
                System.out.println("value = " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void testRandomBoolean() {
        String userParams = "random(300)";
        task = emulatorFactory.builder(userParams);
        new Thread(()->{
            while (true) {
                Object value = task.getValue();
                System.out.println("value = " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void testRandomPositive() {
        String userParams = "random(1000,5,100)";
        task = emulatorFactory.builder(userParams);
        new Thread(()->{
            while (true) {
                Object value = task.getValue();
                System.out.println("value = " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void testRAMPPositive() {
        String userParams = "ramp(1000,3,60,2)";
        task = emulatorFactory.builder(userParams);
        new Thread(()->{
            while (true) {
                Object value = task.getValue();
                System.out.println("value = " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void testRAMPNegative() {
        String userParams = "ramp(1000,3.3,60.8,3.0)";
        task = emulatorFactory.builder(userParams);
        new Thread(()->{
            while (true) {
                Object value = task.getValue();
                System.out.println("value = " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void testUserInput() {
        String userParams = "user(1000,3,45,65,7,8,9,56,4,4,true, 123.543, \"world\", \"hello\")";
        task = emulatorFactory.builder(userParams);
        new Thread(()->{
            while (true) {
                Object value = task.getValue();
                System.out.println("value = " + value.getClass());
                System.out.println("value = " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //不要直接在main方法中使用for循环测试，因为main方法的线程级别高，可能当启动的线程还没执行就会执行main所以会出现空指针
    public static void main(String[] args) {
        EmulatorTest emulatorTest = new EmulatorTest();
        emulatorTest.testUserInput();
    }
}
