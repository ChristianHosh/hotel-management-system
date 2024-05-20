package com.chris.hotelmanagementsystem.addon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/api/addons", consumes = "application/json", produces = "application/json")
class AddonController {

    private final AddonService service;

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get addons page",
            description = "Get addons page",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    public Page<Addon.AddonResponse> getAddons(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "query", defaultValue = "") String query
    ) {
        return service.getAddons(page, size, query);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Create addon",
            description = "Create addon",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public Addon.AddonResponse createAddon(@RequestBody @Valid Addon.AddonRequest request) {
        return service.createAddon(request);
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Get addon by id",
            description = "Get addon by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    public Addon.AddonResponse getAddon(@PathVariable Long id) {
        return service.getAddon(id);
    }

    @PutMapping("/{id}")
    @Operation(
            method = "PUT",
            summary = "Update addon by id",
            description = "Update addon by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    public Addon.AddonResponse updateAddon(@PathVariable Long id, @RequestBody @Valid Addon.AddonRequest request) {
        return service.updateAddon(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete addon by id",
            description = "Delete addon by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    public Addon.AddonResponse deleteAddon(@PathVariable Long id) {
        return service.deleteAddon(id);
    }
}
