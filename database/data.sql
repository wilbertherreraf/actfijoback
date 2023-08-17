INSERT INTO public.sec_roles( rol_codrol, rol_descrip)	VALUES ('ROL1', 'ROLE ONE');
INSERT INTO public.sec_roles( rol_codrol, rol_descrip)	VALUES ('ROL2', 'ROLE TWO');
INSERT INTO public.sec_roles( rol_codrol, rol_descrip)	VALUES ('ROL3', 'ROLE THREE');

INSERT INTO public.sec_recurso(	res_codrec, res_descrip) VALUES ('REC1', 'RECURSO 1');
INSERT INTO public.sec_recurso(	res_codrec, res_descrip) VALUES ('REC2', 'RECURSO 2');
INSERT INTO public.sec_recurso(	res_codrec, res_descrip) VALUES ('REC3', 'RECURSO 3');


INSERT INTO public.sec_usuario (usr_id, usr_login, usr_nombres, usr_email, usr_codemp, usr_password, usr_tabtipousr, usr_tipousr, usr_tabstatuser, usr_statuser, usr_auditusr, usr_auditfho, usr_auditwst) VALUES (2, 'james@example.com', 'james doe', 'james@example.com', NULL, 'password', 10, 1, 20, 1, NULL, NULL, NULL);
INSERT INTO public.sec_usuario (usr_id, usr_login, usr_nombres, usr_email, usr_codemp, usr_password, usr_tabtipousr, usr_tipousr, usr_tabstatuser, usr_statuser, usr_auditusr, usr_auditfho, usr_auditwst) VALUES (27, 'james1', 'jhon doe', 'james1@example.com', NULL, '$2a$10$4H1pWmxLpq7mxjtLpAd2mujKDkZH/sDukkZLFARi6mMLyBvU0XzYS', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.sec_usuario (usr_id, usr_login, usr_nombres, usr_email, usr_codemp, usr_password, usr_tabtipousr, usr_tipousr, usr_tabstatuser, usr_statuser, usr_auditusr, usr_auditfho, usr_auditwst) VALUES (28, 'asdf', 'asdf asdf', 'asdf@asdf.com', NULL, '$2a$10$/qAeorBfe2GsA8jVJYqZX.v4TU1f0YpPwze7D0RJ75z.Srwf2h5ra', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.sec_usuario (usr_id, usr_login, usr_nombres, usr_email, usr_codemp, usr_password, usr_tabtipousr, usr_tipousr, usr_tabstatuser, usr_statuser, usr_auditusr, usr_auditfho, usr_auditwst) VALUES (29, 'asdf1', 'asdf1 asdf1', 'asdf1@asdf.com', NULL, '$2a$10$mXp962gHIlKNA3Yv8vOg.eKB/OLs44EbvEMWgGMGtqYmAff1OPWQe', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.sec_usuario (usr_id, usr_login, usr_nombres, usr_email, usr_codemp, usr_password, usr_tabtipousr, usr_tipousr, usr_tabstatuser, usr_statuser, usr_auditusr, usr_auditfho, usr_auditwst) VALUES (30, 'asdf2', 'asdf2 asdf2', 'asdf2@asdf.com', NULL, '$2a$10$5KVJMb9LPNGdT.F.WBSTsO5s3QuSAlz0DgzVMeeb7whd3uqZ2vPHq', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO public.sec_profile(	prr_resid, prr_rolid)	VALUES (1,1 );
INSERT INTO public.sec_profile(	prr_resid, prr_rolid)	VALUES (1,2 );
INSERT INTO public.sec_profile(	prr_resid, prr_rolid)	VALUES (2,1 );
INSERT INTO public.sec_profile(	prr_resid, prr_rolid)	VALUES (3,1 );
INSERT INTO public.sec_profile(	prr_resid, prr_rolid)	VALUES (3,2 );
INSERT INTO public.sec_profile(	prr_resid, prr_rolid)	VALUES (3,3 );



INSERT INTO public.sec_userrol(	uro_usrid, uro_rolid)	VALUES (27, 1);
INSERT INTO public.sec_userrol(	uro_usrid, uro_rolid)	VALUES (28, 1);
INSERT INTO public.sec_userrol(	uro_usrid, uro_rolid)	VALUES (28, 2);
INSERT INTO public.sec_userrol(	uro_usrid, uro_rolid)	VALUES (28, 3);
