CREATE TABLE IDN_RESOURCE (
  ID                  INTEGER   AUTO_INCREMENT PRIMARY KEY NOT NULL,
  RESOURCE_ID         VARCHAR(255),
  RESOURCE_NAME       VARCHAR(30),
  TIME_CREATED        TIMESTAMP DEFAULT '0',
  RESOURCE_OWNER_ID    VARCHAR(255),
  TENANT_ID           INTEGER
);


CREATE TABLE IDN_RESOURCE_META_DATA (
  ID                INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  RESOURCE_IDENTITY INTEGER NOT NULL,
  PROPERTY_KEY      VARCHAR(40),
  PROPERTY_VALUE    VARCHAR(40),
  CONSTRAINT FK_RESOURCE_META_DATA FOREIGN KEY (RESOURCE_IDENTITY) REFERENCES IDN_RESOURCE (ID)
);


CREATE TABLE IDN_RESOURCE_SCOPE (
  ID          INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  RESOURCE_IDENTITY INTEGER NOT NULL ,
  SCOPE_ID    INTEGER NOT NULL ,
  CONSTRAINT FK_RESOURCE_SCOPE FOREIGN KEY (RESOURCE_IDENTITY) REFERENCES IDN_RESOURCE (ID),
  CONSTRAINT FK_RESOURCE_SET_SCOPE FOREIGN KEY (SCOPE_ID) REFERENCES IDN_OAUTH2_SCOPE (SCOPE_ID)
);

CREATE TABLE IDN_PERMISSION_TICKET (
  ID              INTEGER     AUTO_INCREMENT PRIMARY KEY NOT NULL,
  PT              VARCHAR(255)                           NOT NULL,
  TIME_CREATED    TIMESTAMP   DEFAULT '0',
  VALIDITY_PERIOD BIGINT,
  TICKET_STATE    VARCHAR(25) DEFAULT 'ACTIVE'
);


CREATE TABLE IDN_PT_RESOURCE (
  ID       INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  PT_RESOURCE_ID INTEGER NOT NULL ,
  PT_ID    INTEGER NOT NULL ,
  CONSTRAINT FK_PT FOREIGN KEY (PT_ID) REFERENCES IDN_PERMISSION_TICKET (ID) ON DELETE CASCADE,
  CONSTRAINT FK_PT_RESOURCE FOREIGN KEY (PT_RESOURCE_ID) REFERENCES IDN_RESOURCE (ID)
);



CREATE TABLE IDN_PT_RESOURCE_SCOPE (
  ID          INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  PT_RESOURCE_ID INTEGER NOT NULL ,
  PT_SCOPE_ID    INTEGER NOT NULL ,
  CONSTRAINT FK_PT_RESOURCE_ID FOREIGN KEY (PT_RESOURCE_ID) REFERENCES IDN_PT_RESOURCE (ID),
  CONSTRAINT FK_PT_SCOPE_ID FOREIGN KEY (PT_SCOPE_ID) REFERENCES IDN_OAUTH2_SCOPE (SCOPE_ID)
);



