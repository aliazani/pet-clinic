DROP
    DATABASE IF EXISTS `pet-clinic`;

DROP
    USER IF EXISTS `pet-clinic-user`@`%`;

CREATE
    DATABASE IF NOT EXISTS `pet-clinic` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE
    USER IF NOT EXISTS `pet-clinic-user`@`%` IDENTIFIED WITH mysql_native_password
    BY 'PetClinicPassword1234@gmail.com';

GRANT
    SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
        CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `pet-clinic`.* TO `pet-clinic-user`@`%`;

FLUSH PRIVILEGES;
