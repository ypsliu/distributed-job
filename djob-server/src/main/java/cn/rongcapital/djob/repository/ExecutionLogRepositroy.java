package cn.rongcapital.djob.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.djob.dto.ExecutionLog;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public interface ExecutionLogRepositroy extends MongoRepository<ExecutionLog, String>{

}
