package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class LongCalculationComponent implements Serializable {
    public Integer StudentCount(Integer number){

        try {
            Thread.sleep(4000);
            System.out.println("THREAD SLEEP DONE");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return number;
    }
}
