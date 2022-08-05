CREATE TABLE `TASK` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
