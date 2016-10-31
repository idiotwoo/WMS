create table wms_supplier
(
	SUPPLIER_ID int not null auto_increment,
    SUPPLIER_NAME varchar(30) not null,
    SUPPLIER_PERSON varchar(10) not null,
    SUPPLIER_TEL varchar(20) not null,
    SUPPLIER_EMAIL varchar(20) not null,
    SUPPLIER_ADDRESS varchar(30) not null,
    primary key(SUPPLIER_ID)
)engine=innodb;

create table wms_customer
(
	CUSTOMER_ID int not null auto_increment,
    CUSTOMER_NAME varchar(30) not null,
    CUSTOMER_PERSON varchar(10) not null,
    CUSTOMER_TEL varchar(20) not null,
    CUSTOMER_EMAIL varchar(20) not null,
    CUSTOMER_ADDRESS varchar(30) not null,
    primary key(CUSTOMER_ID)
 )engine=innodb;
 
 create table wms_goods
 (
	GOOD_ID int not null auto_increment,
    GOOD_NAME varChar(30) not null,
    GOOD_RYPE varchar(20),
    GOOD_SIZE varchar(20),
    GOOD_VALUE double not null,
    primary key(GOOD_ID)
 )engine=innodb;
 
 create table wms_respository
 (
	REPO_ID int not null auto_increment,
    REPO_ADDRESS varchar(30) not null,
    REPO_STATUS varchar(20) not null,
    primary key(REPO_ID)
 )engine=innodb;
 
 create table wms_repo_admin
 (
	REPO_ADMIN_ID int not null auto_increment,
    REPO_ADMIN_NAME varchar(10) not null,
    REPO_ADMIN_SEX varchar(10) not null,
    REPO_ADMIN_TEL varchar(20) not null,
    REPO_ADMIN_ADDRESS varchar(30) not null,
    REPO_ADMIN_BIRTH datetime not null,
    REPO_ADMIN_REPOID int not null,
    primary key(REPO_ADMIN_ID),
    foreign key (REPO_ADMIN_REPOID) references wms_respository(REPO_ID)
)engine=innodb;

create table wms_area
(
	AREA_ID int not null auto_increment,
    AREA_REPOID int not null,
    AREA_STATUS varchar(20) not null,
    primary key (AREA_ID),
    foreign key (AREA_REPOID) references wms_respository(REPO_ID)
)engine=innodb;

create table wms_record_in
(
	RECORD_ID int not null auto_increment,
    RECORD_SUPPLIERID int not null,
    RECORD_GOODID int not null,
    RECORD_NUMBER int not null,
    RECORD_TIME datetime not null,
    RECORD_PERSON varchar(10) not null,
    primary key(RECORD_ID),
    foreign key(RECORD_SUPPLIERID) references wms_supplier(SUPPLIER_ID),
    foreign key(RECORD_GOODID) references wms_goods(GOOD_ID)
)engine=innodb;

create table wms_record_out
(
	RECORD_ID int not null auto_increment,
    RECORD_CUSTOMERID int not null,
    RECORD_GOODID int not null,
    RECORD_NUMBER int not null,
    RECORD_TIME datetime not null,
    RECORD_PERSON varchar(10) not null,
    primary key(RECORD_ID),
    foreign key(RECORD_CUSTOMERID) references wms_customer(CUSTOMER_ID),
    foreign key(RECORD_GOODID) references wms_goods(GOOD_ID)
)engine=innodb;

create table wms_record_storage
(
	RECORD_GOODID int not null,
    RECORD_AREAID int not null,
    RECORD_NUMBER int not null,
    primary key(RECORD_GOODID,RECORD_AREAID),
    foreign key (RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key (RECORD_AREAID) references wms_area(AREA_ID)
)engine=innodb;