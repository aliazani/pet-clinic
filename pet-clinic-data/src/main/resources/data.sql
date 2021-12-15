-- owners
INSERT INTO `owners` (first_name, last_name, address, city, telephone)
VALUES ('John', 'Steven', '123 Jay street', 'Miami', '123456'),
       ('Markus', 'Sophia', '123 Roy street', 'Florida', '98765'),
       ('Jeff', 'Sophia', 'Love street', 'Chicago', '828312');
-- pet types
INSERT INTO `pet_types` (name)
VALUES ('Dog'),
       ('Cat'),
       ('Bird');
-- pets
INSERT INTO `pets` (name, birth_date, type_id, owner_id)
VALUES ('Georgy', '2008-7-04', 1, 1),
       ('Fiona', '2010-10-05', 2, 2),
       ('Jeffy', '2012-2-12', 3, 3);
-- visits
INSERT INTO `visits` (date, description, pet_id)
VALUES ('2021-1-04', 'John dog first visit.', 1),
       ('2021-3-22', 'first visit to check if the cat is healthy or not.', 2),
       ('2021-2-12', 'second visit to the broken legs.', 2),
       ('2021-1-10', 'first visit to check if the bird is healthy or not.', 3);

-- speciality
INSERT INTO `specialties` (name)
VALUES ('Surgery'),
       ('Radiology'),
       ('Dentistry');
-- vets
INSERT INTO `vets` (first_name, last_name)
VALUES ('Robert', 'Jobs'),
       ('Mary', 'Curious');
-- vets and speciality
INSERT INTO `vet_specialties` (vet_id, specialty_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 2);