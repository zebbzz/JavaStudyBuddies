import java.time.*;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Test {
    public static void main(String[] args) {
       //     LocalTime time = LocalTime.now();
            LocalTime time = LocalTime.of(21, 1, 2);
            System.out.println(time);

          //  LocalTime utcTime = LocalTime.now(ZoneId.of("UTC"));
            LocalTime utcTime = LocalTime.of(17, 59, 43);
            System.out.println(utcTime);

            //System.out.println(HOURS.between(time, utcTime));

         //Instant instant = Instant.now();
        //OffsetDateTime.now(ZoneOffset.UTC)
        //    System.out.println(OffsetDateTime.now( ZoneOffset.UTC ));

        System.out.println(Duration.between(time, utcTime).getSeconds()/3600);

        //System.out.println(Instant.now());


        int resultTime = 0;
        resultTime+=time.getHour()*60;
        resultTime+=time.getMinute();

        int utcResultTime = 0;
        utcResultTime+=utcTime.getHour()*60;
        utcResultTime+=utcTime.getMinute();

        System.out.println(resultTime);
        System.out.println(utcResultTime);
        System.out.println(resultTime-utcResultTime);
        System.out.println((resultTime-utcResultTime)/60);

        System.out.println("after");
        Instant instant = Instant.now();

        LocalDateTime local = LocalDateTime.from(instant.atZone(ZoneId.of("UTC")));
        System.out.println("first local: " + local);

        local = local.minusSeconds(local.getSecond());
        local = local.minusMinutes(local.getMinute());
        System.out.println("nano: " + local.getNano());
        local.minusNanos(local.getNano());
        //local.minusSeconds(local.getSecond());
        //LocalDateTime local2 = LocalDateTime.from(instant.atZone(ZoneId.systemDefault()));
        LocalDateTime local2 = LocalDateTime.of(2019, 6, 14, 00, 20);


        LocalDateTime local3 = LocalDateTime.of(2019, 6, 14, 10, 20);
        System.out.println("instant: " + instant);  //instant: 2019-06-13T19:06:13.860Z
        System.out.println("local: " + local);  //local: 19:06:13.860
        System.out.println("local2: " + local2);  //local2: 22:06:13.860
        System.out.println(Duration.between(local, local2).getSeconds()/3600);
    }


}
