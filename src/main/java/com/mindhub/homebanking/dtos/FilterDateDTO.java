package com.mindhub.homebanking.dtos;


import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public class FilterDateDTO {
    private LocalDate desde;
    private LocalDate hasta;
    private String number;

    public FilterDateDTO() {
    }

    public FilterDateDTO(LocalDate desde, LocalDate hasta, String number) {
        this.desde = desde;
        this.hasta = hasta;
        this.number = number;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
