/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Aporte;
import entities.Cliente;
import entities.Producto;
import entities.Venta;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
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
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
    private boolean mostrarBoton;
    private boolean tablaAportes;
    private boolean tablaVentas;
    private  StreamedContent file; 

    public ManagedBeanHistorial() {
        disablePeriodo = false;
        mostrarBoton = true;
        tipoGrafico = 1;
        listaGraficos = new LinkedHashMap<String, String>();
        listaGraficos.put("1", "Barras");
        listaGraficos.put("2", "Linea");
    }

    public StreamedContent getFile() {
        return file;
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
                tablaAportes = true;
                tablaVentas = true;

                obtenerVentas(inicio, fin);
                obtenerAportes(inicio, fin);
                if (aportes.isEmpty() || ventas.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran aportes o ventas para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton = false;

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
                tablaAportes = true;
                tablaVentas = false;
                obtenerAportes(inicio, fin);
                if (aportes.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran aportes para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton = false;
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
                tablaAportes = true;
                tablaVentas = false;
                obtenerAportes(inicio, fin);
                if (aportes.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran aportes para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton = false;
                    setTitulo("Aportes por Municipio");
                    setxLabel("Municipio");
                    setyLabel("Valor ($)");
                    createCategoryModelAPM();
                }
                break;
            case 4:
                tablaAportes = false;
                tablaVentas = true;
                obtenerVentas(inicio, fin);

                if (ventas.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran ventas para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton = false;
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
                tablaAportes = false;
                tablaVentas = true;
                obtenerVentas(inicio, fin);

                if (ventas.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido", "No se registran ventas para el periodo seleccionado"));
                    error = true;
                } else {
                    mostrarBoton = false;
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
                    if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
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
                        if (venta.getCodigoProducto().getCodigoProducto().compareTo( producto.getCodigoProducto()) == 0) {
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
            } else if (!contributions.getData().containsKey(fecha)) {
                contributions.set(fecha, cero);
            }
            if (fecha.compareTo(format.format(venta.getFechaVenta())) == 0) {
                it3 = productos.iterator();
                while (it3.hasNext()) {
                    producto = it3.next();
                    if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
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
                        if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
                            valor = producto.getValorProducto() * venta.getCantidadVenta();
                        }
                    }
                    if (sales.getData().containsKey(format.format(venta.getFechaVenta()))) {

                        sales.set(format.format(venta.getFechaVenta()), valor + (Float) sales.getData().get(format.format(venta.getFechaVenta())));
                    } else {

                        sales.set(format.format(venta.getFechaVenta()), valor);
                    }
                }
            } else if (!sales.getData().containsKey(fecha)) {
                sales.set(fecha, cero);
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
                if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
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
                if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
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
        Map<String, String> clients = new HashMap<>();

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
                if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
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
        clientes = clienteFacade.findAll();
        productos = productoFacade.findAll();
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
            return decoded;
        }
        return null;
    }

    class TableHeader extends PdfPageEventHelper {

        /**
         * The header text.
         */
        String header;
        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        /**
         * Allows us to change the content of the header.
         *
         * @param header The new header String
         */
        public void setHeader(String header) {
            this.header = header;
        }

        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a header to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);
            try {
                table.setWidths(new int[]{36, 12, 2});
                table.setTotalWidth(527);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(20);
                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
                table.addCell(new Phrase(header, FontFactory.getFont("arial", // fuente
                        12, // tamaño
                        Font.ITALIC, // estilo
                        BaseColor.LIGHT_GRAY)));
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(new Phrase(String.format("Página %d de", writer.getPageNumber()), FontFactory.getFont("arial", // fuente
                        12, // tamaño
                        Font.ITALIC, // estilo
                        BaseColor.LIGHT_GRAY)));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        /**
         * Fills out the total number of pages before the document is closed.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1), FontFactory.getFont("arial", // fuente
                    12, // tamaño
                    Font.ITALIC, // estilo
                    BaseColor.LIGHT_GRAY)),
                    2, 2, 0);
        }
    }

    public void crearPDF(AjaxBehaviorEvent event ) throws DocumentException, FileNotFoundException, BadElementException, MalformedURLException, IOException {
        Document documento = new Document(PageSize.A4, 36, 36, 66, 36);
        String tituloPDF = "Gráfico " + titulo + " ";

        String realPath = "";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realPath = (String) servletContext.getRealPath("/");

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Object currentUser = session.getAttribute("username");

        FileOutputStream ficheroPdf = new FileOutputStream(realPath + "resources/Temp/grafico" + currentUser + ".pdf");

        PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
        TableHeader evento = new TableHeader();
        writer.setPageEvent(evento);
        documento.open();
        documento.addAuthor("HistoEmeres");
        documento.addCreator("HistoEmeres Web");
        documento.addTitle(titulo);
        if (tipoPeriodo == 1) {
            tituloPDF += "Mensual (";
        } else if (tipoPeriodo == 2) {
            tituloPDF += "Anual (";
        }

        tituloPDF += Herramientas.fechaConDia(inicio) + " - " + Herramientas.fechaConDia(fin) + ")\r\n\r\n\r\n";
        evento.setHeader(tituloPDF);

        Image foto = Image.getInstance(pasarAImagen(), error);

        foto.scaleToFit(740, 640);
        foto.setRotationDegrees(-90);
        foto.setAlignment(Chunk.ALIGN_CENTER);
        documento.add(foto);
        //documento.newPage();
        //writer.setPageEmpty(false);

        documento.newPage();



        if (tablaAportes) {
            Collections.sort(aportes, new Comparator<Aporte>() {
                @Override
                public int compare(Aporte a1, Aporte a2) {
                    return a1.getAportePK().getFechaMunicipalidad().compareTo(a2.getAportePK().getFechaMunicipalidad());
                }
            });
            Iterator<Aporte> it = aportes.listIterator();
            Aporte aporte;

            PdfPTable table = new PdfPTable(new float[]{5, 5, 5});
            table.setWidthPercentage(100f);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            // Add the first header row
            Font f = new Font();
            f.setColor(BaseColor.WHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Aportes", f));
            cell.setBackgroundColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(3);
            table.addCell(cell);
            // Add the second header row twice
            table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

            table.addCell("Municipalidad");
            table.addCell("Valor");
            table.addCell("Fecha");
            table.getDefaultCell().setBackgroundColor(null);
            // There are three special rows
            table.setHeaderRows(2);
            while (it.hasNext()) {
                aporte = it.next();
                table.addCell(aporte.getAportePK().getMunicipioAporte());
                table.addCell(String.valueOf(aporte.getValorAporte()));
                table.addCell(Herramientas.fechaConPalabras(aporte.getAportePK().getFechaMunicipalidad()));


            }
            documento.add(table);
            documento.newPage();

        }
        if (tablaVentas) {
            Collections.sort(ventas, new Comparator<Venta>() {
                @Override
                public int compare(Venta v1, Venta v2) {
                    return v1.getFechaVenta().compareTo(v2.getFechaVenta());
                }
            });

            Iterator<Venta> it2 = ventas.listIterator();

            Venta venta;
            Cliente cliente;
            Producto producto;
            float valor = 0;

            Map<String, String> clients = new HashMap<>();
            Iterator<Cliente> it4 = clientes.listIterator();
            Iterator<Producto> it3 = productos.listIterator();
            while (it4.hasNext()) {
                cliente = it4.next();
                clients.put(cliente.getRutCliente(), cliente.getNombreCliente());
            }


            PdfPTable table = new PdfPTable(new float[]{5, 3, 2, 3, 3});
            table.setWidthPercentage(100f);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            // Add the first header row
            Font f = new Font();
            f.setColor(BaseColor.WHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Ventas", f));
            cell.setBackgroundColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(5);
            table.addCell(cell);
            // Add the second header row twice
            table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

            table.addCell("Nombre cliente");
            table.addCell("Producto");
            table.addCell("Cantidad (fardos)");
            table.addCell("Valor ($)");
            table.addCell("Fecha");
            table.getDefaultCell().setBackgroundColor(null);
            // There are three special rows
            table.setHeaderRows(2);
            while (it2.hasNext()) {
                venta = it2.next();
                it3 = productos.iterator();
                while (it3.hasNext()) {
                    producto = it3.next();
                    if (venta.getCodigoProducto().getCodigoProducto().compareTo(producto.getCodigoProducto()) == 0) {
                        valor = producto.getValorProducto() * venta.getCantidadVenta();
                    }
                }
                table.addCell(clients.get(venta.getRutCliente().getRutCliente()));
                table.addCell(clients.get(venta.getRutCliente().getRutCliente()));
                table.addCell(String.valueOf(venta.getCantidadVenta()));
                table.addCell("$ "+String.valueOf((int)valor));
                table.addCell(Herramientas.fechaConPalabras(venta.getFechaVenta()));


            }
            documento.add(table);
            documento.newPage();

        }
        documento.close();
        InputStream stream = new FileInputStream(realPath + "resources/Temp/grafico" + currentUser + ".pdf");
        file = new DefaultStreamedContent(stream, "application/pdf", "grafico.pdf");
        mostrarBoton=true;

    }
}
 