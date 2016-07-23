package com.hisoka.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Hinsteny
 * @date 2016/7/23
 * @copyright: 2016 All rights reserved.
 */
@MappedTypes(java.time.LocalDateTime.class)
public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setTimestamp(i, convert(parameter));
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getTimestamp(columnName));
    }

    @Override
    public LocalDateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getTimestamp(columnIndex));
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getTimestamp(columnIndex));
    }

    protected LocalDateTime convert(Timestamp timestamp) {
        if (timestamp == null) return null;
        return timestamp.toLocalDateTime();
    }

    protected Timestamp convert(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return new Timestamp(dateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

}
