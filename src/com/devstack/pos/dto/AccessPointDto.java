package com.devstack.pos.dto;

import lombok.*;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessPointDto {
    private Long propertyId;
    private String pointName;

    @Override
    public String toString() {
        return this.pointName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessPointDto that = (AccessPointDto) o;
        return Objects.equals(propertyId, that.propertyId) &&
                Objects.equals(pointName, that.pointName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId, pointName);
    }
}
