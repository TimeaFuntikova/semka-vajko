package java.com.semestral.online.learning.platform.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.com.semestral.online.learning.platform.entity.Entity;
import java.com.semestral.online.learning.platform.mapper.EntityRowMapper;
import java.com.semestral.online.learning.platform.repository.EntityRepository;
import java.util.List;

public class EntityRepositoryImpl implements EntityRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public EntityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Entity> getAllEntities() {
        // Implement SQL query to retrieve all entities
        return jdbcTemplate.query("SELECT * FROM your_table_name", new EntityRowMapper());
    }

    @Override
    public Entity getEntityById(Long id) {
        // Implement SQL query to retrieve an entity by ID
        return jdbcTemplate.queryForObject("SELECT * FROM your_table_name WHERE id = ?", new EntityRowMapper(), id);
    }

    @Override
    public Entity createEntity(Entity entity) {
        // Implement SQL query to insert a new entity
        jdbcTemplate.update("INSERT INTO your_table_name (name, ...) VALUES (?, ...)", entity.getName(), ...);
        return entity;
    }

    @Override
    public Entity updateEntity(Long id, Entity updatedEntity) {
        // Implement SQL query to update an entity
        jdbcTemplate.update("UPDATE your_table_name SET name = ?, ... WHERE id = ?", updatedEntity.getName(), ..., id);
        return updatedEntity;
    }

    @Override
    public void deleteEntity(Long id) {
        // Implement SQL query to delete an entity
        jdbcTemplate.update("DELETE FROM your_table_name WHERE id = ?", id);
    }
}
