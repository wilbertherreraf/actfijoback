package gob.gamo.activosf.app.utils;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import gob.gamo.activosf.app.utils.JsonUtilsTest.Usuario1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaginationUtilTest {
    @Test
    public void createPageable() {
        try {
            List<String> list = Arrays.asList("A", "B", "C", "F", "H", "I", "J");
            //list = Arrays.asList("A", "B", "C");
            List<String> r = list.subList(1, list.size());
            int npages = (int) Math.ceil(10 / 3);
            int npages1 = (int) Math.ceil(9 / 3);
            /*
             * log.info("resp {} {} {}", r, npages, npages1);
             * log.info("resp 8/ 3 {} ", ((int) Math.ceil(8 / 3)));
             * log.info("resp 0/3 {} ", ((int) Math.ceil(0 / 3)));
             * log.info("resp 11/3 {} ", ((int) Math.ceil(11 / 3)));
             */

            Page<String> p = PaginationUtil.pageForList(1, 2, list);
            List<String> result = p.getContent();
            log.info("Page {} pnumber {}, ttal {}, sizx {}, restul {}", list.toString(), p.getNumber(),
                    p.getNumberOfElements(), p.getSize(), result.toString());

            p = PaginationUtil.pageForList(1, 2, list);
            result = p.getContent();
            log.info("Page {} pnumber {}, ttal {}, sizx {}, restul {}", list.toString(), p.getNumber(),
                    p.getNumberOfElements(), p.getSize(), result.toString());

            p = PaginationUtil.pageForList(0, 3, list);
            result = p.getContent();
            log.info("Page {} pnumber {}, ttal {}, sizx {}, restul {}", list.toString(), p.getNumber(),
                    p.getNumberOfElements(), p.getSize(), result.toString());

          p = PaginationUtil.pageForList(5, 2, list);
            result = p.getContent();
            log.info("Page {} pnumber {}, ttal {}, sizx {}, restul {}", list.toString(), p.getNumber(),
                    p.getNumberOfElements(), p.getSize(), result.toString());

          p = PaginationUtil.pageForList(2, 5, list);
            result = p.getContent();
            log.info("Page {} pnumber {}, ttal {}, sizx {}, restul {}", list.toString(), p.getNumber(),
                    p.getNumberOfElements(), p.getSize(), result.toString());                    
            /*
             * JSONObject jsonObject = new JSONObject(new ResponseEntity<>(p,
             * HttpStatus.OK));
             * String jsonPrettyPrintString = jsonObject.toString(4);
             * log.info("jsonObject {} ", jsonPrettyPrintString);
             */

        } catch (Exception e) {
            log.error("error " + e.getMessage(), e);
        }
    }
}
