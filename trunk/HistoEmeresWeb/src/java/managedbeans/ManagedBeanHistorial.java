/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Aporte;
import entities.Cliente;
import entities.Producto;
import entities.Venta;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import sessionbeans.AporteFacadeLocal;
import sessionbeans.ClienteFacadeLocal;
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
    private ClienteFacadeLocal clienteFacade;
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
    private List<Cliente> clientes;
    private String xLabel;
    private String yLabel;
    private String titulo;
    private String grafico;
    private Map<String, String> listaGraficos;
    private boolean disableGrafico = true;
    private boolean disablePeriodo = true;
    private Integer seleccionHistorial = 1;
    private boolean error = false;
    private static String base64Str;
    private RenderedImage renderedImage;
    private boolean mostrarBoton;

    public ManagedBeanHistorial() {
        disablePeriodo = false;
        mostrarBoton=true;
        tipoGrafico = 1;
        listaGraficos = new LinkedHashMap<String, String>();
        listaGraficos.put("1", "Barras");
        listaGraficos.put("2", "Linea");
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
    }

    public boolean isDisableGrafico() {
        return disableGrafico;
    }

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void generarGrafico(ActionEvent actionEvent) {
        error = false;
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
                obtenerAportes(inicio, fin);
                if (aportes.isEmpty() || ventas.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran aportes o ventas para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton=false;
                    productos = productoFacade.findAll();
                    //obtenerAportes(inicio,fin);
                    setTitulo("Aportes V/S Ventas");

                    setyLabel("Valor ($)");
                    switch (tipoPeriodo) {
                        case 1:
                            setxLabel("Mes");
                            createCategoryModelAV();
                            break;
                        case 2:
                            setxLabel("Año");
                            createCategoryModelAVA();
                            break;
                    }
                }

                break;
            case 2:

                obtenerAportes(inicio, fin);
                if (aportes.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran aportes para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton=false;
                    setTitulo("Aportes Totales");

                    setyLabel("Valor ($)");
                    switch (tipoPeriodo) {
                        case 1:
                            setxLabel("Mes");
                            createCategoryModelAT();
                            break;
                        case 2:
                            setxLabel("Año");
                            createCategoryModelATA();
                            break;
                    }
                }
                break;


            case 3:

                obtenerAportes(inicio, fin);
                if (aportes.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran aportes para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton=false;
                    setTitulo("Aportes por Municipio");
                    setxLabel("Municipio");
                    setyLabel("Valor ($)");
                    createCategoryModelAPM();
                }
                break;
            case 4:

                obtenerVentas(inicio, fin);
                productos = productoFacade.findAll();
                if (ventas.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran ventas para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton=false;
                    setTitulo("Ventas Totales");

                    setyLabel("Valor ($)");
                    switch (tipoPeriodo) {
                        case 1:
                            setxLabel("Mes");
                            createCategoryModelVT();
                            break;
                        case 2:
                            setxLabel("Año");
                            createCategoryModelVTA();
                            break;
                    }
                }
                break;
            case 5:

                obtenerVentas(inicio, fin);
                productos = productoFacade.findAll();
                clientes = clienteFacade.findAll();
                if (ventas.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran ventas para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton=false;
                    setTitulo("Ventas por cliente");

                    setyLabel("Valor ($)");
                    setxLabel("Cliente");
                    createCategoryModelVPC();

                }
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
        seleccionHistorial = (Integer) event.getComponent().getAttributes().get("value");
        disableGrafico = false;

        if (seleccionHistorial == 3 || seleccionHistorial == 5) {
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
        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");
        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");

        Aporte aporte;
        Venta venta;
        Producto producto;
        Date fecha;

        float valor = 0;
        float cero = 0;

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
            } else if (!sales.getData().containsKey(format.format(fecha))) {
                sales.set(format.format(fecha), cero);
            }

        }



        categoryModel.addSeries(contributions);
        categoryModel.addSeries(sales);
    }

    private void createCategoryModelAVA() {

        categoryModel = new CartesianChartModel();
        ChartSeries contributions = new ChartSeries();
        contributions.setLabel("Aportes");
        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");

        Aporte aporte;
        Venta venta;
        Producto producto;
        String fecha;

        float valor = 0;
        float cero = 0;

        SortedSet<String> fechas = new TreeSet<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
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
            fechas.add(format.format(aporte.getAportePK().getFechaMunicipalidad()));
        }
        while (it2.hasNext()) {
            venta = it2.next();
            fechas.add(format.format(venta.getFechaVenta()));
        }
        Iterator<String> itss = fechas.iterator();



        it = aportes.listIterator();
        aporte = it.next();
        it2 = ventas.listIterator();
        venta = it2.next();

        while (itss.hasNext()) {
            fecha = itss.next();

            if (fecha.compareTo(format.format(aporte.getAportePK().getFechaMunicipalidad())) == 0) {

                if (contributions.getData().containsKey(format.format(aporte.getAportePK().getFechaMunicipalidad()))) {
                    contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte() + (Float) contributions.getData().get(format.format(aporte.getAportePK().getFechaMunicipalidad())));
                } else {
                    contributions.set(format.format(aporte.getAportePK().getFechaMunicipalidad()), aporte.getValorAporte());
                }
                while (fecha.compareTo(format.format(aporte.getAportePK().getFechaMunicipalidad())) == 0 && it.hasNext()) {

                    aporte = it.next();
                    if (fecha.compareTo(format.format(aporte.getAportePK().getFechaMunicipalidad())) != 0) {
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
            if (fecha.compareTo(format.format(venta.getFechaVenta())) == 0) {
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
                while (it2.hasNext() && fecha.compareTo(format.format(venta.getFechaVenta())) == 0) {
                    venta = it2.next();
                    if (fecha.compareTo(format.format(venta.getFechaVenta())) != 0) {
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
            } else if (!sales.getData().containsKey(format.format(fecha))) {
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

    private void createCategoryModelATA() {

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy");


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

    private void createCategoryModelVT() {

        categoryModel = new CartesianChartModel();

        Venta venta;
        Producto producto;

        float valor = 0;

        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");
        Collections.sort(ventas, new Comparator<Venta>() {
            @Override
            public int compare(Venta v1, Venta v2) {
                return v1.getFechaVenta().compareTo(v2.getFechaVenta());
            }
        });
        Iterator<Venta> it2 = ventas.iterator();
        Iterator<Producto> it3 = productos.iterator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");


        while (it2.hasNext()) {
            venta = it2.next();
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
        categoryModel.addSeries(sales);
    }

    private void createCategoryModelVTA() {

        categoryModel = new CartesianChartModel();

        Venta venta;
        Producto producto;

        float valor = 0;

        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");
        Collections.sort(ventas, new Comparator<Venta>() {
            @Override
            public int compare(Venta v1, Venta v2) {
                return v1.getFechaVenta().compareTo(v2.getFechaVenta());
            }
        });
        Iterator<Venta> it2 = ventas.iterator();
        Iterator<Producto> it3 = productos.iterator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");


        while (it2.hasNext()) {
            venta = it2.next();
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
        categoryModel.addSeries(sales);
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

    private void createCategoryModelVPC() {

        categoryModel = new CartesianChartModel();
        pieModel = new PieChartModel();

        Venta venta;
        Cliente cliente;
        Producto producto;
        Map<Integer, String> clients = new HashMap<>();

        float valor = 0;

        ChartSeries sales = new ChartSeries();
        sales.setLabel("Ventas");

        Iterator<Venta> it2 = ventas.iterator();
        Iterator<Producto> it3 = productos.iterator();
        Iterator<Cliente> it4 = clientes.iterator();

        while (it4.hasNext()) {
            cliente = it4.next();
            clients.put(cliente.getRutCliente(), cliente.getNombreCliente());
        }


        while (it2.hasNext()) {
            venta = it2.next();
            it3 = productos.iterator();
            while (it3.hasNext()) {
                producto = it3.next();
                if (venta.getCodigoProducto().getCodigoProducto() == producto.getCodigoProducto()) {
                    valor = producto.getValorProducto() * venta.getCantidadVenta();
                }
            }
            if (sales.getData().containsKey(clients.get(venta.getRutCliente().getRutCliente()))) {
                pieModel.set(clients.get(venta.getRutCliente().getRutCliente()), valor + (Float) sales.getData().get(clients.get(venta.getRutCliente().getRutCliente())));
                sales.set(clients.get(venta.getRutCliente().getRutCliente()), valor + (Float) sales.getData().get(clients.get(venta.getRutCliente().getRutCliente())));
            } else {
                pieModel.set(clients.get(venta.getRutCliente().getRutCliente()), valor);
                sales.set(clients.get(venta.getRutCliente().getRutCliente()), valor);
            }
        }
        categoryModel.addSeries(sales);

    }

    public void obtenerVentas(Date start, Date end) {
        ventas = ventaFacade.buscarPorPeriodo(inicio, fin);
    }

    public void obtenerAportes(Date start, Date end) {
        aportes = aporteFacade.BuscarPorPeriodo(inicio, fin);
    }

    public byte[] pasarAImagen() {
        // You probably want to have a more comprehensive check here. 
        // In this example I only use a simple check
        if (base64Str.split(",").length > 1) {
            String encoded = base64Str.split(",")[1];
            byte[] decoded = Herramientas.decode(encoded);
            // Write to a .png file
            try {
                renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
                ImageIO.write(renderedImage, "png", new File("C:\\out.png")); // use a proper path & file name here.
            } catch (IOException e) {
                e.printStackTrace();
            }
            return decoded;
        }
        return null;
    }

    public void crearPDF() throws DocumentException, FileNotFoundException, BadElementException, MalformedURLException, IOException {
        Document documento = new Document();
        String realPath = "";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realPath = (String) servletContext.getRealPath("/");
        FacesContext context = FacesContext.getCurrentInstance();
        FileOutputStream ficheroPdf = new FileOutputStream(realPath+"grafico.pdf");
        PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
        documento.open();
        Image foto = Image.getInstance(pasarAImagen(), error);
        // foto.setAlignment(Chunk.ALIGN_MIDDLE);
        documento.add(foto);
        documento.close();


    }
}
