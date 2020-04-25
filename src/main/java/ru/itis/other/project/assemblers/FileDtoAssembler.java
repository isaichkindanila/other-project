package ru.itis.other.project.assemblers;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.controllers.api.FileApiController;
import ru.itis.other.project.controllers.api.StorageApiController;
import ru.itis.other.project.dto.storage.FullFileInfoDto;
import ru.itis.other.project.repositories.interfaces.StorageEntityRepository;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@AllArgsConstructor
public class FileDtoAssembler implements RepresentationModelAssembler<FullFileInfoDto, FullFileInfoDto> {

    private final StorageEntityRepository repository;

    private WebMvcLinkBuilder linkDir(Function<StorageApiController, ?> function) {
        return linkTo(function.apply(methodOn(StorageApiController.class)));
    }

    private FileApiController method() {
        return methodOn(FileApiController.class);
    }

    @Override
    @Transactional
    public FullFileInfoDto toModel(FullFileInfoDto entity) {
        entity.add(linkTo(method().getInfo(entity.getToken())).withSelfRel()
                .andAffordance(afford(method().download(entity.getToken(), null)))
        );

        repository.findByToken(entity.getToken()).ifPresent(e -> {
            if (e.getParent() == null) {
                entity.add(linkDir(StorageApiController::getRoot).withRel("parent"));
            } else {
                entity.add(linkDir(c -> c.getDirectory(e.getParent().getToken())).withRel("parent"));
            }
        });

        return entity;
    }
}
