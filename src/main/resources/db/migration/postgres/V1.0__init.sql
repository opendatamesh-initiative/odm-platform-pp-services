-- Postgres 11;

CREATE SCHEMA IF NOT EXISTS "PUBLIC";
CREATE SEQUENCE "PUBLIC".HIBERNATE_SEQUENCE START WITH 1;

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENT_BUILD_INFOS"(
    "ID" BIGINT PRIMARY KEY,
    "CONFIGURATIONS" VARCHAR(5000),
    "SERVICE_ID" BIGINT,
    "TEMPLATE_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENT_DEPENDENCIES"(
    "ID"  VARCHAR(255) NOT NULL,
    "DEPENDENCE_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENT_DEPLOY_INFOS"(
    "ID" BIGINT PRIMARY KEY,
    "CONFIGURATIONS"  VARCHAR(5000),
    "SERVICE_ID" BIGINT,
    "TEMPLATE_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENT_SINKS"(
    "ID"  VARCHAR(255) NOT NULL,
    "SINK_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENT_SOURCES"(
    "ID"  VARCHAR(255) NOT NULL,
    "SOURCE_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENT_TAGS"(
    "ID"  VARCHAR(255) NOT NULL,
    "TAG_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_APP_COMPONENTS"(
    "ID"  VARCHAR(255) PRIMARY KEY,
    "COMPONENT_GROUP"  VARCHAR(255),
    "CREATED_AT" TIMESTAMP,
    "DESCRIPTION"  VARCHAR(255),
    "DISPLAY_NAME"  VARCHAR(255),
    "ENTITY_TYPE"  VARCHAR(255),
    "FQN"  VARCHAR(255),
    "NAME"  VARCHAR(255),
    "CONTENT"  VARCHAR,
    "UPDATED_AT" TIMESTAMP,
    "VERSION"  VARCHAR(255),
    "APPLICATION_TYPE"  VARCHAR(255),
    "PLATFORM"  VARCHAR(255),
    "BUILD_INFO_ID" BIGINT,
    "DEPLOY_INFO_ID" BIGINT,
    "EXTERNAL_DOC_ID" BIGINT,
    "DATA_PRODUCT_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_DATA_PRODUCT_TAGS"(
    "DATAPRODUCT_ID"  VARCHAR(255) NOT NULL,
    "VERSION"  VARCHAR(255) NOT NULL,
    "TAG_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS"(
    "DATA_PRODUCT_ID"  VARCHAR(255) NOT NULL,
    "VERSION_NUMBER"  VARCHAR(255) NOT NULL,
    "CREATED_AT" TIMESTAMP,
    "DPDS_VERSION"  VARCHAR(255),
    "DESCRIPTION"  VARCHAR(255),
    "DISPLAY_NAME"  VARCHAR(255),
    "DOMAIN"  VARCHAR(255),
    "ENTITY_TYPE"  VARCHAR(255),
    "FQN"  VARCHAR(255),
    "NAME"  VARCHAR(255),
    "CONTENT"  VARCHAR,
    "UPDATED_AT" TIMESTAMP,
    "EXTERNAL_DOC_ID" BIGINT,
    "OWNER_ID"  VARCHAR(255) NOT NULL
);
ALTER TABLE "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS" ADD CONSTRAINT "DPDS_DATA_PRODUCT_VERSIONS_PK" PRIMARY KEY ("DATA_PRODUCT_ID", "VERSION_NUMBER");

CREATE TABLE "PUBLIC"."DPDS_DATA_PRODUCTS"(
    "ID"  VARCHAR(255) PRIMARY KEY,
    "DESCRIPTION"  VARCHAR(255),
    "DOMAIN"  VARCHAR(255),
    "FQN"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_EXTERNAL_RESOURCES"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "DESCRIPTION"  VARCHAR(255),
    "HREF"  VARCHAR(255),
    "MEDIA_TYPE"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_INFO_CONTACT_POINTS"(
    "ID"  VARCHAR(255) NOT NULL,
    "VERSION"  VARCHAR(255) NOT NULL,
    "ADDRESS"  VARCHAR(255),
    "CHANNEL"  VARCHAR(255),
    "DESCRIPTION"  VARCHAR(255),
    "NAME"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_INFO_OWNERS"(
    "ID"  VARCHAR(255) PRIMARY KEY,
    "NAME"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_INFRA_COMPONENT_DEPENDENCIES"(
    "ID"  VARCHAR(255) NOT NULL,
    "DEPENDENCE_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_INFRA_COMPONENT_TAGS"(
    "ID"  VARCHAR(255) NOT NULL,
    "TAG_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_INFRA_COMPONENTS"(
    "ID"  VARCHAR(255) PRIMARY KEY,
    "COMPONENT_GROUP"  VARCHAR(255),
    "CREATED_AT" TIMESTAMP,
    "DESCRIPTION"  VARCHAR(255),
    "DISPLAY_NAME"  VARCHAR(255),
    "ENTITY_TYPE"  VARCHAR(255),
    "FQN"  VARCHAR(255),
    "NAME"  VARCHAR(255),
    "CONTENT"  VARCHAR,
    "UPDATED_AT" TIMESTAMP,
    "VERSION"  VARCHAR(255),
    "INFRASTRUCTURE_TYPE"  VARCHAR(255),
    "PLATFORM"  VARCHAR(255),
    "EXTERNAL_DOC_ID" BIGINT,
    "PROVISION_INFO_ID" BIGINT,
    "DATA_PRODUCT_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_INFRA_PROVISION_INFOS"(
    "ID" BIGINT PRIMARY KEY,
    "CONFIGURATIONS"  VARCHAR(5000),
    "SERVICE_ID" BIGINT,
    "TEMPLATE_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_PORT_CONTRACTS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "BILLING_POLICY_ID" BIGINT,
    "SLA_ID" BIGINT,
    "TERMS_AND_CONDITIONS_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_PORT_EXPECTATIONS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "AUDIENCE_ID" BIGINT,
    "USAGE_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_PORT_PROMISES"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "API_ID" BIGINT,
    "PLATFORM"  VARCHAR(255),
    "SERVICE_TYPE"  VARCHAR(255),
    "DEPRECTAION_POLICY_ID" BIGINT,
    "SLO_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_PORT_TAGS"(
    "ID"  VARCHAR(255) NOT NULL,
    "TAG_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_PORTS"(
    "ID"  VARCHAR(255) PRIMARY KEY,
    "COMPONENT_GROUP"  VARCHAR(255),
    "CREATED_AT" TIMESTAMP,
    "DESCRIPTION"  VARCHAR(255),
    "DISPLAY_NAME"  VARCHAR(255),
    "ENTITY_TYPE"  VARCHAR(255),
    "FQN"  VARCHAR(255),
    "NAME"  VARCHAR(255),
    "CONTENT"  VARCHAR,
    "UPDATED_AT" TIMESTAMP,
    "VERSION"  VARCHAR(255),
    "CONTRACTS_ID" BIGINT,
    "EXPECTATIONS_ID" BIGINT,
    "EXTERNAL_DOC_ID" BIGINT,
    "PROMISES_ID" BIGINT,
    "DATA_PRODUCT_ID"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_REFERENCE_OBJECTS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "DESCRIPTION"  VARCHAR(255),
    "ORIGINAL_REF"  VARCHAR(255),
    "CONTENT"  VARCHAR,
    "REF"  VARCHAR(255)
);

CREATE TABLE "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "DESCRIPTION"  VARCHAR(255),
    "EXTERNAL_DOC_ID" BIGINT
);

CREATE TABLE "PUBLIC"."DPDS_STANDARD_DEFINITIONS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "DESCRIPTION"  VARCHAR(255),
    "SPECIFICATION"  VARCHAR(255),
    "VERSION"  VARCHAR(255),
    "DEFINITION_ID" BIGINT,
    "EXTERNAL_DOC_ID" BIGINT
);

CREATE TABLE "PUBLIC"."LOAD"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
    "CREATED_DATE" TIMESTAMP,
    "ID_DATAPRODUCT"  VARCHAR(255) NOT NULL,
    "LASTUPDATE_DATE" TIMESTAMP,
    "ID_METASERVICE"  VARCHAR(255),
    "STATUS"  VARCHAR(255)
);

ALTER TABLE "PUBLIC"."DPDS_PORT_EXPECTATIONS" ADD CONSTRAINT "FK6OUWT8NGHLS5VGDJMJGNHW76T" FOREIGN KEY("AUDIENCE_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORT_PROMISES" ADD CONSTRAINT "FKWP3UH6EAAQGP6G9L2E54J8S3" FOREIGN KEY("SLO_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS" ADD CONSTRAINT "FK4344ULK9IEKXOR10VWQJATW9T" FOREIGN KEY("OWNER_ID") REFERENCES "PUBLIC"."DPDS_INFO_OWNERS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_DEPENDENCIES" ADD CONSTRAINT "FKE47HEH33IBQL9V2H5LAWHD383" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_APP_COMPONENTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_COMPONENTS" ADD CONSTRAINT "FKFHECTI4D28IF0JYW6CWSXAP2S" FOREIGN KEY("DATA_PRODUCT_ID", "VERSION") REFERENCES "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS"("DATA_PRODUCT_ID", "VERSION_NUMBER");
ALTER TABLE "PUBLIC"."DPDS_PORT_PROMISES" ADD CONSTRAINT "FKTDT2R73RTCJ1SN6GIIQY3G3RP" FOREIGN KEY("DEPRECTAION_POLICY_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS" ADD CONSTRAINT "FK9FJQQ2L7NR7TAXI81TU5J4LBW" FOREIGN KEY("EXTERNAL_DOC_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORT_EXPECTATIONS" ADD CONSTRAINT "FK47C01XXKCY2X9N9QOY3EK0JLD" FOREIGN KEY("USAGE_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFO_CONTACT_POINTS" ADD CONSTRAINT "FKD2XV40J6QLVI1YB7CW7M0VXAN" FOREIGN KEY("ID", "VERSION") REFERENCES "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS"("DATA_PRODUCT_ID", "VERSION_NUMBER");
ALTER TABLE "PUBLIC"."DPDS_PORTS" ADD CONSTRAINT "FKDW3KFLV5SXMUSKX8QFH6J2KQ8" FOREIGN KEY("EXTERNAL_DOC_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_SINKS" ADD CONSTRAINT "FKQE422CLVRRG8J0GXLDRV4IW6V" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_APP_COMPONENTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENTS" ADD CONSTRAINT "FK9N5IG7RIGDP0ECOVUFKSMPWN8" FOREIGN KEY("DATA_PRODUCT_ID", "VERSION") REFERENCES "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS"("DATA_PRODUCT_ID", "VERSION_NUMBER");
ALTER TABLE "PUBLIC"."DPDS_STANDARD_DEFINITIONS" ADD CONSTRAINT "FKN28C6VYQCY0S460D5WB2VO4R0" FOREIGN KEY("EXTERNAL_DOC_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORT_TAGS" ADD CONSTRAINT "FKSXD77OQ8WIO381WHOJWICLXLG" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_PORTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_BUILD_INFOS" ADD CONSTRAINT "FKA97KW9AILVD4E74LC7TJXNDGL" FOREIGN KEY("SERVICE_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_PROVISION_INFOS" ADD CONSTRAINT "FKM7I22E0X3P84YSYUC0525LGC0" FOREIGN KEY("TEMPLATE_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_TAGS" ADD CONSTRAINT "FKSQ1G1IFWKXGV440A0HOKR1LEU" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_APP_COMPONENTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORT_CONTRACTS" ADD CONSTRAINT "FK9DAAXC2S6VM3M6G30EV1F0U8S" FOREIGN KEY("SLA_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_BUILD_INFOS" ADD CONSTRAINT "FKLU60VQDNDN350990MQ19DAAIF" FOREIGN KEY("TEMPLATE_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_SOURCES" ADD CONSTRAINT "FKR33DO8116KIGTE5LYMDB8I1TP" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_APP_COMPONENTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENTS" ADD CONSTRAINT "FK5AGOLVXH41JDNFJXC6AK0G093" FOREIGN KEY("DEPLOY_INFO_ID") REFERENCES "PUBLIC"."DPDS_APP_COMPONENT_DEPLOY_INFOS"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_PROVISION_INFOS" ADD CONSTRAINT "FKOEXPXBDNEG1J0K1252FLJGR0D" FOREIGN KEY("SERVICE_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORTS" ADD CONSTRAINT "FK2SKEW7RDMXDMNXQW1O3RRD7NA" FOREIGN KEY("EXPECTATIONS_ID") REFERENCES "PUBLIC"."DPDS_PORT_EXPECTATIONS"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORTS" ADD CONSTRAINT "FKIH1DEC2JNXAPG185SX15N334I" FOREIGN KEY("PROMISES_ID") REFERENCES "PUBLIC"."DPDS_PORT_PROMISES"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORTS" ADD CONSTRAINT "FKD9WUAB4HK5HFJ12BP2BDRDENU" FOREIGN KEY("CONTRACTS_ID") REFERENCES "PUBLIC"."DPDS_PORT_CONTRACTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENTS" ADD CONSTRAINT "FK9TN586206T7S4T5OKL62WLKSO" FOREIGN KEY("BUILD_INFO_ID") REFERENCES "PUBLIC"."DPDS_APP_COMPONENT_BUILD_INFOS"("ID");
ALTER TABLE "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS" ADD CONSTRAINT "FK9JJ2ABEBWMCK10ROY0KXPPSL1" FOREIGN KEY("EXTERNAL_DOC_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_STANDARD_DEFINITIONS" ADD CONSTRAINT "FKRKJVHRTXR6P31WQBO399FCE26" FOREIGN KEY("DEFINITION_ID") REFERENCES "PUBLIC"."DPDS_REFERENCE_OBJECTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_DATA_PRODUCT_TAGS" ADD CONSTRAINT "FKGVU4XT9F1EQVYKA7VQYJSJ3W" FOREIGN KEY("DATAPRODUCT_ID", "VERSION") REFERENCES "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS"("DATA_PRODUCT_ID", "VERSION_NUMBER");
ALTER TABLE "PUBLIC"."DPDS_PORT_CONTRACTS" ADD CONSTRAINT "FKR2XMY5Q0CTMMQ301KNVHVFQH8" FOREIGN KEY("BILLING_POLICY_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_COMPONENTS" ADD CONSTRAINT "FKADA9AOQKR3QLRO8J1YDTC90LI" FOREIGN KEY("EXTERNAL_DOC_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_COMPONENT_DEPENDENCIES" ADD CONSTRAINT "FK5UVN1900YW1SL0O5S6WX2W1VM" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_INFRA_COMPONENTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_PORTS" ADD CONSTRAINT "FK42A4V80S6UAXLUNO9ASDEYQ02" FOREIGN KEY("DATA_PRODUCT_ID", "VERSION") REFERENCES "PUBLIC"."DPDS_DATA_PRODUCT_VERSIONS"("DATA_PRODUCT_ID", "VERSION_NUMBER");
ALTER TABLE "PUBLIC"."DPDS_PORT_CONTRACTS" ADD CONSTRAINT "FK48OOYF319R9RSVKK9FRW4X7GG" FOREIGN KEY("TERMS_AND_CONDITIONS_ID") REFERENCES "PUBLIC"."DPDS_SPEC_EXTENSION_POINTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_DEPLOY_INFOS" ADD CONSTRAINT "FKTPC0GAMBVQKQK8E3DKRW8R24A" FOREIGN KEY("TEMPLATE_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_COMPONENTS" ADD CONSTRAINT "FK74ACEH7XUNIBJ57QQ1EIHRO95" FOREIGN KEY("PROVISION_INFO_ID") REFERENCES "PUBLIC"."DPDS_INFRA_PROVISION_INFOS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENT_DEPLOY_INFOS" ADD CONSTRAINT "FK8VI9YWFKJOOYEL3URUJICOOK9" FOREIGN KEY("SERVICE_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
ALTER TABLE "PUBLIC"."DPDS_INFRA_COMPONENT_TAGS" ADD CONSTRAINT "FKOGCUHXRVBR8TRJSNF1SCFJK0D" FOREIGN KEY("ID") REFERENCES "PUBLIC"."DPDS_INFRA_COMPONENTS"("ID");
ALTER TABLE "PUBLIC"."DPDS_APP_COMPONENTS" ADD CONSTRAINT "FKJE8PM73V747AL8LQXEA5CY9YE" FOREIGN KEY("EXTERNAL_DOC_ID") REFERENCES "PUBLIC"."DPDS_EXTERNAL_RESOURCES"("ID");
