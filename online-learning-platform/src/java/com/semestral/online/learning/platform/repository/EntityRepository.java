package java.com.semestral.online.learning.platform.repository;

import java.com.semestral.online.learning.platform.entity.Entity;
import java.util.List;

public interface EntityRepository {
    List<Entity> getAllEntities();

    Entity getEntityById(Long id);

    Entity createEntity(Entity entity);

    Entity updateEntity(Long id, Entity updatedEntity);

    void deleteEntity(Long id);
}