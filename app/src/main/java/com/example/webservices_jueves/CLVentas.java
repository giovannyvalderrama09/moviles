package com.example.webservices_jueves;

public class CLVentas {
    private String codigo_venta;
    private String fecha;
    private String identificacion_usuario;
    private String valor_venta;
    private String activo;

    public CLVentas() {

    }
        public String getcodigo_venta() { return codigo_venta; }
        public void setcodigo_venta(String codigo_venta) { this.codigo_venta = codigo_venta; }

        public String getfecha() { return fecha; }
        public void setfecha(String fecha) { this.fecha = fecha; }

        public String getidentificacion_usuario() { return identificacion_usuario;}
        public void setidentificacion_usuario(String identificacion_usuario) { this.identificacion_usuario = identificacion_usuario; }


        public String getvalor_venta() { return valor_venta; }
        public void setvalor_venta(String valor_venta) { this.valor_venta = valor_venta;}
}
