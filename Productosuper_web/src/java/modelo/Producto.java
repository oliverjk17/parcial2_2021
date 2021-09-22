/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author OLIVER
 */
public class Producto {
    private String producto, descripcion;
    private int idmarca,existencia;
    private double precio_costo, precio_venta;
    Conexion cn;
    
    public Producto(){}
    public Producto(String producto, String descripcion, int idmarca, int existencia, double precio_costo, double precio_venta) {
        this.producto = producto;
        this.descripcion = descripcion;
        this.idmarca = idmarca;
        this.existencia = existencia;
        this.precio_costo = precio_costo;
        this.precio_venta = precio_venta;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(int idmarca) {
        this.idmarca = idmarca;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getPrecio_costo() {
        return precio_costo;
    }

    public void setPrecio_costo(double precio_costo) {
        this.precio_costo = precio_costo;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }
    
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla  = new DefaultTableModel();
        try{
            cn = new Conexion();
            String query = "SELECT p.idproducto as id,p.producto,m.marca,p.idmarca,p.descripcion,p.precio_costo,p.precio_venta,p.existencias FROM productos as p inner join marcas as m on p.idmarca = m.idmarca order by id;";
            cn.abrir_conexion();
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            String encabezado[] = {"Id","Producto","Marca","Id_marca","Descripcion","Precio_costo","Precio_venta","Existencia"};
            tabla.setColumnIdentifiers(encabezado);
            String datos[] = new String[8];
            while(consulta.next()){
                datos[0]=consulta.getString("id");
                datos[1]=consulta.getString("producto");
                datos[2]=consulta.getString("marca");
                datos[3]=consulta.getString("idmarca");
                datos[4]=consulta.getString("descripcion");
                datos[5]=consulta.getString("precio_costo");
                datos[6]=consulta.getString("precio_venta");
                datos[7]=consulta.getString("existencias");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
        }
        return tabla;
    }
    
    public int agregar(){
        int retorno = 0;
        try{
            PreparedStatement parametro;
            String query = "INSERT INTO productos(producto, idmarca, descripcion, precio_costo, precio_venta, existencias) VALUES(?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getProducto());
            parametro.setInt(2,getIdmarca());
            parametro.setString(3,getDescripcion());
            parametro.setDouble(4,getPrecio_costo());
            parametro.setDouble(5,getPrecio_venta());
            parametro.setInt(6,getExistencia());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(HeadlessException | SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
            retorno =0;
        }
        return retorno;
    }
    
}
