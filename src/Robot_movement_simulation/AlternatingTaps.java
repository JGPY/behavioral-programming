package Robot_movement_simulation;

import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.BPJException;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;


public class AlternatingTaps implements BApplication {
  @SuppressWarnings("serial")
  static class TapEvent extends Event {

     public TapEvent(String name) {
        this.setName(name);
     }

  }

    static TapEvent EventGlobalPath = new TapEvent("EventGlobalPath"){
    };
    static TapEvent EventPartialPath = new TapEvent("EventPartialPath"){

    };

    public class globalPath extends BThread {
        @Override
        public void runBThread() throws BPJException {
            for (;;) {
                //全局路径数据是否为空
                System.out.println("全局路径数据是否为空");
                    //获取当前坐标和目标点坐标参数
                    System.out.println("获取当前坐标和目标点坐标参数");
                    //加载地图参数获得路径
                    System.out.println("加载地图参数获得路径");
                    //路径数据存储到全局变量
                    System.out.println("路径数据存储到全局变量");
                    bp.bSync(EventGlobalPath, none, none);
            }
        }
    }
    public class partialPath extends BThread {
        @Override
        public void runBThread() throws BPJException {
            for (;;) {
                //获取传感器数据
                System.out.println("获取传感器数据");
                //模糊逻辑处理
                System.out.println("模糊逻辑处理");
                //判断时候有障碍物
                System.out.println("判断时候有障碍物");
                    //若有就调整动作，更新位置
                    System.out.println("若有就调整动作，更新位置");
                    //清除全局路径图
                    System.out.println("清除全局路径图");
                    //没有读取路径数据，移动一步
                    System.out.println("没有读取路径数据，移动一步");
                bp.bSync(EventPartialPath, none, none);
            }
        }
    }
    public class Navigation extends BThread {
        @Override
        public void runBThread() throws BPJException {
            while (true) {
                bp.bSync(none, EventGlobalPath, EventPartialPath);
                bp.bSync(none, EventPartialPath, EventGlobalPath);
            }
        }
    }

    @SuppressWarnings("serial")
    public class DisplayEvents extends BThread {
        @Override
        public void runBThread() throws BPJException {
            while (true) {
                bp.bSync(none, all, none);
                System.out.println("最新事件" + bp.lastEvent);
//                System.out.println("所有启动事件集" + bp.getAllEnabledEvents());
            }
        }
    }

    @Override
    public void runBApplication() {
        System.out.println("runBApplication () at " + this);
        bp.add(new globalPath(),1.0);
        bp.add(new partialPath(),2.0);
        bp.add(new Navigation(),3.0);
        bp.add(new DisplayEvents(), 4.0);
        bp.startAll();
    }

    static public void main(String arg[]) {
        try {

            BProgram.startBApplication(AlternatingTaps.class, "sourceCode.bp.unittest");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}