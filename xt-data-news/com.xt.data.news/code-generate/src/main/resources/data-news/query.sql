select t.table_name , t.TABLE_COMMENT,
c.COLUMN_NAME, c.COLUMN_DEFAULT, c.IS_NULLABLE,
UPPER(c.DATA_TYPE) data_type,c.COLUMN_type, c.COLUMN_KEY, c.COLUMN_COMMENT,
c.CHARACTER_MAXIMUM_LENGTH, c.NUMERIC_PRECISION, c.NUMERIC_SCALE, c.EXTRA
from information_schema.TABLES t
inner join information_schema.COLUMNS c on t.TABLE_NAME=c.TABLE_NAME and t.table_schema=c.table_schema
where t.table_name in (${table})
and t.table_schema='db_data_news' 
order by c.table_name, c.ordinal_position 
