DROP TABLE IF EXISTS permissions;
DROP TABLE IF EXISTS resources;
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
    login VARCHAR(50) PRIMARY KEY,
    salt VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE resources (
    id VARCHAR(50) PRIMARY KEY,
    capacity INT NOT NULL,
    parent_id VARCHAR(50),
    CONSTRAINT fk_resources_parent
        FOREIGN KEY (parent_id) REFERENCES resources(id)
);

CREATE TABLE permissions (
    id IDENTITY PRIMARY KEY,
    user_login VARCHAR(50) NOT NULL,
    resource_id VARCHAR(50) NOT NULL,
    operation VARCHAR(10) NOT NULL,
    CONSTRAINT fk_perm_user
        FOREIGN KEY (user_login) REFERENCES accounts(login),
    CONSTRAINT fk_perm_res
        FOREIGN KEY (resource_id) REFERENCES resources(id)
);