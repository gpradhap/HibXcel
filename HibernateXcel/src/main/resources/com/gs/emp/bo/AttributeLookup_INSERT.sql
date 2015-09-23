/*
desc AttributeLookup;
+--------------+------------------+------+-----+---------+----------------+
| Field        | Type             | Null | Key | Default | Extra          |
+--------------+------------------+------+-----+---------+----------------+
| AttrLookupID | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
| Attribute    | varchar(20)      | NO   |     | NULL    |                |
| AttrGroupID  | int(10) unsigned | YES  | MUL | NULL    |                |
+--------------+------------------+------+-----+---------+----------------+
*/