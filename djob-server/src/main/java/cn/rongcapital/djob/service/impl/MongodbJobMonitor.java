package cn.rongcapital.djob.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.djob.dto.ExecutionLog;
import cn.rongcapital.djob.dto.ExecutionLogCondition;
import cn.rongcapital.djob.repository.ExecutionLogRepositroy;
import cn.rongcapital.djob.service.JobMonitor;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Service
public class MongodbJobMonitor implements JobMonitor{
    @Autowired
    private StringRedisTemplate redisTemplate;
    
	@Autowired(required=false)
	private ExecutionLogRepositroy executionLogRepositroy;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final int MAX_NUM_EVERY_TIME = 1000;
	private static final String EXECUTION_LOG_QUEUE_KEY = "log_queue";
    
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread th = new Thread(r);
			th.setName("job-monitor");
			return th;
		}
	});
    
	@Override
    public void start(){
    	executor.scheduleWithFixedDelay(new SaveExecutionLogTask(), 1, 1, TimeUnit.SECONDS);
    }
    
	@Override
    public void update(){
		int n=0;
    	ListOperations<String, String> listOper = redisTemplate.opsForList();
    	while(true){
    		String elem = listOper.leftPop(EXECUTION_LOG_QUEUE_KEY);
    		if(elem == null){
    			break;
    		}
    		
    		ExecutionLog log = JSON.parseObject(elem,ExecutionLog.class);
    		saveLog(log);
    		
    		if(++n >= MAX_NUM_EVERY_TIME){
    			break;
    		}
    	}
    }
	
	@Override
	public void saveLog(ExecutionLog log ){
		if(executionLogRepositroy!=null && log!=null){
			executionLogRepositroy.save(log);
		}
	}
	
	public List<ExecutionLog> search(ExecutionLogCondition cond){
		List<Criteria> criteriaList = new ArrayList<>();
		if(cond.getTriggerGroup()!=null){
			criteriaList.add(Criteria.where("triggerGroup").is(cond.getTriggerGroup()));
		}
		if(cond.getTriggerName()!=null){
			criteriaList.add(Criteria.where("triggerName").is(cond.getTriggerName()));
		}
		if(cond.getExecutor()!=null){
			criteriaList.add(Criteria.where("executor").is(cond.getExecutor()));
		}
		if(cond.getClientId()!=null){
			criteriaList.add(Criteria.where("clientId").regex(cond.getClientId()));
		}
		if(cond.getStartTime()!=null){
			criteriaList.add(Criteria.where("startTime").gte(cond.getStartTime()));
		}
		if(cond.getCompleteTime()!=null){
			criteriaList.add(Criteria.where("completeTime").lte(cond.getCompleteTime()));
		}
		if(cond.getIsSuccess()){
			criteriaList.add(Criteria.where("isSuccess").is(true));
		}else{
			criteriaList.add(Criteria.where("isSuccess").is(false));
		}
		Query query = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
		query.with(new Sort(Sort.Direction.DESC,"startTime"));
		//TODO
		query.limit(10).skip(0);
		List<ExecutionLog> result =  mongoTemplate.find(query, ExecutionLog.class);
		return result;
	}
    
	@Override
	public void stop() {
		executor.shutdownNow();
	}
    
    public class SaveExecutionLogTask implements Runnable{

		@Override
		public void run()  {

			update();
		}
    	
    }


    
}
