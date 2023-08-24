package gob.gamo.activosf.app.services;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;

// @Startup
// @Singleton
public class MonitorDiario {

    // private CatalogosBl catalogosBl;

    private AfTipoCambioBl afTipoCambioBl;

    private AfMaterialBl afMaterialBl;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Map<String, String> parametros;

    Logger log = Logger.getLogger("bo.gob.uif.safi");

    public MonitorDiario() {}

    @PostConstruct
    public void init() {
        // parametros = catalogosBl.getValoresPorCatalogo("CAT_PARAMETROS_SAFI");
        revisarUfv();
        revisarMateriales();
    }

    public void revisarUfv() {
        throw new RuntimeException("no implementado");
        /*
        LocalDate hoy = new LocalDate();
        log.info("REVISANDO UFV " + hoy);
        UserRequestVo userRequestVo = new UserRequestVo();
        userRequestVo.setDate(hoy.toDate());
        userRequestVo.setHost("localhost");
        userRequestVo.setUserId(0L);
        StringBuilder sb = new StringBuilder("<html><head></head><body>");
        sb.append("<h3>Reporte de actualizaci&oacute;n de UFV</h3>");
        sb.append("<p>Revisando tipo de cambio para: ").append(sdf.format(hoy.toDate())).append("</p>");
        AfTipoCambio afTipoCambio = afTipoCambioBl.getAfTipoCambioByCatMonedaAndFecha("UFV", hoy.toDate());
        if (afTipoCambio == null) {
            log.info("Regularizando UFV ");
            AfTipoCambio ultimoUfv = afTipoCambioBl.getAfTipoCambioUltimo("UFV");
            sb.append("<p> Se actualiza informaci&oacute;n desde: ").append(sdf.format(ultimoUfv.getFecha()))
                    .append("</p>");
            sb.append("<ul>");
            LocalDate ultimaFecha = new LocalDate(ultimoUfv.getFecha());
            do {
                ultimaFecha = ultimaFecha.plusDays(1);
                Date ultimaDate = ultimaFecha.toDate();
                String mensaje = null;
                try {
                    BigDecimal tipoCambio = ConsultaUfv.getTipoCambio(ultimaDate);
                    AfTipoCambio nuevo = new AfTipoCambio();
                    nuevo.setCambio(tipoCambio);
                    nuevo.setFecha(ultimaDate);
                    nuevo.setCatMoneda("UFV");
                    nuevo.setIdTransaccion(0);
                    nuevo.setEstado(StatusEnum.ACTIVE.getStatus());
                    afTipoCambioBl.persistAfTipoCambio(nuevo, userRequestVo);
                    sb.append("<li>").append("Se registr&oacute; tipo de cambio para la fecha ")
                            .append(sdf.format(ultimaDate)).append(": ").append(tipoCambio).append("</li>");
                } catch (ClienteBcbException e) {
                    mensaje = e.getMessage();
                    sb.append("<li><stong> Ocurrio un error: ").append(mensaje)
                            .append(". Por favor contactese con soporte.</strong></li>");
                }
            } while (ultimaFecha.isBefore(hoy));
            sb.append("</ul>");
        } else {
            log.info("UFV ya en fecha actual ");
            sb.append("<p>El tipo de cambio ya se encuentra registrado con el valor: ").append(afTipoCambio.getCambio())
                    .append("</p>");
        }
        sb.append("</body></html>");
        correoBl.enviarCorreo(parametros.get("MONITOR_UFV_TO"), "Reporte de actualización de UFV", sb.toString());
        */
    }

    public void revisarMateriales() {
        /* LocalDate hoy = new LocalDate();
        log.info("Revisando Material en fecha: " + hoy); */
        // afMaterialBl.
        throw new RuntimeException("no implementado");
    }

    // @Schedule(second="0", minute="0", hour="7", dayOfWeek="Mon-Fri",
    // persistent=false)
    public void demonioRevisarUfv() {
        revisarUfv();
    }

    // @Schedule(second="0", minute="30", hour="7", dayOfWeek="Mon-Fri",
    // persistent=false)
    public void demonioRevisarMaterial() {
        revisarMateriales();
    }
}
