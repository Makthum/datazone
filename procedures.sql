DELIMITER $$
CREATE DEFINER=`jpradmin`@`localhost` PROCEDURE `scrap_issued`(
 IN fromDate Date,
 IN toDate date
)
BEGIN
SELECT
  GROUP_CONCAT(
CONCAT("SUM(IF(name='", name, "',factscrapissued.quantity ,0)) AS '", name, "'"), "\n"
  ) INTO @answers
FROM (
  SELECT DISTINCT name FROM dimScrap
) A;

SET @query := 
  CONCAT(
    'SELECT dimdate.date, ', @answers,',SUM( factscrapissued.quantity) as Total', 
    ' FROM factscrapissued INNER JOIN  dimScrap ON dimScrap.id = factscrapissued.dim_scrap_id INNER JOIN dimDate  ON dimDate.date_id= factscrapissued.dim_date_id WHERE dimDate.date >=\'', fromDate, '\' and dimDate.date <= \'',toDate,'\' GROUP BY factscrapissued.dim_date_id'
  );

PREPARE statement FROM @query;
EXECUTE statement;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`jpradmin`@`localhost` PROCEDURE `scrap_received`(
 IN fromDate Date,
 IN toDate date
)
BEGIN
SELECT
  GROUP_CONCAT(
CONCAT("SUM(IF(name='", name, "',factscraprecv.quantity ,0)) AS '", name, "'"), "\n"
  ) INTO @answers
FROM (
  SELECT DISTINCT name FROM dimScrap
) A;

SET @query := 
  CONCAT(
    'SELECT dimdate.date, ', @answers,',SUM( factscraprecv.quantity) as Total', 
    ' FROM factscraprecv INNER JOIN  dimScrap ON dimScrap.id = factscraprecv.dim_scrap_id INNER JOIN dimDate  ON dimDate.date_id= factscraprecv.dim_date_id WHERE dimDate.date >=\'', fromDate, '\' and dimDate.date <= \'',toDate,'\' GROUP BY factscraprecv.dim_date_id'
  );

PREPARE statement FROM @query;
EXECUTE statement;
END$$
DELIMITER ;
