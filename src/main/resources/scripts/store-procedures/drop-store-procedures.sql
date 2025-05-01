DELIMITER
CREATE PROCEDURE DropAllProcedures()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE proc_name VARCHAR(255);

    DECLARE cur CURSOR FOR
      SELECT routine_name
      FROM information_schema.routines
      WHERE routine_type = 'PROCEDURE'
      AND routine_schema = DATABASE();

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO proc_name;
        IF done THEN
            LEAVE read_loop;
        END IF;
        SET @stmt = CONCAT('DROP PROCEDURE IF EXISTS ', proc_name);
        PREPARE stmt FROM @stmt;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    CLOSE cur;
END
DELIMITER ;