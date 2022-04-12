package emulator.test;
import emulator.AbstractTask;
import emulator.EmulatorFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/30 03-30 20:48
 * @Description: com.emulator.plcemulator.test
 * @Version 1.0
 */
public class EmulatorTest {

    private EmulatorFactory emulatorFactory = EmulatorFactory.getInstance();

    private Executor executor = Executors.newFixedThreadPool(6);

    public  void testRandomNegative() {
        String userParams = "random(1000,3.2,60.9)";
        AbstractTask task = this.emulatorFactory.builder(userParams);
        executor.execute(()->{
                while (true) {
                    Object value = task.getValue();
                    System.out.println(task.getClass() + "value = " + value);
            }
        });

    }

    public  void testRandomBoolean() {

        String userParams = "random(300)";
        AbstractTask task = emulatorFactory.builder(userParams);
        executor.execute(()->{
                while (true) {
                    Object value = task.getValue();
                    System.out.println(task.getClass()+"value = " + value);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        });

    }

    public   void testRandomPositive() {

        String userParams = "random(1000,20000,3000000)";
        AbstractTask task = emulatorFactory.builder(userParams);
        executor.execute(()->{
                while (true) {
                    Object value = task.getValue();
                    System.out.println(task.getValue().getClass()+"value = " + value);
                }
        });

    }

    public void testRAMPPositive() {

        String userParams = "ramp(1000,32760,2,2)";
        AbstractTask task = emulatorFactory.builder(userParams);
        executor.execute(()->{
                while (true) {
                    Object value = task.getValue();
                    System.out.println(value.getClass()+"value = " + value);
                }

        });

    }


    public void testRAMPNegative() {

        String userParams = "ramp(1000,50.2,12,-2)";
        AbstractTask task = emulatorFactory.builder(userParams);
        executor.execute(()->{
                while (true) {
                    Object value = task.getValue();
                    System.out.println(task.getClass().getName()+"value = " + value);

                }
        });
    }

    public void testUserInput() {

        String userParams = "user(1000,3,45.2552522525211,65.2,7,856756756765765,9,56.5254,456756,true, 123.543, \"world\", \"hello\")";
        AbstractTask task = emulatorFactory.builder(userParams);
        executor.execute(()->{
                while (true) {

                    Object value = task.getValue();
                    System.out.println(value.getClass()+"value = " + value);

            }

        });
    }

    //不要直接在main方法中使用for循环测试，因为main方法的线程级别高，可能当启动的线程还没执行就会执行main所以会出现空指针
    public static void main(String[] args) throws InterruptedException {
        EmulatorTest emulatorTest = new EmulatorTest();
//        emulatorTest.testUserInput();
//        Thread.sleep(1000);
//        emulatorTest.testRandomPositive();
//        Thread.sleep(1000);
//        emulatorTest.testRandomBoolean();
//        Thread.sleep(1000);
//        emulatorTest.testRandomNegative();
//        Thread.sleep(1000);
        emulatorTest.testRAMPNegative();
//        Thread.sleep(1000);
//        emulatorTest.testRAMPPositive();
    }
}
