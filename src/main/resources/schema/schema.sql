
CREATE TABLE IF NOT EXISTS topics(id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
url VARCHAR(100) UNIQUE NOT NULL,
status VARCHAR(100)  NOT NULL,
picture_url VARCHAR(100));



CREATE TABLE IF NOT EXISTS message_mapping(id SERIAL PRIMARY KEY,
message_id VARCHAR(100) NOT NULL,
topic_url VARCHAR(100) UNIQUE NOT NULL);


CREATE TABLE IF NOT EXISTS is_game_url(id SERIAL PRIMARY KEY,
topic_url VARCHAR(100) UNIQUE NOT NULL,
is_game BOOLEAN NOT NULL);


CREATE TABLE IF NOT EXISTS customers(id SERIAL PRIMARY KEY,
telegram_user_id INTEGER UNIQUE NOT NULL,
role VARCHAR(100) NOT NULL,
state VARCHAR(100) NOT NULL,
private_chat_id BIGINT);
