<#assign hasMember=false />
<#assign hasMemberId=false />
<#assign hasMemberName=false />
<#assign hasStore=false />
<#assign hasStoreName=false />
<#assign hasUser=false />
<#assign hasUserName=false />
<#assign hasBusiness=false />
<#assign hasBusinessName=false />
<#assign hasProductId=false />
<#assign hasProductName=false />
<#assign hasSkuId=false />
<#assign hasSkuName=false />
<#list table.columns as col>
<#if col.columnName=='member_id' || col.columnName=='memberId'>
<#assign hasMember=true /><#assign hasMemberId=true />
</#if>
<#if col.columnName=='member_name' || col.columnName=='memberName'>
<#assign hasMemberName=true />
</#if>
<#if col.columnName=='store_id' || col.columnName=='storeId'>
<#assign hasStore=true />
</#if>
<#if col.columnName=='store_name' || col.columnName=='storeName'>
<#assign hasStoreName=true />
</#if>
<#if col.columnName=='user_id' || col.columnName=='userId'>
<#assign hasUser=true />
</#if>
<#if col.columnName=='user_name' || col.columnName=='userName'>
<#assign hasUserName=true />
</#if>
<#if col.columnName=='business_id' || col.columnName=='businessId'>
<#assign hasBusiness=true />
</#if>
<#if col.columnName=='business_name' || col.columnName=='businessName'>
<#assign hasBusinessName=true />
</#if>
<#if col.columnName=='product_id' || col.columnName=='productId'>
<#assign hasProductId=true />
</#if>
<#if col.columnName=='product_name' || col.columnName=='productName'>
<#assign hasProductName=true />
</#if>
<#if col.columnName=='sku_id' || col.columnName=='skuId'>
<#assign hasskuId=true />
</#if>
<#if col.columnName=='sku_name' || col.columnName=='skuName'>
<#assign hasskuName=true />
</#if>
</#list>
<#assign listDisplay=["id", "ID", "lastModifiedDate", "last_modified_date", "version", "createUserId", "create_iser_id", "member_id", "memberId", "store_id", "storeId", "user_id", "userId", "business_id", "businessId", "product_id", "productId", "sku_id", "skuId"] />