package gob.gamo.activosf.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author wilherrera Wilbert Herrera Flores Banco Central de Bolivia
 *         Departamento de Desarrollo
 */
public final class UtilsDate {
    public static final Integer TYPE_DATE_DAY = 5;
    public static final Integer TYPE_DATE_MONTH = 2;
    public static final Integer TYPE_DATE_YEAR = 1;

    /**
     * Retorna un objeto Date a partir de un formato definido, si el formato es
     * incorrecto se lanza una excepcion Ejemplos: dd/MM/yyyy 12/10/2011,
     * dd.MM.yy 09.04.98, yyyy.MM.dd G 'at' hh:mm:ss z 1998.04.09 AD at 06:15:55
     * PDT EEE, MMM d, ''yy Thu, Apr 9, '98 h:mm a 6:15 PM H:mm 18:15
     * H:mm:ss:SSS 18:15:55:624 K:mm a,z 6:15 PM,PDT yyyy.MMMMM.dd GGG hh:mm aaa
     * 
     * @param date
     *               cadena fecha
     * @param format
     *               formato de la fecha introducida
     * @return objeto Date
     */
    public static Date dateFromString(String date, String format) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat(format);
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(date);
        } catch (ParseException ex) {
            try {
                SimpleDateFormat formatoDelTexto1 = new SimpleDateFormat(format, new Locale("es", "ES"));
                fecha = formatoDelTexto1.parse(date);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return fecha;
    }

    /**
     * Retorna una cadena con formato fecha. Ejemplos: dd/MM/yyyy 12/10/2011
     * dd.MM.yy 09.04.98 yyyy.MM.dd G 'at' hh:mm:ss z 1998.04.09 AD at 06:15:55
     * PDT EEE, MMM d, ''yy Thu, Apr 9, '98 h:mm a 6:15 PM H:mm 18:15
     * H:mm:ss:SSS 18:15:55:624 K:mm a,z 6:15 PM,PDT yyyy.MMMMM.dd GGG hh:mm aaa
     * 
     * @param date
     *               fecha a dar formato
     * @param format
     *               formato de fecha segun los ejemplos u otros
     * @return
     */
    public static String stringFromDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat(format);
        String fecha = null;
        // try {
        fecha = formatoDelTexto.format(date);
        /*
         * } catch (ParseException ex) { ex.printStackTrace(); }
         */
        return fecha;
    }

    /**
     * retorna un objeto XML de formato fecha para intercambio de fechas en
     * objetos xml
     * 
     * @param fecha
     * @param delimitador
     * @return
     */
    public static XMLGregorianCalendar StrToXMLGregoCal(String fecha, String delimitador) {

        String[] fec_YYYYMMDD = null;
        GregorianCalendar gcal = new GregorianCalendar();
        Date fecha1 = dateFromString(fecha, "yyyy-MM-dd");
        XMLGregorianCalendar fecGregoCal = null;
        try {
            fecGregoCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

            fec_YYYYMMDD = fecha.split(delimitador);

            fecGregoCal.setYear(Integer.parseInt(fec_YYYYMMDD[0]));
            fecGregoCal.setMonth(Integer.parseInt(fec_YYYYMMDD[1]));
            fecGregoCal.setDay(Integer.parseInt(fec_YYYYMMDD[2]));

        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(WSasicap_ccb_ws.class.getName()).log(Level.SEVERE,
            // null, ex);
            System.out.println(ex.getMessage());
        }
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(fecha1);
        DatatypeFactory factory = null;
        try {
            factory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // whf definir cual es el optimo
        XMLGregorianCalendar calendar = factory.newXMLGregorianCalendar((GregorianCalendar) c);
        // return fecGregoCal;
        return calendar;
    }

    /**
     * Compara fechas retorna -1 si fecha1 es menor a fecha2, 1 si fecha1 es
     * mayor a fecha2 y cero si son iguales
     * 
     * @param fecha1
     * @param fecha2
     * @return
     */

    public static int compara(Date fecha1, Date fecha2) {
        if (fecha1 == null || fecha2 == null) {
            return 0;
        }
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(fecha1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Calendar c2 = GregorianCalendar.getInstance();
        c2.setTime(fecha2);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        return c.compareTo(c2);
    }

    public static boolean entreFechas(Date fechaEval, Date fechaini, Date fechafin) {
        return (compara(fechaEval, fechaini) >= 0 && compara(fechaEval, fechafin) <= 0);
    }

    public static Date restarDias(Date fecha, int dias) {
        throw new RuntimeException("no implementado");
        // return null;
    }

    /**
     * Agrega o resta dias, meses ... a una fecha determinada
     * 
     * @param fecha
     *                       fecha base a calcular
     * @param cantidadTiempo
     *                       cantidad de tiempo
     * @param tipoTiempo
     *                       unidad de tiempo, 5 Dias, 2 meses, 1 AÃƒÂ±o, 11 hora,
     *                       12
     *                       minuto, 13 segundo
     * @return
     */
    public static Date addTime(Date fecha, int cantidadTiempo, int unidTiempo) {
        if (fecha == null) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);

        if (unidTiempo == Calendar.YEAR || unidTiempo == Calendar.MONTH || unidTiempo == Calendar.DAY_OF_MONTH) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        calendar.add(unidTiempo, cantidadTiempo);

        return calendar.getTime();
    }

    /**
     * Agrega o resta dias, meses ... a una fecha determinada
     * 
     * @param fecha
     *                       fecha base a calcular
     * @param cantidadTiempo
     *                       cantidad de tiempo
     * @param tipoTiempo
     *                       unidad de tiempo, 5 Dias, 2 meses, 1 Anio
     * @return
     */
    public static Date addTime(Date fecha, int cantidadTiempo, int unidTiempo, boolean fechaSiguiente) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(unidTiempo, cantidadTiempo);

        if (!fechaSiguiente) {
            // se retorna la fecha ultima es decir la fecha menos un dia
            calendar.setTime(calendar.getTime());
            calendar.add(5, -1);
        }
        return calendar.getTime();
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }

        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * retorna el dia, mes año de una fecha según
     * dia=5
     * mes=2
     * año=1
     * 
     * @return
     */
    public static Function<Date, Function<Integer, Integer>> dmyDate() {
        return fecha -> {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fecha);

            return dmy -> dmy == 2 ? calendar.get(dmy) + 1 : calendar.get(dmy);
        };
    }

    public static Date dateOf(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static int gestionActual() {
        return dmyDate().apply(new Date()).apply(TYPE_DATE_YEAR);
    }

    public static void main(String[] args) {
        System.out.println(UtilsDate.stringFromDate(
                UtilsDate.addTime(UtilsDate.dateFromString("12/01/2015", "dd/MM/yyyy"), 31, 5), "dd/MM/yyyy"));
        System.out.println(UtilsDate.stringFromDate(
                UtilsDate.addTime(UtilsDate.dateFromString("12/01/2015", "dd/MM/yyyy"), 31, 5), "dd/MM/yyyy"));
        System.out.println(UtilsDate.stringFromDate(
                UtilsDate.addTime(UtilsDate.dateFromString("12/01/2015", "dd/MM/yyyy"), 1, 1), "dd/MM/yyyy"));
        System.out.println(UtilsDate.stringFromDate(
                UtilsDate.addTime(UtilsDate.dateFromString("12/01/2015", "dd/MM/yyyy"), 1, 2), "dd/MM/yyyy"));

        Calendar c = GregorianCalendar.getInstance();
        System.out.println(c.getActualMaximum(Calendar.DAY_OF_YEAR));
        System.out.println(c.getActualMaximum(Calendar.DATE));
        System.out.println(c.getActualMinimum(Calendar.DATE));
        System.out.println(c.getMinimum(Calendar.DATE));
        System.out.println(UtilsDate.stringFromDate(
                UtilsDate.addTime(UtilsDate.dateFromString("01/01/" + c.get(Calendar.YEAR), "dd/MM/yyyy"),
                        c.getActualMaximum(Calendar.DAY_OF_YEAR) - 1, 5),
                "dd/MM/yyyy"));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.get(Calendar.YEAR);
        System.out.println(calendar.get(Calendar.YEAR) - 2);

        Date d = UtilsDate.dateFromString("12/01/2015", "dd/MM/yyyy");
        System.out.println("año " + dmyDate().apply(d).apply(1));
        System.out.println("mes " + dmyDate().apply(d).apply(2));
        System.out.println("dia " + dmyDate().apply(d).apply(5));

        d = UtilsDate.dateFromString("27/12/2015", "dd/MM/yyyy");
        System.out.println("año " + dmyDate().apply(d).apply(1));
        System.out.println("mes " + dmyDate().apply(d).apply(2));
        System.out.println("dia " + dmyDate().apply(d).apply(5));
    }

}
