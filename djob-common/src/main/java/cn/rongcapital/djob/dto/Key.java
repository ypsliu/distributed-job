/**
 * 
 */
package cn.rongcapital.djob.dto;

/**
 * @author shangchunming
 *
 */
public class Key {

	private String name;

	private String group;
	
	public Key(){
	}

	/**
	 * to create the key by name and group
	 * 
	 * @param name
	 * @param group
	 */
	public Key(String name, String group) {
		super();
		this.name = name;
		this.group = group;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return this.group + "." + this.name;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
