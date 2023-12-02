package java.com.semestral.online.learning.platform.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.com.semestral.online.learning.platform.entity.Entity;
import java.com.semestral.online.learning.platform.repository.EntityRepository;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class YourEntityService {
    private final EntityRepository repository;

    @Autowired
    public YourEntityService(EntityRepository repository) {
        this.repository = repository;
    }

    public List<Entity> getAllEntities() {
        return repository.getAllEntities();
    }

    public Entity getEntityById(Long id) {
        return repository.getEntityById(id);
    }

    public Entity createEntity(Entity entity) {
        return repository.createEntity(entity);
    }

    public Entity updateEntity(Long id, Entity updatedEntity) {
        return repository.updateEntity(id, updatedEntity);
    }

    public void deleteEntity(Long id) {
        repository.deleteEntity(id);
    }
}
