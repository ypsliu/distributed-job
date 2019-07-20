/**
 * 
 */
package cn.rongcapital.djob.utils;

import java.util.UUID;

/**
 * @author shangchunming
 *
 */
public class IdUtils {

	/**
	 * to generate new client Id
	 * 
	 * @return the id
	 */
	public static String generateClientId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * to generate new execution Id
	 * 
	 * @return the id
	 */
	public static String generateExecutionId() {
		return UUID.randomUUID().toString();
	}

}
