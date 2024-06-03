package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CEntityFacade<T extends OEntity> {

  public abstract OEntityRepository<T> getRepository();

  @SuppressWarnings("unchecked")
  public final Class<T> entityClass() {
    var genericSuperclass = getClass().getGenericSuperclass();
    var parameterizedType = genericSuperclass instanceof ParameterizedType type ? type : null;
    if (parameterizedType != null) {
      return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    } else {
      return (Class<T>) CEntity.class;
    }
  }

  public final T save(T entity) {
    try {
      List<OEntity.SpecWrapper<T>> checkUnique = entity.checkUnique();
      for (OEntity.SpecWrapper<T> spec : checkUnique) {
        if (getRepository().count(spec.spec()) > 0) {
          throw CxException.duplicate(entityClass(), spec.fieldName(), spec.value());
        }
      }

      entity.doValidate();
      return getRepository().save(entity.save());
    } catch (Exception e) {
      throw CxException.unexpected(e);
    }

  }

  public final T delete(T entity) {
    getRepository().delete(entity);
    return entity;
  }

  public final T findById(Long id) {
    return getRepository().findById(id).orElseThrow(() -> CxException.notFound(entityClass(), "id", id));
  }

  public final List<T> findAllById(List<Long> ids) {
    return getRepository().findAllById(ids);
  }

  public final Page<T> findAll(Pageable pageable) {
    return getRepository().findAll(pageable);
  }

  public final Page<T> findAll(String query, Pageable pageable) {
    return getRepository().findAll(query, pageable);
  }

  public final long count() {
    return getRepository().count();
  }

}
