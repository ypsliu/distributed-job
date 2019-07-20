package test;

import cn.rongcapital.djob.JobExecutor;
import cn.rongcapital.djob.dto.JobContext;

/**
 * Created by lilupeng on 14/04/2017.
 */
public class TestExecutor implements JobExecutor {

    @Override
    public void execute(JobContext jobContext) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&222");
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&222");
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&222");
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&222");
    }
}
