CREATE TABLE user (
                        uuid binary(255) NOT NULL,
                        birthdate date NOT NULL,
                        created_at datetime(6) NOT NULL,
                        name varchar(100) NOT NULL,
                        nickname varchar(32) NOT NULL,
                        updated_at datetime(6) NOT NULL,
                        PRIMARY KEY (uuid),
                        UNIQUE KEY UK_n4swgcf30j6bmtb4l4cjryuym (nickname)
);

CREATE TABLE stacks (
                          user_id binary(255) NOT NULL,
                          stack varchar(32) NOT NULL,
                          KEY FKhyruc4egc7h4rl3wbs7a1fuh0 (user_id),
                          CONSTRAINT FKhyruc4egc7h4rl3wbs7a1fuh0 FOREIGN KEY (user_id) REFERENCES user (uuid)
);
