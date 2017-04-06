create database wms_db
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

use wms_db;

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
    REPO_AREA varchar(20) not null,
    REPO_DESC varchar(50),
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

create table wms_record_in
(
	RECORD_ID int not null auto_increment,
    RECORD_SUPPLIERID int not null,
    RECORD_GOODID int not null,
    RECORD_NUMBER int not null,
    RECORD_TIME datetime not null,
    RECORD_PERSON varchar(10) not null,
    RECORD_REPOSITORYID int not null,
    primary key(RECORD_ID),
    foreign key(RECORD_SUPPLIERID) references wms_supplier(SUPPLIER_ID),
    foreign key(RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key(RECORD_REPOSITORYID) references wms_respository(REPO_ID)
)engine=innodb;

create table wms_record_out
(
	RECORD_ID int not null auto_increment,
    RECORD_CUSTOMERID int not null,
    RECORD_GOODID int not null,
    RECORD_NUMBER int not null,
    RECORD_TIME datetime not null,
    RECORD_PERSON varchar(10) not null,
     RECORD_REPOSITORYID int not null,
    primary key(RECORD_ID),
    foreign key(RECORD_CUSTOMERID) references wms_customer(CUSTOMER_ID),
    foreign key(RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key(RECORD_REPOSITORYID) references wms_respository(REPO_ID)
)engine=innodb;

create table wms_record_storage
(
	RECORD_GOODID int not null auto_increment,
    RECORD_REPOSITORY int not null,
    RECORD_NUMBER int not null,
    primary key(RECORD_GOODID),
    foreign key (RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key (RECORD_REPOSITORY) references wms_respository(REPO_ID)
)engine=innodb;

create table wms_user
(
	USER_ID int not null auto_increment,
    USER_USERNAME varchar(30) not null,
    USER_PASSWORD varchar(40) not null,
    primary key (USER_ID)
)engine=innodb;

create table wms_roles
(
	ROLE_ID int not null auto_increment,
    ROLE_NAME varchar(20) not null,
    ROLE_DESC varchar(30),
    ROLE_URL_PREFIX varchar(20) not null,
    primary key(ROLE_ID)
)engine=innodb;

create table wms_action
(
	ACTION_ID int not null auto_increment,
    ACTION_NAME varchar(30) not null,
    ACTION_DESC varchar(30),
    ACTION_PARAM varchar(50) not null,
    primary key(ACTION_ID)
)engine=innodb;

create table wms_user_role
(
	ROLE_ID int not null,
    USER_ID int not null,
    primary key(ROLE_ID,USER_ID),
    foreign key(ROLE_ID) references wms_roles(ROLE_ID),
    foreign key(USER_ID) references wms_user(USER_ID)
)engine=innodb;

create table wms_role_action
(
	ACTION_ID int not null,
    ROLE_ID int not null,
    primary key(ACTION_ID,ROLE_ID),
    foreign key(ROLE_ID) references wms_roles(ROLE_ID),
    foreign key(ACTION_ID) references wms_action(ACTION_ID)
)engine=innodb;


create table wms_access_record
(
	RECORD_ID int auto_increment key,
    USER_ID int not null,
    USER_NAME varchar(50) not null,
    ACCESS_TYPE varchar(30) not null,
    ACCESS_TIME datetime not null,
    ACCESS_IP varchar(45) not null
);