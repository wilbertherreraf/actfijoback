/*genericos*/

drop table  if exists gen_claves;
create table gen_claves   (
	clv_id serial not null primary key,
    clv_codclave varchar(20) not null ,
    clv_descrip varchar(200),
    clv_valor varchar(240),
	constraint UK_clvcodclave unique (clv_codclave)
  );

drop table if exists gen_desctabla ;
drop table if exists gen_tablas ;

create table gen_tablas   (
    tab_codigo integer not null,
    tab_descripcion varchar(50) not null ,
    primary key (tab_codigo)  
  );

create table gen_desctabla   (
    des_codtab integer not null ,
    des_codigo integer not null ,
    des_descrip varchar(100) not null ,
    des_codeiso varchar(15),
    des_auditusr varchar(15),
    des_updatedat timestamp,
    des_auditwst varchar(20),
	primary key (des_codtab,des_codigo)  ,
	constraint FK_clvcodclave foreign key (des_codtab) references gen_tablas (tab_codigo)
  );




/* unidades*/
create table gen_participante (
    pte_codpte varchar(40) not null ,
    pte_nombre varchar(100),
    pte_direccion varchar(200),
    pte_telefono varchar(40),
    pte_casilla varchar(40),
    pte_email varchar(40),
    pte_tabtipopte integer         default 1011,
    pte_tipopte integer,
    pte_tabtipopers integer        default 1012,
    pte_tipopers integer,
    pte_tabstatreg integer         default 1008,
    pte_statreg integer         default 1,
    pte_nemonico varchar(20)         default '-',
    pte_auditusr varchar(15),
    pte_auditfho datetime year to second,
    pte_auditwst varchar(20),
    primary key (pte_codpte)  constraint pk_gen_participant
  );

create table gen_personaplaza  (
    ppl_codpte varchar(40) not null ,
    ppl_codplaza varchar(10) not null ,
    ppl_tabstatreg integer         default 1008,
    ppl_statreg integer         default 1,
    ppl_auditusr varchar(15),
    ppl_auditfho datetime year to second,
    ppl_auditwst varchar(20),
    primary key (ppl_codpte,ppl_codplaza)  constraint pk_gen_personaplaz
  );

create table gen_agencia   (
    age_codage varchar(10) not null ,
    age_nombre varchar(100),
    age_codpte varchar(40),
    age_codplaza varchar(10),
    age_urbano_rural varchar(3),
    age_auditusr varchar(15),
    age_auditfho datetime year to second,
    age_auditwst varchar(20),
    primary key (age_codage)  constraint pk_gen_agencia
  );


/*security*/
drop if exists table axs_userrol;
drop table if exists axs_profile;
drop table if exists axs_recurso ;
drop table if exists axs_roles ;
drop table  if exists axs_usuario;

create table axs_usuario   (
    usr_id serial not null ,
    usr_login varchar(100) not null ,
    usr_codemp varchar(50),
    usr_nombres varchar(200) not null ,
    usr_email varchar(100),
    usr_password varchar(250),
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


create table axs_recurso   (
    rap_id serial not null ,
    rap_codrec varchar(50) not null,
    rap_auditusr varchar(15),
    rap_auditfho timestamp,
    rap_auditwst varchar(20),
    primary key (rap_id)  ,
	constraint UK_rapcodrec unique (rap_codrec)	
  ) ;
  

create table axs_roles   (
    apr_id serial not null ,
	apr_codrol varchar(50) not null ,
    apr_descrip varchar(100),
    apr_tabstatreg integer         default 20,
    apr_statreg integer         default 1,
    apr_auditusr varchar(15),
    apr_auditfho timestamp,
    apr_auditwst varchar(20),
	primary key (apr_id)  ,
    constraint UK_aprcodrol unique (apr_codrol)	,
	constraint FK_aprtabstatreg foreign key (apr_tabstatreg,apr_statreg) references gen_desctabla (des_codtab,des_codigo)		
  );

create table axs_profile   (
	rre_id serial not null,
    rap_id integer not null ,
    apr_id integer not null ,
    rre_auditusr varchar(15),
    rre_auditfho timestamp,
    rre_auditwst varchar(20),
    primary key (rre_id),
	constraint UK_rapaprid unique (rap_id,apr_id)	,
	constraint FK_prorapid foreign key (rap_id) references axs_recurso (rap_id),
	constraint FK_proaprid foreign key (apr_id) references axs_roles (apr_id)
  );
  

create table axs_userrol   (
    usr_id integer not null ,
    rap_id integer not null ,
    ura_auditusr varchar(15),
    ura_auditfho timestamp,
    ura_auditwst varchar(20),
    primary key (usr_id,rap_id),
	constraint FK_prousrid foreign key (usr_id) references axs_usuario (usr_id),
	constraint FK_prorapid foreign key (rap_id) references axs_recurso (rap_id)		
	
  );







create index index_12 on axs_usuario (usr_statuser)   using btree  in datos;
create index index_13 on axs_usuario (usr_codemp)     using btree  in datos;
create index index_14 on axs_usuario (usr_tipousr)     using btree  in datos;
create unique index index_5 on axs_usuario     (usr_login) using btree  in datos;
create index index_15 on gen_participante     (pte_tipopte) using btree  in datos;
create index index_16 on gen_participante     (pte_statreg) using btree  in datos;
create index index_17 on gen_participante     (pte_tipopers) using btree  in datos;


alter table axs_claves add constraint (foreign key     (clv_tabtipodato,clv_tipodato) references gen_desctabla      constraint fk_axs_cla_gen_des);
alter table axs_grupo add constraint (foreign key     (grp_tabtipgrupo,grp_tipgrupo) references gen_desctabla      constraint fk_axs_gru_gen_des);
alter table axs_profile add constraint (foreign key     (rre_tabtippermiso,rre_tippermiso) references gen_desctabla      constraint fk_axs_pro_gen_des);
alter table axs_recurso add constraint (foreign key     (rap_tabtiprecur,rap_tiprecur) references gen_desctabla      constraint fk_axs_rec_gen_des);
alter table axs_recurso add constraint (foreign key     (rap_codapp) references axs_aplicacion  constraint     fk_axs_rec_axs_ap1);
alter table axs_roles add constraint (foreign key     (apr_codapp) references axs_aplicacion  constraint     fk_axs_rol_axs_apl);
alter table axs_userrol add constraint (foreign key     (ura_coduser) references axs_usuario  constraint     fk_axs_use_axs_usu);
alter table axs_usuario add constraint (foreign key     (usr_tabstatuser,usr_statuser) references gen_desctabla      constraint fk_axs_usu_gen_des);
alter table axs_usuario add constraint (foreign key     (usr_tabtipousr,usr_tipousr) references gen_desctabla      constraint fk_axs_usu_gen_de1);
alter table axs_usuario add constraint (foreign key     (usr_codage) references gen_agencia  constraint     fk_axs_usu_gen_age);
alter table gen_agencia add constraint (foreign key     (age_codpte,age_codplaza) references gen_personaplaza      constraint fk_gen_age_gen_per);
alter table gen_desctabla add constraint (foreign     key (des_codtab) references gen_tablas  constraint     fk_gen_des_gen_tab);
alter table gen_personaplaza add constraint (foreign     key (ppl_codpte) references gen_participante  constraint     fk_gen_per_gen_par);
alter table gen_personaplaza add constraint (foreign     key (ppl_codplaza) references gen_plaza  constraint     fk_gen_per_gen_pla);
alter table gen_plaza add constraint (foreign key     (plz_tabdepto,plz_depto) references gen_desctabla      constraint fkgen_pla_gen_des1);
alter table gen_plaza add constraint (foreign key     (plz_tabsegmento,plz_segmento) references gen_desctabla      constraint fkgen_pla_gen_des2);
alter table gen_plaza add constraint (foreign key     (plz_tabstatreg,plz_statreg) references gen_desctabla      constraint fkgen_pla_gen_des3);




