package ru.itis.other.project.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import ru.itis.other.project.controllers.api.FileApiController;
import ru.itis.other.project.controllers.api.StorageApiController;
import ru.itis.other.project.dto.storage.DirectoryDto;
import ru.itis.other.project.dto.storage.IndexDirectoryInfoDto;
import ru.itis.other.project.dto.storage.IndexFileInfoDto;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DirectoryDtoAssembler implements RepresentationModelAssembler<DirectoryDto, DirectoryDto> {

    private FileApiController methodFile() {
        return methodOn(FileApiController.class);
    }

    private StorageApiController methodDir() {
        return methodOn(StorageApiController.class);
    }

    private WebMvcLinkBuilder linkFile(Function<FileApiController, ?> function) {
        return linkTo(function.apply(methodFile()));
    }

    private WebMvcLinkBuilder linkDir(Function<StorageApiController, ?> function) {
        return linkTo(function.apply(methodDir()));
    }

    private void process(IndexFileInfoDto dto) {
        dto.add(linkFile(c -> c.getInfo(dto.getToken())).withSelfRel());
    }

    private void process(IndexDirectoryInfoDto dto) {
        dto.add(linkDir(c -> c.getDirectory(dto.getToken())).withSelfRel());
    }

    @Override
    public DirectoryDto toModel(DirectoryDto entity) {
        if (entity.getSelf() != null) {
            process(entity.getSelf());
            entity.add(linkDir(c -> c.getDirectory(entity.getSelf().getToken())).withSelfRel()
                    .andAffordance(afford(methodDir().createWithParent(entity.getSelf().getToken(), null)))
            );
        } else {
            entity.add(linkDir(StorageApiController::getRoot).withSelfRel()
                    .andAffordance(afford(methodDir().createInRoot(null)))
            );
        }

        if (entity.getParent() != null) {
            process(entity.getParent());
            entity.add(linkDir(c -> c.getDirectory(entity.getParent().getToken())).withRel("parent"));
        } else if (entity.getSelf() != null) {
            entity.add(linkDir(StorageApiController::getRoot).withRel("parent"));
        }

        entity.getFiles().forEach(this::process);
        entity.getDirectories().forEach(this::process);

        return entity;
    }
}
