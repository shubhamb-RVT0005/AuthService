package com.shrayu.entity;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class RolesConverter
        implements AttributeConverter<Roles, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Roles role) {

        if (role == null) {
            return null;
        }

        return role.getCode();
    }

    @Override
    public Roles convertToEntityAttribute(Integer code) {

        return Roles.fromCode(code);
    }
}




