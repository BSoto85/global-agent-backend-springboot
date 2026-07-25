-- Global Agent Database Schema
-- PostgreSQL

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    uid VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    dob DATE DEFAULT NULL,
    photo TEXT DEFAULT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stats (
    id BIGSERIAL PRIMARY KEY,
    xp INTEGER NOT NULL DEFAULT 0,
    games_played INTEGER NOT NULL DEFAULT 0,
    questions_correct INTEGER NOT NULL DEFAULT 0,
    questions_wrong INTEGER NOT NULL DEFAULT 0,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS countries (
    id BIGSERIAL PRIMARY KEY,
    flag TEXT NOT NULL,
    country_code VARCHAR(2),
    name VARCHAR(30) UNIQUE,
    language_code VARCHAR(2),
    silhouette TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS case_files (
    id BIGSERIAL PRIMARY KEY,
    article_id INTEGER UNIQUE,
    article_content TEXT,
    article_title TEXT,
    publish_date VARCHAR(50),
    summary_young TEXT DEFAULT NULL,
    summary_old TEXT DEFAULT NULL,
    countries_id BIGINT REFERENCES countries(id),
    photo_url TEXT
);

CREATE TABLE IF NOT EXISTS questions_younger (
    id BIGSERIAL PRIMARY KEY,
    y_question VARCHAR(150) NOT NULL,
    y_correct_answer VARCHAR(100) NOT NULL,
    y_incorrect_answer1 VARCHAR(100) NOT NULL,
    y_incorrect_answer2 VARCHAR(100) NOT NULL,
    y_incorrect_answer3 VARCHAR(100) NOT NULL,
    y_case_files_article_id INTEGER NOT NULL REFERENCES case_files(article_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS questions_older (
    id BIGSERIAL PRIMARY KEY,
    o_question VARCHAR(150) NOT NULL,
    o_correct_answer VARCHAR(100) NOT NULL,
    o_incorrect_answer1 VARCHAR(100) NOT NULL,
    o_incorrect_answer2 VARCHAR(100) NOT NULL,
    o_incorrect_answer3 VARCHAR(100) NOT NULL,
    o_case_files_article_id INTEGER NOT NULL REFERENCES case_files(article_id) ON DELETE CASCADE
);
