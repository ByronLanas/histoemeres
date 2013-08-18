/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Aporte;
import entities.Producto;
import entities.Venta;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import sessionbeans.AporteFacadeLocal;
import sessionbeans.ProductoFacadeLocal;
import sessionbeans.VentaFacadeLocal;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanHistorial")
@SessionScoped
public class ManagedBeanHistorial implements Serializable {

    @EJB
    private ProductoFacadeLocal productoFacade;
    @EJB
    private AporteFacadeLocal aporteFacade;
    @EJB
    private VentaFacadeLocal ventaFacade;
    private CartesianChartModel categoryModel;
    private int tipoGrafico;
    private int tipoHistorial;
    private int tipoPeriodo;
    private Date inicio;
    private Date fin;
    private List<Venta> ventas;
    private List<Aporte> aportes;
    private List<Producto> productos;
    private String xLabel;
    private String yLabel;
    private String titulo;
    private String grafico;

    public ManagedBeanHistorial() {
    }

    public String getGrafico() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
    }

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTipoGrafico() {
        return tipoGrafico;
    }

    public void setTipoGrafico(int tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public List<Aporte> getAportes() {
        return aportes;
    }

    public void setAportes(List<Aporte> aportes) {
        this.aportes = aportes;
    }

    public int getTipoHistorial() {
        return tipoHistorial;
    }

    public void setTipoHistorial(int tipoHistorial) {
        this.tipoHistorial = tipoHistorial;
    }

    public int getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(int tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Date fechaActual() {
        return new Date();
    }
    /*
     @PostConstruct
     public void init(){
     ventas=ventaFacade.findAll();
     aportes=aporteFacade.findAll();
     productos=productoFacade.findAll();
            
     }
     */

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void generarGrafico(ActionEvent actionEvent) {
        switch (tipoGrafico) {
            case 1:
                setGrafico("barra");
                break;
            case 2:
                setGrafico("linea");
                break;
        }
        switch (tipoHistorial) {
            case 1:
                ventas = ventaFacade.findAll();
                aportes = aporteFacade.findAll();
                productos = productoFacade.findAll();
                //obtenerAportes(inicio,fin);
                setTitulo("Aportes V/S Ventas");
                setxLabel("Mes");
                setyLabel("Valor ($)");
                createCategoryModelAV();

                break;
            case 2:
                aportes = aporteFacade.findAll();
                //obtenerAportes(inicio,fin);
                
                setTitulo("Aportes Totales");
                setxLabel("Mes");
                setyLabel("Valor ($)");
                createCategoryModelAT();
                break;
            case 3:
                aportes = aporteFacade.findAll();
                //obtenerAportes(inicio,fin);
                setTitulo("Aportes por Municipio");
                setxLabel("Municipio");
                setyLabel("Valor ($)");
                createCategoryModelAPM();
                break;
        }
        
    }

    public CartesianChartModel getCategoryModel() {
        return categoryModel;
    }

    private void createCategoryModelAV() {
        categoryModel = new CartesianChartModel();
        Aporte aporte;
        Venta venta;
        float valor = 0;
        Producto producto;
        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");

        Iterator<Aporte> it = aportes.iterator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        aporte = it.next();
        contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());

        while (it.hasNext()) {
            aporte = it.next();
            if (contributions.getData().containsKey(format.format(aporte.getAportePK().getFechaMunicipalidad()))) {
                contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte() + (Float) contributions.getData().get(format.format(aporte.getAportePK().getFechaMunicipalidad())));
            } else {
                contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());
            }
        }

        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");

        Iterator<Venta> it2 = ventas.iterator();
        Iterator<Producto> it3 = productos.iterator();


        venta = it2.next();

        while (it3.hasNext()) {
            producto = it3.next();
            if (venta.getCodigoProducto().getCodigoProducto() == producto.getCodigoProducto()) {
                valor = producto.getValorProducto() * venta.getCantidadVenta();
            }
        }

        sales.set(format.format(venta.getFechaVenta()), valor);

        while (it2.hasNext()) {
            venta = it2.next();
            while (it3.hasNext()) {
                producto = it3.next();
                if (venta.getCodigoProducto().getCodigoProducto() == producto.getCodigoProducto()) {
                    valor = producto.getValorProducto() * venta.getCantidadVenta();
                }
            }
            if (sales.getData().containsKey(format.format(venta.getFechaVenta()))) {

                sales.set(format.format(venta.getFechaVenta()), valor + (Float) sales.getData().get(format.format(venta.getFechaVenta())));
            } else {

                sales.set(format.format(venta.getFechaVenta()), valor);
            }
        }
        categoryModel.addSeries(contributions);
        categoryModel.addSeries(sales);
    }

    private void createCategoryModelAT() {

        categoryModel = new CartesianChartModel();
        Aporte aporte;
        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");

        Iterator<Aporte> it = aportes.iterator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        aporte = it.next();
        contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());

        while (it.hasNext()) {
            aporte = it.next();
            if (contributions.getData().containsKey(format.format(aporte.getAportePK().getFechaMunicipalidad()))) {
                contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte() + (Float) contributions.getData().get(format.format(aporte.getAportePK().getFechaMunicipalidad())));
            } else {
                contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());
            }
        }
        categoryModel.addSeries(contributions);
    }

    private void createCategoryModelAPM() {

        categoryModel = new CartesianChartModel();
        Aporte aporte;
        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");

        Iterator<Aporte> it = aportes.iterator();

        aporte = it.next();
        contributions.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte());

        while (it.hasNext()) {
            aporte = it.next();
            if (contributions.getData().containsKey(aporte.getAportePK().getMunicipioAporte())) {
                contributions.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte() + (Float) contributions.getData().get(aporte.getAportePK().getMunicipioAporte()));
            } else {
                contributions.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte());
            }
        }
        categoryModel.addSeries(contributions);
    }

    public void obtenerVentas(Date start, Date end) {
        ventas = ventaFacade.buscarPorPeriodo(inicio, fin);
    }

    public void obtenerAportes(Date start, Date end) {
        //aporteFacade
    }

    public class SwitchController {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }
}
