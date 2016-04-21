package com.tom.workflow.form;

import com.tom.workflow.HttpClientService;
import com.tom.workflow.Main;
import com.tom.workflow.entity.Panel;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;
import org.lilystudio.smarty4j.Template;
import org.lilystudio.smarty4j.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.lilystudio.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tom on 16/4/18.
 */
public abstract class WorkflowBase {
    private static final  Logger logger = LoggerFactory.getLogger(WorkflowBase.class);

    public static final String WORKFLOW_URL_FORMAT = "%s%s/SC_%sView.aspx?busId=%s&checkType=%s&id=%s";
    public static final String CHECK_TYPE = "0";

    protected String username;
    protected String password;

    protected String busId;
    protected String checkType;
    protected String id;

    private HttpClientService httpClientService;


    public WorkflowBase(String username, String password, String busId, String checkType, String id) {
        this.username = username;
        this.password = password;
        this.busId = busId;
        this.checkType = checkType;
        this.id = id;

        httpClientService = new HttpClientService(this.username, this.password);
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HttpClientService getHttpClientService() {
        return httpClientService;
    }

    /**
     * 工作流类型
     * @return
     */
    public abstract String getType();

    /**
     * 工作流类型
     * @return
     */
    public abstract String getName();

    /**
     * 格式化 详情页面url
     * @return
     */
    public String getDetailViewUrlFormat(){
        return WORKFLOW_URL_FORMAT;
    }

    /**
     * 详情页面 url
     * @return
     */
    public String getDetailViewUrl(){
        return String.format(getDetailViewUrlFormat(), Main.ROOT_URL, getType(), getType(), getBusId(), getCheckType(), getId());
    }

    /**
     * 详情页面 url
     * @return
     */
    public String getDetailViewUrlEN(){
        return this.getDetailViewUrl() + "&uilang=en-US";
    }

    /**
     * 解析页面数据
     * @return
     */
    private List<Panel> parsePage() {
        List<Panel> result = new ArrayList<Panel>();

        Document document = this.getHttpClientService().get(this.getDetailViewUrl());
        Document documentEN = this.getHttpClientService().get(this.getDetailViewUrlEN());
        if (null == document){
            logger.warn("document is null!");
        }

        Elements panels = document.select(".panel");

        for (Element panel: panels){
            Element title = panel.select("span").first();
            Panel pagePanel = new Panel(title.attr("id"), title.text(), documentEN.getElementById(title.attr("id")).text());

//            for (Element tr: panel.select("tr")){
                Elements ths = panel.select("div.content table.table-1 tbody tr th");
                logger.debug("{} -> page element:{}", title.text(), ths);
                for (Element th: ths) {
                    Element pageElement = th.select("span").first();
                    Element valueTdElement = th.nextElementSibling();
                    if (null != pageElement && null != valueTdElement) {
                        Element valueElement = valueTdElement.child(0);
                        if (null != valueElement) {
                            if ("a".equals(valueElement.tagName())){
                                pagePanel.addElement(pageElement.attr("id"),"td", pageElement.text(), documentEN.getElementById(pageElement.attr("id")).text(), valueTdElement.attr("id"));
                            }else{
                                pagePanel.addElement(pageElement.attr("id"),valueElement.tagName(), pageElement.text(), documentEN.getElementById(pageElement.attr("id")).text(), valueElement.attr("id"));
                            }

                        }else{
                            pagePanel.addElement(pageElement.attr("id"),"td", pageElement.text(), documentEN.getElementById(pageElement.attr("id")).text(), valueElement.attr("id"));
                        }

                    }
                }

                //表格处理
                if(ths.size() == 0){
                    Element table = panel.select(".grid").first();
                    logger.debug("table:{}", table);
                    if (null != table) {
                        pagePanel.addElement(table.attr("id"),table.tagName(), "", "", table.attr("id"));
                        pagePanel.setType("table");
                    }

                }
//            }

            logger.debug("panel :{}", pagePanel);
            result.add(pagePanel);
        }

        return result;


    }

    public void render(String outPath){
        List<Panel> panels = parsePage();

        File outPathFile = new File(outPath + this.getType());
        outPathFile.mkdirs();

        render(panels, outPathFile, "web/workflow-form-html.tpl", "detail.html");
        render(panels, outPathFile, "web/workflow-form-js.tpl", "detail.js");
        render(panels, outPathFile, "service/WorkflowService.tpl", this.getType() + "Service.java");
    }

    private void render(List<Panel> panels, File outPath, String templateName, String outFileName) {
        FileOutputStream fileOutputStream = null;
        try {
            File outFile = new File(outPath.getAbsolutePath() + File.separatorChar + outFileName);

            fileOutputStream = new FileOutputStream(outFile);

            renderTemplate(panels, templateName, fileOutputStream);

            logger.info("template render:{}", outFile.canRead());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }

    }

    private Template renderTemplate(List<Panel> panels, String templateName, final FileOutputStream fileOutputStream) throws Exception {
        Map<String, String> config = new HashMap<String, String>();
        config.put("template.path", "src/main/resources/template/");
        config.put("encoding", "utf-8");

        Engine smartyEngine = new Engine(config);
        smartyEngine.setLeftDelimiter("<#");
        smartyEngine.setRightDelimiter("#>");

        Template template = smartyEngine.getTemplate(templateName);

        Context context = new Context();
        context.set("panels", panels);
        context.set("workflowType", this.getType());
        context.set("workflowName", this.getName());

        template.merge(context, fileOutputStream);

        return template;
    }
}
