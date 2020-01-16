INSERT INTO `naucnacentrala_milan`.`naucna_oblast` (`id`, `naziv`) VALUES ('no1', 'Prirodno-matematičke nauke');
INSERT INTO `naucnacentrala_milan`.`naucna_oblast` (`id`, `naziv`) VALUES ('no2', 'Tehničko-tehnološke nauke');
INSERT INTO `naucnacentrala_milan`.`naucna_oblast` (`id`, `naziv`) VALUES ('no3', 'Društveno-humanističke nauke');
INSERT INTO `naucnacentrala_milan`.`naucna_oblast` (`id`, `naziv`) VALUES ('no4', 'Medicinske nauke');
INSERT INTO `naucnacentrala_milan`.`naucna_oblast` (`id`, `naziv`) VALUES ('no5', 'Umetnosti');

INSERT INTO `_user` (`id`, `active`, `city`, `country`, `email`, `firstname`, `lastname`, `password`, `title`, `username`) VALUES (1,1,'Novi Sad','Serbia','sep.bbf@gmail.com','Administrator','NC','$2a$10$MjqeiRFBUh2n776p/EwRC.l2B7YDZkJKEwzJRF2653BL/Gp6yk8WW',NULL,'demo');
INSERT INTO `authority` (`id`, `name`) VALUES (1,'ADMIN'),(2,'UREDNIK'),(3,'RECENZENT'),(4,'AUTOR');
INSERT INTO `user_authority` (`user_id`, `authority_id`) VALUES (1,1);