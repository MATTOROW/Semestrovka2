CREATE TABLE subtask (
                         id BIGSERIAL PRIMARY KEY,
                         inner_id UUID NOT NULL,
                         subtask_group_id BIGINT NOT NULL,
                         name TEXT NOT NULL,
                         completed BOOLEAN NOT NULL DEFAULT FALSE,
                         position INT NOT NULL
);

ALTER TABLE subtask ADD CONSTRAINT uq_subtask_inner_id UNIQUE (inner_id);
ALTER TABLE subtask ADD CONSTRAINT fk_subtask_group FOREIGN KEY (subtask_group_id) REFERENCES subtask_group(id);

COMMENT ON TABLE subtask IS 'Подзадача внутри группы подзадач';
COMMENT ON COLUMN subtask.inner_id IS 'UUID для фронта';
COMMENT ON COLUMN subtask.subtask_group_id IS 'ID родительской группы подзадач';
COMMENT ON COLUMN subtask.name IS 'Название подзадачи';
COMMENT ON COLUMN subtask.completed IS 'Флаг завершения подзадачи';
COMMENT ON COLUMN subtask.position IS 'Порядок подзадачи в группе';