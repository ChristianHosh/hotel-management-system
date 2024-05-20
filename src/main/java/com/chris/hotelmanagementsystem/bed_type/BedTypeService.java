package com.chris.hotelmanagementsystem.bed_type;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BedTypeService {

  private final BedTypeFacade bedTypeFacade;

  public BedType.BedTypeResponse getBedType(Long id) {
    return bedTypeFacade.findById(id).toResponse();
  }

  public BedType.BedTypeResponse deleteBedType(Long id) {
    BedType bedType = bedTypeFacade.findById(id);
    bedTypeFacade.delete(bedType);
    return bedType.toResponse();
  }

  public BedType.BedTypeResponse updateBedType(Long id, BedType.BedTypeRequest request) {
    BedType bedType = bedTypeFacade.findById(id);
    bedType.setName(request.name());

    return bedTypeFacade.save(bedType).toResponse();
  }

  public Page<BedType.BedTypeResponse> getBedTypes(int page, int size, String query) {
    return bedTypeFacade.findAll(query, PageRequest.of(page, size)).map(BedType::fromEntity);
  }

  public BedType.BedTypeResponse createBedType(BedType.BedTypeRequest request) {
    BedType bedType = new BedType();
    bedType.setName(request.name());

    return bedTypeFacade.save(bedType).toResponse();
  }
}
