INSERT INTO resources (id, capacity, parent_id) VALUES
 ('system', 100, NULL),
 ('data',   50,  'system'),
 ('logs',   20,  'data'),
 ('config', 10,  'logs');

INSERT INTO permissions (user_login, resource_id, operation) VALUES
 ('player', 'data',   'READ'),
 ('player', 'logs',   'WRITE'),
 ('player', 'config', 'EXECUTE');