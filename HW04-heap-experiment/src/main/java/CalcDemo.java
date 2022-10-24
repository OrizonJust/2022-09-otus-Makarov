import java.time.LocalDateTime;

public class CalcDemo {

    public static void main(String[] args) {

        long counter = 100_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < counter; i++) {
            var data = new Data(i);
            summator.calc(data);

            if (i % 10_000_000 == 0) {
                System.out.println(LocalDateTime.now() + " current i:" + i);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }
}
