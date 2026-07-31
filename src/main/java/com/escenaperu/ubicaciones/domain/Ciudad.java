package com.escenaperu.ubicaciones.domain;

public class Ciudad {
    private final Integer id;
    private final String nombre;
    private final String departamento;
    private final boolean activa; // permite lanzar Lima/Arequipa primero, sumar ciudades despues

    public Ciudad(Integer id, String nombre, String departamento, boolean activa) {
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
