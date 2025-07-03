package com.example.EduTech.Assembler;

import com.example.EduTech.Controller.AdminSistemaController;
import com.example.EduTech.Model.Usuarios.AdminSistema;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class AdminSistemaModelAssembler implements RepresentationModelAssembler<AdminSistema, EntityModel<AdminSistema>> {

     /*
    RepresentionalModel = Permite a la clase contener una lista de Links de acceso
    EntityModel = Es un contenedor generico que adjunta la entidad mas una serie de enlaces
    LinkTo = Es un metodo que nos permite construir los Links o URL's de acceso que posee nuestro controlador
    */

    @Override
    public EntityModel<AdminSistema> toModel(AdminSistema admin) {
        return EntityModel.of(admin,
                linkTo(methodOn(AdminSistemaController.class).obtenerPorId(admin.getId())).withSelfRel(),
                linkTo(methodOn(AdminSistemaController.class).listar()).withRel("administradores"),
                linkTo(methodOn(AdminSistemaController.class).crear(null)).withRel("crear"),
                linkTo(methodOn(AdminSistemaController.class).actualizar(null)).withRel("actualizar"),
                linkTo(methodOn(AdminSistemaController.class).eliminar(admin.getId())).withRel("eliminar")
        );
    }

    @Override
    public CollectionModel<EntityModel<AdminSistema>> toCollectionModel(Iterable<? extends AdminSistema> entities) {
        CollectionModel<EntityModel<AdminSistema>> model = RepresentationModelAssembler.super.toCollectionModel(entities);
        model.add(linkTo(methodOn(AdminSistemaController.class).listar()).withSelfRel());
        return model;
    }
}