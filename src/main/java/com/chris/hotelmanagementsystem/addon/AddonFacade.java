package com.chris.hotelmanagementsystem.addon;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.CEntityRepository;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddonFacade extends CEntityFacade<Addon> {

  private final AddonRepository repository;

  @Override
  public CEntityRepository<Addon> repository() {
    return repository;
  }

  @Override
  public Class<Addon> entityClass() {
    return Addon.class;
  }

  public Addon findAddonByName(String name) {
    return repository.findAddonByName(name)
        .orElseThrow(() -> CxException.notFound(entityClass(), "name", name));
  }
}
