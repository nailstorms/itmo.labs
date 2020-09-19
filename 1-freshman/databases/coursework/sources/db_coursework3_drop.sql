DROP TABLE employees CASCADE;
DROP TABLE transport CASCADE;
DROP TABLE weaponry CASCADE;
DROP TABLE weapons_in_arms_deals CASCADE;
DROP TABLE prawns CASCADE;
DROP TABLE prawns_in_experiments CASCADE;
DROP TABLE scientist_profiles CASCADE;
DROP TABLE security_profiles CASCADE;
DROP TABLE management_profiles CASCADE;
DROP TABLE arms_deals CASCADE;
DROP TABLE experiments CASCADE;
DROP TABLE district_houses CASCADE;

DROP TYPE GENDERS;
DROP TYPE TRANSPORT_TYPE;

DROP FUNCTION check_transport_level_before_using();
DROP FUNCTION check_weapon_level_before_using();
DROP FUNCTION check_weapon_level_before_deal();
