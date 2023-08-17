package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import gob.gamo.activosf.app.domain.AfActivoFijo;

import java.time.temporal.ChronoUnit;


public class CalcContabService {

	private static final Integer SCALE = 12;
    private static final MathContext MATH_CONTEXT = new MathContext(SCALE, RoundingMode.HALF_UP);
    private static final BigDecimal DAYS_ON_YEAR = new BigDecimal(365);
    private static final Integer DIAS_A_DEPRECIAR_DESDE_REGISTRO = 0; // 1 en la version similiar al VSIAF, respadado con Acta 5.

	private AfActivoFijo afActivoFijo;
	private LocalDate fechaCalculo;
	private BigDecimal tipoCambioCalculo;
	private BigDecimal costoInicial;
	private BigDecimal depreciacionAcumuladaInicial;
	private BigDecimal factorActual;
	private BigDecimal valorActual;
	private BigDecimal factorDepreciacion;
	private Integer diasConsumidos;
	private BigDecimal depreciacionGestion;
	private BigDecimal depreciacionAcumulada;
	private BigDecimal valorNeto;
	private BigDecimal depreciacionMensual;
	private BigDecimal actualizacionDepreciacionAcumulada;
	//Auxiliares
	private BigDecimal porcentajeDepreciacion;
	private LocalDate fechaVidaUtil;
	private Integer diasVidaUtil;
	//Variables por conveniencia
	private LocalDate fechaActivo;
	private boolean deprecia;
	private boolean actualiza;
	private BigDecimal tipoCambioIncorporacion;
	private boolean incorporacionEspecial;
	private BigDecimal aniosVidaUtil;
	
	public CalcContabService(AfActivoFijo afActivoFijo,Date fechaCalculo) {
		this.afActivoFijo = afActivoFijo;
		//this.fechaCalculo = new LocalDate(fechaCalculo);
        this.fechaCalculo = LocalDate.ofInstant(fechaCalculo.toInstant(),ZoneId.systemDefault());
		fechaActivo = LocalDate.ofInstant(afActivoFijo.getFechaActual().toInstant(),ZoneId.systemDefault());
		deprecia = afActivoFijo.getIdSubFamilia().isDepreciar();
		actualiza = afActivoFijo.getIdSubFamilia().isActualizar();
		costoInicial = afActivoFijo.getCostoActual();
		incorporacionEspecial = afActivoFijo.getIncorporacionEspecial();
		depreciacionAcumuladaInicial = afActivoFijo.getDepAcumuladaActual();
		factorDepreciacion = afActivoFijo.getFactorDepreciacionActual();
		calculateDataVidaUtil();
	}
	
	public void calcular(BigDecimal tipoCambioIncorporacion, BigDecimal tipoCambioCalculo, BigDecimal tipoCambioVidaUtil){
		System.out.println(">>> CALC VO >>" +  afActivoFijo.getCodigoExtendido() + " > fecha activo > " +  fechaActivo + " > fecha Calculo >" + fechaCalculo);
		this.tipoCambioCalculo = tipoCambioCalculo;
		this.tipoCambioIncorporacion = tipoCambioIncorporacion;
		//Validar que el tipoCambioCalculo exista. Tambien del ActivoFijo
        //Validamos que la fecha del activo no sea despues que la del calculo
        boolean fechaCalculoMayorIgualAFechaActivo = fechaCalculo.isAfter(fechaActivo) || fechaCalculo.isEqual(fechaActivo);
        boolean existenTiposCambio = (tipoCambioIncorporacion != null 
                                        && tipoCambioIncorporacion.compareTo(BigDecimal.ZERO) != 0 
                                        && tipoCambioCalculo != null 
                                        && tipoCambioCalculo.compareTo(BigDecimal.ZERO) != 0 
                );
        if ( fechaCalculoMayorIgualAFechaActivo && existenTiposCambio) {
            //System.out.println(">> Fin Vida Util: " + fechaVidaUtil);
            
            //*** (8) Factor de actualizacion
            factorActual = calularFactor(fechaCalculo, tipoCambioCalculo, tipoCambioVidaUtil); 
            //System.out.println("Factor: " + factorActual);
            
            //*** (10) dias de vida consumidos
            diasConsumidos = calcularDiasCosumidos(fechaCalculo);
            //System.out.println("Dias Consumidos:" + diasConsumidos);
            
            //** (11) Porcentaje de Depreciacion
            porcentajeDepreciacion = calcularPorcentajeDepreciacion();
            //System.out.println("Porcentaje Depreciacion:" + porcentajeDepreciacion);
            
            //*** (12) Costo actualizado
            valorActual = calcularValorActualizado(factorActual);
            //System.out.println("Valor Actualizado:" + valorActual);
            
            //*** (14) DEPRECIACION ACUMULADA Y DEL EJERCICIO
            depreciacionAcumulada= calcularDepreciacionAcumulada(fechaCalculo, factorActual);
            //System.out.println("Depreciación Acumulada:" + depreciacionAcumulada);
            
            
            /**
             * PARCHE PARA PATRICIA EN MIGRACION DE SOBRANTES
             */
            boolean casoEspecialSobrante = "MIGRAC".equals(afActivoFijo.getCatTipoActualizacion()) && BigDecimal.ZERO.compareTo(afActivoFijo.getCostoActual()) == 0; 
            if(casoEspecialSobrante) {
            	valorNeto = BigDecimal.ZERO;
            } else {
            	//*** (15) Valor Neto
                valorNeto = calcularValorNeto(fechaCalculo, factorActual);
            }
            //System.out.println("Valor Neto:" +  valorNeto);
            
            depreciacionGestion = calcularDepreciacionGestion(fechaCalculo, factorActual);
            //System.out.println("Depreciación Gestión:"  +  depreciacionGestion);
            
            depreciacionMensual = calcularDepreciacionMensual(fechaCalculo, factorActual);
            //System.out.println("Depreciación Mensual:"  +  depreciacionMensual);
            
            
            if(deprecia) {
                if (valorNeto.compareTo(BigDecimal.ONE) <= 0 && !casoEspecialSobrante ) {
                    valorNeto = BigDecimal.ONE;

                    // Si el año de calculo es mayor al de la vida util
                    if ( (fechaCalculo.getYear() - fechaVidaUtil.getYear() ) > 0 ) {
                        depreciacionGestion = BigDecimal.ZERO;
                    }
                    //System.out.println("DEPGESTION !!!:"+ depreciacionGestion);
                    depreciacionMensual = BigDecimal.ZERO;
                    
                    depreciacionAcumulada = valorActual.subtract(BigDecimal.ONE, MATH_CONTEXT); 
                    
                    //System.out.println("DEPRECIA_ACUM!!!:" +  depreciacionAcumulada);
                    
                    //System.out.println("ESTE ACTIVO SE DEBE REVALORIZAR O DAR DE BAJA");
                } else {
                    //Mostrar mensaje eln blanco
                }
                actualizacionDepreciacionAcumulada = depreciacionAcumuladaInicial.multiply(factorActual,MATH_CONTEXT).subtract(depreciacionAcumuladaInicial,MATH_CONTEXT);
            } else {
                valorNeto = valorActual;
                actualizacionDepreciacionAcumulada = BigDecimal.ZERO;
                //System.out.println("VALNET !!!:" + valorNeto);
            }
            
        } else {
            String mensaje = "Error con los parametros de entrada ["+ 
            				afActivoFijo.getCodigoExtendido() +"|Activo:"+ 
            				fechaActivo+"|Calculo:"+fechaCalculo+"] : Fecha de Calculo Mayor o Igual a Fecha del Activo(" +
            				fechaCalculoMayorIgualAFechaActivo + "). Existe Tipo de Cambio:("+existenTiposCambio+")" ;
            throw new RuntimeException(mensaje);
        }
	}

   public BigDecimal getActualizacionDepreciacionAcumulada() {
		return actualizacionDepreciacionAcumulada;
	}

	public void setActualizacionDepreciacionAcumulada(
			BigDecimal actualizacionDepreciacionAcumulada) {
		this.actualizacionDepreciacionAcumulada = actualizacionDepreciacionAcumulada;
	}

/**
    * FUNCTION vvalnet (14) Valor neto
    * @param fechaCalculo
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularValorNeto(LocalDate fechaCalculo, BigDecimal factorActualizacion) {
       BigDecimal result;
       BigDecimal temp;
       BigDecimal temp1;
       BigDecimal temp2;
       if (deprecia) {
           if(fechaCalculo.isAfter(fechaVidaUtil)){
               result = BigDecimal.ONE;
           } else {
               temp1 = calcularValorActualizado(factorActualizacion);
               if(temp1.compareTo(BigDecimal.ZERO) == 0) {
                   result = BigDecimal.ZERO;
               } else {
                   temp2 = calcularDepreciacionAcumulada(fechaCalculo, factorActualizacion);
                   temp = temp1.subtract(temp2, MATH_CONTEXT);
                   if(temp.compareTo(BigDecimal.ZERO)>0){
                       result = temp;
                   } else {
                       result = BigDecimal.ONE;
                   }
               }
           }
       } else {
           result = calcularValorActualizado(factorActualizacion);
       }
       return result;
   }
   
   /**
    * *!   FUNCTION ddepmensual () DEPRECIACION MENSUAL
    * @param fechaCalculo
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularDepreciacionMensual(LocalDate fechaCalculo, BigDecimal factorActualizacion) {
       BigDecimal depreciacionAFechaCalculo = BigDecimal.ZERO;
       BigDecimal depreciacionAPrimeroMesFechaCalculo = BigDecimal.ZERO;
       
       if(fechaCalculo.isAfter(fechaVidaUtil) || fechaCalculo.isEqual(fechaVidaUtil)) {
           return BigDecimal.ZERO;
       }
       
       if (!deprecia) {
           return BigDecimal.ZERO;
       } else {
           //***(14)	= (12) * (11) * (10) /365
           BigDecimal diasConsumidos = new BigDecimal(ChronoUnit.DAYS.between(fechaActivo,fechaCalculo) + DIAS_A_DEPRECIAR_DESDE_REGISTRO); //FIXME Se puede reemplazar por dias consumidos
           depreciacionAFechaCalculo = calcularValorActualizado(factorActualizacion).multiply(calcularPorcentajeDepreciacion(), MATH_CONTEXT).multiply(diasConsumidos).divide(DAYS_ON_YEAR,MATH_CONTEXT);
           LocalDate fechaPrimeroMesCalculo =  LocalDate.of(fechaCalculo.getYear(),fechaCalculo.getMonthValue(),1);
           if(fechaActivo.isBefore(fechaPrimeroMesCalculo) || fechaActivo.isEqual(fechaPrimeroMesCalculo) ){
               BigDecimal diasConsumidosPrimeroMes = new BigDecimal(ChronoUnit.DAYS.between(fechaActivo,fechaPrimeroMesCalculo) + DIAS_A_DEPRECIAR_DESDE_REGISTRO); //FIXME Se puede reemplazar por dias consumidos
               depreciacionAPrimeroMesFechaCalculo = calcularValorActualizado(factorActualizacion).multiply(calcularPorcentajeDepreciacion(), MATH_CONTEXT).multiply(diasConsumidosPrimeroMes).divide(DAYS_ON_YEAR,MATH_CONTEXT);
           }
           
       }
       return depreciacionAFechaCalculo.subtract(depreciacionAPrimeroMesFechaCalculo, MATH_CONTEXT);
   }
   
   /* ORIGINAL del VSIAF
    * 
    private BigDecimal calcularDepreciacionGestion(LocalDate fechaCalculo, BigDecimal factorActualizacion) {
       BigDecimal temp1 = BigDecimal.ZERO; //ValorDepreciacion
       BigDecimal temp2 = BigDecimal.ZERO;
       LocalDate fec = null;
       Integer diasConsumidos = 0; //FIXME se deberia utilizar la función dias consumidos
       if(!deprecia) {
           return BigDecimal.ZERO;
       } else{
           //int diasConsumidos = 0; //FIXME se deberia utilizar la función dias consumidos y eliminar el calculo del if y else
           if (fechaCalculo.isAfter(fechaVidaUtil) || fechaCalculo.isEqual(fechaVidaUtil)){
               diasConsumidos = ChronoUnit.DAYS.between(fechaActivo, fechaVidaUtil).getDays() + 1; //FIXME Se puede reemplazar por dias consumidos //FIXME Se puede eliminar, se sospecha copy paste
               temp1 = calcularValorActualizadoCosto(factorActualizacion).subtract(BigDecimal.ONE, MATH_CONTEXT);
           } else{
               diasConsumidos = ChronoUnit.DAYS.between(fechaActivo, fechaCalculo).getDays() + 1; //FIXME Se puede reemplazar por dias consumidos
               //FIXME: Logica similar en Depreciacion acumulada
               temp1 = calcularValorActualizadoCosto(factorActualizacion).multiply(calcularPorcentajeDepreciacion(), MATH_CONTEXT).multiply(new BigDecimal(diasConsumidos),MATH_CONTEXT).divide(DAYS_ON_YEAR, MATH_CONTEXT);
           }
           //Si el calculo se lo hace de un activo adquirido diferente al periodo actual
           if(fechaActivo.getYear() <= (fechaCalculo.getYear() - 1) ) { 
               if(fechaCalculo.isAfter(fechaVidaUtil) || fechaCalculo.isEqual(fechaVidaUtil)) {
                   fec = new LocalDate( (fechaVidaUtil.getYear()-1)  ,12,31); //Anio / mes / dia
               } else{
                   fec = new LocalDate( (fechaCalculo.getYear()-1) ,12,31);
               }
               diasConsumidos = ChronoUnit.DAYS.between(fechaActivo,fec).getDays() + 1; 
               temp2 = calcularValorActualizadoCosto(factorActualizacion).multiply(calcularPorcentajeDepreciacion(), MATH_CONTEXT).multiply(new BigDecimal(diasConsumidos),MATH_CONTEXT).divide(DAYS_ON_YEAR, MATH_CONTEXT);
           }
       }
       
       if ((fechaCalculo.getYear() - fechaVidaUtil.getYear()) >0) {
           return BigDecimal.ZERO;
       } else{
           return temp1.subtract(temp2, MATH_CONTEXT);
       }
   }
    */
   
   /**
    * FUNCTION ddepgestion (14) DEPRECIACION DE LA GESTION SOLAMENTE
    * @param fechaCalculo
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularDepreciacionGestion(LocalDate fechaCalculo, BigDecimal factorActualizacion) {
	   BigDecimal result = BigDecimal.ZERO;
	   
       
       
       if (!deprecia) {
           return result; //ZERO
       } else if((fechaCalculo.getYear() - fechaVidaUtil.getYear()) >0) {
    	   return result; //ZERO
       }
       else{
           BigDecimal temp2 = BigDecimal.ZERO;
           LocalDate fec = null;
           Integer diasConsumidos = 0; //FIXME se deberia utilizar la función dias consumidos
           
           BigDecimal valorActualizado = calcularValorActualizado(factorActualizacion).subtract(BigDecimal.ONE, MATH_CONTEXT);
           if (fechaCalculo.isBefore(fechaVidaUtil) ){
               diasConsumidos = (int)(ChronoUnit.DAYS.between(fechaActivo, fechaCalculo) + DIAS_A_DEPRECIAR_DESDE_REGISTRO); //FIXME Se puede reemplazar por dias consumidos
               valorActualizado = calcularValorActualizadoCosto(factorActualizacion).multiply(calcularPorcentajeDepreciacion(), MATH_CONTEXT).multiply(new BigDecimal(diasConsumidos),MATH_CONTEXT).divide(DAYS_ON_YEAR, MATH_CONTEXT);
           }
           //Si el calculo se lo hace de un activo adquirido diferente al periodo actual
           if(fechaActivo.getYear() <= (fechaCalculo.getYear() - 1) ) { 
               if(fechaCalculo.isAfter(fechaVidaUtil) || fechaCalculo.isEqual(fechaVidaUtil)) {
                   fec = LocalDate.of( (fechaVidaUtil.getYear()-1)  ,12,31); //Anio / mes / dia
               } else{
                   fec = LocalDate.of( (fechaCalculo.getYear()-1) ,12,31);
               }
               diasConsumidos = (int) (ChronoUnit.DAYS.between(fechaActivo,fec) + DIAS_A_DEPRECIAR_DESDE_REGISTRO); 
               temp2 = calcularValorActualizadoCosto(factorActualizacion).multiply(calcularPorcentajeDepreciacion(), MATH_CONTEXT).multiply(new BigDecimal(diasConsumidos),MATH_CONTEXT).divide(DAYS_ON_YEAR, MATH_CONTEXT);
           }
           return valorActualizado.subtract(temp2, MATH_CONTEXT);
       }
   }
   
   private Integer calcularDiasCosumidos(LocalDate fechaCalculo) {
       Integer result = 0;
       /* si el activo ya llego a su vida util=> los dias consumidos se mantienen */
       if (fechaCalculo.isAfter(fechaVidaUtil)) {
           result = (int) (ChronoUnit.DAYS.between(fechaActivo,fechaVidaUtil) + DIAS_A_DEPRECIAR_DESDE_REGISTRO);
           if(result > diasVidaUtil ) {
               result = diasVidaUtil;
           }
       } else {
           result = (int) (ChronoUnit.DAYS.between( fechaActivo, fechaCalculo) + DIAS_A_DEPRECIAR_DESDE_REGISTRO);
       }
       return result;
   }
   
   /**
    * FUNCTION ddeprecia  () DEPRECIACION ACUMULADA 
    * llamada: ddeprecia()   
    * @param fechaCalculo
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularDepreciacionAcumulada(LocalDate fechaCalculo, BigDecimal factorActualizacion) {
	   BigDecimal result = BigDecimal.ZERO;
	   
       if(deprecia) {
           BigDecimal valorActualizado = calcularValorActualizado(factorActualizacion).subtract(BigDecimal.ONE, MATH_CONTEXT);
           if (fechaCalculo.isAfter(fechaVidaUtil) || fechaCalculo.isEqual(fechaVidaUtil)) {
               result = valorActualizado;
           } else {
               // FIXME Se puede cambiar por la función diasConsumidos =  ChronoUnit.DAYS.between(fechaCalculo, fechaActivo).getDays() + 1
        	   BigDecimal valorDepreciacion;
               BigDecimal diasConsumidos = new BigDecimal(ChronoUnit.DAYS.between(fechaActivo,fechaCalculo) + DIAS_A_DEPRECIAR_DESDE_REGISTRO);
               BigDecimal valorActualizadoCosto = calcularValorActualizadoCosto(factorActualizacion);
               valorDepreciacion = valorActualizadoCosto.multiply(calcularPorcentajeDepreciacion()).multiply(diasConsumidos).divide(DAYS_ON_YEAR, MATH_CONTEXT);
               if(valorDepreciacion.compareTo(valorActualizadoCosto) >= 0) {
            	   result = valorActualizado;
               } else {
            	   result = valorDepreciacion.add(calcularValorActualizadoDepreciacionAcumulada(factorActualizacion), MATH_CONTEXT);
               }
           }
       }
       
       if (result.compareTo(BigDecimal.ZERO) < 0) {
    	   result = BigDecimal.ZERO;
       }
       return result;
   }
   
   /**
    * FUNCTION vvalact_da   DEPRECIACION ACUMULADA ACTUALIZADA
    * llamada: vvalact_da() CUANDO LA CONVERSION A UFV Y DA != 0
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularValorActualizadoDepreciacionAcumulada(BigDecimal factorActualizacion) {
       if (!actualiza){
           return depreciacionAcumuladaInicial;
       } else {
           return depreciacionAcumuladaInicial.multiply(factorActualizacion, MATH_CONTEXT);
       }
   }
   
   /**
    * FUNCTION vvalact_cto  (12) COSTO ACTUALIZADO o VALOR ACTUAL
    * llamada: vvalact_cto()     PARA EL CALCULO DE DEPRECIACION
    *                      en la funcion d()
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularValorActualizadoCosto(BigDecimal factorActualizacion) {
       if(!actualiza) {
           return costoInicial;
       } else{
           if (incorporacionEspecial) {
               //Existe un depacu > 0 por conversion a UFV
               return costoInicial.subtract(depreciacionAcumuladaInicial, MATH_CONTEXT).multiply(factorActualizacion, MATH_CONTEXT);
           } else {
               return costoInicial.multiply(factorActualizacion,MATH_CONTEXT);
           }
       }
   }
   
   /**
    * FUNCTION vvalact (12) COSTO ACTUALIZADO o VALOR ACTUAL 
    * llamada: vvalact()
    * @param factorActualizacion
    * @return 
    */
   private BigDecimal calcularValorActualizado(BigDecimal factorActualizacion) {
       if(!actualiza) {
           return costoInicial;
       } else {
           return costoInicial.multiply(factorActualizacion,MATH_CONTEXT);
       }
   }
   
   /**
    * FUNCTION pporciento (11)  PORCIENTO DE DEPRECIACION
    * llamada: pporciento()
    * @return 
    */
   private BigDecimal calcularPorcentajeDepreciacion() {
       if (!deprecia) {
           return BigDecimal.ZERO;
       } 
       //FIXME se puede eliminar el else porque el factor nunca deberia ser Zero
       if (factorDepreciacion.compareTo(BigDecimal.ZERO) != 0 ) {
           return factorDepreciacion;
       } else{
           return BigDecimal.ZERO;
       }
   }
   
   
   //Si  ni se tiene el tipo de cambio de la vida util se envia cero
   private BigDecimal calularFactor(LocalDate fechaCalculo, BigDecimal tipoCambioCalculo, BigDecimal tipoCambioVidaUtil) {
       if(!deprecia) {
           if (!actualiza) {
               return BigDecimal.ONE;
           } else {
               if (fechaActivo.isBefore(fechaCalculo)){
                   return tipoCambioCalculo.divide(tipoCambioIncorporacion,SCALE, RoundingMode.HALF_UP);
               } else { // Solo cuando la fechaActivo == fechaCalculo // FIXME se puede omitir el if/else si se valida que las fechas de calculo sean antes de la fecha de adquisicion
                   return BigDecimal.ONE; 
               }
           }
       } else {
           /* si el activo ya llego a su vida util=> el factor se mantiene constante */
           if (fechaCalculo.isAfter(fechaVidaUtil)) {
               if(tipoCambioVidaUtil.compareTo(BigDecimal.ZERO) > 0 ) {
                   return tipoCambioVidaUtil.divide(tipoCambioIncorporacion, SCALE, RoundingMode.HALF_UP);
               } else {
                   return BigDecimal.ONE;
               }
           } else {
               if (fechaCalculo.isAfter(fechaActivo)) {
                   return tipoCambioCalculo.divide(tipoCambioIncorporacion,SCALE, RoundingMode.HALF_UP);
               } else { // Solo cuando la fechaActivo == fechaCalculo // FIXME se puede omitir el if/else si se valida que las fechas de calculo sean antes de la fecha de adquisicion
                   return BigDecimal.ONE;
               }
           }
       }
   }
   
	private void calculateDataVidaUtil(){
		if (deprecia && factorDepreciacion.compareTo(BigDecimal.ZERO) > 0 ) {
			aniosVidaUtil = BigDecimal.ONE.divide(factorDepreciacion,SCALE,RoundingMode.HALF_UP); 
	        diasVidaUtil = aniosVidaUtil.multiply(DAYS_ON_YEAR,MATH_CONTEXT).intValue();
	        fechaVidaUtil = fechaActivo.plusDays(diasVidaUtil);
		} else {
			aniosVidaUtil = BigDecimal.ZERO;
			diasVidaUtil = 0;
			fechaVidaUtil = fechaActivo;
		}
    }

	public BigDecimal getDiasVidaUtilResidualNominal(){
		if(deprecia && factorDepreciacion.compareTo(BigDecimal.ZERO) > 0 && costoInicial.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal dias = BigDecimal.ONE.divide(factorDepreciacion,MATH_CONTEXT).subtract(depreciacionAcumuladaInicial.divide(costoInicial.multiply(factorDepreciacion, MATH_CONTEXT),MATH_CONTEXT) ,MATH_CONTEXT);
			if (dias.compareTo(BigDecimal.ZERO) > 0) {
				return dias;
			}
			else{
				return BigDecimal.ZERO;
			}
		} else {
			return BigDecimal.ZERO;
		}
	}
	
	public BigDecimal getActualizacionGestion(){
		return valorActual.subtract(costoInicial, MATH_CONTEXT);
	}
	
	
	
	@Override
	public String toString() {
		return "CalculoContableVo [afActivoFijo=" + afActivoFijo
				+ ",\n fechaCalculo=" + fechaCalculo + ",\n tipoCambioCalculo="
				+ tipoCambioCalculo + ",\n costoInicial=" + costoInicial
				+ ",\n depreciacionAcumuladaInicial="
				+ depreciacionAcumuladaInicial + ",\n factorActual="
				+ factorActual + ",\n valorActual=" + valorActual
				+ ",\n factorDepreciacion=" + factorDepreciacion
				+ ",\n diasConsumidos=" + diasConsumidos
				+ ",\n depreciacionGestion=" + depreciacionGestion
				+ ",\n depreciacionAcumulada=" + depreciacionAcumulada
				+ ",\n valorNeto=" + valorNeto + ",\n depreciacionMensual="
				+ depreciacionMensual + ",\n actualizacionDepreciacionAcumulada="
				+ actualizacionDepreciacionAcumulada
				+ ",\n porcentajeDepreciacion=" + porcentajeDepreciacion
				+ ",\n fechaVidaUtil=" + fechaVidaUtil + ",\n diasVidaUtil="
				+ diasVidaUtil + ",\n fechaActivo=" + fechaActivo + ",\n deprecia="
				+ deprecia + ",\n actualiza=" + actualiza
				+ ",\n tipoCambioIncorporacion=" + tipoCambioIncorporacion
				+ ",\n incorporacionEspecial=" + incorporacionEspecial
				+ ",\n aniosVidaUtil=" + aniosVidaUtil + "]";
	}

	public AfActivoFijo getAfActivoFijo() {
		return afActivoFijo;
	}

	public void setAfActivoFijo(AfActivoFijo afActivoFijo) {
		this.afActivoFijo = afActivoFijo;
	}

	public LocalDate getFechaCalculo() {
		return fechaCalculo;
	}

	public void setFechaCalculo(LocalDate fechaCalculo) {
		this.fechaCalculo = fechaCalculo;
	}

	public BigDecimal getTipoCambioCalculo() {
		return tipoCambioCalculo;
	}

	public void setTipoCambioCalculo(BigDecimal tipoCambioCalculo) {
		this.tipoCambioCalculo = tipoCambioCalculo;
	}

	public BigDecimal getCostoInicial() {
		return costoInicial;
	}

	public void setCostoInicial(BigDecimal costoInicial) {
		this.costoInicial = costoInicial;
	}

	public BigDecimal getDepreciacionAcumuladaInicial() {
		return depreciacionAcumuladaInicial;
	}

	public void setDepreciacionAcumuladaInicial(
			BigDecimal depreciacionAcumuladaInicial) {
		this.depreciacionAcumuladaInicial = depreciacionAcumuladaInicial;
	}

	public BigDecimal getFactorActual() {
		return factorActual;
	}

	public void setFactorActual(BigDecimal factorActual) {
		this.factorActual = factorActual;
	}

	public BigDecimal getValorActual() {
		return valorActual;
	}

	public void setValorActual(BigDecimal valorActual) {
		this.valorActual = valorActual;
	}

	public BigDecimal getFactorDepreciacion() {
		return factorDepreciacion;
	}

	public void setFactorDepreciacion(BigDecimal factorDepreciacion) {
		this.factorDepreciacion = factorDepreciacion;
	}

	public Integer getDiasConsumidos() {
		return diasConsumidos;
	}

	public void setDiasConsumidos(int diasConsumidos) {
		this.diasConsumidos = diasConsumidos;
	}

	public BigDecimal getDepreciacionGestion() {
		return depreciacionGestion;
	}

	public void setDepreciacionGestion(BigDecimal depreciacionGestion) {
		this.depreciacionGestion = depreciacionGestion;
	}

	public BigDecimal getDepreciacionAcumulada() {
		return depreciacionAcumulada;
	}

	public void setDepreciacionAcumulada(BigDecimal depreciacionAcumulada) {
		this.depreciacionAcumulada = depreciacionAcumulada;
	}

	public BigDecimal getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(BigDecimal valorNeto) {
		this.valorNeto = valorNeto;
	}

	public BigDecimal getDepreciacionMensual() {
		return depreciacionMensual;
	}

	public void setDepreciacionMensual(BigDecimal depreciacionMensual) {
		this.depreciacionMensual = depreciacionMensual;
	}

	public BigDecimal getPorcentajeDepreciacion() {
		return porcentajeDepreciacion;
	}

	public void setPorcentajeDepreciacion(BigDecimal porcentajeDepreciacion) {
		this.porcentajeDepreciacion = porcentajeDepreciacion;
	}

	public Date getFechaVidaUtil() {
		//return fechaVidaUtil.toDate();
        return java.sql.Date.valueOf(fechaVidaUtil);
	}

	public void setFechaVidaUtil(Date fechaVidaUtil) {
		this.fechaVidaUtil = LocalDate.ofInstant(fechaVidaUtil.toInstant(),ZoneId.systemDefault());
	}

	public Integer getDiasVidaUtil() {
		return diasVidaUtil;
	}

	public void setDiasVidaUtil(int diasVidaUtil) {
		this.diasVidaUtil = diasVidaUtil;
	}

	public BigDecimal getAniosVidaUtil() {
		return aniosVidaUtil;
	}

	public void setAniosVidaUtil(BigDecimal aniosVidaUtil) {
		this.aniosVidaUtil = aniosVidaUtil;
	}

	public BigDecimal getTipoCambioIncorporacion() {
		return tipoCambioIncorporacion;
	}

	public void setTipoCambioIncorporacion(BigDecimal tipoCambioIncorporacion) {
		this.tipoCambioIncorporacion = tipoCambioIncorporacion;
	}
    
}
