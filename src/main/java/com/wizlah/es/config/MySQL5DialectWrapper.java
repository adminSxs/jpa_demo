package com.wizlah.es.config;

import org.hibernate.dialect.MySQL55Dialect;

public class MySQL5DialectWrapper extends MySQL55Dialect {
  @Override
  public String getAddForeignKeyConstraintString(
      String constraintName,
      String[] foreignKey,
      String referencedTable,
      String[] primaryKey,
      boolean referencesPrimaryKey) {
    String sql = "";
    /*
     * for (String key : foreignKey) { sql += " drop foreign key " + key + ";"; }
     */
    return sql;
  }
}
