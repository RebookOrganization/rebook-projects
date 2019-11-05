CREATE TABLE IF NOT EXISTS `realEstateSchema`.`property_project201911` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `project_name` VARCHAR(255) NULL DEFAULT NULL,
  `project_owner` VARCHAR(255) NULL DEFAULT NULL,
  `project_size` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 
COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `realEstateSchema`.`contact_owner201911` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `contact_name` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `phone_number` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 
COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `realEstateSchema`.`property_address201911` ;
CREATE TABLE IF NOT EXISTS `realEstateSchema`.`property_address201911` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `district` VARCHAR(255) NULL DEFAULT NULL,
  `province` VARCHAR(255) NULL DEFAULT NULL,
  `street` VARCHAR(255) NULL DEFAULT NULL,
  `summary` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 
COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `news_item201911` (
 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `area` VARCHAR(255) NULL DEFAULT NULL,
  `balcony` VARCHAR(255) NULL DEFAULT NULL,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `direct_of_house` VARCHAR(255) NULL DEFAULT NULL,
  `floor_number` VARCHAR(255) NULL DEFAULT NULL,
  `front_end` VARCHAR(255) NULL DEFAULT NULL,
  `interior` VARCHAR(255) NULL DEFAULT NULL,
  `posted_date` VARCHAR(255) NULL DEFAULT NULL,
  `posted_milisec` BIGINT(20) NULL DEFAULT NULL,
  `price` VARCHAR(255) NULL DEFAULT NULL,
  `pub_date` VARCHAR(255) NULL DEFAULT NULL,
  `room_number` VARCHAR(255) NULL DEFAULT NULL,
  `summary` TEXT NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `toilet_number` VARCHAR(255) NULL DEFAULT NULL,
  `trans_type` VARCHAR(255) NULL DEFAULT NULL,
  `url` VARCHAR(255) NULL DEFAULT NULL,
  `wardin` VARCHAR(255) NULL DEFAULT NULL,
  `contact_owner_id` BIGINT(20) NULL DEFAULT NULL,
  `property_address_id` BIGINT(20) NULL DEFAULT NULL,
  `property_project_id` BIGINT(20) NULL DEFAULT NULL,
  `user_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
	FOREIGN KEY (`property_project_id`) REFERENCES `realEstateSchema`.`property_project201911` (`id`),
	FOREIGN KEY (`contact_owner_id`) REFERENCES `realEstateSchema`.`contact_owner201911` (`id`),
	FOREIGN KEY (`property_address_id`) REFERENCES `realEstateSchema`.`property_address201911` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `images201911` (
 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `image_size` BIGINT(20) NULL DEFAULT NULL,
  `image_type` VARCHAR(255) NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `news_item_id` BIGINT(20) NULL DEFAULT NULL,
  `pic_byte` LONGBLOB NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`news_item_id`) REFERENCES `news_item201911` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;