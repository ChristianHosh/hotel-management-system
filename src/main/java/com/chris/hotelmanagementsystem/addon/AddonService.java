package com.chris.hotelmanagementsystem.addon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AddonService {

    private final AddonFacade addonFacade;

    public Addon.AddonResponse getAddon(Long id) {
        return addonFacade.findById(id).toResponse();
    }

    public Addon.AddonResponse deleteAddon(Long id) {
        Addon addon = addonFacade.findById(id);
        addonFacade.delete(addon);
        return addon.toResponse();
    }

    public Addon.AddonResponse updateAddon(Long id, Addon.AddonRequest request) {
        Addon addon = addonFacade.findById(id);
        addon.setName(request.name());
        addon.setBasePrice(request.basePrice());

        return addonFacade.save(addon).toResponse();
    }


    public Page<Addon.AddonResponse> getAddons(int page, int size, String query) {
        return addonFacade.findAll(query, PageRequest.of(page, size)).map(Addon::fromEntity);
    }

    public Addon.AddonResponse createAddon(Addon.AddonRequest request) {
        Addon addon = new Addon();
        addon.setName(request.name());
        addon.setBasePrice(request.basePrice());

        return addonFacade.save(addon).toResponse();
    }
}
