-- 	ROLE I PRAVA PRISTUPA

INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

/*
	ADMINISTRATORI
	SIFRA ZA ADMINE : 123
		  
 *  KADA PRODJE 10X KROZ BCRYPT ENKODER POSTANE: '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.' sadrzi verziju, salt i hash! Salt se ne cuva posebno!!
 	 
**/
INSERT INTO admin (username, password, first_name, last_name) VALUES ('admin1@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Djanluka', 'Paljuka');
INSERT INTO admin_authority(admin_id, authority_id) VALUES (1,1);

INSERT INTO admin (username, password, first_name, last_name) VALUES ('admin2@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Fabio', 'Materaci');
INSERT INTO admin_authority(admin_id, authority_id) VALUES (2,1);

/*
	KORISNICI
	SIFRA ZA KORISNIKE : 123
 	 
**/
INSERT INTO users (username, password, account_name, first_name, last_name, biography, phone_number, date_of_birth, gender, type, website_link, is_public, is_verified, is_removed, can_be_tagged, can_receive_messages, can_receive_notifications, active)
VALUES ('user1@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Djidji99', 'Djanluidji', 'Donaruma', 'Pro Goalkeeper, art enthusiast', '+35468113289', '1999-08-18', 'MALE', null, null, true, false, false, false, false, true, true );
INSERT INTO user_authority(user_id, authority_id) VALUES (1,2);

INSERT INTO users (username, password, account_name, first_name, last_name, biography, phone_number, date_of_birth, gender, type, website_link, is_public, is_verified, is_removed, can_be_tagged, can_receive_messages, can_receive_notifications, active)
VALUES ('user2@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'MarkoPolo21652', 'Marko', 'Materazzi', 'La Vita E Bella', '+354644413289', '1989-10-14', 'MALE', null, null, false, false, false, true, true, true, true );
INSERT INTO user_authority(user_id, authority_id) VALUES (2,2);

INSERT INTO users (username, password, account_name, first_name, last_name, biography, phone_number, date_of_birth, gender, type, website_link, is_public, is_verified, is_removed, can_be_tagged, can_receive_messages, can_receive_notifications, active)
VALUES ('user3@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Kijela', 'Djordjo', 'Kijelini', 'All is good', '+35468888289', '1985-08-18', 'MALE', null, null, false, false, false, false, false, true, true );
INSERT INTO user_authority(user_id, authority_id) VALUES (3,2);

INSERT INTO users (username, password, account_name, first_name, last_name, biography, phone_number, date_of_birth, gender, type, website_link, is_public, is_verified, is_removed, can_be_tagged, can_receive_messages, can_receive_notifications, active)
VALUES ('user4@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'BonucciGOAT', 'Leonardo', 'Bonuci', 'Va fangul', '+354644413289', '1989-10-14', 'MALE', null, null, true, false, false, true, true, true, true );
INSERT INTO user_authority(user_id, authority_id) VALUES (4,2);



INSERT INTO follow (user_id, follower_id, close_friend, blocked, muted) VALUES (1, 2, true,  false, false );
INSERT INTO follow (user_id, follower_id, close_friend, blocked, muted) VALUES (2, 1, false, true,  false );

INSERT INTO follow (user_id, follower_id, close_friend, blocked, muted) VALUES (1, 3, false, false, true  );
INSERT INTO follow (user_id, follower_id, close_friend, blocked, muted) VALUES (3, 1, true,  false, false );

INSERT INTO follow (user_id, follower_id, close_friend, blocked, muted) VALUES (1, 4, false, false, false );

INSERT INTO follow (user_id, follower_id, close_friend, blocked, muted) VALUES (2, 3, true,  false, false );



INSERT INTO follow_request(user_id, requester_id) VALUES (3, 2);
INSERT INTO follow_request(user_id, requester_id) VALUES (2, 4);




