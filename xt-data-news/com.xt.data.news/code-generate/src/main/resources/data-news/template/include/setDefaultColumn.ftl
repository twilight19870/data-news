<#list table.columns as col>
<#if col.columnName=='create_user'>
<#assign _createUser='create_user' />
</#if>
<#if col.columnName=='create_user_id'>
<#assign _createUser='create_user_id' />
</#if>
<#if col.columnName=='update_user'>
<#assign _updateUser='update_user' />
</#if>
<#if col.columnName=='update_user_id'>
<#assign _updateUser='update_user_id' />
</#if>
</#list>