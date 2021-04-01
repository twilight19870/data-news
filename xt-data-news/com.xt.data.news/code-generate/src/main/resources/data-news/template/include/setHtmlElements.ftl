<#assign ng_pattern='' />
<#if colJavaType=='Integer' || colJavaType=='Long'>
	<#assign item_type='text' />
	<#assign ng_pattern=Config['reg.int'] />
<#elseif colJavaType=='Double' || colJavaType=='Float'>
	<#assign item_type='text' />
	<#if (col.numericSacle)??>
		<#assign ng_pattern=Config['reg.number'] />
	<#else>
		<#assign ng_pattern=Config['reg.int'] />
	</#if>
<#elseif colJavaType=='Date' || colJavaType=='Timestamp'>
	<#assign item_type='text' />
<#elseif colJavaType=='String'>
	<#assign item_type='text' />
<#else>
	<#assign item_type='text' />
</#if>
<#if (col.characterMaximumLength)??>
	<#assign max_length=col.characterMaximumLength />
<#elseif (col.numericPrecision)??>
	<#assign max_length=col.numericPrecision />
	<#assign ng_pattern=ng_pattern?replace('#len#',max_length) />
<#else>
	<#assign max_length=-1 />	
	<#assign ng_pattern='' />
</#if>
<#if (col.numericSacle)??>
	<#assign item_sacle=col.numericPrecision />
	<#assign ng_pattern=ng_pattern?replace('#sacle#',item_sacle) />
</#if>
<#if col.isNullable=='NO' && col.extra!='auto_increment'>
	<#assign required='required' />
<#else>
	<#assign required='' />
</#if>