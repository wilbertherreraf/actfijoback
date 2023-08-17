create table ses_assertions   (
    ast_codast varchar(50) not null ,
    ast_assertion text,
    ast_assertionid varchar(50),
    ast_sessionindex varchar(100),
    ast_timestamp datetime year to fraction(3),
    ast_login varchar(20),
    ast_address varchar(30),
    primary key (ast_codast)  constraint pk_ses_assertions
  );

create table ses_requestdata  (
    rqd_codreqd varchar(50) not null ,
    rqd_data text,
    rqd_timestamp datetime year to fraction(3),
    primary key (rqd_codreqd)  constraint pk_ses_requestdata
  );

create table ses_requests   (
    req_codreq varchar(50) not null ,
    req_receiver text,
    req_timestamp datetime year to fraction(3),
    primary key (req_codreq)  constraint pk_ses_requests
  );


create table axs_usuario_h   (
    usr_codaudit datetime year to fraction(3) not null ,
    usr_tipoaudit varchar(1),
    usr_coduser integer,
    usr_login varchar(60),
    usr_codemp varchar(20),
    usr_nombres varchar(20),
    usr_apellidos varchar(40),
    usr_email varchar(100),
    usr_ipasignado varchar(100),
    usr_tabtipousr integer,
    usr_tipousr integer,
    usr_tabstatuser integer,
    usr_statuser integer,
    usr_codage varchar(10),
    usr_caduca varchar(1),
    usr_auditusr varchar(15),
    usr_auditfho datetime year to second,
    usr_auditwst varchar(20),
    usr_gerencia varchar(10)
  ) ;


create table axs_roles_h   (
    apr_codaudit datetime year to fraction(3) not null ,
    apr_tipoaudit varchar(1),
    apr_codapp varchar(10),
    apr_codrol varchar(30),
    apr_descrip varchar(100),
    apr_codrolapppar varchar(10),
    apr_tabstatreg integer,
    apr_statreg integer,
    apr_tabtiporol varchar(100),
    apr_tiporol integer,
    apr_auditusr varchar(15),
    apr_auditfho datetime year to second,
    apr_auditwst varchar(20)
  ) ;


create table axs_userrol_h   (
    ura_codaudit datetime year to fraction(3) not null ,
    ura_tipoaudit varchar(1),
    ura_coduser integer,
    ura_codapp varchar(10),
    ura_codrol varchar(30),
    ura_auditusr varchar(15),
    ura_auditfho datetime year to second,
    ura_auditwst varchar(20)
  ) ;


create table ses_assertions_h   (
    ast_codaudit datetime year to fraction(3) not null ,
    ast_tipoaudit varchar(1),
    ast_codast varchar(50),
    ast_assertion text,
    ast_assertionid varchar(50),
    ast_sessionindex varchar(100),
    ast_timestamp datetime year to fraction(3),
    ast_login varchar(20),
    ast_address varchar(30)
  ) ;

create table axs_recurso_h   (
    rap_codaudit datetime year to fraction(3) not null ,
    rap_tipoaudit varchar(1),
    rap_codapp varchar(10),
    rap_codrecur varchar(100),
    rap_codreclit char(300),
    rap_descrip varchar(200),
    rap_tabtiprecur integer,
    rap_tiprecur integer,
    rap_codrecpar varchar(10),
    rap_tabstatreg integer,
    rap_statreg integer,
    rap_permdefault varchar(20),
    rap_auditusr varchar(15),
    rap_auditfho datetime year to second,
    rap_auditwst varchar(20),
    rap_codrecur_padre varchar(20)
  ) ;


create table gen_agencia_h   (
    age_codaudit datetime year to fraction(3) not null ,
    age_tipoaudit varchar(1),
    age_codage varchar(10),
    age_nombre varchar(100),
    age_codpte varchar(40),
    age_codplaza varchar(10),
    age_urbano_rural varchar(3),
    age_auditusr varchar(15),
    age_auditfho datetime year to second,
    age_auditwst varchar(20)
  ) ;


create table gen_participante_h   (
    pte_codaudit datetime year to fraction(3) not null ,
    pte_tipoaudit varchar(1),
    pte_codpte varchar(40),
    pte_nombre varchar(100),
    pte_direccion varchar(200),
    pte_telefono varchar(40),
    pte_casilla varchar(40),
    pte_nit varchar(40),
    pte_email varchar(40),
    pte_tabtipopte integer,
    pte_tipopte integer,
    pte_tabtipopers integer,
    pte_tipopers integer,
    pte_tabciudad integer,
    pte_ciudad integer,
    pte_tabstatreg integer,
    pte_statreg integer,
    pte_auditusr varchar(15),
    pte_auditfho datetime year to second,
    pte_auditwst varchar(20),
    pte_nemonico varchar(20)
  ) ;


create table gen_personaplaza_h   (
    ppl_codaudit datetime year to fraction(3) not null ,
    ppl_tipoaudit varchar(1),
    ppl_codpte varchar(40),
    ppl_codplaza varchar(10),
    ppl_rangoip varchar(50),
    ppl_mascara varchar(20),
    ppl_tabstatreg integer 
        default 1008,
    ppl_statreg integer 
        default 1,
    ppl_auditusr varchar(15),
    ppl_auditfho datetime year to second,
    ppl_auditwst varchar(20)
  ) ;


create table axs_profile_h   (
    rre_codaudit datetime year to fraction(3) not null ,
    rre_tipoaudit varchar(1),
    rre_codapp varchar(10),
    rre_codrecur varchar(100),
    rre_codrol varchar(30),
    rre_permiso varchar(20),
    rre_tabtippermiso integer,
    rre_tippermiso integer,
    rre_auditusr varchar(15),
    rre_auditfho datetime year to second,
    rre_auditwst varchar(20)
  ) ;

create trigger tu_axs_usuario update on axs_usuario 
    referencing old as o_upd new as n_upd
    for each row
        (
        insert into axs_usuario_h (usr_codaudit,usr_tipoaudit,
    usr_coduser,usr_login,usr_codemp,usr_nombres,usr_apellidos,usr_email,
    usr_ipasignado,usr_tabtipousr,usr_tipousr,usr_tabstatuser,usr_statuser,
    usr_codage,usr_caduca,usr_auditusr,usr_auditfho,usr_auditwst,usr_gerencia) 
     values (EXTEND (CURRENT year to fraction(3) ,year to fraction(3)) 
    ,'U' ,o_upd.usr_coduser ,o_upd.usr_login ,o_upd.usr_codemp ,o_upd.usr_nombres 
    ,o_upd.usr_apellidos ,o_upd.usr_email ,o_upd.usr_ipasignado ,o_upd.usr_tabtipousr 
    ,o_upd.usr_tipousr ,o_upd.usr_tabstatuser ,o_upd.usr_statuser ,o_upd.usr_codage 
    ,o_upd.usr_caduca ,o_upd.usr_auditusr ,o_upd.usr_auditfho ,o_upd.usr_auditwst 
    ,o_upd.usr_gerencia ));

create trigger td_axs_usuario delete on axs_usuario 
    referencing old as o_upd
    for each row
        (
        insert into axs_usuario_h (usr_codaudit,usr_tipoaudit,
    usr_coduser,usr_login,usr_codemp,usr_nombres,usr_apellidos,usr_email,
    usr_ipasignado,usr_tabtipousr,usr_tipousr,usr_tabstatuser,usr_statuser,
    usr_codage,usr_caduca,usr_auditusr,usr_auditfho,usr_auditwst,usr_gerencia) 
     values (EXTEND (CURRENT year to fraction(3) ,year to fraction(3)) 
    ,'D' ,o_upd.usr_coduser ,o_upd.usr_login ,o_upd.usr_codemp ,o_upd.usr_nombres 
    ,o_upd.usr_apellidos ,o_upd.usr_email ,o_upd.usr_ipasignado ,o_upd.usr_tabtipousr 
    ,o_upd.usr_tipousr ,o_upd.usr_tabstatuser ,o_upd.usr_statuser ,o_upd.usr_codage 
    ,o_upd.usr_caduca ,o_upd.usr_auditusr ,o_upd.usr_auditfho ,o_upd.usr_auditwst 
    ,o_upd.usr_gerencia ));

create trigger tu_gen_participante update on "d_axesso".gen_participante referencing old as o_upd new as n_upd
    for each row
        (
        insert into gen_participante_h (pte_codaudit,
    pte_tipoaudit,pte_codpte,pte_nombre,pte_direccion,pte_telefono,pte_casilla,
    pte_nit,pte_email,pte_tabtipopte,pte_tipopte,pte_tabtipopers,pte_tipopers,
    pte_tabciudad,pte_ciudad,pte_tabstatreg,pte_statreg,pte_auditusr,
    pte_auditfho,pte_auditwst,pte_nemonico)  values (EXTEND (CURRENT 
    year to fraction(3) ,year to fraction(3)) ,'U' ,o_upd.pte_codpte ,
    o_upd.pte_nombre ,o_upd.pte_direccion ,o_upd.pte_telefono ,o_upd.pte_casilla 
    ,o_upd.pte_nit ,o_upd.pte_email ,o_upd.pte_tabtipopte ,o_upd.pte_tipopte 
    ,o_upd.pte_tabtipopers ,o_upd.pte_tipopers ,o_upd.pte_tabciudad ,
    o_upd.pte_ciudad ,o_upd.pte_tabstatreg ,o_upd.pte_statreg ,o_upd.pte_auditusr 
    ,o_upd.pte_auditfho ,o_upd.pte_auditwst ,o_upd.pte_nemonico ));

create trigger td_gen_participante delete on "d_axesso"
    .gen_participante referencing old as o_upd
    for each row
        (
        insert into gen_participante_h (pte_codaudit,
    pte_tipoaudit,pte_codpte,pte_nombre,pte_direccion,pte_telefono,pte_casilla,
    pte_nit,pte_email,pte_tabtipopte,pte_tipopte,pte_tabtipopers,pte_tipopers,
    pte_tabciudad,pte_ciudad,pte_tabstatreg,pte_statreg,pte_auditusr,
    pte_auditfho,pte_auditwst,pte_nemonico)  values (EXTEND (CURRENT 
    year to fraction(3) ,year to fraction(3)) ,'D' ,o_upd.pte_codpte ,
    o_upd.pte_nombre ,o_upd.pte_direccion ,o_upd.pte_telefono ,o_upd.pte_casilla 
    ,o_upd.pte_nit ,o_upd.pte_email ,o_upd.pte_tabtipopte ,o_upd.pte_tipopte 
    ,o_upd.pte_tabtipopers ,o_upd.pte_tipopers ,o_upd.pte_tabciudad ,
    o_upd.pte_ciudad ,o_upd.pte_tabstatreg ,o_upd.pte_statreg ,o_upd.pte_auditusr 
    ,o_upd.pte_auditfho ,o_upd.pte_auditwst ,o_upd.pte_nemonico ));

create trigger tu_gen_personaplaza update on "d_axesso"
    .gen_personaplaza referencing old as o_upd new as n_upd
    for each row
        (
        insert into gen_personaplaza_h (ppl_codaudit,
    ppl_tipoaudit,ppl_codpte,ppl_codplaza,ppl_rangoip,ppl_mascara,ppl_tabstatreg,
    ppl_statreg,ppl_auditusr,ppl_auditfho,ppl_auditwst)  values (EXTEND 
    (CURRENT year to fraction(3) ,year to fraction(3)) ,'U' ,o_upd.ppl_codpte 
    ,o_upd.ppl_codplaza ,o_upd.ppl_rangoip ,o_upd.ppl_mascara ,o_upd.ppl_tabstatreg 
    ,o_upd.ppl_statreg ,o_upd.ppl_auditusr ,o_upd.ppl_auditfho ,o_upd.ppl_auditwst 
    ));

create trigger td_gen_personaplaza delete on "d_axesso"
    .gen_personaplaza referencing old as o_upd
    for each row
        (
        insert into gen_personaplaza_h (ppl_codaudit,
    ppl_tipoaudit,ppl_codpte,ppl_codplaza,ppl_rangoip,ppl_mascara,ppl_tabstatreg,
    ppl_statreg,ppl_auditusr,ppl_auditfho,ppl_auditwst)  values (EXTEND 
    (CURRENT year to fraction(3) ,year to fraction(3)) ,'D' ,o_upd.ppl_codpte 
    ,o_upd.ppl_codplaza ,o_upd.ppl_rangoip ,o_upd.ppl_mascara ,o_upd.ppl_tabstatreg 
    ,o_upd.ppl_statreg ,o_upd.ppl_auditusr ,o_upd.ppl_auditfho ,o_upd.ppl_auditwst 
    ));

create trigger tu_gen_agencia update on gen_agencia 
    referencing old as o_upd new as n_upd
    for each row
        (
        insert into gen_agencia_h (age_codaudit,age_tipoaudit,
    age_codage,age_nombre,age_codpte,age_codplaza,age_urbano_rural,age_auditusr,
    age_auditfho,age_auditwst)  values (EXTEND (CURRENT year to fraction(3) 
    ,year to fraction(3)) ,'U' ,o_upd.age_codage ,o_upd.age_nombre ,o_upd.age_codpte 
    ,o_upd.age_codplaza ,o_upd.age_urbano_rural ,o_upd.age_auditusr ,
    o_upd.age_auditfho ,o_upd.age_auditwst ));

create trigger td_gen_agencia delete on gen_agencia 
    referencing old as o_upd
    for each row
        (
        insert into gen_agencia_h (age_codaudit,age_tipoaudit,
    age_codage,age_nombre,age_codpte,age_codplaza,age_urbano_rural,age_auditusr,
    age_auditfho,age_auditwst)  values (EXTEND (CURRENT year to fraction(3) 
    ,year to fraction(3)) ,'D' ,o_upd.age_codage ,o_upd.age_nombre ,o_upd.age_codpte 
    ,o_upd.age_codplaza ,o_upd.age_urbano_rural ,o_upd.age_auditusr ,
    o_upd.age_auditfho ,o_upd.age_auditwst ));

create trigger tu_axs_recurso update on axs_recurso 
    referencing old as o_upd new as n_upd
    for each row
        (
        insert into axs_recurso_h (rap_codaudit,rap_tipoaudit,
    rap_codapp,rap_codrecur,rap_codreclit,rap_descrip,rap_tabtiprecur,
    rap_tiprecur,rap_codrecpar,rap_tabstatreg,rap_statreg,rap_permdefault,
    rap_auditusr,rap_auditfho,rap_auditwst,rap_codrecur_padre)  values 
    (EXTEND (CURRENT year to fraction(3) ,year to fraction(3)) ,'U' ,o_upd.rap_codapp 
    ,o_upd.rap_codrecur ,o_upd.rap_codreclit ,o_upd.rap_descrip ,o_upd.rap_tabtiprecur 
    ,o_upd.rap_tiprecur ,o_upd.rap_codrecpar ,o_upd.rap_tabstatreg ,o_upd.rap_statreg 
    ,o_upd.rap_permdefault ,o_upd.rap_auditusr ,o_upd.rap_auditfho ,o_upd.rap_auditwst 
    ,o_upd.rap_codrecur_padre ));

create trigger td_axs_recurso delete on axs_recurso 
    referencing old as o_upd
    for each row
        (
        insert into axs_recurso_h (rap_codaudit,rap_tipoaudit,
    rap_codapp,rap_codrecur,rap_codreclit,rap_descrip,rap_tabtiprecur,
    rap_tiprecur,rap_codrecpar,rap_tabstatreg,rap_statreg,rap_permdefault,
    rap_auditusr,rap_auditfho,rap_auditwst,rap_codrecur_padre)  values 
    (EXTEND (CURRENT year to fraction(3) ,year to fraction(3)) ,'D' ,o_upd.rap_codapp 
    ,o_upd.rap_codrecur ,o_upd.rap_codreclit ,o_upd.rap_descrip ,o_upd.rap_tabtiprecur 
    ,o_upd.rap_tiprecur ,o_upd.rap_codrecpar ,o_upd.rap_tabstatreg ,o_upd.rap_statreg 
    ,o_upd.rap_permdefault ,o_upd.rap_auditusr ,o_upd.rap_auditfho ,o_upd.rap_auditwst 
    ,o_upd.rap_codrecur_padre ));

create trigger tu_axs_roles update on axs_roles 
    referencing old as o_upd new as n_upd
    for each row
        (
        insert into axs_roles_h (apr_codaudit,apr_tipoaudit,
    apr_codapp,apr_codrol,apr_descrip,apr_codrolapppar,apr_tabstatreg,
    apr_statreg,apr_tabtiporol,apr_tiporol,apr_auditusr,apr_auditfho,
    apr_auditwst)  values (EXTEND (CURRENT year to fraction(3) ,year 
    to fraction(3)) ,'U' ,o_upd.apr_codapp ,o_upd.apr_codrol ,o_upd.apr_descrip 
    ,o_upd.apr_codrolapppar ,o_upd.apr_tabstatreg ,o_upd.apr_statreg 
    ,o_upd.apr_tabtiporol ,o_upd.apr_tiporol ,o_upd.apr_auditusr ,o_upd.apr_auditfho 
    ,o_upd.apr_auditwst ));

create trigger td_axs_roles delete on axs_roles 
    referencing old as o_upd
    for each row
        (
        insert into axs_roles_h (apr_codaudit,apr_tipoaudit,
    apr_codapp,apr_codrol,apr_descrip,apr_codrolapppar,apr_tabstatreg,
    apr_statreg,apr_tabtiporol,apr_tiporol,apr_auditusr,apr_auditfho,
    apr_auditwst)  values (EXTEND (CURRENT year to fraction(3) ,year 
    to fraction(3)) ,'D' ,o_upd.apr_codapp ,o_upd.apr_codrol ,o_upd.apr_descrip 
    ,o_upd.apr_codrolapppar ,o_upd.apr_tabstatreg ,o_upd.apr_statreg 
    ,o_upd.apr_tabtiporol ,o_upd.apr_tiporol ,o_upd.apr_auditusr ,o_upd.apr_auditfho 
    ,o_upd.apr_auditwst ));

create trigger tu_axs_profile update on axs_profile 
    referencing old as o_upd new as n_upd
    for each row
        (
        insert into axs_profile_h (rre_codaudit,rre_tipoaudit,
    rre_codapp,rre_codrecur,rre_codrol,rre_permiso,rre_tabtippermiso,
    rre_tippermiso,rre_auditusr,rre_auditfho,rre_auditwst)  values (EXTEND 
    (CURRENT year to fraction(3) ,year to fraction(3)) ,'U' ,o_upd.rre_codapp 
    ,o_upd.rre_codrecur ,o_upd.rre_codrol ,o_upd.rre_permiso ,o_upd.rre_tabtippermiso 
    ,o_upd.rre_tippermiso ,o_upd.rre_auditusr ,o_upd.rre_auditfho ,o_upd.rre_auditwst 
    ));

create trigger td_axs_profile delete on axs_profile 
    referencing old as o_upd
    for each row
        (
        insert into axs_profile_h (rre_codaudit,rre_tipoaudit,
    rre_codapp,rre_codrecur,rre_codrol,rre_permiso,rre_tabtippermiso,
    rre_tippermiso,rre_auditusr,rre_auditfho,rre_auditwst)  values (EXTEND 
    (CURRENT year to fraction(3) ,year to fraction(3)) ,'D' ,o_upd.rre_codapp 
    ,o_upd.rre_codrecur ,o_upd.rre_codrol ,o_upd.rre_permiso ,o_upd.rre_tabtippermiso 
    ,o_upd.rre_tippermiso ,o_upd.rre_auditusr ,o_upd.rre_auditfho ,o_upd.rre_auditwst 
    ));

create trigger tu_axs_userrol update on axs_userrol 
    referencing old as o_upd new as n_upd
    for each row
        (
        insert into axs_userrol_h (ura_codaudit,ura_tipoaudit,
    ura_coduser,ura_codapp,ura_codrol,ura_auditusr,ura_auditfho,ura_auditwst) 
     values (EXTEND (CURRENT year to fraction(3) ,year to fraction(3)) 
    ,'U' ,o_upd.ura_coduser ,o_upd.ura_codapp ,o_upd.ura_codrol ,o_upd.ura_auditusr 
    ,o_upd.ura_auditfho ,o_upd.ura_auditwst ));

create trigger td_axs_userrol delete on axs_userrol 
    referencing old as o_upd
    for each row
        (
        insert into axs_userrol_h (ura_codaudit,ura_tipoaudit,
    ura_coduser,ura_codapp,ura_codrol,ura_auditusr,ura_auditfho,ura_auditwst) 
     values (EXTEND (CURRENT year to fraction(3) ,year to fraction(3)) 
    ,'D' ,o_upd.ura_coduser ,o_upd.ura_codapp ,o_upd.ura_codrol ,o_upd.ura_auditusr 
    ,o_upd.ura_auditfho ,o_upd.ura_auditwst ));




create table tx_cargo (
id_cargo serial not null,

constraint PK_tx_cargo primary key (id_cargo)
);

create table tx_area (
id_area serial not null,
constraint PK_tx_area primary key (id_area)
);


create table tx_persona (
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
constraint PK_tx_persona primary key (id_persona)
);

create table tx_transaccion(
id_transaccion serial not null,
tx_fecha timestamp with time zone NOT NULL,
    tx_usuario integer NOT NULL,
    tx_host character varying(30) ,	
constraint PK_tx_transaccion primary key (id_transaccion)
);	


create table tx_usuario   (
    id_usuario serial not null ,
    usr_login varchar(100) not null ,
    usr_nombres varchar(200) not null ,
    usr_email varchar(100),
    usr_password varchar(250),
    id_unid_empl integer,	
    primary key (id_usuario)
  ) ;
  
create table tx_usuario_rol   (
    id_usuario_rol serial not null ,  
primary key (id_usuario_rol)
  ) ;  

GRANT ALL ON TABLE public.tx_area TO activosf;

GRANT ALL ON TABLE public.tx_cargo TO activosf;

GRANT ALL ON TABLE public.tx_persona TO activosf;

GRANT ALL ON TABLE public.tx_transaccion TO activosf;

GRANT ALL ON TABLE public.tx_usuario TO activosf;

GRANT ALL ON TABLE public.tx_usuario_rol TO activosf;  