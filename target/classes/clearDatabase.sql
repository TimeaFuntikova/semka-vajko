DROP TABLE IF EXISTS certificate CASCADE;

---INSERT INTO users (username, password, hashedPassword, email, role_name)
--VALUES ('admin', 'admin123', 'hashedAdminPassword', 'admin@example.com', 'ADMIN');



--ALTER TABLE users
 --   ADD COLUMN id_serial SERIAL;

-- Update existing rows with unique values
---UPDATE users
--SET id_serial = DEFAULT;

-- Drop the old id column
--ALTER TABLE users
   -- DROP COLUMN id;

-- Rename the new serial column to id
--ALTER TABLE users
   -- RENAME COLUMN id_serial TO id;

-- Set the id column as the primary key
--ALTER TABLE users
   --ADD PRIMARY KEY (id);