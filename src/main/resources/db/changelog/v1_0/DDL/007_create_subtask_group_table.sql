CREATE TABLE subtask_group (
    id BIGSERIAL PRIMARY KEY,
    inner_id UUID NOT NULL,
    name TEXT NOT NULL,
    task_id BIGINT NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    position INT NOT NULL
);

ALTER TABLE subtask_group ADD CONSTRAINT uq_subtask_group_inner_id UNIQUE (inner_id);
ALTER TABLE subtask_group ADD CONSTRAINT fk_subtask_group_task FOREIGN KEY (task_id) REFERENCES task(id)
ON DELETE CASCADE;

COMMENT ON TABLE subtask_group IS 'Группа подзадач, связанных с конкретной задачей';
COMMENT ON COLUMN subtask_group.inner_id IS 'UUID для внешнего использования';
COMMENT ON COLUMN subtask_group.name IS 'Название группы подзадач';
COMMENT ON COLUMN subtask_group.task_id IS 'ID родительской задачи';
COMMENT ON COLUMN subtask_group.completed IS 'Флаг, что вся группа выполнена';
COMMENT ON COLUMN subtask_group.position IS 'Позиция группы в списке (для сортировки)';