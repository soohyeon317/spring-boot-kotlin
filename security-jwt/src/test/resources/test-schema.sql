DROP TABLE IF EXISTS account;
CREATE TABLE account
(
    `id` bigint NOT NULL AUTO_INCREMENT,
    `email` varchar(512) NOT NULL,
    `password` varchar(512) NOT NULL,
    `created_at` datetime NOT NULL,
    `updated_at` datetime DEFAULT NULL,
    `deleted_at` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS auth_token;
CREATE TABLE auth_token
(
    `id` bigint NOT NULL AUTO_INCREMENT,
    `account_id` bigint NOT NULL,
    `access_token` varchar(512) NOT NULL,
    `refresh_token` varchar(512) NOT NULL,
    `created_at` datetime NOT NULL,
    `updated_at` datetime DEFAULT NULL,
    `deleted_at` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
);