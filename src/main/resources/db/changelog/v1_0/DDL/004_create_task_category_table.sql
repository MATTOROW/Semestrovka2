-- Создание таблицы категорий задач
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    inner_id UUID NOT NULL,
    project_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(7) NOT NULL
);

-- Внешний ключ
ALTER TABLE category
    ADD CONSTRAINT fk_category_project
        FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE;

-- Индексы
CREATE UNIQUE INDEX idx_category_inner_id ON category(inner_id);
CREATE INDEX idx_category_project_id ON category(project_id);

-- Комментарии
COMMENT ON TABLE category IS 'Категории задач в проектах';
COMMENT ON COLUMN category.id IS 'Уникальный числовой ID категории';
COMMENT ON COLUMN category.inner_id IS 'Публичный UUID категории';
COMMENT ON COLUMN category.project_id IS 'ID проекта (связь с projects.id)';
COMMENT ON COLUMN category.name IS 'Название категории';
COMMENT ON COLUMN category.color IS 'Цвет категории в HEX (#FF0000)';