CREATE TABLE IF NOT EXISTS `user` (
  `uuid` varchar(45) NOT NULL,
  `email` varchar(200) DEFAULT NULL,
  `pwd` varchar(60) DEFAULT NULL,
  `user_name` varchar(60) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `rating` int DEFAULT '0',
  `resume_pdf_path` varchar(200) DEFAULT NULL,
  `is_administrator` tinyint DEFAULT '0',
  `locked_status` tinyint DEFAULT '0',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);

CREATE TABLE IF NOT EXISTS `case` (
  `id` int NOT NULL AUTO_INCREMENT,
  `case_name` varchar(100) DEFAULT NULL,
  `budget` int NOT NULL DEFAULT '0',
  `location` varchar(30) DEFAULT NULL,
  `content` text,
  `deadline` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `case_class` varchar(50) DEFAULT NULL,
  `initiator` varchar(45) DEFAULT NULL,
  `on_shelf` tinyint NOT NULL DEFAULT '1',
  `current_status` varchar(45) DEFAULT 'Not Started',
  `progress_percentage` int DEFAULT '0',
  `completion_date` datetime DEFAULT NULL,
  `case_rating` int DEFAULT '0',
  PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `case_contractor` (
  `case_id` int NOT NULL,
  `contractor_uid` varchar(45) NOT NULL,
  `is_accepted` tinyint DEFAULT '0',
  `accept_date` datetime DEFAULT NULL,
  PRIMARY KEY (`case_id`,`contractor_uid`)
);


CREATE TABLE IF NOT EXISTS `case_current_status` (
  `status_name` varchar(45) NOT NULL,
  `status_zh_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`status_name`)
);

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` varchar(20) NOT NULL,
  `location_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
);
