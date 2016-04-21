package com.tom.workflow.form;

import com.tom.workflow.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tom on 16/4/18.
 */
public class VehicleUseApplication extends WorkflowBase{
    private static final Logger logger = LoggerFactory.getLogger(VehicleUseApplication.class);

    public static final String WORKFLOW_NAME = "车辆申请";
    public static final String WORKFLOW_TYPE = "VechicleUserApply";
    public static final String WORKFLOW_URL_FORMAT = "%sVehicleUseApplication/VechicleApplicationView.aspx?busId=%s&checkType=%s&id=%s";

    public VehicleUseApplication(String username, String password, String busId, String checkType, String id) {
        super(username, password, busId, checkType, id);
    }

    public String getType() {
        return WORKFLOW_TYPE;
    }

    public String getName() {
        return WORKFLOW_NAME;
    }

    /**
     * 格式化 详情页面url
     * @return
     */
    public String getDetailViewUrlFormat(){
        return VehicleUseApplication.WORKFLOW_URL_FORMAT;
    }
    /**
     * 详情页面 url
     * @return
     */
    public String getDetailViewUrl(){
        return String.format(getDetailViewUrlFormat(), Main.ROOT_URL, getBusId(), getCheckType(), getId());
    }

}
