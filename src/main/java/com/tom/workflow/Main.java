package com.tom.workflow;

import com.tom.workflow.form.FreeOfChargeOrder;
import com.tom.workflow.form.VehicleUseApplication;
import com.tom.workflow.form.WorkflowBase;

/**
 * Created by tom on 16/4/18.
 */
public class Main {
    public static final String HOST = "http://61.129.41.99:9099/";
    public static final String ROOT_URL = HOST + "secco/";


    public static void main(String[] args){
        //免费发货单
//        FreeOfChargeOrder freeOfChargeOrder = new FreeOfChargeOrder("xie.ling", "Secco321","07bbadaf-0514-11e6-915e-78acc0f888fa", WorkflowBase.CHECK_TYPE, "207508");
//        freeOfChargeOrder.render("/work/code/github/java/work-flow-form-util/out/");

        VehicleUseApplication workflowBase= new VehicleUseApplication("zhang.mike", "Secco321","a3b1494a-06c4-11e6-915e-78acc0f888fa", WorkflowBase.CHECK_TYPE, "207543");
        workflowBase.render("/work/code/github/java/work-flow-form-util/out/");

    }
}
