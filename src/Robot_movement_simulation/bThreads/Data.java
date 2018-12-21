package Robot_movement_simulation.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static Robot_movement_simulation.events.StaticEvents.EventData;
import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;


public class Data extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        while (true) {
            logger.info("采集数据");
            //获取当前位置
            // 计算传感器距离
            Robot r = null;
            try {
                r = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            Color color = r.getPixelColor(50,50);
            // 依次存储传感器数据

            Map map = new HashMap(18);


            map.put("1",0);
            map.put("2",0);
            map.put("3",0);
            map.put("4",0);
            map.put("5",0);
            map.put("6",0);
            map.put("7",0);
            map.put("8",0);
            map.put("9",0);
            Thread.sleep(500);
            logger.info("同步：bp.bSync(EventData, none, none)");
            bp.bSync(EventData, none, none);
            logger.info("结束：bp.bSync(EventData, none, none)");
        }
    }
}
