CREATE TYPE GENDERS AS ENUM ('male', 'female', 'other');

CREATE TABLE places (
  id SERIAL PRIMARY KEY,
  name TEXT,
  area REAL,
  description TEXT,
  CHECK (area > 0)
);

CREATE TABLE states (
  id SERIAL PRIMARY KEY,
  name TEXT,
  description TEXT
);

CREATE TABLE characters (
  id SERIAL PRIMARY KEY,
  location_id INTEGER REFERENCES places (id),
  state_id INTEGER REFERENCES states (id),
  name TEXT,
  date_of_birth DATE,
  gender GENDERS,
  height REAL,
  weight REAL,
  is_alive BOOLEAN,
  date_of_death DATE,
  CONSTRAINT invalid_death CHECK (date_of_death > date_of_birth),
  CONSTRAINT mandatory_death CHECK ((is_alive=TRUE AND date_of_death IS NULL ) OR
                                    (is_alive=FALSE AND date_of_death IS NOT NULL)),
  CONSTRAINT valid_values CHECK (height > 0 AND weight > 0)

);

CREATE TABLE animals (
  id SERIAL PRIMARY KEY,
  location_id INTEGER REFERENCES places (id),
  name TEXT,
  type TEXT,
  height REAL,
  weight REAL,
  CONSTRAINT valid_values CHECK (height > 0 AND weight > 0)
);

CREATE TABLE objects (
  id SERIAL PRIMARY KEY,
  location_id INTEGER REFERENCES places (id),
  name TEXT,
  purpose TEXT
);

CREATE TABLE action_logs (
  id SERIAL PRIMARY KEY,
  character_id INTEGER REFERENCES characters (id),
  animal_id INTEGER REFERENCES animals (id),
  action TEXT,
  result TEXT,
  time_of_occurrency TIMESTAMP,
  CONSTRAINT only_one_performer CHECK (((character_id IS NOT NULL)::INTEGER
                                        + (animal_id IS NOT NULL)::INTEGER) = 1)
);

CREATE TABLE objects_in_actions (
  object_id INTEGER NOT NULL REFERENCES objects (id),
  action_id INTEGER NOT NULL REFERENCES action_logs (id)
);

CREATE TABLE sound_logs (
  id SERIAL PRIMARY KEY,
  reason_id INTEGER REFERENCES action_logs (id),
  name TEXT,
  volume REAL,
  pitch REAL,
  time_of_occurrency TIMESTAMP,
  CONSTRAINT valid_values CHECK (volume > 0 AND pitch > 0)
);

INSERT INTO places (name, area, description) VALUES
  ('Bowman''s home', 50.0, 'Place where Bowman has resided during his life on Earth.'),
  ('Mothership', 1000.0, 'Bowman''s current place of residence.'),
  ('Bob''s cafe', 300.0, 'Cafe where Bowman used to spend his free time.'),
  ('Watch', 10.0, 'Bowman''s place of work.'),
  ('Graveyard', 1500.0, 'The city''s graveyard.');

INSERT INTO states (name, description) VALUES
  ('Calm', 'State of being free from strong emotions.'),
  ('Amused', 'State of finding something funny.'),
  ('Lonely', 'State of sadness due to having no company.'),
  ('Shocked', 'State of feeling surprised and upset at the same time.'),
  ('Joyful' ,'State of expressing pleasure and happiness.'),
  ('Dead', 'Self-explanatory.');

INSERT INTO characters (location_id, state_id, name, date_of_birth
  , gender, height, weight, is_alive, date_of_death) VALUES
  (4, 3, 'David Bowman', '1965-01-23', 'male', 183.0, 85.3, TRUE, NULL),
  (1, 3, 'Betty Bowman', '1968-05-10', 'female', 171.4, 56.7, TRUE, NULL),
  (2, 6, 'Francis Poole', '1969-10-17', 'male', 191.6, 90.5, FALSE, '2001-03-30'),
  (5, 6, 'Robert Bowman', '1972-08-29', 'male', 181.2, 75.6, FALSE, '2005-11-02'),
  (1, 5, 'Annie Poole', '1996-12-13', 'female', 111.4, 18.2, TRUE, NULL);

INSERT INTO animals (location_id, name, type, height, weight) VALUES
  (5, 'Crow', 'Crow', 60.0, 1.5),
  (1, 'Thomas', 'Cat', 51.4, 4.5),
  (1, 'Charlie', 'Dog', 80.7, 73.6),
  (1, 'Buddy', 'Dog', 82.4, 82.5),
  (3, 'Asshole', 'Parrot', 50.2, 0.28);

INSERT INTO objects (location_id, name, purpose) VALUES
  (4, 'Volume controller', 'Device for volume regulating.'),
  (2, 'Alarm system', 'System designed to detect intrusion.'),
  (1, 'Kitchen knife', 'Cutting tool.'),
  (5, 'Robert''s grave', 'Right here rests the remains of Robert.'),
  (3, 'Cup', 'A water distribution vessel.');

INSERT INTO action_logs (character_id, animal_id, action, result, time_of_occurrency) VALUES
  (1, NULL, 'Raising the alarm', 'Alarm has been sounded.', '2001-05-15 10:23:54'),
  (2, NULL, 'Petting Thomas', 'Thomas is now happy.', '1995-02-28 22:03:58'),
  (NULL, 2, 'Meowing sounds', 'Now everyone knows that Thomas is happy.', '1995-02-28 22:03:59'),
  (1, NULL, 'Raising the volume', 'Screeching sounds have started to occur.', '2001-04-23 03:13:23'),
  (NULL, 1, 'Crowing sounds', 'The crow is now known to be at Robert''s grave.', '2006-06-13 05:34:45');

INSERT INTO objects_in_actions (object_id, action_id) VALUES
  (4,5),
  (2,1),
  (1,1),
  (1,4);

INSERT INTO sound_logs (reason_id, name, volume, pitch, time_of_occurrency) VALUES
  (1, 'Alarm sounds', 80.8, 2014.5, '2001-05-15 10:23:56'),
  (3, 'Meow', 40.58, 1004.6, '1995-02-28 22:03:59'),
  (4, 'Creaks', 65.3, 1472.97, '2001-04-23 03:13:27'),
  (4, 'Hisses', 67.5, 1456.35, '2001-04-23 03:13:35');
  
  
SELECT * FROM characters;
SELECT * FROM animals;
SELECT * FROM objects;
SELECT * FROM places;
SELECT * FROM states;
SELECT * FROM action_logs;
SELECT * FROM objects_in_actions;
SELECT * FROM sound_logs;