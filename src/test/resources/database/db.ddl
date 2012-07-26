CREATE TABLE "MRCOC" (
  "CUI1" char(8) NOT NULL,
  "AUI1" varchar(9) NOT NULL,
  "CUI2" char(8) default NULL,
  "AUI2" varchar(9) default NULL,
  "SAB" varchar(20) NOT NULL,
  "COT" varchar(3) NOT NULL,
  "COF" integer  default NULL,
  "COA" LONGVARCHAR,
  "CVF" integer  default NULL,
 );
 

 
 
 
 
 

CREATE TABLE "MRCOLS" (
  "COL" varchar(20) default NULL,
  "DES" varchar(200) default NULL,
  "REF" varchar(20) default NULL,
  "MIN" integer  default NULL,
  "AV" decimal(5,2) default NULL,
  "MAX" integer  default NULL,
  "FIL" varchar(50) default NULL,
  "DTY" varchar(20) default NULL
);
 

 
 

 
 
 

CREATE TABLE "MRCONSO" (
  "CUI" char(8) NOT NULL,
  "LAT" char(3) NOT NULL,
  "TS" char(1) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "STT" varchar(3) NOT NULL,
  "SUI" varchar(10) NOT NULL,
  "ISPREF" char(1) NOT NULL,
  "AUI" varchar(9) NOT NULL,
  "SAUI" varchar(50) default NULL,
  "SCUI" varchar(50) default NULL,
  "SDUI" varchar(50) default NULL,
  "SAB" varchar(20) NOT NULL,
  "TTY" varchar(20) NOT NULL,
  "CODE" varchar(50) NOT NULL,
  "STR" LONGVARCHAR NOT NULL,
  "SRL" integer  NOT NULL,
  "SUPPRESS" char(1) NOT NULL,
  "CVF" integer  default NULL
);


 
 

 
 
 
 
 
CREATE TABLE "MRDEF" (
  "CUI" char(8) NOT NULL,
  "AUI" varchar(9) NOT NULL,
  "ATUI" varchar(11) NOT NULL,
  "SATUI" varchar(50) default NULL,
  "SAB" varchar(20) NOT NULL,
  "DEF" LONGVARCHAR NOT NULL,
  "SUPPRESS" char(1) NOT NULL,
  "CVF" integer  default NULL
);


 
 

 
 
 

CREATE TABLE "MRDOC" (
  "DOCKEY" varchar(50) NOT NULL,
  "VALUE" varchar(200) default NULL,
  "TYPE" varchar(50) NOT NULL,
  "EXPL" LONGVARCHAR
);


 
 
 
 
 
 
 
CREATE TABLE "MRFILES" (
  "FIL" varchar(50) default NULL,
  "DES" varchar(200) default NULL,
  "FMT" LONGVARCHAR,
  "CLS" integer  default NULL,
  "RWS" integer  default NULL,
  "BTS" integer default NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRHIER" (
  "CUI" char(8) NOT NULL,
  "AUI" varchar(9) NOT NULL,
  "CXN" integer  NOT NULL,
  "PAUI" varchar(10) default NULL,
  "SAB" varchar(20) NOT NULL,
  "RELA" varchar(100) default NULL,
  "PTR" LONGVARCHAR,
  "HCD" varchar(50) default NULL,
  "CVF" integer  default NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRHIST" (
  "CUI" char(8) default NULL,
  "SOURCEUI" varchar(50) default NULL,
  "SAB" varchar(20) default NULL,
  "SVER" varchar(20) default NULL,
  "CHANGETYPE" LONGVARCHAR,
  "CHANGEKEY" LONGVARCHAR,
  "CHANGEVAL" LONGVARCHAR,
  "REASON" LONGVARCHAR,
  "CVF" integer  default NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRMAP" (
  "MAPSETCUI" char(8) NOT NULL,
  "MAPSETSAB" varchar(20) NOT NULL,
  "MAPSUBSETID" varchar(10) default NULL,
  "MAPRANK" integer  default NULL,
  "MAPID" varchar(50) NOT NULL,
  "MAPSID" varchar(50) default NULL,
  "FROMID" varchar(50) NOT NULL,
  "FROMSID" varchar(50) default NULL,
  "FROMEXPR" LONGVARCHAR NOT NULL,
  "FROMTYPE" varchar(50) NOT NULL,
  "FROMRULE" LONGVARCHAR,
  "FROMRES" LONGVARCHAR,
  "REL" varchar(4) NOT NULL,
  "RELA" varchar(100) default NULL,
  "TOID" varchar(50) default NULL,
  "TOSID" varchar(50) default NULL,
  "TOEXPR" LONGVARCHAR,
  "TOTYPE" varchar(50) default NULL,
  "TORULE" LONGVARCHAR,
  "TORES" LONGVARCHAR,
  "MAPRULE" LONGVARCHAR,
  "MAPRES" LONGVARCHAR,
  "MAPTYPE" varchar(50) default NULL,
  "MAPATN" varchar(100) default NULL,
  "MAPATV" LONGVARCHAR,
  "CVF" integer  default NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRRANK" (
  "RANK" integer  NOT NULL,
  "SAB" varchar(20) NOT NULL,
  "TTY" varchar(20) NOT NULL,
  "SUPPRESS" char(1) NOT NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRREL" (
  "CUI1" char(8) NOT NULL,
  "AUI1" varchar(9) default NULL,
  "STYPE1" varchar(50) NOT NULL,
  "REL" varchar(4) NOT NULL,
  "CUI2" char(8) NOT NULL,
  "AUI2" varchar(9) default NULL,
  "STYPE2" varchar(50) NOT NULL,
  "RELA" varchar(100) default NULL,
  "RUI" varchar(10) NOT NULL,
  "SRUI" varchar(50) default NULL,
  "SAB" varchar(20) NOT NULL,
  "SL" varchar(20) NOT NULL,
  "RG" varchar(10) default NULL,
  "DIR" varchar(1) default NULL,
  "SUPPRESS" char(1) NOT NULL,
  "CVF" integer  default NULL,
  PRIMARY KEY  ("RUI")
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRSAB" (
  "VCUI" char(8) default NULL,
  "RCUI" char(8) default NULL,
  "VSAB" varchar(20) NOT NULL,
  "RSAB" varchar(20) NOT NULL,
  "SON" LONGVARCHAR NOT NULL,
  "SF" varchar(20) NOT NULL,
  "SVER" varchar(20) default NULL,
  "VSTART" char(8) default NULL,
  "VEND" char(8) default NULL,
  "IMETA" varchar(10) NOT NULL,
  "RMETA" varchar(10) default NULL,
  "SLC" LONGVARCHAR,
  "SCC" LONGVARCHAR,
  "SRL" integer  NOT NULL,
  "TFR" integer  default NULL,
  "CFR" integer  default NULL,
  "CXTY" varchar(50) default NULL,
  "TTYL" varchar(400) default NULL,
  "ATNL" LONGVARCHAR,
  "LAT" char(3) default NULL,
  "CENC" varchar(20) NOT NULL,
  "CURVER" char(1) NOT NULL,
  "SABIN" char(1) NOT NULL,
  "SSN" LONGVARCHAR NOT NULL,
  "SCIT" LONGVARCHAR NOT NULL
);
 

 
 

 
 
 
 
CREATE TABLE "MRSAT" (
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) default NULL,
  "SUI" varchar(10) default NULL,
  "METAUI" varchar(50) default NULL,
  "STYPE" varchar(50) NOT NULL,
  "CODE" varchar(50) default NULL,
  "ATUI" varchar(11) NOT NULL,
  "SATUI" varchar(50) default NULL,
  "ATN" varchar(100) NOT NULL,
  "SAB" varchar(20) NOT NULL,
  "ATV" LONGVARCHAR,
  "SUPPRESS" char(1) NOT NULL,
  "CVF" integer  default NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRSMAP" (
  "MAPSETCUI" char(8) NOT NULL,
  "MAPSETSAB" varchar(20) NOT NULL,
  "MAPID" varchar(50) NOT NULL,
  "MAPSID" varchar(50) default NULL,
  "FROMEXPR" LONGVARCHAR NOT NULL,
  "FROMTYPE" varchar(50) NOT NULL,
  "REL" varchar(4) NOT NULL,
  "RELA" varchar(100) default NULL,
  "TOEXPR" LONGVARCHAR,
  "TOTYPE" varchar(50) default NULL,
  "CVF" integer  default NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRSTY" (
  "CUI" char(8) NOT NULL,
  "TUI" char(4) NOT NULL,
  "STN" varchar(100) NOT NULL,
  "STY" varchar(50) NOT NULL,
  "ATUI" varchar(11) NOT NULL,
  "CVF" integer  default NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRXNS_ENG" (
  "LAT" char(3) NOT NULL,
  "NSTR" LONGVARCHAR NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRXNW_ENG" (
  "LAT" char(3) NOT NULL,
  "NWD" varchar(100) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRXW_BAQ" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_CZE" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_DAN" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_DUT" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_ENG" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 

 
 
 
 
 
CREATE TABLE "MRXW_FIN" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_FRE" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_GER" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_HEB" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_HUN" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_ITA" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_JPN" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(500) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_KOR" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(500) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_LAV" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_NOR" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_POL" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_POR" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_RUS" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_SCR" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_SPA" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 
 
 
CREATE TABLE "MRXW_SWE" (
  "LAT" char(3) NOT NULL,
  "WD" varchar(200) NOT NULL,
  "CUI" char(8) NOT NULL,
  "LUI" varchar(10) NOT NULL,
  "SUI" varchar(10) NOT NULL
);
 

 
 
 
 
 

 
 
 
 

