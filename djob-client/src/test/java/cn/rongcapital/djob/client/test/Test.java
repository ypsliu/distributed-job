package cn.rongcapital.djob.client.test;

import cn.rongcapital.djob.client.function.CallBack;
import cn.rongcapital.djob.client.utils.JobUtils;
import cn.rongcapital.djob.client.utils.PropertyUtils;
import cn.rongcapital.djob.dto.JobContext;

/**
 * Created by lilupeng on 09/05/2017.
 */
public class Test {

    public static void main(String args[]) {
        ExecutorTest test = new ExecutorTest();
        JobUtils.getTaskExecutor().execute(() -> {
            test.execute(new JobContext(), () -> {
               System.out.println("call_函数_back ...");
            });
        });
        
       /* test.execute(new JobContext(),() -> {
            System.out.println("callback here");
        }
           // new Test.TesCallBack()
          );*/
        
    }
     static class TesCallBack implements CallBack
     {

        /* (non-Javadoc)
         * @see cn.rongcapital.djob.client.function.CallBack#deal()
         */
        @Override
        public void deal() {
            // TODO Auto-generated method stub
            System.out.println("oo_callback_...");
        }
         
     }
}
