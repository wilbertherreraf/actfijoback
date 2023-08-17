drop table  if exists org_unidad_emp;
drop table  if exists org_persona;
drop table  if exists org_empleado;
drop table  if exists org_unidad;
drop table if exists sec_userrol;
drop table if exists sec_profile;
drop table if exists sec_recurso ;
drop table if exists sec_roles ;
drop table  if exists sec_usuario;

create table org_unidad (
id_unidad serial not null,
nombre varchar(200),
sigla varchar(50),
telefono varchar(50),
tipo_unidad varchar(50),
id_unidad_padre integer,
estado varchar(10),
constraint PK_orgunidad primary key (id_unidad)
);

create table org_persona (
id_persona serial not null,
nombre  varchar(200),
nombre_desc varchar( 200),
primer_apellido    varchar( 100),
segundo_apellido    varchar( 100),
numero_documento     varchar( 50),
tipo_documento     varchar( 20),
tabtipodoc integer        default 108,
tipodoc integer default 1,	
direccion varchar(200),
telefono varchar(40),
email varchar(40),
tabtipopers integer        default 1012,
tipopers integer default 1,
nemonico varchar(100),
tx_fecha date,
usuario    varchar( 50),
estado    varchar( 10),
trato    varchar( 100),
constraint PK_orgpersona primary key (id_persona)
);

create table org_empleado (
id_empleado serial not null,
cod_internoempl varchar(100),
id_persona integer,
id_cargo integer,
fecha_ingreso date,
fecha_baja date,
estado    varchar( 10),
constraint PK_orgempleado primary key (id_empleado)
);

create table org_unidad_emp (
id_unid_empl serial not null,
id_unidad integer,
id_emplresponsable integer,
id_emplsubordinado integer,
fecha_reg date,
estado varchar(10),
constraint PK_org_unidad_emp primary key (id_unid_empl)
);


create table sec_usuario   (
    usr_id serial not null ,
    usr_login varchar(100) not null ,
    usr_nombres varchar(200) not null ,
    usr_email varchar(100),
    usr_password varchar(250),
    id_unid_empl integer,	
    usr_tabtipousr integer         default 10,
    usr_tipousr integer         default 1,
    usr_tabstatuser integer         default 20,
    usr_statuser integer         default 1,
    usr_auditusr varchar(15),
    usr_auditfho timestamp,
    usr_auditwst varchar(20),
    primary key (usr_id),
	constraint UK_usrlogin unique (usr_login),
	constraint UK_usremail unique (usr_email),
	constraint FK_usrtabtipousr foreign key (usr_tabtipousr,usr_tipousr) references gen_desctabla (des_codtab,des_codigo),
	constraint FK_usrtabstatuser foreign key (usr_tabstatuser,usr_statuser) references gen_desctabla (des_codtab,des_codigo)	
  ) ;

create table sec_recurso   (
    res_id serial not null ,
    res_codrec varchar(50) not null,
    res_descrip varchar(150) not null,	
    primary key (res_id)  ,
	constraint UK_rapcodrec unique (res_codrec)	
  ) ;
  

create table sec_roles   (
    rol_id serial not null ,
	rol_codrol varchar(50) not null ,
    rol_descrip varchar(100),
    rol_tabstatreg integer         default 20,
    rol_statreg integer         default 1,
    rol_auditusr varchar(15),
    rol_auditfho timestamp,
    rol_auditwst varchar(20),
	primary key (rol_id) ,
    constraint UK_aprcodrol unique (rol_codrol)	,
	constraint FK_aprtabstatreg foreign key (rol_tabstatreg,rol_statreg) references gen_desctabla (des_codtab,des_codigo)		
  );

create table sec_profile   (
    prr_resid integer not null ,
    prr_rolid integer not null ,
    prr_auditusr varchar(15),
    prr_auditfho timestamp,
    prr_auditwst varchar(20),
    primary key (prr_resid,prr_rolid),
	constraint FK_prorapid foreign key (prr_resid) references sec_recurso (res_id),
	constraint FK_proaprid foreign key (prr_rolid) references sec_roles (rol_id)
  );
  
create table sec_userrol   (
    uro_usrid integer not null ,
    uro_rolid integer not null ,
    uro_auditusr varchar(15),
    uro_auditfho timestamp,
    uro_auditwst varchar(20),
    primary key (uro_usrid,uro_rolid),
	constraint FK_urousrid foreign key (uro_usrid) references sec_usuario (usr_id),
	constraint FK_urorolid foreign key (uro_rolid) references sec_roles (rol_id)		
	
  );



GRANT ALL ON TABLE public.gen_claves TO activosf;

GRANT ALL ON TABLE public.gen_desctabla TO activosf;

GRANT ALL ON TABLE public.gen_tablas TO activosf;

GRANT ALL ON TABLE public.sec_profile TO activosf;

GRANT ALL ON TABLE public.sec_recurso TO activosf;

GRANT ALL ON TABLE public.sec_roles TO activosf;

GRANT ALL ON TABLE public.sec_userrol TO activosf;

GRANT ALL ON TABLE public.sec_usuario TO activosf;


GRANT ALL ON SEQUENCE public.gen_claves_clv_id_seq TO activosf;

GRANT ALL ON SEQUENCE public.sec_profile_prr_id_seq TO activosf;

GRANT ALL ON SEQUENCE public.sec_recurso_res_id_seq TO activosf;

GRANT ALL ON SEQUENCE public.sec_roles_rol_id_seq TO activosf;

GRANT ALL ON SEQUENCE public.sec_userrol_uro_id_seq TO activosf;

GRANT ALL ON SEQUENCE public.sec_usuario_usr_id_seq TO activosf;