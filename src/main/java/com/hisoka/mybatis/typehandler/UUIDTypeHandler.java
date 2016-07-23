package com.hisoka.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.nio.ByteBuffer;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Hinsteny
 * @date 2016/7/23
 * @copyright: 2016 All rights reserved.
 */
@MappedTypes(java.util.UUID.class)
public class UUIDTypeHandler implements TypeHandler<UUID> {

    static final int UUID_SIZE = 16;

    @Override
    public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, convert(parameter));
    }

    @Override
    public UUID getResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getBytes(columnName));
    }

    @Override
    public UUID getResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getBytes(columnIndex));
    }

    @Override
    public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getBytes(columnIndex));
    }

    protected byte[] convert(UUID uuid) {
        if (uuid == null) return null;
        ByteBuffer bb = ByteBuffer.wrap(new byte[UUID_SIZE]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    protected UUID convert(byte[] data) {
        if (data == null || data.length != UUID_SIZE) return null;
        ByteBuffer bb = ByteBuffer.wrap(data);
        return new UUID(bb.getLong(), bb.getLong());
    }
}
