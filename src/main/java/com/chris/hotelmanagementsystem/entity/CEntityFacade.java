package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class CEntityFacade <T extends CEntity> {
  
  public abstract CEntityRepository<T> repository();
  public abstract Class<T> entityClass();
  
  public T save(T entity) {
    try {
      List<CEntity.SpecWrapper<T>> checkUnique = entity.checkUnique();
      for (CEntity.SpecWrapper<T> spec : checkUnique) {
        if (repository().exists(spec.spec())) {
          throw CxException.duplicate(entityClass(), spec.fieldName(), spec.value());
        }
      }
      
      entity.doValidate();
      return repository().save(entity);
    } catch (Exception e) {
      throw CxException.unexpected(e);
    }
    
  }
  
  public void delete(T entity) {
    repository().delete(entity);
  }
  
  public T findById(Long id) {
    return repository().findById(id).orElseThrow(() -> CxException.notFound(entityClass(), "id", id));
  }
  
  public Page<T> findAll(Pageable pageable) {
    return repository().findAll(pageable);
  }
  
  public Page<T> findAll(String query, Pageable pageable) {
    return repository().findAll(query, pageable);
  }
  
  public long count() {
    return repository().count();
  }
  
}
