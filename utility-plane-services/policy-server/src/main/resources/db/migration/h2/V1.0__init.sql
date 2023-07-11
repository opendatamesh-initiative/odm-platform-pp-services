-- H2 2.1.214;
;
CREATE USER IF NOT EXISTS "SA" SALT '99e1bf31400a23ce' HASH 'eed73f4d67b4d242c768dcb5a6b2b120dba3a19a5d789f5312ef568e9faf4c19' ADMIN;

CREATE MEMORY TABLE "PUBLIC"."POLICY"(
    "ID" CHARACTER VARYING(255) NOT NULL,
    "DISPLAY_NAME" CHARACTER VARYING(255),
    "DESCRIPTION" CHARACTER VARYING(1000),
    "RAW_POLICY" CLOB,
    "CREATED_AT" TIMESTAMP,
    "UPDATED_AT" TIMESTAMP
);
ALTER TABLE "PUBLIC"."POLICY" ADD CONSTRAINT "PUBLIC"."POLICY_PKEY" PRIMARY KEY("ID");

CREATE MEMORY TABLE "PUBLIC"."SUITE"(
    "ID" CHARACTER VARYING(255) NOT NULL,
    "DISPLAY_NAME" CHARACTER VARYING(255),
    "DESCRIPTION" CHARACTER VARYING(1000),
    "CREATED_AT" TIMESTAMP,
    "UPDATED_AT" TIMESTAMP
);
ALTER TABLE "PUBLIC"."SUITE" ADD CONSTRAINT "PUBLIC"."SUITE_PKEY" PRIMARY KEY("ID");

CREATE TABLE "PUBLIC"."SUITEENTITY_POLICIES" (
	SUITEENTITY_ID varchar(255) NOT NULL,
	POLICIES varchar(255) NULL
);