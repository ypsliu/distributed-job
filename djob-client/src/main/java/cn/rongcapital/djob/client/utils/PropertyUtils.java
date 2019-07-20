package cn.rongcapital.djob.client.utils;

import cn.rongcapital.djob.client.bean.Property;

/**
 * Created by lilupeng on 10/05/2017
 *
 */
public class PropertyUtils {

    private static Property property = new Property();

    /**
     *
     * @return
     */
    public static Property getProperty() {
        return property;
    }

    /**
     *
     * @param property
     */
    public void setProperty(Property property) {
        PropertyUtils.property = property;
    }
}
