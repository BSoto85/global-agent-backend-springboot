-- Global Agent Production Schema + Seed Data
-- Run on first deploy by Render PostgreSQL service

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    uid VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    dob DATE DEFAULT NULL,
    photo TEXT DEFAULT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stats (
    id SERIAL PRIMARY KEY,
    xp INTEGER NOT NULL DEFAULT 0,
    games_played INTEGER NOT NULL DEFAULT 0,
    questions_correct INTEGER NOT NULL DEFAULT 0,
    questions_wrong INTEGER NOT NULL DEFAULT 0,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS countries (
    id SERIAL PRIMARY KEY,
    flag TEXT NOT NULL,
    country_code VARCHAR(2),
    name VARCHAR(30),
    language_code VARCHAR(2),
    silhouette TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS case_files (
    id SERIAL PRIMARY KEY,
    article_id INTEGER UNIQUE,
    article_content TEXT,
    article_title TEXT,
    publish_date VARCHAR(50),
    summary_young TEXT DEFAULT NULL,
    summary_old TEXT DEFAULT NULL,
    countries_id INTEGER REFERENCES countries(id),
    photo_url TEXT
);

CREATE TABLE IF NOT EXISTS questions_younger (
    id SERIAL PRIMARY KEY,
    y_question VARCHAR(150) NOT NULL,
    y_correct_answer VARCHAR(100) NOT NULL,
    y_incorrect_answer1 VARCHAR(100) NOT NULL,
    y_incorrect_answer2 VARCHAR(100) NOT NULL,
    y_incorrect_answer3 VARCHAR(100) NOT NULL,
    y_case_files_article_id INTEGER NOT NULL REFERENCES case_files(article_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS questions_older (
    id SERIAL PRIMARY KEY,
    o_question VARCHAR(150) NOT NULL,
    o_correct_answer VARCHAR(100) NOT NULL,
    o_incorrect_answer1 VARCHAR(100) NOT NULL,
    o_incorrect_answer2 VARCHAR(100) NOT NULL,
    o_incorrect_answer3 VARCHAR(100) NOT NULL,
    o_case_files_article_id INTEGER NOT NULL REFERENCES case_files(article_id) ON DELETE CASCADE
);

-- Seed countries
INSERT INTO countries(name, flag, country_code, language_code, silhouette)
VALUES
    ('Canada', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_139,w_200/v1721233336/GlobalAgent-flagCanadaGif_f9bbfq.gif', 'ca', 'en', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_390,w_390/v1723473096/GlobalAgent-Canada_x9mxgc.png'),
    ('Mexico', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_139,w_200/v1721589677/GlobalAgent-Mexicoflag_pat31d.gif', 'mx', 'es', 'https://res.cloudinary.com/dgifdj6nx/image/upload/v1722611809/GlobalAgent-Tile-MexicoNB_qtzvgs.webp'),
    ('Germany', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_139,w_200/v1721419276/GlobalAgent-flag_wkgicw.gif', 'de', 'de', 'https://res.cloudinary.com/dgifdj6nx/image/upload/v1722611885/GlobalAgent-Tile-Germany_1_gazhk1.png');
