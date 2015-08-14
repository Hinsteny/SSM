# Create User table
-- SCRIPTS
	CREATE TABLE "user"
	(
	  id bigserial NOT NULL,
	  username character varying(24) NOT NULL,
	  password character varying(64) NOT NULL,
	  ctime timestamp without time zone,
      utime timestamp without time zone,

	  CONSTRAINT "id" PRIMARY KEY (id),
	  CONSTRAINT unq_name UNIQUE (username)
	)
	WITH (
	  OIDS=FALSE
	);
    
-- SCRIPTS