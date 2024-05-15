package com.yangxj96.saas.starter.db.handlers;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Geom类型转换
 */
public class GeomTypeHandler extends BaseTypeHandler<Object> {

    private final WKBReader reader = new WKBReader();
    private final WKBWriter writer = new WKBWriter(2, ByteOrderValues.BIG_ENDIAN, true);
    private final GeometryJSON gj = new GeometryJSON(10);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        var om = SpringUtil.getBean(ObjectMapper.class);
        try {
            var geom = gj.read(om.readTree(parameter.toString()).toPrettyString());
            geom.setSRID(4490);
            var bytes = writer.write(geom);
            var s1 = WKBWriter.toHex(bytes);
            var obj = new PGobject();
            obj.setType("geometry");
            obj.setValue(s1);
            ps.setObject(i, obj);
        } catch (Exception e) {
            throw new RuntimeException("转换geom错误," + e.getMessage());
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        var r = rs.getString(columnName);
        if (r == null) {
            return null;
        }
        return convert(r);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        var r = rs.getString(columnIndex);
        if (r == null) {
            return null;
        }
        return convert(r);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        var r = cs.getString(columnIndex);
        if (r == null) {
            return null;
        }
        return convert(r);
    }


    private Object convert(String str) {
        try {
            var geometry = reader.read(WKBReader.hexToBytes(str));
            var s = gj.toString(geometry);
            return SpringUtil.getBean(ObjectMapper.class).readTree(s);
        } catch (Exception e) {
            throw new RuntimeException("地理位置信息转换失败:" + e.getMessage());
        }
    }

}
