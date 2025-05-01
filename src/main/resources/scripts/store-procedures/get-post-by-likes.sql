DELIMITER //
CREATE PROCEDURE GetPostsWithLikes()
BEGIN
    SELECT p.id AS post_id, p.content, COUNT(l.id) AS like_count
    FROM Posts p
    LEFT JOIN Likes l ON p.id = l.post_id
    GROUP BY p.id, p.content;
END //
DELIMITER ;