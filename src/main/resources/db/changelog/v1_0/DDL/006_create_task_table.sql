CREATE TABLE task (
                      id BIGSERIAL PRIMARY KEY,
                      inner_id UUID NOT NULL,
                      name TEXT NOT NULL,
                      description TEXT,
                      status VARCHAR(50) NOT NULL,
                      create_date TIMESTAMP NOT NULL,
                      end_date TIMESTAMP,
                      deadline TIMESTAMP,
                      category_id BIGINT NOT NULL,
                      author_id BIGINT NOT NULL,
                      position INTEGER NOT NULL
);

ALTER TABLE task ADD CONSTRAINT uq_task_inner_id UNIQUE (inner_id);
ALTER TABLE task ADD CONSTRAINT fk_task_category FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE task ADD CONSTRAINT fk_task_author FOREIGN KEY (author_id) REFERENCES account(id);

COMMENT ON TABLE task IS 'Задача проекта с описанием, сроками и статусом';
COMMENT ON COLUMN task.inner_id IS 'Внешний UUID, используется на фронте';
COMMENT ON COLUMN task.name IS 'Название задачи';
COMMENT ON COLUMN task.description IS 'Описание задачи';
COMMENT ON COLUMN task.status IS 'Статус задачи (как строка из enum)';
COMMENT ON COLUMN task.create_date IS 'Дата создания задачи';
COMMENT ON COLUMN task.end_date IS 'Дата завершения задачи';
COMMENT ON COLUMN task.deadline IS 'Дедлайн задачи';
COMMENT ON COLUMN task.category_id IS 'ID категории задачи';
COMMENT ON COLUMN task.author_id IS 'Автор задачи (создатель)';
COMMENT ON COLUMN task.position IS 'Позиция для отображения';