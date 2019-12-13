package run.aquan.iron;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Class Test
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/13 10:13
 * @Version 1.0
 **/
@Slf4j
public class Test {

    @org.junit.Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", "小白1");
        map.put("2", "小白2");
        map.put("3", "小白3");
        map.put("4", "小白4");
        map.put("5", "小白5");
        System.err.println("map.keySet() : ");
        for (String mp : map.keySet()) {
            log.info("key = " + mp + ", value = " + map.get(mp));
        }

    }

}
