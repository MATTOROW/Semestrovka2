-- Создание таблицы проектов
CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    inner_id UUID UNIQUE NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT TIMEZONE('UTC', NOW())
);

-- Уникальный индекс для inner_id
CREATE UNIQUE INDEX idx_project_inner_id ON project(inner_id);

-- Комментарии к колонкам
COMMENT ON TABLE project IS 'Таблица проектов';
COMMENT ON COLUMN project.id IS 'Уникальный числовой ID (для JOIN)';
COMMENT ON COLUMN project.inner_id IS 'Публичный UUID проекта (для API)';
COMMENT ON COLUMN project.name IS 'Название проекта';
COMMENT ON COLUMN project.description IS 'Описание проекта';
COMMENT ON COLUMN project.created_at IS 'Дата создания проекта';