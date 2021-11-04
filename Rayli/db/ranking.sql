
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 排行榜
-- ----------------------------
DROP TABLE IF EXISTS `rayli_ranking`;
CREATE TABLE `rayli_ranking`  (
                               `fk_food_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品外键id',
                               `rank` int(11) not NULL COMMENT '排行'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

ALTER TABLE rayli_ranking COMMENT '排行榜';
