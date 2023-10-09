SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                   `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                   `request_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                   `response_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                   `status` int NULL DEFAULT NULL,
                                   `is_delete` tinyint(1) NULL DEFAULT NULL,
                                   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   `method` int NULL DEFAULT NULL,
                                   `user_id` int NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
