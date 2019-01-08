package robotNavigationSimulation.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation.events.StaticEvents;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

/**
 *
 * @author L
 */
public class Navigation extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Navigation.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        while (true) {

//            Scanner in =new Scanner(System.in);
//
//            System.out.println("请输入横坐标 X ");
//            int X=in.nextInt();//输入一个整数
//            System.out.println("请输入横坐标 Y ");
//            int Y=in.nextInt();//输入一个整数
//
//
//            //输入数据校验


            bp.bSync(none, StaticEvents.LPathEvent, StaticEvents.GPathEvent);
            bp.bSync(none, StaticEvents.GPathEvent, StaticEvents.LPathEvent);

        }
    }
}
