DELIMITER //
CREATE PROCEDURE AddPost(IN user_id INT, IN content TEXT)
BEGIN
    INSERT INTO Posts (user_id, content) VALUES (user_id, content);
END //
DELIMITER ;