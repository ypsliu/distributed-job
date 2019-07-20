package cn.rongcapital.djob.client.test;

import cn.rongcapital.djob.client.function.CallBack;
import cn.rongcapital.djob.client.function.JobExecutor;
import cn.rongcapital.djob.dto.JobContext;

/**
 * Created by lilupeng on 09/05/2017.
 */
public class ExecutorTest implements JobExecutor {

    @Override
    public void execute(JobContext jobContext, CallBack callBack) {

        System.out.println("biz logic here");

        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////
        ////////////

         JobExecutor.super.callback(callBack);
    }
}
