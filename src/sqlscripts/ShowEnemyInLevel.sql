USE corvinsadventure;

SELECT level.LEVELID,level.LEVELNAME,enemy.ENEMYID,enemy.ENEMYNAME FROM LEVEL
INNER JOIN enemy ON (level.LEVELID = enemy.LEVELID);