var carMulti = {
    EN: {
     <#foreach from=$panels item=panel#>
        <#$panel.id#>:'<#$panel.titleEN#>',
        <#foreach from=$panel.elements item=element#>
                <#$element.id#>:'<#$element.textEn#>',<#/foreach#>
     <#/foreach#>

        Dome: 'Dome',

        Related:'Related',
        RelatedAccessories: 'RelatedAccessories'
    },
    ZH_CN: {
        <#foreach from=$panels item=panel#>
            <#$panel.id#>:'<#$panel.title#>',<#foreach from=$panel.elements item=element#>
                    <#$element.id#>:'<#$element.text#>',<#/foreach#>
         <#/foreach#>

        Dome: '备注',

        Related:'相关附件',
        RelatedAccessories: '相关附件RelatedAccessories'
    }
}
$(function () {
    SystemUtil.injection(public_lg.secco[language]);
    SystemUtil.injection(carMulti[language]);
    var type = SystemUtil.getUrlParam("parentId");
    var paramStr = $.param({type: type, parseUrl: parseUrl, token: token});
    initUtils.parsePage({
        param: paramStr, control: "webParse/<#$workflowType#>", callbackFn: function (val) {
            for (var key in val){
                $('#' + key).text(val[key]);
            }

            buildUtils.buildTable("ProductList", val.ProductList);
            buildUtils.buildAnnex("Annex", val.Annex, "secco");
            if ("0:2" === type) {
                $('#Review_approval').parent().remove();
                buildUtils.buildTable("Record", val.Record);
            } else {
                buildUtils.buildTable("Record", val.Record);
                submitUtils.otherEvents("");
            }
        }
    });
});