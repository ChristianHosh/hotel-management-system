package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.CEntityRepository;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BedTypeFacade extends CEntityFacade<BedType> {

    private final BedTypeRepository repository;

    @Override
    public CEntityRepository<BedType> repository() {
        return repository;
    }

    @Override
    public Class<BedType> entityClass() {
        return BedType.class;
    }

    public BedType findBedTypeByName(String name) {
        return repository.findBedTypeByName(name)
                .orElseThrow(() -> CxException.notFound(entityClass(), "name", name));
    }
}
