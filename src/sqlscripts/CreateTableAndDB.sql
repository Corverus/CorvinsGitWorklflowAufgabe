DROP DATABASE IF EXISTS corvinsadventure;
CREATE DATABASE IF NOT EXISTS corvinsadventure;
USE corvinsadventure;

CREATE TABLE ENEMY (
	ENEMYID		INT  NOT NULL,
	ENEMYNAME 	VARCHAR(30),
	ENEMYLP		INT,
	ENEMYATK		INT,
	ENEMYDEF		INT,
	ENEMYSPE		INT,
	ENEMYLVL		INT,
	LEVELID		INT,
);

CREATE TABLE LEVEL (
	LEVELID		INT  NOT NULL,
	LEVELNAME	VARCHAR(30)
);

CREATE TABLE ROOM (
	ROOMID		INT  NOT NULL,
	LENGTHX		INT,
	LENGTHY		INT,
	OBSTACLEID	INT
);

CREATE TABLE OBSTACLE(
	OBSTACLEID		INT NOT NULL,
	OBSTACLENAME	VARCHAR(30),
);

CREATE TABLE WEAPON (
	WEAPONID				INT NOT NULL,
	WEAPONNAME			VARCHAR (30),
	WEAPONREQUIREMENT   INT,
);

CREATE TABLE PLAYER (
	PLAYERID	INT  NOT NULL,
	PLAYERNAME		VARCHAR(30),
	PLAYERLP			INT,
	PLAYERATK		INT,
	PLAYERDEF		INT,
	PLAYERSPE		INT,
	PLAYERLVL		INT,
	WEAPONID	INT
);

/******************************************************************************/
/***                              Primary Keys                              ***/
/******************************************************************************/

ALTER TABLE ENEMY ADD PRIMARY KEY (ENEMYID);
ALTER TABLE LEVEL ADD PRIMARY KEY (LEVELID);
ALTER TABLE ROOM ADD PRIMARY KEY (ROOMID);
ALTER TABLE OBSTACLE ADD PRIMARY KEY (OBSTACLEID);
ALTER TABLE WEAPON ADD PRIMARY KEY (WEAPONID);
ALTER TABLE PLAYER ADD PRIMARY KEY (PLAYERID);
/******************************************************************************/
/***                              Foreign Keys                              ***/
/******************************************************************************/
ALTER TABLE ENEMY ADD FOREIGN KEY (LEVELID) REFERENCES LEVEL (LEVELID);
ALTER TABLE ROOM ADD FOREIGN KEY (OBSTACLEID) REFERENCES OBSTACLE (OBSTACLEID);
ALTER TABLE PLAYER ADD FOREIGN KEY (WEAPONID) REFERENCES WEAPON (WEAPONID);