INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (30, 'TAREA OPERACION');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (30, 1, 'PRE OPERACION', 'ADM');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (30, 2, 'REGISTRAR', 'ADM');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (30, 3, 'ACTUALIZAR', 'ADM');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (30, 5, 'ELIMINAR', 'ADM');

INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (32, 'UNIDAD');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 1, 'UNIDAD', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 2, 'METROS', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 3, 'KILOS', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 5, 'LIBRAS', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 6, 'GRAMOS', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 7, 'LITROS', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 8, 'GALONES', 'UND');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (32, 9, 'CAJA', 'UND');

INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (101, 'TIPOS DE PERSONA                        ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (102, 'SEXO                                    ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (103, 'ESTADO CIVIL                            ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (104, 'DOCUMENTOS DE IDENTIDAD                 ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (105, 'MONEDAS                                 ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (110, 'MONEDAS                                 ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (176, 'CIUDADES DEL PAIS                       ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (177, 'ESTADO DEL CLIENTE                      ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (179, 'PROFESIONES                             ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (154, 'PAISES                                  ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (1351, 'TIPOS DE DOCUMENTOS                     ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (1602, 'ESTADOS                                 ');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (13, 'ESTADO DE REGISTRO');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (18, 'TIPOS DE SOLICITUD');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (31, 'TIPO USUARIO');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (53, 'TIPO DE ACTUALIZACION');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (55, 'ROL EMPLEADO');


INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (1, 'MODULO');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (1, 1, 'ADMINISTRACION SISTEMA', 'ADM');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (1, 2, 'ADMINISTRACION SEGURIDAD', 'SEG');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (1, 5, 'ACTIVO FIJOS', 'ACF');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (1, 8, 'ALMACENES', 'ALM');

INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (5, 'MODULO - ACT FIJOS');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 1, 'REGISTRO ACTIVO', null);

INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (8, 'MODULO - ALMACENES');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 1, 'CREACION KARDEX', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 2, 'INGRESO ALMACEN', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 3, 'SALIDA ALMACEN', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 5, 'ACTUALIZACION ITEM', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 6 ,'SOLICITUD DE MATERIALES' ,null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 7 ,'KARDEX DE MATERIALES',null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 39,'ALMACENES', null);                                   
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 52,'KARDEX'   , null);                                   
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 9 ,'REPORTE MOVIMIENTOS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (8, 13,'INVENTARIO CIERRE' , null);

INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (58, 'OPERACION MAYOR');
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (58, 1, 'SALDO INICIAL', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (58, 2, 'SALIDA DE STOCK', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (58, 3, 'INGRESO A STOCK', null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (58, 5, 'ACTUALIZACION PRECIO STOCK', null);

INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (85, 'OPERACION AF SUB');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (88, 'OPERACION MAYOR AF');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (86, 'ITEM DETALLE OPERACION');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (180, 'OPERACION ALM');
INSERT INTO gen_tablas (tab_codigo, tab_descripcion) VALUES (185, 'OPERACION ALM SUB');

INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 1, 'PERSONA NATURAL                         ', 'P.NAT',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 2, 'SUCESION INDIVISA                       ', 'SUC.I',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 3, 'EMPRESA UNIPERSONAL                     ', 'EMP.U',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 4, 'SOCIEDAD COLECTIVA                      ', 'SOC.C',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 5, 'SOCIEDAD ANONIMA                        ', 'SOC.A',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 6, 'SOCIEDAD EN COMANDITA SIMPLE            ', 'SOC.C',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 7, 'SOCIEDAD EN COMANDITA POR ACCIONES      ', 'SOC.C',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 8, 'SOCIEDAD DE RESPONSABILIDAD LIMITADA    ', 'S.R.L',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 9, 'ASOCIACION ACCIDENTAL O D/CUENTAS EN PAR', 'ASOC.',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 10, 'SOCIEDAD O ENTIDAD CONSTITUIDA EN EL EXT', 'SOC.E',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 11, 'COOPERATIVAS O MUTUALES                 ', 'COOP/',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 12, 'SOCIEDAD SOCIAL, CULTURAL Y DEPORTIVA   ', 'SOC. ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 13, 'ASOC. O FUNDACIONES RELIGIOSAS Y/O EDUCA', 'SOC. ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 14, 'EMPRESAS PUBLICAS                       ', 'EMP.P',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 15, 'EMP.PUBLICAS DESCENTRALIZADAS MUNICIP.  ', 'EMP.P',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 16, 'SOCIEDAD DE ECONOMIA MIXTA              ', 'SOC.E',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (101, 17, 'OTRAS NO ESPECIFICADAS                  ', 'OTRAS',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (102, 1, 'MASCULINO                               ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (102, 2, 'FEMENINO                                ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (103, 1, 'SOLTERO(A)                              ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (103, 2, 'CASADO(A)                               ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (103, 3, 'VIUDA(A)                                ', 'VDA. ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 1, 'CARNET DE IDENTIDAD                     ', 'C.I. ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 2, 'REGISTRO UNICO NACIONAL                 ', 'RUN  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 3, 'PERSONA EXTRANJERA                      ', 'PE   ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 4, 'CORRELATIVO PERSONA NATURAL             ', 'CPN  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 5, 'REGISTRO UNICO DE CONTRIBUYENTES        ', 'RUC  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 6, 'EMPRESA EXTRANJERA                      ', 'EE   ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 7, 'CORRELATIVO PERSONA JURIDICA            ', 'CPJ  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 8, 'POR RESOLUCION                          ', 'PR   ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (104, 9, 'NUMERO IDENTIFICACION TRIBUTARIA        ', 'NIT  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (105, 1, 'BOLIVIANOS               BS.            ', 'Bs.  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (105, 2, 'DOLARES                  $US.           ', '$us. ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (105, 3, 'MANTENIMIENTO DE VALOR                  ', 'MV.  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (105, 4, 'MONEDA NACIONAL CMV UFV                 ', 'UFV  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 1, 'SANTA CRUZ                              ', 'SCZ  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 2, 'LA PAZ                                  ', 'LPZ  ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 3, 'COCHABAMBA                              ', 'CBBA ',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 4, 'ORURO                                   ', 'ORURO',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 5, 'TARIJA                                  ', 'TARJA',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 6, 'SUCRE                                   ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (176, 7, 'BENI                                    ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (177, 1, 'ACTIVO                                  ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (177, 9, 'INACTIVO                                ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 1, 'FACTURA COMERCIAL                       ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 2, 'CONOMICIMIENTO DE EMBARQUE MARITIMO     ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 3, 'GUIA CAMIONERA                          ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 4, 'GUIA DE FERROCARRIL                     ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 5, 'GUIA AEREA                              ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 6, 'CERTIFICADO DE ORIGEN                   ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 7, 'LISTA DE EMPAQUE                        ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 8, 'CERTIFICADO FITOSANITARIO               ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 9, 'CERTIFICADO DE INSPECCION P. IMPORT.    ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1351, 10, 'POLIZA/CERTIFICADO DE SEGURO            ', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1602, 1, 'PRE OPERACION', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1602, 2, 'HABILITADO                              ', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1602, 7, 'BLOQUEADO                               ', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1602, 8, 'PAGADO                                  ', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (1602, 9, 'ANULADO                                 ', '',  null, null);

INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (13, 0, 'VIGENTE', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (13, 9, 'SUSPENDIDO', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (31, 1, 'MODIFICA', 'OPE',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (31, 2, 'SUPERVISA', 'PRE',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (31, 3, 'AUTORIZA', 'AUT',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (31, 4, 'CONSULTA', 'CONS',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (55, 1, 'SUPERVISOR', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (55, 2, 'OPERATIVO', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (55, 3, 'TEMPORAL', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (55, 5, 'JEFE', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (55, 6, 'GERENTE', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (55, 99, 'NaN', null,  null, null);

INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 1, 'SOLICITUD ACTIVO', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 2, 'ALTA ACTIVO', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 3, 'ASIGNACION ACTIVO', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 5, 'BAJA/REVERSA ACTIVO', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 6, 'RECHAZO ACTIVO', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 7, 'RECEPCION AF', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 8, 'RECEPCION ALM', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 20, 'REGISTRO POR ROBO', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 21, 'REGISTRO POR PERDIDA', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (85, 23, 'REVALORIZACION', '',  null, null);

INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (86, 1, 'BIEN', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (86, 2, 'INMUEBLE', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (86, 3, 'FAMILIA', '',  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (86, 5, 'ITEM', '',  null, null);

INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (88, 1, 'ACTIVO - COTIZACION', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (88, 2, 'MONTO COMPRA', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (88, 3, 'PERDIDA POR MERMA', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (88, 6, 'PERDIDA POR ROBO', null,  null, null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso,  des_auditusr,  des_auditwst) VALUES (88, 7, 'REVALORIZACION', null,  null, null);




-- POSIBLES PARAMETRIZACIONES 

INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 1 ,'ASIGNACIONES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 2 ,'RECEPCION' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 3 ,'GESTION AF' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 25 ,'INGRESO DE MATERIALES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 5 ,'BAJA DE MATERIALES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 8 ,'REPORTE EXISTENCIA MATER' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 10,'BUSCADOR' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 11,'ASIGNACION AF' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 12,'REPORTE UFV' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 16,'PARTIDAS PRESUPUESTARIAS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 17,'CODIGOS CONTABLES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 18,'FAMILIAS AF' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 19,'SUB-FAMILIAS AF' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 20,'AMBIENTES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 23,'GESTIONES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 37,'MATERIALES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 38,'ACTIVOSF' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 50,'CODIGOSCONTABLES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 51,'FAMILIASACTIVOS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 53,'PARTIDAS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (5, 55,'PROVEEDORES' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (2, 56,'EMPLEADOS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (2, 57,'PERSONAS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (2, 35,'USERS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (1, 58,'ITEMS' , null);
INSERT INTO gen_desctabla (des_codtab, des_codigo, des_descrip, des_codeiso) VALUES (1, 36,'UNIDADES' , null);
