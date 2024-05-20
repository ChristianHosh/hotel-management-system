package com.chris.hotelmanagementsystem.addon;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class AddonFacade extends CEntityFacade<Addon> {

  private final AddonRepository repository;

  public Addon findAddonByName(String name) {
    return repository.findAddonByName(name)
        .orElseThrow(() -> CxException.notFound(entityClass(), "name", name));
  }
}
