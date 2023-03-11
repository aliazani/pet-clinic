-- owners
INSERT INTO `owners` (id, first_name, last_name, address, city, telephone)
VALUES (1, 'John', 'Steven', '123 Jay street', 'Miami', '6085551023'),
       (2, 'Markus', 'Sophia', '123 Roy street', 'Florida', '6085551749'),
       (3, 'Jeff', 'Sophia', 'Love street', 'Chicago', '6085558763');
-- pet types
INSERT INTO `pet_types` (id, name)
VALUES (1, 'Dog'),
       (2, 'Cat'),
       (3, 'Bird');
-- pets
INSERT INTO `pets` (id, name, birth_date, type_id, owner_id)
VALUES (1, 'Georgy', '2008-7-04', 1, 1),
       (2, 'Fiona', '2010-10-05', 2, 2),
       (3, 'Jeffy', '2012-2-12', 3, 3);
-- visits
INSERT INTO `visits` (id, date, description, pet_id)
VALUES (1, '2021-1-04', 'John dog first visit.', 1),
       (2, '2021-3-22', 'first visit to check if the cat is healthy or not.', 2),
       (3, '2021-2-12', 'second visit to the broken legs.', 2),
       (4, '2021-1-10', 'first visit to check if the bird is healthy or not.', 3);

-- speciality
INSERT INTO `specialties` (id, name)
VALUES (1, 'Surgery'),
       (2, 'Radiology'),
       (3, 'Dentistry');
-- vets
INSERT INTO `vets` (first_name, last_name)
VALUES (1, 'Robert', 'Jobs'),
       (2, 'Mary', 'Curious');
-- vets and speciality
INSERT INTO `vet_specialties` (vet_id, specialty_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 2);