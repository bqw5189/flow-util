package com.daosheng.field.service.backlog.detail.webparse;


import com.daosheng.field.service.webParse.entity.RegixEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <#$workflowName#> 服务
 * Created by tom on <#$now#>.
 */
@Component
public class <#$workflowType#>Service extends FlowBaseService {
    public final static String FLOW_TYPE = "<#$workflowType#>";
    public final static String FLOW_NAME = "<#$workflowName#>";

    @Override
    protected List<RegixEntity> buildRegixEntity() {
        List<RegixEntity> regixEntityList = new ArrayList<RegixEntity>();

        <#foreach from=$panels item=panel#>
        //<#$panel.title#><#foreach from=$panel.elements item=element#>
        regixEntityList.add(RegixEntity.create("<#$element.id#>", "<#$element.tagName#>", "#<#$element.id#>"));<#/foreach#>
        <#/foreach#>

        //产品信息
        regixEntityList.add(RegixEntity.create("ProductList", RegixEntity.PARSE_TYPE_TABLE, "table", "#ctl00_body_gvProductsInfor"));

        //备注
        regixEntityList.add(RegixEntity.create("Dome", "span", "#ctl00_body_txtDome"));

        //附件
        regixEntityList.add(RegixEntity.create("Related", "td", "#ctl00_body_ContractAttachment1_tdDownFile"));

        return regixEntityList;
    }

    @Override
    protected String detailPageUri() {
        return "secco/<#$workflowType#>/SC_<#$workflowType#>View.aspx";
    }


    /**
     *描述：<#$workflowName#>
     *@param @param url
     *@param @param type
     *@param @param token
     *@author nimin
     */
    IServiceResponse parse<#$workflowType#>(String url, String type, String token);

    @Autowired
	private <#$workflowType#>Service <#$workflowType#>Service;

    @Override
    public IServiceResponse parse<#$workflowType#>(String url, String type, String token) {
        JsonResponse resp = new JsonResponse();

        try {
            Map<String, Object> result=	<#$workflowType#>Service.parseResult(url, type,token);

            resp.setJsonVal(FieldUtils.gson.toJson(result));

            this.success(resp, "success");
        } catch (Exception e) {
            e.printStackTrace();
            this.error(resp, "error");
        }
        return resp;
    }

    else if(<#$workflowType#>Service.FLOW_NAME.equalsIgnoreCase(type)) {
                type = <#$workflowType#>Service.FLOW_TYPE;
            }

    @RequestMapping(value="/<#$workflowType#>",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        @LogForDbAnnotation(title="<#$workflowName#>数据爬取",operationType="第三方服务日志",level="中级")
        public ResponseEntity<?> parse<#$workflowType#>(
                @RequestParam("parseUrl") String parseUrl,
                @RequestParam("type") String type,
                @RequestParam("token") String token) {
            if ("0:1".equals(type) || "0:2".equals(type)) {
                return new ResponseEntity(webParseService.parse<#$workflowType#>(parseUrl,type,token),HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

}
