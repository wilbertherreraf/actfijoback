drop table if exists sec_userrol;
drop table if exists sec_profile;
drop table if exists org_unidad_emp;
drop table if exists org_empleado;
drop table if exists org_persona;
drop table if exists org_unidad;
drop table if exists sec_recurso;
drop table if exists sec_roles;
drop table if exists sec_usuario;
drop table if exists ACF_IMAGEN_ACTIVO_FIJO;
drop table if exists ACF_INMUEBLE;
drop table if exists ACF_ACCESORIO_ACTIVO_FIJO;
drop table if exists ACF_ACTIVO_FIJO_HIST;
drop table if exists ACF_ALTA_MATERIAL_DETALLE;
drop table if exists ACF_ALTA_MATERIAL;
drop table if exists ACF_ATRIBUTO_ACTIVO_FIJO;
drop table if exists ACF_ATRIBUTO_SUB_FAMILIA;
drop table if exists ACF_BAJA_ACTIVO_FIJO;
drop table if exists ACF_BAJA_MATERIAL;
drop table if exists ACF_COMISION_RECEPCION;
drop table if exists ACF_COMPONENTE_ACTIVO_FIJO;
drop table if exists ACF_KARDEX_MATERIAL_HIST;
drop table if exists ACF_REGISTRO_KARDEX_MATERIAL_HIS;
drop table if exists ACF_REGISTRO_KARDEX_MATERIAL;
drop table if exists ACF_KARDEX_MATERIAL;
drop table if exists ACF_MATERIAL_PROVEEDOR;
drop table if exists ACF_REVALUO_ACTIVO_FIJO;
drop table if exists ACF_SOLICITUD_ACTIVO_FIJO;
drop table if exists ACF_SOLICITUD_MATERIAL;
drop table if exists ACF_TIPO_CAMBIO;
drop table if exists ACF_TRANSFERENCIA_ASIGNACION;
drop table if exists ACF_PROVEEDOR_ACT_ECO;
drop table if exists ACF_ALMACEN;
drop table if exists ACF_MATERIAL;
drop table if exists ACF_SOLICITUD;
drop table if exists ACF_ACTIVO_FIJO;
drop table if exists ACF_NOTA_RECEPCION;
drop table if exists ACF_GARANTIA_ACTIVO_FIJO;
drop table if exists ACF_SUB_FAMILIA_ACTIVO;
drop table if exists ACF_FAMILIA_ACTIVO;
drop table if exists ACF_CODIGO_CONTABLE;
drop table if exists ACF_PARTIDA_PRESUPUESTARIA;
drop table if exists ACF_AMBIENTE;
drop table if exists ACF_PROVEEDOR;
drop table if exists ACF_FACTURA;
drop table if exists ACF_GESTION;
drop table if exists TX_TRANSACCION;
drop table if exists gen_claves;
drop table if exists gen_desctabla;
drop table if exists gen_tablas;
/*genericos*/
create table gen_claves (
    clv_id serial not null primary key,
    clv_codclave varchar(50) not null,
    clv_descrip varchar(200),
    clv_valor varchar(240),
    constraint UK_clvcodclave unique (clv_codclave)
);
create table gen_tablas (
    tab_codigo integer not null,
    tab_descripcion varchar(50) not null,
    primary key (tab_codigo)
);
create table gen_desctabla (
    des_codtab integer not null,
    des_codigo integer not null,
    des_descrip varchar(100) not null,
    des_codeiso varchar(50),
    des_codrec varchar(50),
    des_codigopadre integer,
    des_auditusr varchar(15),
    des_auditwst varchar(20),
    primary key (des_codtab, des_codigo),
    constraint FK_clvcodclave foreign key (des_codtab) references gen_tablas (tab_codigo)
);
create table org_unidad (
    id_unidad serial not null,
    nombre varchar(200),
    DOMICILIO VARCHAR(150),
    sigla varchar(50),
    telefono varchar(50),
    tipo_unidad varchar(50),
    id_unidad_padre integer,
    tabrolempleado integer default 55,
    rolempleado integer default 99,    
    id_empleado integer,
    estado varchar(10),
    constraint PK_orgunidad primary key (id_unidad)
);
create table org_persona (
    id_persona serial not null,
    nombre varchar(200),
    nombre_desc varchar(200),
    primer_apellido varchar(100),
    segundo_apellido varchar(100),
    numero_documento varchar(50),
    tipo_documento varchar(20),
    tabtipodoc integer default 108,
    tipodoc integer default 1,
    direccion varchar(200),
    telefono varchar(40),
    email varchar(40),
    tabtipopers integer default 1012,
    tipopers integer default 1,
    nemonico varchar(100),
    tx_fecha date,
    usuario varchar(50),
    estado varchar(10),
    trato varchar(100),
    constraint PK_orgpersona primary key (id_persona)
);
create table org_empleado (
    id_empleado serial not null,
    id_unidad integer,
    id_persona integer,
    cod_persona varchar(100),
    cod_internoempl varchar(100),    
    id_cargo integer,
    tabrolempleado integer,
    rolempleado integer,
    id_empleadopadre integer,
    fecha_ingreso date,
    fecha_baja date,
    estado varchar(10),
    constraint PK_orgempleado primary key (id_empleado)
);
create table org_unidad_emp (
    id_unid_empl serial not null,
    id_unidad integer,
    id_empleado integer,    
    id_emplresponsable integer,
    fecha_reg date,
    estado varchar(10),
    constraint PK_org_unidad_emp primary key (id_unid_empl)
);
create table sec_usuario (
    usr_id serial not null,
    usr_login varchar(100) not null,
    usr_nombres varchar(200) not null,
    usr_email varchar(100),
    usr_password varchar(250),
    id_persona integer,
    cod_persona varchar(100),
    id_unid_empl integer,
    usr_tabtipousr integer default 10,
    usr_tipousr integer default 1,
    usr_tabstatuser integer default 20,
    usr_statuser integer default 1,
    usr_auditusr varchar(15),
    usr_auditfho timestamp,
    usr_auditwst varchar(20),
    primary key (usr_id),
    constraint UK_usrlogin unique (usr_login),
    constraint UK_usremail unique (usr_email),
    constraint FK_usrtabtipousr foreign key (usr_tabtipousr, usr_tipousr) references gen_desctabla (des_codtab, des_codigo),
    constraint FK_usrtabstatuser foreign key (usr_tabstatuser, usr_statuser) references gen_desctabla (des_codtab, des_codigo)
);
create table sec_recurso (
    res_id serial not null,
    res_codrec varchar(50) not null,
    res_descrip varchar(150) not null,
    primary key (res_id),
    constraint UK_rapcodrec unique (res_codrec)
);
create table sec_roles (
    rol_id serial not null,
    rol_codrol varchar(50) not null,
    rol_descrip varchar(100),
    rol_tabstatreg integer default 20,
    rol_statreg integer default 1,
    rol_auditusr varchar(15),
    rol_auditfho timestamp,
    rol_auditwst varchar(20),
    primary key (rol_id),
    constraint UK_aprcodrol unique (rol_codrol),
    constraint FK_aprtabstatreg foreign key (rol_tabstatreg, rol_statreg) references gen_desctabla (des_codtab, des_codigo)
);
create table sec_profile (
    prr_resid integer not null,
    prr_rolid integer not null,
    prr_auditusr varchar(15),
    prr_auditfho timestamp,
    prr_auditwst varchar(20),
    primary key (prr_resid, prr_rolid),
    constraint FK_prorapid foreign key (prr_resid) references sec_recurso (res_id),
    constraint FK_proaprid foreign key (prr_rolid) references sec_roles (rol_id)
);
create table sec_userrol (
    uro_usrid integer not null,
    uro_rolid integer not null,
    uro_auditusr varchar(15),
    uro_auditfho timestamp,
    uro_auditwst varchar(20),
    primary key (uro_usrid, uro_rolid),
    constraint FK_urousrid foreign key (uro_usrid) references sec_usuario (usr_id),
    constraint FK_urorolid foreign key (uro_rolid) references sec_roles (rol_id)
);
create table ACF_INMUEBLE (
    ID_INMUEBLE SERIAL NOT NULL,
    DOMICILIO VARCHAR(150),
    FECHA_REGISTRO DATE,
    NOMBRE VARCHAR(150),
    SIGLA VARCHAR(150),
    TELEFONO VARCHAR(150),
    id_empleado_RESP INTEGER,
    FECHA_CREACION DATE,
    id_unidad INTEGER,
    constraint PK_ACF_INMUEBLE primary key (ID_INMUEBLE)
);
create table ACF_ACCESORIO_ACTIVO_FIJO (
    ID_ACCESORIO_ACTIVO_FIJO SERIAL not null,
    ID_ACTIVO_FIJO INTEGER not null,
    ID_FACTURA INTEGER not null,
    CAT_TIPO_ACCESORIO VARCHAR(20) not null,
    TAB_TIPO_ACCESORIO INTEGER DEFAULT 1001,
    TIPO_ACCESORIO INTEGER DEFAULT 1,
    FECHA_ADQUISICION DATE not null,
    DETALLE VARCHAR(200) not null,
    IMPORTE_TOTAL NUMERIC(20, 5) not null,
    CANTIDAD INTEGER not null,
    OBSERVACION VARCHAR(400) null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ACCESORIO_ACTIVO_FIJO primary key (ID_ACCESORIO_ACTIVO_FIJO)
);
comment on column ACF_ACCESORIO_ACTIVO_FIJO.ID_ACCESORIO_ACTIVO_FIJO is 'Id del atributo.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'ID del Activo Fijo';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.ID_FACTURA is 'Id de la factura.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.CAT_TIPO_ACCESORIO is 'Tipo de atributo, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1007.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.DETALLE is 'Detalle o valor del atributo.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.IMPORTE_TOTAL is 'Importe unitario pagado por el Activo Fijo';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.OBSERVACION is 'Observaciones al atributo.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACCESORIO_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ACTIVO_FIJO                                        */
create table ACF_ACTIVO_FIJO (
    ID_ACTIVO_FIJO SERIAL not null,
    CORRELATIVO INTEGER null,
    GESTION INTEGER,
    ID_SUB_FAMILIA INTEGER,
    ID_PROVEEDOR INTEGER null,
    ID_AMBIENTE INTEGER,
    ID_NOTA_RECEPCION INTEGER,
    ID_GARANTIA_ACTIVO_FIJO INTEGER null,
    ID_FACTURA INTEGER null,
    REVALORIZADO BOOLEAN,
    CODIGO_ANTIGUO VARCHAR(50) null,
    CAT_CENTRO_COSTO VARCHAR(20),
    TAB_CENTRO_COSTO INTEGER DEFAULT 1035,
    CENTRO_COSTO INTEGER DEFAULT 1,
    CAT_ESTADO_USO VARCHAR(20),
    TAB_ESTADO_USO INTEGER DEFAULT 1003,
    ESTADO_USO INTEGER DEFAULT 1,
    CAT_TIPO_ASIGNACION VARCHAR(20) null,
    TAB_TIPO_ASIGNACION INTEGER DEFAULT 1005,
    TIPO_ASIGNACION INTEGER DEFAULT 1,
    CAT_TIPO_ACTUALIZACION VARCHAR(20),
    TAB_TIPO_ACTUALIZACION INTEGER DEFAULT 1006,
    TIPO_ACTUALIZACION INTEGER DEFAULT 1,
    COSTO_HISTORICO NUMERIC(40, 20),
    COSTO_ACTUAL NUMERIC(40, 20),
    COSTO_ANTES_REVALUO NUMERIC(40, 20) null,
    DEP_ACUMULADA_ACTUAL NUMERIC(40, 20),
    DEP_ACUMULADA_HISTORICO NUMERIC(40, 20),
    FECHA_HISTORICO DATE,
    FECHA_ACTUAL DATE,
    FACTOR_DEPRECIACION_HISTORICO NUMERIC(10, 5),
    FACTOR_DEPRECIACION_ACTUAL NUMERIC(10, 5),
    INCORPORACION_ESPECIAL BOOL,
    CAT_FUENTE_FINANCIAMIENTO VARCHAR(20) not null,
    TAB_FUENTE_FINANCIAMIENTO INTEGER DEFAULT 1007,
    FUENTE_FINANCIAMIENTO INTEGER DEFAULT 1,
    CAT_ORGANISMO_FINANCIADOR VARCHAR(20) null,
    NRO_CONVENIO VARCHAR(100) null,
    ORDEN_COMPRA VARCHAR(100) null,
    ID_USUARIO_ASIGNADO INTEGER null,
    DESCRIPCION VARCHAR(200) not null,
    OBSERVACIONES VARCHAR(400) null,
    CODIGO_EXTENDIDO VARCHAR(200) null,
    CODIGO_RFID VARCHAR(200) null,
    CODIGO_EAN VARCHAR(200) null,
    CAT_ESTADO_ACTIVO_FIJO VARCHAR(20) not null,
    TAB_ESTADO_ACTIVO_FIJO INTEGER DEFAULT 1008,
    ESTADO_ACTIVO_FIJO INTEGER DEFAULT 1,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ACTIVO_FIJO primary key (ID_ACTIVO_FIJO)
);
comment on table ACF_ACTIVO_FIJO is 'Entidad que almacena la informacion de los Activos Fijos.';
comment on column ACF_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'ID del Activo Fijo';
comment on column ACF_ACTIVO_FIJO.CORRELATIVO is 'Numero correlativo asignado al Activo Fijo, se asigna mediante trigger cuando el Activo Fijo cambia su CAT_ESTADO_ACTIVO_FIJO de PROREC a RECEPC. Se utiliza la secuencia SEQ_ACTIVO_FIJO_CORRELATIVO';
comment on column ACF_ACTIVO_FIJO.ID_PROVEEDOR is 'Id del Proveedor que entrego en Activo Fijo';
comment on column ACF_ACTIVO_FIJO.ID_AMBIENTE is 'Id del ambiente donde se encuentra localizado el Activo Fijo';
comment on column ACF_ACTIVO_FIJO.ID_NOTA_RECEPCION is 'Id de la nota de recepcion asignada al Activo Fijo';
comment on column ACF_ACTIVO_FIJO.ID_GARANTIA_ACTIVO_FIJO is 'Id de la garant�a del Activo Fijo si es que contara con una.';
comment on column ACF_ACTIVO_FIJO.ID_FACTURA is 'Id de la factura asociada a la adquisicion del Activo Fijo.';
comment on column ACF_ACTIVO_FIJO.CAT_CENTRO_COSTO is 'Centro del Costo al cual esta asignado el Activo Fijo, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1006.';
comment on column ACF_ACTIVO_FIJO.CAT_ESTADO_USO is 'TBD';
comment on column ACF_ACTIVO_FIJO.CAT_TIPO_ASIGNACION is 'TBD';
comment on column ACF_ACTIVO_FIJO.CAT_TIPO_ACTUALIZACION is 'TBD';
comment on column ACF_ACTIVO_FIJO.COSTO_HISTORICO is 'Importe unitario pagado por el Activo Fijo';
comment on column ACF_ACTIVO_FIJO.FACTOR_DEPRECIACION_HISTORICO is 'Factor de depreciacion del Activo Fijo. Utilizado para propositos contables.';
comment on column ACF_ACTIVO_FIJO.FACTOR_DEPRECIACION_ACTUAL is 'Factor de depreciacion del Activo Fijo. Utilizado para propositos contables.';
comment on column ACF_ACTIVO_FIJO.ORDEN_COMPRA is 'El Numero de la orden de compra relacionado con la adquisicion del Activo Fijo.';
comment on column ACF_ACTIVO_FIJO.ID_USUARIO_ASIGNADO is 'Id del usuario al que se le asignara el Activo Fijo, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACTIVO_FIJO.DESCRIPCION is 'Descripcion del ActivoFijo';
comment on column ACF_ACTIVO_FIJO.OBSERVACIONES is 'Observaciones del Activo Fijo';
comment on column ACF_ACTIVO_FIJO.CODIGO_EXTENDIDO is 'Codigo RFID. TBD';
comment on column ACF_ACTIVO_FIJO.CODIGO_RFID is 'Codigo RFID. TBD';
comment on column ACF_ACTIVO_FIJO.CODIGO_EAN is 'Codigo de barras, se utilizara el formato CODE 128 para almacenar texto.';
comment on column ACF_ACTIVO_FIJO.CAT_ESTADO_ACTIVO_FIJO is 'Estado del Activo Fijo, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1014. El universo de valores inicial es:

''PROREC'':''PROCESO DE RECEPCion''
''RECEPC'':''RECEPCIONADO''
''ASGNDO'':''ASIGNADO''
';
comment on column ACF_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ACTIVO_FIJO_HIST                                   */
create table ACF_ACTIVO_FIJO_HIST (
    ID_ACTIVO_FIJO_HIST SERIAL not null,
    ID_ACTIVO_FIJO INTEGER,
    CORRELATIVO INTEGER null,
    GESTION INTEGER,
    ID_SUB_FAMILIA INTEGER,
    ID_PROVEEDOR INTEGER null,
    ID_AMBIENTE INTEGER,
    ID_NOTA_RECEPCION INTEGER,
    ID_GARANTIA_ACTIVO_FIJO INTEGER null,
    ID_FACTURA INTEGER null,
    REVALORIZADO BOOLEAN,
    CODIGO_ANTIGUO VARCHAR(50) null,
    CAT_CENTRO_COSTO VARCHAR(20),
    TAB_CENTRO_COSTO INTEGER DEFAULT 1035,
    CENTRO_COSTO INTEGER DEFAULT 1,
    CAT_ESTADO_USO VARCHAR(20),
    TAB_ESTADO_USO INTEGER DEFAULT 1003,
    ESTADO_USO INTEGER DEFAULT 1,
    CAT_TIPO_ASIGNACION VARCHAR(20) null,
    TAB_TIPO_ASIGNACION INTEGER DEFAULT 1005,
    TIPO_ASIGNACION INTEGER DEFAULT 1,
    CAT_TIPO_ACTUALIZACION VARCHAR(20),
    TAB_TIPO_ACTUALIZACION INTEGER DEFAULT 1006,
    TIPO_ACTUALIZACION INTEGER DEFAULT 1,
    COSTO_HISTORICO NUMERIC(40, 20),
    COSTO_ACTUAL NUMERIC(40, 20),
    COSTO_ANTES_REVALUO NUMERIC(40, 20) null,
    DEP_ACUMULADA_ACTUAL NUMERIC(40, 20),
    DEP_ACUMULADA_HISTORICO NUMERIC(40, 20),
    FECHA_HISTORICO DATE,
    FECHA_ACTUAL DATE,
    FACTOR_DEPRECIACION_HISTORICO NUMERIC(10, 5),
    FACTOR_DEPRECIACION_ACTUAL NUMERIC(10, 5),
    INCORPORACION_ESPECIAL BOOL,
    CAT_FUENTE_FINANCIAMIENTO VARCHAR(20) not null,
    TAB_FUENTE_FINANCIAMIENTO INTEGER DEFAULT 1007,
    FUENTE_FINANCIAMIENTO INTEGER DEFAULT 1,
    CAT_ORGANISMO_FINANCIADOR VARCHAR(20) null,
    NRO_CONVENIO VARCHAR(100) null,
    ORDEN_COMPRA VARCHAR(100) null,
    ID_USUARIO_ASIGNADO INTEGER null,
    DESCRIPCION VARCHAR(200) not null,
    OBSERVACIONES VARCHAR(400) null,
    CODIGO_EXTENDIDO VARCHAR(200) null,
    CODIGO_RFID VARCHAR(200) null,
    CODIGO_EAN VARCHAR(200) null,
    CAT_ESTADO_ACTIVO_FIJO VARCHAR(20) not null,
    TAB_ESTADO_ACTIVO_FIJO INTEGER DEFAULT 1008,
    ESTADO_ACTIVO_FIJO INTEGER DEFAULT 1,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ACTIVO_FIJO_HIST primary key (ID_ACTIVO_FIJO_HIST)
);
comment on table ACF_ACTIVO_FIJO_HIST is 'Entidad que almacena la informacion de los Activos Fijos.';
comment on column ACF_ACTIVO_FIJO_HIST.ID_ACTIVO_FIJO_HIST is 'ID del Activo Fijo';
comment on column ACF_ACTIVO_FIJO_HIST.ID_ACTIVO_FIJO is 'Numero correlativo asignado al Activo Fijo, se asigna mediante trigger cuando el Activo Fijo cambia su CAT_ESTADO_ACTIVO_FIJO de PROREC a RECEPC. Se utiliza la secuencia SEQ_ACTIVO_FIJO_CORRELATIVO';
comment on column ACF_ACTIVO_FIJO_HIST.CORRELATIVO is 'Numero correlativo asignado al Activo Fijo, se asigna mediante trigger cuando el Activo Fijo cambia su CAT_ESTADO_ACTIVO_FIJO de PROREC a RECEPC. Se utiliza la secuencia SEQ_ACTIVO_FIJO_CORRELATIVO';
comment on column ACF_ACTIVO_FIJO_HIST.ID_PROVEEDOR is 'Id del Proveedor que entrego en Activo Fijo';
comment on column ACF_ACTIVO_FIJO_HIST.ID_AMBIENTE is 'Id del ambiente donde se encuentra localizado el Activo Fijo';
comment on column ACF_ACTIVO_FIJO_HIST.ID_NOTA_RECEPCION is 'Id de la nota de recepcion asignada al Activo Fijo';
comment on column ACF_ACTIVO_FIJO_HIST.ID_GARANTIA_ACTIVO_FIJO is 'Id de la garant�a del Activo Fijo si es que contara con una.
';
comment on column ACF_ACTIVO_FIJO_HIST.ID_FACTURA is 'Id de la factura asociada a la adquisicion del Activo Fijo.';
comment on column ACF_ACTIVO_FIJO_HIST.CAT_CENTRO_COSTO is 'Centro del Costo al cual esta asignado el Activo Fijo, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1006.';
comment on column ACF_ACTIVO_FIJO_HIST.CAT_ESTADO_USO is 'TBD';
comment on column ACF_ACTIVO_FIJO_HIST.CAT_TIPO_ASIGNACION is 'TBD';
comment on column ACF_ACTIVO_FIJO_HIST.CAT_TIPO_ACTUALIZACION is 'TBD';
comment on column ACF_ACTIVO_FIJO_HIST.COSTO_HISTORICO is 'Importe unitario pagado por el Activo Fijo';
comment on column ACF_ACTIVO_FIJO_HIST.FACTOR_DEPRECIACION_HISTORICO is 'Factor de depreciacion del Activo Fijo. Utilizado para propositos contables.';
comment on column ACF_ACTIVO_FIJO_HIST.FACTOR_DEPRECIACION_ACTUAL is 'Factor de depreciacion del Activo Fijo. Utilizado para propositos contables.';
comment on column ACF_ACTIVO_FIJO_HIST.ORDEN_COMPRA is 'El Numero de la orden de compra relacionado con la adquisicion del Activo Fijo.';
comment on column ACF_ACTIVO_FIJO_HIST.ID_USUARIO_ASIGNADO is 'Id del usuario al que se le asignara el Activo Fijo, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACTIVO_FIJO_HIST.DESCRIPCION is 'Descripcion del ActivoFijo';
comment on column ACF_ACTIVO_FIJO_HIST.OBSERVACIONES is 'Observaciones del Activo Fijo';
comment on column ACF_ACTIVO_FIJO_HIST.CODIGO_EXTENDIDO is 'Codigo RFID. TBD';
comment on column ACF_ACTIVO_FIJO_HIST.CODIGO_RFID is 'Codigo RFID. TBD';
comment on column ACF_ACTIVO_FIJO_HIST.CODIGO_EAN is 'Codigo de barras, se utilizara el formato CODE 128 para almacenar texto.';
comment on column ACF_ACTIVO_FIJO_HIST.CAT_ESTADO_ACTIVO_FIJO is 'Estado del Activo Fijo, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1014. El universo de valores inicial es:

''PROREC'':''PROCESO DE RECEPCion''
''RECEPC'':''RECEPCIONADO''
''ASGNDO'':''ASIGNADO''
';
comment on column ACF_ACTIVO_FIJO_HIST.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ACTIVO_FIJO_HIST.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ACTIVO_FIJO_HIST.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ACTIVO_FIJO_HIST.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACTIVO_FIJO_HIST.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ACTIVO_FIJO_HIST.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ACTIVO_FIJO_HIST.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ACTIVO_FIJO_HIST.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ALMACEN                                            */
create table ACF_ALMACEN (
    ID_ALMACEN SERIAL not null,
    NOMBRE VARCHAR(20) not null,
    OBSERVACIONES VARCHAR(400) null,
    ES_VALORADO BOOLEAN not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ALMACEN primary key (ID_ALMACEN)
);
comment on column ACF_ALMACEN.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ALMACEN.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ALMACEN.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ALMACEN.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ALMACEN.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ALMACEN.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ALMACEN.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ALMACEN.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ALTA_MATERIAL                                      */
create table ACF_ALTA_MATERIAL (
    ID_ALTA_MATERIAL SERIAL not null,
    CORRELATIVO INTEGER null,
    GESTION INTEGER not null,
    CAT_TIPO_ALTA_MATERIAL VARCHAR(20) not null,
    TAB_TIPO_ALTA_MATERIAL INTEGER DEFAULT 1009,
    TIPO_ALTA_MATERIAL INTEGER DEFAULT 1,
    ID_FACTURA INTEGER null,
    ID_ALMACEN INTEGER not null,
    ID_PROVEEDOR INTEGER null,
    FECHA_ALTA DATE not null,
    ID_USUARIO_ALTA INTEGER null,
    FECHA_VALORADO DATE null,
    OBSERVACIONES VARCHAR(400) null,
    CAT_ESTADO_ALTA_MATERIAL VARCHAR(20) not null,
    TAB_ESTADO_ALTA_MATERIAL INTEGER DEFAULT 1010,
    ESTADO_ALTA_MATERIAL INTEGER DEFAULT 1,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ALTA_MATERIAL primary key (ID_ALTA_MATERIAL)
);
comment on column ACF_ALTA_MATERIAL.ID_FACTURA is 'Id de la factura.';
comment on column ACF_ALTA_MATERIAL.ID_PROVEEDOR is 'Id del proveedor.';
comment on column ACF_ALTA_MATERIAL.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ALTA_MATERIAL.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ALTA_MATERIAL.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ALTA_MATERIAL.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ALTA_MATERIAL.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ALTA_MATERIAL.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ALTA_MATERIAL.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ALTA_MATERIAL.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ALTA_MATERIAL_DETALLE                              */
create table ACF_ALTA_MATERIAL_DETALLE (
    ID_ALTA_MATERIAL_DETALLE SERIAL not null,
    ID_ALTA_MATERIAL INTEGER not null,
    ID_MATERIAL INTEGER null,
    ID_REGISTRO_KARDEX_MATERIAL INTEGER null,
    IMPORTE_UNITARIO NUMERIC(20, 5) null,
    CANTIDAD INTEGER not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ALTA_MATERIAL_DETALLE primary key (ID_ALTA_MATERIAL_DETALLE)
);
comment on column ACF_ALTA_MATERIAL_DETALLE.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ALTA_MATERIAL_DETALLE.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ALTA_MATERIAL_DETALLE.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ALTA_MATERIAL_DETALLE.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ALTA_MATERIAL_DETALLE.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ALTA_MATERIAL_DETALLE.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ALTA_MATERIAL_DETALLE.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ALTA_MATERIAL_DETALLE.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_AMBIENTE                                           */
create table ACF_AMBIENTE (
    ID_AMBIENTE SERIAL not null,
    CAT_TIPO_AMBIENTE VARCHAR(20) not null,
    TAB_TIPO_AMBIENTE INTEGER DEFAULT 1011,
    TIPO_AMBIENTE INTEGER DEFAULT 1,
    CAT_EDIFICIO VARCHAR(20) not null,
    CAT_PISO VARCHAR(20) not null,
    NOMBRE VARCHAR(200) not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_AMBIENTE primary key (ID_AMBIENTE)
);
comment on table ACF_AMBIENTE is 'Entidad que almacenara los diferentes ambientes donde se colocar�n los Activos Fijos. Permite registrar diferente edificios.';
comment on column ACF_AMBIENTE.ID_AMBIENTE is 'Id del ambiente.';
comment on column ACF_AMBIENTE.CAT_TIPO_AMBIENTE is 'Tipo de ambiente, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1011.';
comment on column ACF_AMBIENTE.CAT_EDIFICIO is 'Edificio donde se encuentra el ambiente, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1012.';
comment on column ACF_AMBIENTE.CAT_PISO is 'Piso donde se encuentra el ambiente, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1013.';
comment on column ACF_AMBIENTE.NOMBRE is 'Numero o nombre del ambiente, de acuerdo a la nomenclatura utilizada por la institucion';
comment on column ACF_AMBIENTE.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_AMBIENTE.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_AMBIENTE.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_AMBIENTE.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_AMBIENTE.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_AMBIENTE.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_AMBIENTE.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_AMBIENTE.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ATRIBUTO_ACTIVO_FIJO                               */
create table ACF_ATRIBUTO_ACTIVO_FIJO (
    ID_ATRIBUTO_ACTIVO_FIJO SERIAL not null,
    ID_ACTIVO_FIJO INTEGER not null,
    CAT_TIPO_ATRIBUTO VARCHAR(20) not null,
    TAB_TIPO_ATRIBUTO INTEGER DEFAULT 1012,
    TIPO_ATRIBUTO INTEGER DEFAULT 1,
    DETALLE VARCHAR(200) not null,
    OBSERVACION VARCHAR(400) null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ATRIBUTO_ACTIVO_FIJO primary key (ID_ATRIBUTO_ACTIVO_FIJO)
);
comment on table ACF_ATRIBUTO_ACTIVO_FIJO is 'Entidad que representa los diferentes atributos de un Activo Fijo, como por ejemplo: MARCA, SERIE, COLOR, etc.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.ID_ATRIBUTO_ACTIVO_FIJO is 'Id del atributo.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'Id del activo fijo al que le pertenece el atributo.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.CAT_TIPO_ATRIBUTO is 'Tipo de atributo, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1007.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.DETALLE is 'Detalle o valor del atributo.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.OBSERVACION is 'Observaciones al atributo.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ATRIBUTO_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_ATRIBUTO_SUB_FAMILIA                               */
create table ACF_ATRIBUTO_SUB_FAMILIA (
    ID_ATRIBUTO_SUB_FAMILIA SERIAL not null,
    ID_SUB_FAMILIA INTEGER not null,
    CAT_TIPO_ATRIBUTO VARCHAR(20) not null,
    TAB_TIPO_ATRIBUTO INTEGER DEFAULT 1013,
    TIPO_ATRIBUTO INTEGER DEFAULT 1,
    PRIORIDAD INTEGER not null,
    IMPRIMIBLE BOOLEAN not null,
    ID_ORIGEN INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_ATRIBUTO_SUB_FAMILIA primary key (ID_ATRIBUTO_SUB_FAMILIA)
);
comment on column ACF_ATRIBUTO_SUB_FAMILIA.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_ATRIBUTO_SUB_FAMILIA.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_BAJA_ACTIVO_FIJO                                   */
create table ACF_BAJA_ACTIVO_FIJO (
    ID_BAJA_ACTIVO_FIJO SERIAL not null,
    CORRELATIVO INTEGER not null,
    GESTION INTEGER not null,
    ID_ACTIVO_FIJO INTEGER not null,
    FECHA_BAJA TIMESTAMP WITH TIME ZONE not null,
    CAT_MOTIVO_BAJA_ACTIVO_FIJO VARCHAR(20) not null,
    DOCUMENTO_RESPALDO VARCHAR(400) not null,
    OBSERVACIONES VARCHAR(400) null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_BAJA_ACTIVO_FIJO primary key (ID_BAJA_ACTIVO_FIJO)
);
comment on column ACF_BAJA_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'ID del Activo Fijo';
comment on column ACF_BAJA_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_BAJA_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_BAJA_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_BAJA_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_BAJA_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_BAJA_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_BAJA_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_BAJA_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_BAJA_MATERIAL                                      */
create table ACF_BAJA_MATERIAL (
    ID_BAJA_MATERIAL SERIAL not null,
    ID_KARDEX_MATERIAL INTEGER not null,
    ID_REGISTRO_KARDEX_MATERIAL INTEGER null,
    CORRELATIVO INTEGER null,
    GESTION INTEGER not null,
    CAT_TIPO_BAJA_MATERIAL VARCHAR(20) not null,
    TAB_TIPO_BAJA_MATERIAL INTEGER DEFAULT 1015,
    TIPO_BAJA_MATERIAL INTEGER DEFAULT 1,
    FECHA_BAJA TIMESTAMP WITH TIME ZONE null,
    ID_USUARIO_BAJA INTEGER null,
    DETALLE VARCHAR(400) not null,
    CANTIDAD INTEGER not null,
    CAT_ESTADO_BAJA_MATERIAL VARCHAR(20) not null,
    TAB_ESTADO_BAJA_MATERIAL INTEGER DEFAULT 1016,
    ESTADO_BAJA_MATERIAL INTEGER DEFAULT 1,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_BAJA_MATERIAL primary key (ID_BAJA_MATERIAL, ID_KARDEX_MATERIAL)
);
comment on column ACF_BAJA_MATERIAL.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_BAJA_MATERIAL.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_BAJA_MATERIAL.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_BAJA_MATERIAL.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_BAJA_MATERIAL.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_BAJA_MATERIAL.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_BAJA_MATERIAL.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_BAJA_MATERIAL.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_CODIGO_CONTABLE                                    */
create table ACF_CODIGO_CONTABLE (
    ID_CODIGO_CONTABLE SERIAL not null,
    GESTION INTEGER not null,
    CODIGO VARCHAR(50) not null,
    DESCRIPCION VARCHAR(200) not null,
    ID_ORIGEN INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_CODIGO_CONTABLE primary key (ID_CODIGO_CONTABLE)
);
comment on column ACF_CODIGO_CONTABLE.CODIGO is 'Codigo de la familia del tipo de activo.';
comment on column ACF_CODIGO_CONTABLE.DESCRIPCION is 'La descripcion del tipo familia activo fijo.';
comment on column ACF_CODIGO_CONTABLE.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_CODIGO_CONTABLE.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_CODIGO_CONTABLE.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_CODIGO_CONTABLE.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_CODIGO_CONTABLE.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_CODIGO_CONTABLE.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_CODIGO_CONTABLE.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_CODIGO_CONTABLE.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';

create table ACF_ITEMAF (
id_item SERIAL not null,
codnemo varchar(20),
nivel        varchar(15),
tipo         varchar(15),
grupo        varchar(15),
clase        varchar(15),
familia      varchar(15),
item         varchar(15),
nombre       varchar(150),
unidmedida   varchar(15),
tab_umedida INTEGER default 32,
umedida INTEGER default 1,
codclasif    varchar(15),
tipo_costodi varchar(15),
tipo_costofv varchar(15),
precio_unitario NUMERIC(15,5) DEFAULT 0,
stock integer DEFAULT 0,
stock_min integer DEFAULT 1,
    constraint PK_AF_ITEMAF primary key (ID_ITEM)
);

/* Table: ACF_COMISION_RECEPCION                                 */
create table ACF_COMISION_RECEPCION (
    ID_COMISION_RECEPCION SERIAL not null,
    ID_NOTA_RECEPCION INTEGER not null,
    ID_USUARIO INTEGER not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_COMISION_RECEPCION primary key (ID_COMISION_RECEPCION)
);
comment on table ACF_COMISION_RECEPCION is 'Entidad que almacena a los integrantes de una comision de recepcion para una nota de recepcion.';
comment on column ACF_COMISION_RECEPCION.ID_COMISION_RECEPCION is 'Id del integrante de la comision de recepcion.';
comment on column ACF_COMISION_RECEPCION.ID_NOTA_RECEPCION is 'Id de la nota de recepcion vinculada.';
comment on column ACF_COMISION_RECEPCION.ID_USUARIO is 'Id del usuario miembro de la comision de recepcion, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_COMISION_RECEPCION.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_COMISION_RECEPCION.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_COMISION_RECEPCION.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_COMISION_RECEPCION.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_COMISION_RECEPCION.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_COMISION_RECEPCION.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_COMISION_RECEPCION.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_COMISION_RECEPCION.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_COMPONENTE_ACTIVO_FIJO                             */
create table ACF_COMPONENTE_ACTIVO_FIJO (
    ID_COMPONENTE_ACTIVO_FIJO SERIAL not null,
    ID_ACTIVO_FIJO INTEGER not null,
    CORRELATIVO INTEGER null,
    CAT_COMPONENTE_ACTIVO_FIJO VARCHAR(20) not null,
    CANTIDAD NUMERIC(4) not null,
    OBSERVACION VARCHAR(400) null,
    CODIGO_RFID VARCHAR(200) not null,
    CODIGO_EAN VARCHAR(200) not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_COMPONENTE_ACTIVO_FIJO primary key (ID_COMPONENTE_ACTIVO_FIJO)
);
comment on table ACF_COMPONENTE_ACTIVO_FIJO is 'Entidad que almacena los diferentes componentes de un Activo Fijo. Por ejemplo para un COMPUTADOR  pueden ser: PARLANTES, MONITOR, TECLADO, etc.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.ID_COMPONENTE_ACTIVO_FIJO is 'Id del componente del Activo Fijo.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'Id del Activo Fijo al que esta vinculado el componente.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.CORRELATIVO is 'Correlativo asignado al componente, esta numeracion es individual para cada Activo Fijo  y comienza en 1.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.CAT_COMPONENTE_ACTIVO_FIJO is 'Tipo de componente, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1010.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.CANTIDAD is 'Cantidad de elementos que conforman el componente.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.OBSERVACION is 'Observaciones del componente.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.CODIGO_RFID is 'Codigo RFID para el componente.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.CODIGO_EAN is 'Codigo de barras, se utilizara el formato CODE 128 para almacenar texto.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_COMPONENTE_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_FACTURA                                            */
create table ACF_FACTURA (
    ID_FACTURA SERIAL not null,
    NRO_FACTURA VARCHAR(50) not null,
    FECHA_FACTURA DATE not null,
    NRO_AUTORIZACION VARCHAR(50) null,
    CODIGO_CONTROL VARCHAR(20) null,
    RAZON_SOCIAL VARCHAR(100) not null,
    NIT VARCHAR(50) not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_FACTURA primary key (ID_FACTURA)
);
comment on table ACF_FACTURA is 'Entidad que almacena los datos de la factura asociados a la adquisicion de un Activo Fijo.';
comment on column ACF_FACTURA.ID_FACTURA is 'Id de la factura.';
comment on column ACF_FACTURA.NRO_FACTURA is 'Numero de la factura.';
comment on column ACF_FACTURA.FECHA_FACTURA is 'Fecha de la factura.';
comment on column ACF_FACTURA.NRO_AUTORIZACION is 'Numero de autorizacion de la factura.';
comment on column ACF_FACTURA.CODIGO_CONTROL is 'Codigo de control de la factura, si corresponde.';
comment on column ACF_FACTURA.RAZON_SOCIAL is 'Raz�n social de la empresa que emite la factura.';
comment on column ACF_FACTURA.NIT is 'Nit de la empresa que emite la factura.';
comment on column ACF_FACTURA.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_FACTURA.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_FACTURA.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_FACTURA.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_FACTURA.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_FACTURA.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_FACTURA.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_FACTURA.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_FAMILIA_ACTIVO                                     */
create table ACF_FAMILIA_ACTIVO (
    ID_FAMILIA_ACTIVO SERIAL not null,
    ID_CODIGO_CONTABLE INTEGER not null,
    ID_PARTIDA_PRESUPUESTARIA INTEGER not null,
    GESTION INTEGER not null,
    CODIGO VARCHAR(50) not null,
    DESCRIPCION VARCHAR(200) not null,
    ID_ORIGEN INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_FAMILIA_ACTIVO primary key (ID_FAMILIA_ACTIVO)
);
comment on table ACF_FAMILIA_ACTIVO is 'Entidad que registra la familia a la que pertenece el Activo Fijo.';
comment on column ACF_FAMILIA_ACTIVO.ID_FAMILIA_ACTIVO is 'Id de la familia de tipo de activo para los activos fijos.';
comment on column ACF_FAMILIA_ACTIVO.CODIGO is 'Codigo de la familia del tipo de activo.';
comment on column ACF_FAMILIA_ACTIVO.DESCRIPCION is 'La descripcion del tipo familia activo fijo.';
comment on column ACF_FAMILIA_ACTIVO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_FAMILIA_ACTIVO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_FAMILIA_ACTIVO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_FAMILIA_ACTIVO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_FAMILIA_ACTIVO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_FAMILIA_ACTIVO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_FAMILIA_ACTIVO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_FAMILIA_ACTIVO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_GARANTIA_ACTIVO_FIJO                               */
create table ACF_GARANTIA_ACTIVO_FIJO (
    ID_GARANTIA_ACTIVO_FIJO SERIAL not null,
    FECHA_INICIO TIMESTAMP WITH TIME ZONE null,
    FECHA_FIN TIMESTAMP WITH TIME ZONE null,
    CAT_TIPO_GARANTIA VARCHAR(20) null,
    TAB_TIPO_GARANTIA INTEGER DEFAULT 1017,
    TIPO_GARANTIA INTEGER DEFAULT 1,
    CODIGO_CONTRATO VARCHAR(100) null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_GARANTIA_ACTIVO_FIJO primary key (ID_GARANTIA_ACTIVO_FIJO)
);
comment on table ACF_GARANTIA_ACTIVO_FIJO is 'Entidad que almacena la informacion sobre la garant�a del Activo Fijo.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.ID_GARANTIA_ACTIVO_FIJO is 'Id de la garant�a del Activo Fijo.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.FECHA_INICIO is 'Fecha de inicio de la vigencia de la garant�a.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.FECHA_FIN is 'Fecha de fin de la vigencia de la garant�a.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.CAT_TIPO_GARANTIA is 'Tipo de garant�a, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1009.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.CODIGO_CONTRATO is 'Codigo o Numero del contrato asociado a la garant�a.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_GARANTIA_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_GARANTIA_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_GARANTIA_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_GARANTIA_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_GESTION                                            */
create table ACF_GESTION (
    ID_GESTION SERIAL not null,
    GESTION INTEGER not null,
    VIGENTE BOOLEAN not null,
    CAT_ESTADO_GESTION VARCHAR(20) not null,
    TAB_ESTADO_GESTION INTEGER DEFAULT 1018,
    ESTADO_GESTION INTEGER DEFAULT 1,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_GESTION primary key (ID_GESTION)
);
comment on column ACF_GESTION.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_GESTION.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_GESTION.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_GESTION.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_GESTION.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_GESTION.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_GESTION.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_GESTION.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_IMAGEN_ACTIVO_FIJO                                 */
create table ACF_IMAGEN_ACTIVO_FIJO (
    ID_IMAGEN_ACTIVO_FIJO SERIAL not null,
    ID_ACTIVO_FIJO INTEGER not null,
    ID_ACCESORIO_ACTIVO_FIJO INTEGER null,
    ID_TRANSFERENCIA_ACTIVO_FIJO INTEGER null,
    IMAGEN bytea not null,
    NOMBRE_ARCHIVO VARCHAR(200) not null,
    TIPO_MIME VARCHAR(100) not null,
    FECHA_CAPTURA TIMESTAMP WITH TIME ZONE not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_IMAGEN_ACTIVO_FIJO primary key (ID_IMAGEN_ACTIVO_FIJO)
);
comment on table ACF_IMAGEN_ACTIVO_FIJO is 'En esta relacion se permite tener varias imagenes para un solo Activo Fijo, esto deb�do a que es posible requerir varias imagenes desde diferentes angulos o porque se requiere imagen de los componentes que conforman el activo fijo.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.ID_IMAGEN_ACTIVO_FIJO is 'Llave primaria de la imagen del activo fijo. ';
comment on column ACF_IMAGEN_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'Id del activo fijo al que le pertenece la imagen';
comment on column ACF_IMAGEN_ACTIVO_FIJO.ID_ACCESORIO_ACTIVO_FIJO is 'Id del atributo.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.IMAGEN is 'Imagen asociada al Activo Fijo';
comment on column ACF_IMAGEN_ACTIVO_FIJO.NOMBRE_ARCHIVO is 'Nombre del archivo de la imagen.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TIPO_MIME is 'El tipo mime de la imagen para correcta visualizacion.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_IMAGEN_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_IMAGEN_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_KARDEX_MATERIAL                                    */
create table ACF_KARDEX_MATERIAL (
    ID_KARDEX_MATERIAL SERIAL not null,
    GESTION INTEGER not null,
    ID_MATERIAL INTEGER null,
    ID_ALMACEN INTEGER null,
    SALDO_CANTIDAD INTEGER not null,
    SALDO_IMPORTE NUMERIC(20, 5) null,
    VERSION INTEGER not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_KARDEX_MATERIAL primary key (ID_KARDEX_MATERIAL)
);
comment on column ACF_KARDEX_MATERIAL.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_KARDEX_MATERIAL.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_KARDEX_MATERIAL.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_KARDEX_MATERIAL.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_KARDEX_MATERIAL.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_KARDEX_MATERIAL.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_KARDEX_MATERIAL.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_KARDEX_MATERIAL.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_KARDEX_MATERIAL_HIST                               */
create table ACF_KARDEX_MATERIAL_HIST (
    ID_KARDEX_MATERIAL_HIST SERIAL not null,
    ID_KARDEX_MATERIAL INTEGER not null,
    GESTION INTEGER not null,
    ID_MATERIAL INTEGER null,
    ID_ALMACEN INTEGER null,
    SALDO_CANTIDAD INTEGER not null,
    SALDO_IMPORTE NUMERIC(20, 5) null,
    VERSION INTEGER not null,
    ULTIMO_GESTION BOOL not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_KARDEX_MATERIAL_HIST primary key (ID_KARDEX_MATERIAL_HIST)
);
comment on column ACF_KARDEX_MATERIAL_HIST.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_KARDEX_MATERIAL_HIST.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_KARDEX_MATERIAL_HIST.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_KARDEX_MATERIAL_HIST.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_KARDEX_MATERIAL_HIST.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_KARDEX_MATERIAL_HIST.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_KARDEX_MATERIAL_HIST.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_KARDEX_MATERIAL_HIST.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_MATERIAL                                           */
create table ACF_MATERIAL (
    ID_MATERIAL SERIAL not null,
    CODIGO VARCHAR(50) not null,
    CAT_FAMILIA_MATERIAL VARCHAR(20) not null,
    ID_PARTIDA_PRESUPUESTARIA INTEGER not null,
    CAT_MEDIDA VARCHAR(20) not null,
    NOMBRE VARCHAR(200) not null,
    MINIMO INTEGER not null,
    MAXIMO INTEGER not null,
    FUNGIBLE BOOLEAN not null,
    REVISAR_MINIMO BOOLEAN not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_MATERIAL primary key (ID_MATERIAL)
);
comment on column ACF_MATERIAL.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_MATERIAL.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_MATERIAL.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_MATERIAL.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_MATERIAL.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_MATERIAL.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_MATERIAL.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_MATERIAL.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_MATERIAL_PROVEEDOR                                 */
create table ACF_MATERIAL_PROVEEDOR (
    ID_MATERIAL_PROVEEDOR SERIAL not null,
    ID_MATERIAL INTEGER null,
    ID_PROVEEDOR INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_MATERIAL_PROVEEDOR primary key (ID_MATERIAL_PROVEEDOR)
);
comment on column ACF_MATERIAL_PROVEEDOR.ID_PROVEEDOR is 'Id del proveedor.';
comment on column ACF_MATERIAL_PROVEEDOR.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_MATERIAL_PROVEEDOR.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_MATERIAL_PROVEEDOR.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_MATERIAL_PROVEEDOR.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_MATERIAL_PROVEEDOR.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_MATERIAL_PROVEEDOR.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_MATERIAL_PROVEEDOR.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_MATERIAL_PROVEEDOR.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_NOTA_RECEPCION                                     */
create table ACF_NOTA_RECEPCION (
    ID_NOTA_RECEPCION SERIAL not null,
    CORRELATIVO INTEGER null,
    GESTION INTEGER not null,
    CAT_TIPO_MOVIMIENTO VARCHAR(20) not null,
    TAB_TIPO_MOVIMIENTO INTEGER DEFAULT 1019,
    TIPO_MOVIMIENTO INTEGER DEFAULT 1,
    CAT_MOTIVO_TIPO_MOVIMIENTO VARCHAR(20) not null,
    CAT_TIPO_DOCUMENTO_RECEPCION VARCHAR(20) not null,
    TAB_TIPO_DOCUMENTO_RECEPCION INTEGER DEFAULT 1020,
    TIPO_DOCUMENTO_RECEPCION INTEGER DEFAULT 1,
    NRO_DOCUMENTO_RECEPCION VARCHAR(100) not null,
    FECHA_RECEPCION TIMESTAMP WITH TIME ZONE not null,
    ORDEN_COMPRA VARCHAR(100) null,
    CAT_ESTADO_NOTA_RECEPCION VARCHAR(20) not null,
    TAB_ESTADO_NOTA_RECEPCION INTEGER DEFAULT 1021,
    ESTADO_NOTA_RECEPCION INTEGER DEFAULT 1,
    ID_USUARIO_RECEPCION INTEGER not null,
    ID_AREA_SOLICITANTE INTEGER not null,
    ID_CONTROL_CALIDAD INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_NOTA_RECEPCION primary key (ID_NOTA_RECEPCION)
);
comment on table ACF_NOTA_RECEPCION is 'Entidad que almacena una nota de recepcion.';
comment on column ACF_NOTA_RECEPCION.ID_NOTA_RECEPCION is 'Id de la Nota de Recepction.';
comment on column ACF_NOTA_RECEPCION.CORRELATIVO is 'Correlativo de la nota de recepcion, se lo asigna cuando el CAT_ESTADO_NOTA_RECEPCION pasa de PROREC a INGRES. Se lo as�gna a partir de la secuencia SEQ_NOTA_RECEPCION_CORRELATIVO';
comment on column ACF_NOTA_RECEPCION.CAT_TIPO_MOVIMIENTO is 'Tipo de movimiento, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1000.';
comment on column ACF_NOTA_RECEPCION.CAT_MOTIVO_TIPO_MOVIMIENTO is 'Motivo para el tipo de movimiento, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1001.';
comment on column ACF_NOTA_RECEPCION.FECHA_RECEPCION is 'Fecha de la recepcion';
comment on column ACF_NOTA_RECEPCION.ORDEN_COMPRA is 'Numero de la orden de compra asociada a la recepcion.';
comment on column ACF_NOTA_RECEPCION.CAT_ESTADO_NOTA_RECEPCION is 'Estado de la nota de recepcion, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1015. Su posibles valores son:

''PROREC'':''PROCESO DE RECEPCion''
''INGRES'':''INGRESADO''
''CANCEL'':''CANCELADO''';
comment on column ACF_NOTA_RECEPCION.ID_USUARIO_RECEPCION is 'Id del usuario que realiza la recepcion, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_NOTA_RECEPCION.ID_AREA_SOLICITANTE is 'Id de la �rea que realizo la solicitud, este ID corresponde con la columna ID_AREA de l tabla TX_AREA del esquema "seguridad".';
comment on column ACF_NOTA_RECEPCION.ID_CONTROL_CALIDAD is 'Id del usuario que realiza el control de calidad, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_NOTA_RECEPCION.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_NOTA_RECEPCION.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_NOTA_RECEPCION.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_NOTA_RECEPCION.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_NOTA_RECEPCION.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_NOTA_RECEPCION.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_NOTA_RECEPCION.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_NOTA_RECEPCION.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_PARTIDA_PRESUPUESTARIA                             */
create table ACF_PARTIDA_PRESUPUESTARIA (
    ID_PARTIDA_PRESUPUESTARIA SERIAL not null,
    GESTION INTEGER not null,
    CODIGO VARCHAR(50) not null,
    DESCRIPCION VARCHAR(200) not null,
    ID_ORIGEN INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_PARTIDA_PRESUPUESTARIA primary key (ID_PARTIDA_PRESUPUESTARIA)
);
comment on column ACF_PARTIDA_PRESUPUESTARIA.CODIGO is 'Codigo de la familia del tipo de activo.';
comment on column ACF_PARTIDA_PRESUPUESTARIA.DESCRIPCION is 'La descripcion del tipo familia activo fijo.';
comment on column ACF_PARTIDA_PRESUPUESTARIA.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_PARTIDA_PRESUPUESTARIA.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_PARTIDA_PRESUPUESTARIA.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_PARTIDA_PRESUPUESTARIA.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_PARTIDA_PRESUPUESTARIA.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_PARTIDA_PRESUPUESTARIA.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_PARTIDA_PRESUPUESTARIA.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_PARTIDA_PRESUPUESTARIA.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_PROVEEDOR                                          */
create table ACF_PROVEEDOR (
    ID_PROVEEDOR SERIAL not null,
    NOMBRE VARCHAR(100) not null,
    NIT VARCHAR(30) not null,
    TELEFONO VARCHAR(30) null,
    CORREO_ELECTRONICO VARCHAR(50) null,
    PERSONA_CONTACTO VARCHAR(100) null,
    CARGO_CONTACTO VARCHAR(100) null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_PROVEEDOR primary key (ID_PROVEEDOR)
);
comment on table ACF_PROVEEDOR is 'Entidad que registra a los proveedores.';
comment on column ACF_PROVEEDOR.ID_PROVEEDOR is 'Id del proveedor.';
comment on column ACF_PROVEEDOR.NOMBRE is 'Nombre del proveedor.';
comment on column ACF_PROVEEDOR.NIT is 'NIT del proveedor.';
comment on column ACF_PROVEEDOR.TELEFONO is 'Telefono del Proveedor';
comment on column ACF_PROVEEDOR.CORREO_ELECTRONICO is 'Correo electr�nico del proveedor.';
comment on column ACF_PROVEEDOR.PERSONA_CONTACTO is 'Nombre de la persona de contacto asignada por el proveedor.';
comment on column ACF_PROVEEDOR.CARGO_CONTACTO is 'Cargo de la persona de contacto asignada por el proveedor.';
comment on column ACF_PROVEEDOR.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_PROVEEDOR.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_PROVEEDOR.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_PROVEEDOR.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_PROVEEDOR.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_PROVEEDOR.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_PROVEEDOR.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_PROVEEDOR.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_PROVEEDOR_ACT_ECO                                  */
create table ACF_PROVEEDOR_ACT_ECO (
    ID_PROVEEDOR_ACT_ECO SERIAL not null,
    CAT_ACTIVIDAD_ECONOMICA VARCHAR(20) not null,
    ID_PROVEEDOR INTEGER not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_PROVEEDOR_ACT_ECO primary key (ID_PROVEEDOR_ACT_ECO)
);
comment on table ACF_PROVEEDOR_ACT_ECO is 'Entidad para almacenar las Actividades Econ�micas asociadas a un proveedor.';
comment on column ACF_PROVEEDOR_ACT_ECO.CAT_ACTIVIDAD_ECONOMICA is 'Actividad Econ�mica, obtiene su valor del catalogo definido en la tabla configuraciones.cnf_valor donde el id_catalogo es 1002.';
comment on column ACF_PROVEEDOR_ACT_ECO.ID_PROVEEDOR is 'Id del proveedor asociado con la Actividad Econ�mica';
comment on column ACF_PROVEEDOR_ACT_ECO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_PROVEEDOR_ACT_ECO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_PROVEEDOR_ACT_ECO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_PROVEEDOR_ACT_ECO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_PROVEEDOR_ACT_ECO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_PROVEEDOR_ACT_ECO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_PROVEEDOR_ACT_ECO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_PROVEEDOR_ACT_ECO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_REGISTRO_KARDEX_MATERIAL                           */
create table ACF_REGISTRO_KARDEX_MATERIAL (
    ID_REGISTRO_KARDEX_MATERIAL SERIAL not null,
    ID_KARDEX_MATERIAL INTEGER not null,
    ID_SOLICITUD_MATERIAL INTEGER null,
    FECHA_REGISTRO TIMESTAMP WITH TIME ZONE not null,
    DETALLE VARCHAR(450) not null,
    IMPORTE_UNITARIO NUMERIC(20, 5) null,
    CANTIDAD INTEGER not null,
    SALDO INTEGER null,
    CAT_TIPO_REGISTRO_KARDEX VARCHAR(20) not null,
    TAB_TIPO_REGISTRO_KARDEX INTEGER DEFAULT 1023,
    TIPO_REGISTRO_KARDEX INTEGER DEFAULT 1,
    ID_USUARIO_REGISTRO INTEGER not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_REGISTRO_KARDEX_MATERIAL primary key (ID_REGISTRO_KARDEX_MATERIAL)
);
comment on column ACF_REGISTRO_KARDEX_MATERIAL.ID_USUARIO_REGISTRO is 'SI ES UNA:

SALIDA: Persona a la que se le asigno el material.
ENTVAL: Persona que registra el material
ENTFIS: Persona que devuelve el material ';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_REGISTRO_KARDEX_MATERIAL.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_REGISTRO_KARDEX_MATERIAL_HIS                       */
create table ACF_REGISTRO_KARDEX_MATERIAL_HIS (
    ID_REGISTRO_KARDEX_MATERIAL_HIS SERIAL not null,
    ID_REGISTRO_KARDEX_MATERIAL INTEGER not null,
    ID_KARDEX_MATERIAL INTEGER not null,
    ID_SOLICITUD_MATERIAL INTEGER null,
    FECHA_REGISTRO TIMESTAMP WITH TIME ZONE not null,
    DETALLE VARCHAR(450) not null,
    IMPORTE_UNITARIO NUMERIC(20, 5) null,
    CANTIDAD INTEGER not null,
    SALDO INTEGER null,
    CAT_TIPO_REGISTRO_KARDEX VARCHAR(20) not null,
    TAB_TIPO_REGISTRO_KARDEX INTEGER DEFAULT 1025,
    TIPO_REGISTRO_KARDEX INTEGER DEFAULT 1,
    ID_USUARIO_REGISTRO INTEGER not null,
    ULTIMO_GESTION BOOL not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_REGISTRO_KARDEX_MAT_HIS primary key (ID_REGISTRO_KARDEX_MATERIAL_HIS)
);
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.ID_USUARIO_REGISTRO is 'SI ES UNA:

SALIDA: Persona a la que se le asigno el material.
ENTVAL: Persona que registra el material
ENTFIS: Persona que devuelve el material ';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_REGISTRO_KARDEX_MATERIAL_HIS.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_REVALUO_ACTIVO_FIJO                                */
create table ACF_REVALUO_ACTIVO_FIJO (
    ID_REVALUO_ACTIVO_FIJO SERIAL not null,
    ID_ACTIVO_FIJO INTEGER not null,
    FECHA_REVALUO DATE not null,
    NUEVO_FACTOR_DEPRECIACION NUMERIC(10, 5) not null,
    DISPOCISION_RESPALDO VARCHAR(200) not null,
    MOTIVO VARCHAR(400) null,
    COSTO_HISTORICO NUMERIC(20, 5) null,
    COSTO_NUEVO NUMERIC(20, 5) null,
    DEP_AL_REVALUO NUMERIC(20, 5) not null,
    DEP_ACUM_AL_REVALUO NUMERIC(20, 5) not null,
    VALOR_NETO_AL_REVALUO NUMERIC(20, 5) not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_REVALUO_ACTIVO_FIJO primary key (ID_REVALUO_ACTIVO_FIJO)
);
comment on column ACF_REVALUO_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'ID del Activo Fijo';
comment on column ACF_REVALUO_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_REVALUO_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_REVALUO_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_REVALUO_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_REVALUO_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_REVALUO_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_REVALUO_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_REVALUO_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_SOLICITUD                                          */
create table ACF_SOLICITUD (
    ID_SOLICITUD SERIAL not null,
    CORRELATIVO INTEGER null,
    GESTION INTEGER not null,
    CAT_TIPO_SOLICITUD VARCHAR(20) not null,
    TAB_TIPO_SOLICITUD INTEGER DEFAULT 1026,
    TIPO_SOLICITUD INTEGER DEFAULT 1,
    CAT_ESTADO_SOLICITUD VARCHAR(20) not null,
    TAB_ESTADO_SOLICITUD INTEGER DEFAULT 1027,
    ESTADO_SOLICITUD INTEGER DEFAULT 1,
    ID_USUARIO_SOLICITUD INTEGER not null,
    DETALLE_SOLICITUD VARCHAR(400) not null,
    FECHA_SOLICITUD TIMESTAMP WITH TIME ZONE not null,
    ID_USUARIO_AUTORIZACION INTEGER null,
    DETALLE_AUTORIZACION VARCHAR(400) null,
    FECHA_AUTORIZACION TIMESTAMP WITH TIME ZONE null,
    ID_USUARIO_EJECUCION INTEGER null,
    DETALLE_EJECUCION VARCHAR(400) null,
    FECHA_EJECUCION TIMESTAMP WITH TIME ZONE null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_SOLICITUD primary key (ID_SOLICITUD)
);
comment on column ACF_SOLICITUD.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_SOLICITUD.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_SOLICITUD.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_SOLICITUD.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SOLICITUD.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_SOLICITUD.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_SOLICITUD.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SOLICITUD.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_SOLICITUD_ACTIVO_FIJO                              */
create table ACF_SOLICITUD_ACTIVO_FIJO (
    ID_SOLICITUD_ACTIVO_FIJO SERIAL not null,
    ID_SOLICITUD INTEGER null,
    ID_ACTIVO_FIJO INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_SOLICITUD_ACTIVO_FIJO primary key (ID_SOLICITUD_ACTIVO_FIJO)
);
comment on column ACF_SOLICITUD_ACTIVO_FIJO.ID_ACTIVO_FIJO is 'ID del Activo Fijo';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SOLICITUD_ACTIVO_FIJO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_SOLICITUD_MATERIAL                                 */
create table ACF_SOLICITUD_MATERIAL (
    ID_SOLICITUD_MATERIAL SERIAL not null,
    ID_SOLICITUD INTEGER not null,
    ID_MATERIAL INTEGER not null,
    CANTIDAD_SOLICITADA INTEGER not null,
    CANTIDAD_APROBADA INTEGER null,
    CANTIDAD_ENTREGADA INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_SOLICITUD_MATERIAL primary key (ID_SOLICITUD_MATERIAL)
);
comment on column ACF_SOLICITUD_MATERIAL.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_SOLICITUD_MATERIAL.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_SOLICITUD_MATERIAL.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_SOLICITUD_MATERIAL.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SOLICITUD_MATERIAL.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_SOLICITUD_MATERIAL.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_SOLICITUD_MATERIAL.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SOLICITUD_MATERIAL.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_SUB_FAMILIA_ACTIVO                                 */
create table ACF_SUB_FAMILIA_ACTIVO (
    ID_SUB_FAMILIA SERIAL not null,
    GESTION INTEGER not null,
    ID_FAMILIA_ACTIVO INTEGER not null,
    CODIGO VARCHAR(50) not null,
    DESCRIPCION VARCHAR(200) not null,
    FACTOR_DEPRECIACION NUMERIC(10, 5) not null,
    DEPRECIAR BOOLEAN not null,
    ACTUALIZAR BOOLEAN not null,
    AMORTIZACION_VARIABLE BOOLEAN not null,
    ID_ORIGEN INTEGER null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_SUB_FAMILIA_ACTIVO primary key (ID_SUB_FAMILIA)
);
comment on column ACF_SUB_FAMILIA_ACTIVO.ID_FAMILIA_ACTIVO is 'Id de la familia de tipo de activo para los activos fijos.';
comment on column ACF_SUB_FAMILIA_ACTIVO.CODIGO is 'Codigo de la familia del tipo de activo.';
comment on column ACF_SUB_FAMILIA_ACTIVO.DESCRIPCION is 'La descripcion del tipo familia activo fijo.';
comment on column ACF_SUB_FAMILIA_ACTIVO.FACTOR_DEPRECIACION is 'Factor de depreciacion asignado.';
comment on column ACF_SUB_FAMILIA_ACTIVO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_SUB_FAMILIA_ACTIVO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_SUB_FAMILIA_ACTIVO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_SUB_FAMILIA_ACTIVO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SUB_FAMILIA_ACTIVO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_SUB_FAMILIA_ACTIVO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_SUB_FAMILIA_ACTIVO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_SUB_FAMILIA_ACTIVO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_TIPO_CAMBIO                                        */
create table ACF_TIPO_CAMBIO (
    ID_TIPO_CAMBIO SERIAL not null,
    CAT_MONEDA VARCHAR(20) not null,
    TAB_MONEDA INTEGER DEFAULT 1028,
    MONEDA INTEGER DEFAULT 1,
    FECHA DATE not null,
    CAMBIO NUMERIC(12, 6) not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_TIPO_CAMBIO primary key (ID_TIPO_CAMBIO)
);
comment on column ACF_TIPO_CAMBIO.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_TIPO_CAMBIO.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_TIPO_CAMBIO.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_TIPO_CAMBIO.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_TIPO_CAMBIO.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_TIPO_CAMBIO.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_TIPO_CAMBIO.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_TIPO_CAMBIO.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';
/* Table: ACF_TRANSFERENCIA_ASIGNACION                           */
create table ACF_TRANSFERENCIA_ASIGNACION (
    ID_TRANSFERENCIA_ACTIVO_FIJO SERIAL not null,
    CORRELATIVO INTEGER not null,
    GESTION INTEGER not null,
    ID_ACTIVO_FIJO INTEGER not null,
    ID_NOTA_RECEPCION INTEGER null,
    CAT_TRANSFERENCIA_ASIGNACION VARCHAR(20) not null,
    FECHA_TRANSFERENCIA TIMESTAMP WITH TIME ZONE not null,
    CAT_MOTIVO_TRANSFERENCIA VARCHAR(20) null,
    CAT_TIPO_ASIGNACION VARCHAR(20) not null,
    TAB_TIPO_ASIGNACION INTEGER DEFAULT 1005,
    TIPO_ASIGNACION INTEGER DEFAULT 1,
    CAT_ESTADO_USO VARCHAR(20) not null,
    TAB_ESTADO_USO INTEGER DEFAULT 1003,
    ESTADO_USO INTEGER DEFAULT 1,
    OBSERVACIONES VARCHAR(400) null,
    CAT_CENTRO_COSTO_ORIGEN VARCHAR(20) null,
    TAB_CENTRO_COSTO_ORIGEN INTEGER DEFAULT 1035,
    CENTRO_COSTO_ORIGEN INTEGER DEFAULT 1,
    ID_USUARIO_ORIGEN INTEGER null,
    ID_AMBIENTE_ORIGEN INTEGER null,
    CAT_CENTRO_COSTO_DESTINO VARCHAR(20) not null,
    TAB_CENTRO_COSTO_DESTINO INTEGER DEFAULT 1035,
    CENTRO_COSTO_DESTINO INTEGER DEFAULT 1,
    ID_USUARIO_DESTINO INTEGER not null,
    ID_AMBIENTE_DESTINO INTEGER not null,
    ESTADO VARCHAR(6) not null,
    ID_TRANSACCION INTEGER not null,
    TX_FCH_INI TIMESTAMP WITH TIME ZONE not null,
    TX_USR_INI INTEGER not null,
    TX_HOST_INI VARCHAR(30) not null,
    TX_FCH_MOD TIMESTAMP WITH TIME ZONE null,
    TX_USR_MOD INTEGER null,
    TX_HOST_MOD VARCHAR(30) null,
    constraint PK_AF_TRANSFERENCIA_ASIGNACION primary key (ID_TRANSFERENCIA_ACTIVO_FIJO)
);
comment on column ACF_TRANSFERENCIA_ASIGNACION.ID_ACTIVO_FIJO is 'ID del Activo Fijo';
comment on column ACF_TRANSFERENCIA_ASIGNACION.ID_NOTA_RECEPCION is 'Id de la Nota de Recepction.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.ID_AMBIENTE_ORIGEN is 'Id del ambiente.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.ID_AMBIENTE_DESTINO is 'Id del ambiente.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.ESTADO is 'Estado del registro, pudiendo tener los siguientes valores:
A: Activo
I: Inactivo';
comment on column ACF_TRANSFERENCIA_ASIGNACION.ID_TRANSACCION is 'El id de la transaccion que realizo la ultima modificacion o su creacion, utilizado para propositos de auditoria.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.TX_FCH_INI is 'La fecha en la que se creo el registro.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.TX_USR_INI is 'Id del usuario que creo el registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_TRANSFERENCIA_ASIGNACION.TX_HOST_INI is 'IP de la maquina desde la cual se creo el registro.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.TX_FCH_MOD is 'Fecha de modificacion del registro.';
comment on column ACF_TRANSFERENCIA_ASIGNACION.TX_USR_MOD is 'Id del usuario que realizo la ultima modificacion al registro, este ID corresponde con la columna ID_USUARIO de la tabla TX_USUARIO del esquema "seguridad".';
comment on column ACF_TRANSFERENCIA_ASIGNACION.TX_HOST_MOD is 'IP de la maquina donde se realizo la ultima modificacion del registro.';

create table TX_TRANSACCION (
    ID_TRANSACCION SERIAL not null,
    TAB_TIPOOPERACION INTEGER,
    TIPOOPERACION INTEGER,
    TAB_TIPOOPERSUB INTEGER,
    TIPOOPERSUB INTEGER,    
    GLOSA VARCHAR(150),
    MONTO NUMERIC(15,2),
    FECHA_OPER DATE,
    FECHA_VALOR DATE,
    ID_EMPLEADO INTEGER,
    ID_EMPLEADOAUT INTEGER,
    ID_UNIDAD INTEGER,
    ID_UNIDADDEST INTEGER,
    ID_USRREG VARCHAR(50),
    ID_USRAUT VARCHAR(50),
    ID_TRXORIGEN INTEGER,
    TX_FECHA TIMESTAMP WITH TIME ZONE not null,
    TX_USUARIO Integer not null,
    TX_HOST VARCHAR(30) not null,
    constraint PK_TX_TRANSACCION primary key (ID_TRANSACCION)
);

comment on table TX_TRANSACCION is 'Entidad destinada a almacenar los IDs de todas las transacciones realizadas en el sistema.';
comment on column TX_TRANSACCION.ID_TRANSACCION is 'Id de la transaccion';
comment on column TX_TRANSACCION.TX_FECHA is 'Fecha de la transaccion.';
comment on column TX_TRANSACCION.TX_USUARIO is 'Usuario que realiza la transaccion';
comment on column TX_TRANSACCION.TX_HOST is 'Maquina desde la cual se realiza la transaccion.';
alter table ACF_ACCESORIO_ACTIVO_FIJO
add constraint FK_AF_ACCES_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_ACCESORIO_ACTIVO_FIJO
add constraint FK_AF_ACCES_REFERENCE_AF_FACTU foreign key (ID_FACTURA) references ACF_FACTURA (ID_FACTURA) on delete restrict on update restrict;
alter table ACF_ACTIVO_FIJO
add constraint FK_AF_ACTIV_REFERENCE_AF_AMBIE foreign key (ID_AMBIENTE) references ACF_AMBIENTE (ID_AMBIENTE) on delete restrict on update restrict;
alter table ACF_ACTIVO_FIJO
add constraint FK_AF_ACTIV_REFERENCE_AF_GARAN foreign key (ID_GARANTIA_ACTIVO_FIJO) references ACF_GARANTIA_ACTIVO_FIJO (ID_GARANTIA_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_ACTIVO_FIJO
add constraint FK_AF_ACTIV_REFERENCE_AF_NOTA_ foreign key (ID_NOTA_RECEPCION) references ACF_NOTA_RECEPCION (ID_NOTA_RECEPCION) on delete restrict on update restrict;
alter table ACF_ACTIVO_FIJO
add constraint FK_AF_ACTIV_REFERENCE_AF_FACTU foreign key (ID_FACTURA) references ACF_FACTURA (ID_FACTURA) on delete restrict on update restrict;
alter table ACF_ACTIVO_FIJO
add constraint FK_AF_ACTIV_REFERENCE_AF_SUB_F foreign key (ID_SUB_FAMILIA) references ACF_SUB_FAMILIA_ACTIVO (ID_SUB_FAMILIA) on delete restrict on update restrict;
alter table ACF_ACTIVO_FIJO
add constraint FK_AF_ACTIV_REFERENCE_AF_PROVE foreign key (ID_PROVEEDOR) references ACF_PROVEEDOR (ID_PROVEEDOR) on delete restrict on update restrict;
alter table ACF_ALTA_MATERIAL
add constraint FK_AF_ALTA__REFERENCE_AF_FACTU foreign key (ID_FACTURA) references ACF_FACTURA (ID_FACTURA) on delete restrict on update restrict;
alter table ACF_ALTA_MATERIAL
add constraint FK_AF_ALTA__REFERENCE_AF_ALMAC foreign key (ID_ALMACEN) references ACF_ALMACEN (ID_ALMACEN) on delete restrict on update restrict;
alter table ACF_ALTA_MATERIAL
add constraint FK_AF_ALTA__REFERENCE_AF_PROVE foreign key (ID_PROVEEDOR) references ACF_PROVEEDOR (ID_PROVEEDOR) on delete restrict on update restrict;
alter table ACF_ALTA_MATERIAL_DETALLE
add constraint FK_AF_ALTA__REFERENCE_AF_ALTA_ foreign key (ID_ALTA_MATERIAL) references ACF_ALTA_MATERIAL (ID_ALTA_MATERIAL) on delete restrict on update restrict;
alter table ACF_ALTA_MATERIAL_DETALLE
add constraint FK_AF_ALTA__REFERENCE_AF_REGIS foreign key (ID_REGISTRO_KARDEX_MATERIAL) references ACF_REGISTRO_KARDEX_MATERIAL (ID_REGISTRO_KARDEX_MATERIAL) on delete restrict on update restrict;
alter table ACF_ALTA_MATERIAL_DETALLE
add constraint FK_AF_ALTA__REFERENCE_AF_MATER foreign key (ID_MATERIAL) references ACF_MATERIAL (ID_MATERIAL) on delete restrict on update restrict;
alter table ACF_ATRIBUTO_ACTIVO_FIJO
add constraint FK_AF_ATRIB_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_ATRIBUTO_SUB_FAMILIA
add constraint FK_AF_ATRIB_REFERENCE_AF_SUB_F foreign key (ID_SUB_FAMILIA) references ACF_SUB_FAMILIA_ACTIVO (ID_SUB_FAMILIA) on delete restrict on update restrict;
alter table ACF_BAJA_ACTIVO_FIJO
add constraint FK_AF_BAJA__REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_BAJA_MATERIAL
add constraint FK_AF_BAJA__REFERENCE_AF_KARDE foreign key (ID_KARDEX_MATERIAL) references ACF_KARDEX_MATERIAL (ID_KARDEX_MATERIAL) on delete restrict on update restrict;
alter table ACF_BAJA_MATERIAL
add constraint FK_AF_BAJA__REFERENCE_AF_REGIS foreign key (ID_REGISTRO_KARDEX_MATERIAL) references ACF_REGISTRO_KARDEX_MATERIAL (ID_REGISTRO_KARDEX_MATERIAL) on delete restrict on update restrict;
alter table ACF_COMISION_RECEPCION
add constraint FK_AF_COMIS_REFERENCE_AF_NOTA_ foreign key (ID_NOTA_RECEPCION) references ACF_NOTA_RECEPCION (ID_NOTA_RECEPCION) on delete restrict on update restrict;
alter table ACF_COMPONENTE_ACTIVO_FIJO
add constraint FK_AF_COMPO_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_FAMILIA_ACTIVO
add constraint FK_AF_FAMIL_REFERENCE_AF_CODIG foreign key (ID_CODIGO_CONTABLE) references ACF_CODIGO_CONTABLE (ID_CODIGO_CONTABLE) on delete restrict on update restrict;
alter table ACF_FAMILIA_ACTIVO
add constraint FK_AF_FAMIL_REFERENCE_AF_PARTI foreign key (ID_PARTIDA_PRESUPUESTARIA) references ACF_PARTIDA_PRESUPUESTARIA (ID_PARTIDA_PRESUPUESTARIA) on delete restrict on update restrict;
alter table ACF_IMAGEN_ACTIVO_FIJO
add constraint FK_AF_IMAGE_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_IMAGEN_ACTIVO_FIJO
add constraint FK_AF_IMAGE_REFERENCE_AF_ACCES foreign key (ID_ACCESORIO_ACTIVO_FIJO) references ACF_ACCESORIO_ACTIVO_FIJO (ID_ACCESORIO_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_IMAGEN_ACTIVO_FIJO
add constraint FK_AF_IMAGE_REFERENCE_AF_TRANS foreign key (ID_TRANSFERENCIA_ACTIVO_FIJO) references ACF_TRANSFERENCIA_ASIGNACION (ID_TRANSFERENCIA_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_KARDEX_MATERIAL
add constraint FK_AF_KARDE_REFERENCE_AF_MATER foreign key (ID_MATERIAL) references ACF_MATERIAL (ID_MATERIAL) on delete restrict on update restrict;
alter table ACF_KARDEX_MATERIAL
add constraint FK_AF_KARDE_REFERENCE_AF_ALMAC foreign key (ID_ALMACEN) references ACF_ALMACEN (ID_ALMACEN) on delete restrict on update restrict;
alter table ACF_MATERIAL
add constraint FK_AF_MATER_REFERENCE_AF_PARTI foreign key (ID_PARTIDA_PRESUPUESTARIA) references ACF_PARTIDA_PRESUPUESTARIA (ID_PARTIDA_PRESUPUESTARIA) on delete restrict on update restrict;
alter table ACF_MATERIAL_PROVEEDOR
add constraint FK_AF_MATER_REFERENCE_AF_MATER foreign key (ID_MATERIAL) references ACF_MATERIAL (ID_MATERIAL) on delete restrict on update restrict;
alter table ACF_MATERIAL_PROVEEDOR
add constraint FK_AF_MATER_REFERENCE_AF_PROVE foreign key (ID_PROVEEDOR) references ACF_PROVEEDOR (ID_PROVEEDOR) on delete restrict on update restrict;
alter table ACF_PROVEEDOR_ACT_ECO
add constraint FK_AF_PROVE_REFERENCE_AF_PROVE foreign key (ID_PROVEEDOR) references ACF_PROVEEDOR (ID_PROVEEDOR) on delete restrict on update restrict;
alter table ACF_REGISTRO_KARDEX_MATERIAL
add constraint FK_AF_REGIS_REFERENCE_AF_KARDE foreign key (ID_KARDEX_MATERIAL) references ACF_KARDEX_MATERIAL (ID_KARDEX_MATERIAL) on delete restrict on update restrict;
alter table ACF_REGISTRO_KARDEX_MATERIAL
add constraint FK_AF_REGIS_REFERENCE_AF_SOLIC foreign key (ID_SOLICITUD_MATERIAL) references ACF_SOLICITUD_MATERIAL (ID_SOLICITUD_MATERIAL) on delete restrict on update restrict;
alter table ACF_REVALUO_ACTIVO_FIJO
add constraint FK_AF_REVAL_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_SOLICITUD_ACTIVO_FIJO
add constraint FK_AF_SOLIC_REFERENCE_AF_SOLIC foreign key (ID_SOLICITUD) references ACF_SOLICITUD (ID_SOLICITUD) on delete restrict on update restrict;
alter table ACF_SOLICITUD_ACTIVO_FIJO
add constraint FK_AF_SOLIC_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_SOLICITUD_MATERIAL
add constraint FK_AF_SOLIC_REFERENCE_AF_MATER foreign key (ID_MATERIAL) references ACF_MATERIAL (ID_MATERIAL) on delete restrict on update restrict;
alter table ACF_SOLICITUD_MATERIAL
add constraint FK_AF_SOLIC_REFERENCE_AF_SOLIC foreign key (ID_SOLICITUD) references ACF_SOLICITUD (ID_SOLICITUD) on delete restrict on update restrict;
alter table ACF_SUB_FAMILIA_ACTIVO
add constraint FK_AF_SUB_F_REFERENCE_AF_FAMIL foreign key (ID_FAMILIA_ACTIVO) references ACF_FAMILIA_ACTIVO (ID_FAMILIA_ACTIVO) on delete restrict on update restrict;
alter table ACF_TRANSFERENCIA_ASIGNACION
add constraint FK_AF_TRANS_REFERENCE_AF_NOTA_ foreign key (ID_NOTA_RECEPCION) references ACF_NOTA_RECEPCION (ID_NOTA_RECEPCION) on delete restrict on update restrict;
alter table ACF_TRANSFERENCIA_ASIGNACION
add constraint FK_AF_TRANS_REFERENCE_AF_ACTIV foreign key (ID_ACTIVO_FIJO) references ACF_ACTIVO_FIJO (ID_ACTIVO_FIJO) on delete restrict on update restrict;
alter table ACF_TRANSFERENCIA_ASIGNACION
add constraint FK_AF_TRANS_AF_AMB_ORG foreign key (ID_AMBIENTE_ORIGEN) references ACF_AMBIENTE (ID_AMBIENTE) on delete restrict on update restrict;
alter table ACF_TRANSFERENCIA_ASIGNACION
add constraint FK_AF_TRANs_AF_AMB_DEST foreign key (ID_AMBIENTE_DESTINO) references ACF_AMBIENTE (ID_AMBIENTE) on delete restrict on update restrict;
alter table gen_desctabla
add constraint fk_gen_des_gen_tab foreign key (des_codtab) references gen_tablas(tab_codigo) on delete restrict on update restrict;

create table TX_TRANSDET (
    ID_TRANSDET SERIAL not null,
    ID_TRANSACCION INTEGER,
    ID_CORRELATIVO INTEGER,
    TAB_DETOPERACION INTEGER,
    DETOPERACION INTEGER,        
    TAB_TAREAOPERACION INTEGER,
    TAREAOPERACION INTEGER,    
    TAB_OPERMAYOR INTEGER,
    OPERMAYOR INTEGER,        
    ID_ITEMAF INTEGER,
    GLOSA VARCHAR(150),
    MONTO_ORIG NUMERIC(15,5) DEFAULT 0,
    TAB_MONEDAAMTORIG INTEGER DE,
    MONEDAAMTORIG INTEGER,     
    TIPO_CAMBIO NUMERIC(15,5) DEFAULT 1,   
    TIPO_CARGO INTEGER,
    MONTO NUMERIC(15,5) DEFAULT 0,
    MONTO_DESC NUMERIC(15,5) DEFAULT 0,
    MONTO_CONT NUMERIC(15,5) DEFAULT 0,    
    CANTIDAD NUMERIC(15,5),    
    TAB_UNIDADMED INTEGER,
    UNIDADMED INTEGER,
    TAB_METODOCALC INTEGER,
    METODOCALC INTEGER,    
    FECHA_OPER DATE,
    FECHA_VALOR DATE,
    TAB_TIPOOPERACION INTEGER,
    TIPOOPERACION INTEGER,
    ID_EMPLEADO INTEGER,    
    ID_EMPLEADOAUT INTEGER,        
    ID_UNIDAD INTEGER,
    ID_USRREG VARCHAR(50),
    ID_USRAUT VARCHAR(50),
    ID_TRANSDETPADRE INTEGER,    
    ID_TRXORIGEN INTEGER,
    TX_FECHA TIMESTAMP WITH TIME ZONE not null,
    TX_USUARIO Integer not null,
    TX_HOST VARCHAR(30) not null,
    constraint PK_TX_IDTRANSDET primary key (ID_TRANSDET)
);





drop table if exists tx_area;
drop table if exists tx_persona;
drop table if exists tx_usuario;



create table tx_area (
    id_area serial not null,
    constraint PK_tx_area primary key (id_area)
);
create table tx_persona (
    id_persona serial not null,
    nombre varchar(200),
    nombre_desc varchar(200),
    primer_apellido varchar(100),
    segundo_apellido varchar(100),
    numero_documento varchar(50),
    tipo_documento varchar(20),
    tabtipodoc integer default 108,
    tipodoc integer default 1,
    direccion varchar(200),
    telefono varchar(40),
    email varchar(40),
    tabtipopers integer default 1012,
    tipopers integer default 1,
    nemonico varchar(100),
    tx_fecha date,
    usuario varchar(50),
    estado varchar(10),
    trato varchar(100),
    constraint PK_tx_persona primary key (id_persona)
);
create table tx_usuario (
    id_usuario serial not null,
    usr_login varchar(100) not null,
    usr_nombres varchar(200) not null,
    usr_email varchar(100),
    usr_password varchar(250),
    id_unid_empl integer,
    primary key (id_usuario)
);
GRANT ALL ON TABLE public.acf_accesorio_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_activo_fijo_hist TO activosf;
GRANT ALL ON TABLE public.acf_almacen TO activosf;
GRANT ALL ON TABLE public.acf_alta_material TO activosf;
GRANT ALL ON TABLE public.acf_alta_material_detalle TO activosf;
GRANT ALL ON TABLE public.acf_ambiente TO activosf;
GRANT ALL ON TABLE public.acf_atributo_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_atributo_sub_familia TO activosf;
GRANT ALL ON TABLE public.acf_baja_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_baja_material TO activosf;
GRANT ALL ON TABLE public.acf_codigo_contable TO activosf;
GRANT ALL ON TABLE public.acf_comision_recepcion TO activosf;
GRANT ALL ON TABLE public.acf_componente_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_factura TO activosf;
GRANT ALL ON TABLE public.acf_familia_activo TO activosf;
GRANT ALL ON TABLE public.acf_garantia_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_gestion TO activosf;
GRANT ALL ON TABLE public.acf_imagen_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_inmueble TO activosf;
GRANT ALL ON TABLE public.acf_kardex_material TO activosf;
GRANT ALL ON TABLE public.acf_kardex_material_hist TO activosf;
GRANT ALL ON TABLE public.acf_material TO activosf;
GRANT ALL ON TABLE public.acf_material_proveedor TO activosf;
GRANT ALL ON TABLE public.acf_nota_recepcion TO activosf;
GRANT ALL ON TABLE public.acf_partida_presupuestaria TO activosf;
GRANT ALL ON TABLE public.acf_proveedor TO activosf;
GRANT ALL ON TABLE public.acf_proveedor_act_eco TO activosf;
GRANT ALL ON TABLE public.acf_registro_kardex_material TO activosf;
GRANT ALL ON TABLE public.acf_registro_kardex_material_his TO activosf;
GRANT ALL ON TABLE public.acf_revaluo_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_solicitud TO activosf;
GRANT ALL ON TABLE public.acf_solicitud_activo_fijo TO activosf;
GRANT ALL ON TABLE public.acf_solicitud_material TO activosf;
GRANT ALL ON TABLE public.acf_sub_familia_activo TO activosf;
GRANT ALL ON TABLE public.acf_tipo_cambio TO activosf;
GRANT ALL ON TABLE public.acf_transferencia_asignacion TO activosf;
GRANT ALL ON TABLE public.gen_claves TO activosf;
GRANT ALL ON TABLE public.gen_desctabla TO activosf;
GRANT ALL ON TABLE public.gen_tablas TO activosf;
GRANT ALL ON TABLE public.org_empleado TO activosf;
GRANT ALL ON TABLE public.org_persona TO activosf;
GRANT ALL ON TABLE public.org_unidad TO activosf;
GRANT ALL ON TABLE public.org_unidad_emp TO activosf;
GRANT ALL ON TABLE public.sec_profile TO activosf;
GRANT ALL ON TABLE public.sec_recurso TO activosf;
GRANT ALL ON TABLE public.sec_roles TO activosf;
GRANT ALL ON TABLE public.sec_userrol TO activosf;
GRANT ALL ON TABLE public.sec_usuario TO activosf;
GRANT ALL ON TABLE public.tx_area TO activosf;
GRANT ALL ON TABLE public.tx_persona TO activosf;
GRANT ALL ON TABLE public.tx_transaccion TO activosf;
GRANT ALL ON TABLE public.tx_transdet TO activosf;
GRANT ALL ON TABLE public.tx_usuario TO activosf;
GRANT ALL ON TABLE public.ACF_ITEMAF TO activosf;

GRANT ALL ON SEQUENCE public.acf_itemaf_id_item_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_accesorio_activo_fijo_id_accesorio_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_activo_fijo_hist_id_activo_fijo_hist_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_activo_fijo_id_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_almacen_id_almacen_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_alta_material_detalle_id_alta_material_detalle_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_alta_material_id_alta_material_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_ambiente_id_ambiente_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_atributo_activo_fijo_id_atributo_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_atributo_sub_familia_id_atributo_sub_familia_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_baja_activo_fijo_id_baja_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_baja_material_id_baja_material_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_codigo_contable_id_codigo_contable_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_comision_recepcion_id_comision_recepcion_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_componente_activo_fijo_id_componente_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_factura_id_factura_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_familia_activo_id_familia_activo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_garantia_activo_fijo_id_garantia_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_gestion_id_gestion_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_imagen_activo_fijo_id_imagen_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_inmueble_id_inmueble_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_kardex_material_hist_id_kardex_material_hist_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_kardex_material_id_kardex_material_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_material_id_material_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_material_proveedor_id_material_proveedor_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_nota_recepcion_id_nota_recepcion_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_partida_presupuestaria_id_partida_presupuestaria_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_proveedor_act_eco_id_proveedor_act_eco_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_proveedor_id_proveedor_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_registro_kardex_material__id_registro_kardex_material_h_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_registro_kardex_material_id_registro_kardex_material_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_revaluo_activo_fijo_id_revaluo_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_solicitud_activo_fijo_id_solicitud_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_solicitud_id_solicitud_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_solicitud_material_id_solicitud_material_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_sub_familia_activo_id_sub_familia_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_tipo_cambio_id_tipo_cambio_seq TO activosf;
GRANT ALL ON SEQUENCE public.acf_transferencia_asignacion_id_transferencia_activo_fijo_seq TO activosf;
GRANT ALL ON SEQUENCE public.gen_claves_clv_id_seq TO activosf;
GRANT ALL ON SEQUENCE public.org_empleado_id_empleado_seq TO activosf;
GRANT ALL ON SEQUENCE public.org_persona_id_persona_seq TO activosf;
GRANT ALL ON SEQUENCE public.org_unidad_emp_id_unid_empl_seq TO activosf;
GRANT ALL ON SEQUENCE public.org_unidad_id_unidad_seq TO activosf;
GRANT ALL ON SEQUENCE public.sec_recurso_res_id_seq TO activosf;
GRANT ALL ON SEQUENCE public.sec_roles_rol_id_seq TO activosf;
GRANT ALL ON SEQUENCE public.sec_usuario_usr_id_seq TO activosf;
GRANT ALL ON SEQUENCE public.tx_area_id_area_seq TO activosf;
GRANT ALL ON SEQUENCE public.tx_persona_id_persona_seq TO activosf;
GRANT ALL ON SEQUENCE public.tx_transaccion_id_transaccion_seq TO activosf;
GRANT ALL ON SEQUENCE public.tx_usuario_id_usuario_seq TO activosf;