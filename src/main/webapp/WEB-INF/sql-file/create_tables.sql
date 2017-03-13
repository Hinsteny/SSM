-- Create User table
-- SCRIPTS
	CREATE TABLE "user"
	(
			id bigserial NOT NULL,
			username character varying(24) NOT NULL,
			password character varying(64) NOT NULL,
			ctime timestamp without time zone,
			utime timestamp without time zone,
			noticenum integer NOT NULL DEFAULT 0,

			CONSTRAINT "id" PRIMARY KEY (id),
			CONSTRAINT unq_name UNIQUE (username)
	)
	WITH (
	  	OIDS=FALSE
	);
-- SCRIPTS

-- Create tables for user authorization
-- SCRIPTS
CREATE TABLE Actor
(
	actorId serial PRIMARY KEY,
	name character varying(255) NOT NULL,
	password character varying(255) NOT NULL,
	enabled boolean NOT NULL,
	firstName character varying(255),
	lastName character varying(255),
	email character varying(255),
	created timestamp NOT NULL,
	lastLogin timestamp,

	CONSTRAINT Actor_unq_name UNIQUE (name)
);

CREATE TABLE Role
(
	roleId serial PRIMARY KEY,
	name character varying(255) NOT NULL,
	displayName character varying(255),
	description character varying(255),

	CONSTRAINT Role_unq_name UNIQUE (name)
);

CREATE TABLE Permission
(
	permissionId serial PRIMARY KEY,
	name character varying(255) NOT NULL,
	url character varying(255) DEFAULT NULL,
	description character varying(255),

	CONSTRAINT Permission_unq_name UNIQUE (name)
);

CREATE TABLE ActorRole
(
	actorRoleId serial PRIMARY KEY,
	actorId integer REFERENCES Actor,
	roleId integer REFERENCES Role,

	CONSTRAINT ActorRole_unq_actor_role UNIQUE (actorId, roleId)
);

CREATE TABLE RolePermission
(
	rolePermissionId serial PRIMARY KEY,
	roleId integer REFERENCES Role,
	permissionId integer REFERENCES Permission,

	CONSTRAINT RolePermission_unq_role_permission UNIQUE (roleId, permissionId)
);

-- input data for user a
set SEARCH_PATH to public;
-- SCRIPTS
INSERT INTO public.Actor (name, password, enabled, firstName, lastName, email, created)
VALUES ('Hinsteny','welcome', TRUE, 'Hisoka', 'Hinsteny', 'hinsteny@gmail.com', now());

INSERT INTO public.Role (name, displayName, description)
VALUES ('ROLE_ADMIN','Administrator', 'user could control all things!'), ('ROLE_USER','nurmal_user', 'user could visit most of things!');

INSERT INTO public.Permission (name, url, description)
VALUES ('AdminIndex','/admin', 'Visit admin index page!');

INSERT INTO public.ActorRole (actorId, roleId)
VALUES ('1','1');

INSERT INTO public.RolePermission (roleId, permissionId)
VALUES ('1','1');
-- SCRIPTS
