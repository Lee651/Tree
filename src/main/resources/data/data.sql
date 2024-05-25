CREATE TABLE `province` (
    `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `city` (
    `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `provinceCode` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `area` (
    `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `cityCode` varchar(255) DEFAULT NULL,
    `provinceCode` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `street` (
    `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `areaCode` varchar(255) DEFAULT NULL,
    `provinceCode` varchar(255) DEFAULT NULL,
    `cityCode` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `village` (
    `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `streetCode` varchar(255) DEFAULT NULL,
    `provinceCode` varchar(255) DEFAULT NULL,
    `cityCode` varchar(255) DEFAULT NULL,
    `areaCode` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
