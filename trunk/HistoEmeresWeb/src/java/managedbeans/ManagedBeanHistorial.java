/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Aporte;
import entities.Producto;
import entities.Venta;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
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
    private PieChartModel pieModel;
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
    private Map<String, String> listaGraficos;
    private boolean disableGrafico = true;
    private boolean disablePeriodo = true;

    public ManagedBeanHistorial() {
    }

    public boolean isDisableGrafico() {
        return disableGrafico;
    }

    public void setDisableGrafico(boolean disableGrafico) {
        this.disableGrafico = disableGrafico;
    }

    public boolean isDisablePeriodo() {
        return disablePeriodo;
    }

    public void setDisablePeriodo(boolean disablePeriodo) {
        this.disablePeriodo = disablePeriodo;
    }

    public String getGrafico() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
    }

    public Map<String, String> getListaGraficos() {
        return listaGraficos;
    }

    public void setListaGRaficos(Map<String, String> listaPeriodos) {
        this.listaGraficos = listaPeriodos;
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
                obtenerVentas(inicio, fin);
                obtenerAportes(inicio,fin);
                productos = productoFacade.findAll();
                //obtenerAportes(inicio,fin);
                setTitulo("Aportes V/S Ventas");
                setxLabel("Mes");
                setyLabel("Valor ($)");
                createCategoryModelAV();

                break;
            case 2:
                
                obtenerAportes(inicio,fin);

                setTitulo("Aportes Totales");
                setxLabel("Mes");
                setyLabel("Valor ($)");
                createCategoryModelAT();
                break;
            case 3:
                
                obtenerAportes(inicio,fin);
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

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void cargaTipoPeriodo(AjaxBehaviorEvent event) {
        Integer seleccionHistorial = (Integer) event.getComponent().getAttributes().get("value");
        disableGrafico = false;
        if (seleccionHistorial == 3) {
            disablePeriodo = true;
            listaGraficos = new LinkedHashMap<String, String>();
            listaGraficos.put("1", "Barras");
            listaGraficos.put("2", "Linea");
            listaGraficos.put("3", "Torta");

        } else {
            disablePeriodo = false;
            listaGraficos = new LinkedHashMap<String, String>();
            listaGraficos.put("1", "Barras");
            listaGraficos.put("2", "Linea");
        }
    }

    private void createCategoryModelAV() {

        categoryModel = new CartesianChartModel();

        Aporte aporte;
        Venta venta;
        Producto producto;
        Date fecha;

        float valor = 0;
        float cero = 0;

        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");
        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");

        SortedSet<Date> fechas = new TreeSet<Date>();




        Collections.sort(aportes, new Comparator<Aporte>() {
            @Override
            public int compare(Aporte a1, Aporte a2) {
                return a1.getAportePK().getFechaMunicipalidad().compareTo(a2.getAportePK().getFechaMunicipalidad());
            }
        });
        Collections.sort(ventas, new Comparator<Venta>() {
            @Override
            public int compare(Venta v1, Venta v2) {
                return v1.getFechaVenta().compareTo(v2.getFechaVenta());
            }
        });

        Iterator<Aporte> it = aportes.listIterator();
        Iterator<Venta> it2 = ventas.listIterator();
        Iterator<Producto> it3 = productos.iterator();

        while (it.hasNext()) {
            aporte = it.next();
            fechas.add(aporte.getAportePK().getFechaMunicipalidad());
        }
        while (it2.hasNext()) {
            venta = it2.next();
            fechas.add(venta.getFechaVenta());
        }
        Iterator<Date> itss = fechas.iterator();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

        it = aportes.listIterator();
        aporte = it.next();
        it2 = ventas.listIterator();
        venta = it2.next();

        while (itss.hasNext()) {
            fecha = itss.next();

            if (fecha.compareTo(aporte.getAportePK().getFechaMunicipalidad()) == 0) {

                if (contributions.getData().containsKey(format.format(aporte.getAportePK().getFechaMunicipalidad()))) {
                    contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte() + (Float) contributions.getData().get(format.format(aporte.getAportePK().getFechaMunicipalidad())));
                } else {
                    contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());
                }
                while (fecha.compareTo(aporte.getAportePK().getFechaMunicipalidad()) == 0 && it.hasNext()) {

                    aporte = it.next();
                    if (fecha.compareTo(aporte.getAportePK().getFechaMunicipalidad()) != 0) {
                        break;
                    }
                    if (contributions.getData().containsKey(format.format(aporte.getAportePK().getFechaMunicipalidad()))) {
                        contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte() + (Float) contributions.getData().get(format.format(aporte.getAportePK().getFechaMunicipalidad())));
                    } else {
                        contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());
                    }
                }
            } else if (!contributions.getData().containsKey(format.format(fecha))) {
                contributions.set(format.format(fecha), cero);
            }
            if (fecha.compareTo(venta.getFechaVenta()) == 0) {
                it3 = productos.iterator();
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
                while (it2.hasNext() && fecha.compareTo(venta.getFechaVenta()) == 0) {
                    venta = it2.next();
                    if (fecha.compareTo(venta.getFechaVenta()) != 0) {
                        break;
                    }
                    it3 = productos.iterator();
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
            } else if(!sales.getData().containsKey(format.format(fecha))){
                sales.set(format.format(fecha), cero);
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
        Collections.sort(aportes, new Comparator<Aporte>() {
            @Override
            public int compare(Aporte a1, Aporte a2) {
                return a1.getAportePK().getFechaMunicipalidad().compareTo(a2.getAportePK().getFechaMunicipalidad());
            }
        });
        Iterator<Aporte> it = aportes.iterator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");


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
        pieModel = new PieChartModel();
        Aporte aporte;
        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");

        Iterator<Aporte> it = aportes.iterator();

        aporte = it.next();
        contributions.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte());
        pieModel.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte());
        while (it.hasNext()) {
            aporte = it.next();
            if (contributions.getData().containsKey(aporte.getAportePK().getMunicipioAporte())) {
                contributions.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte() + (Float) contributions.getData().get(aporte.getAportePK().getMunicipioAporte()));
                pieModel.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte() + (Float) pieModel.getData().get(aporte.getAportePK().getMunicipioAporte()));
            } else {
                pieModel.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte());
                contributions.set(aporte.getAportePK().getMunicipioAporte(), aporte.getValorAporte());
            }
        }
        categoryModel.addSeries(contributions);
    }

    public void obtenerVentas(Date start, Date end) {
        ventas = ventaFacade.buscarPorPeriodo(inicio, fin);
    }

    public void obtenerAportes(Date start, Date end) {
        aportes=aporteFacade.BuscarPorPeriodo(inicio, fin);
    }
}
