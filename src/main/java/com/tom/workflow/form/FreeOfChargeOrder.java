package com.tom.workflow.form;

import com.tom.workflow.entity.Panel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 16/4/18.
 */
public class FreeOfChargeOrder extends WorkflowBase{
    private static final Logger logger = LoggerFactory.getLogger(FreeOfChargeOrder.class);

    public static final String WORKFLOW_NAME = "免费发货订单";
    public static final String WORKFLOW_TYPE = "FreeOfChargeOrder";

    public FreeOfChargeOrder(String username, String password, String busId, String checkType, String id) {
        super(username, password, busId, checkType, id);
    }

    public String getType() {
        return WORKFLOW_TYPE;
    }

    public String getName() {
        return WORKFLOW_NAME;
    }


}
