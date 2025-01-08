CREATE TABLE IF NOT EXISTS Users (
    id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    secret_question TEXT NOT NULL,
    secret_answer TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS DiaryEntries (
    id SERIAL PRIMARY KEY,
    entry_title TEXT NOT NULL,
    entry_content TEXT NOT NULL,
    entry_date TEXT NOT NULL,
    user_id INTEGER REFERENCES Users(id)
);