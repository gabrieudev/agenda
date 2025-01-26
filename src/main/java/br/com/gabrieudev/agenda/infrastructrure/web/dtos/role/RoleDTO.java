package br.com.gabrieudev.agenda.infrastructrure.web.dtos.role;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private UUID id;
    private String name;
    private String description;

    public static RoleDTO from(Role role) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role toDomainObj() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Role.class);
    }
}