INSERT INTO weaponry (name, type, ammo_type, quantity, required_access_lvl) VALUES
  ('крутое', 'оч крутое', 'крутые', 999, 1),
  ('Spear of Gungnir', 'Spear', 'None', 1, 5);

INSERT INTO transport (type, name, availability, required_access_lvl) VALUES
  ('land', 'вертолет', true, 2), ('air', 'машина', true, 4 );

INSERT INTO employees (name, date_of_birth, date_of_death, date_of_employment, gender, salary) VALUES
  ('Karen Kisaragi', '1989-05-29', null, '2007-06-18', 'female', 9922),
  ('DeadMan', '1000-03-03', '1021-03-03', '1020-02-02', 'male', 911);

INSERT INTO security_profiles (employee_id, supervisor_id, transport_id, weapon_id, name, position, access_level) VALUES
  (1, null, 1, 1, 'некий КК', 'Где', 3);

INSERT INTO management_profiles (employee_id, supervisor_id, name, position, access_level) VALUES
  (2, null, 'DeadMan', 'где', 2);

INSERT INTO arms_deals (dealer_id, client, earn) VALUES
  (2, 'дед в пальто',99);

INSERT INTO weapons_in_arms_deals (weapon_id, deal_id) VALUES
  (1, 1);
