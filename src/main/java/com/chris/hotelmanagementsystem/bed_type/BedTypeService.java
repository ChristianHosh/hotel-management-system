package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.SpecRequest;
import com.chris.hotelmanagementsystem.entity.SpecResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BedTypeService {

  private final BedTypeFacade bedTypeFacade;

  public SpecResponse getBedType(Long id) {
    return bedTypeFacade.findById(id).toResponse();
  }

  public SpecResponse deleteBedType(Long id) {
    BedType bedType = bedTypeFacade.findById(id);
    bedTypeFacade.delete(bedType);
    return bedType.toResponse();
  }

  public SpecResponse updateBedType(Long id, SpecRequest request) {
    BedType bedType = bedTypeFacade.findById(id);
    bedType.setName(request.name());

    return bedTypeFacade.save(bedType).toResponse();
  }

  public Page<SpecResponse> getBedTypes(int page, int size, String query) {
    return bedTypeFacade.findAll(query, PageRequest.of(page, size)).map(BedType::fromEntity);
  }

  public SpecResponse createBedType(SpecRequest request) {
    BedType bedType = new BedType();
    bedType.setName(request.name());

    return bedTypeFacade.save(bedType).toResponse();
  }
}
