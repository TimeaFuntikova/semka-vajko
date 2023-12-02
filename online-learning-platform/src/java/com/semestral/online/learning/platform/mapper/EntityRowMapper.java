package java.com.semestral.online.learning.platform.mapper;

import javax.swing.tree.RowMapper;
import java.com.semestral.online.learning.platform.entity.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityRowMapper implements RowMapper<Entity> {
    @Override
    public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Entity entity = new Entity();
        entity.setId(rs.getLong("id"));
        entity.setName(rs.getString("name"));
        // Map other fields
        return entity;
    }
}
