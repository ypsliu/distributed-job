/**
 * 
 */
package cn.rongcapital.djob;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Component
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory{
	@Autowired
	private transient AutowireCapableBeanFactory beanFactory;


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.scheduling.quartz.SpringBeanJobFactory#
	 * createJobInstance(org.quartz.spi.TriggerFiredBundle)
	 */
	@Override
	public Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object job = super.createJobInstance(bundle);
		this.beanFactory.autowireBean(job);
		return job;
	}

}
