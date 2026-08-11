package com.escenaperu.ubicaciones.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "ciudades")
public class CiudadJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String departamento;
    private boolean activa;

    protected CiudadJpaEntity() { }

    public CiudadJpaEntity(Integer id, String nombre, String departamento, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.activa = activa;
    }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDepartamento() { return departamento; }
    public boolean isActiva() { return activa; }
}