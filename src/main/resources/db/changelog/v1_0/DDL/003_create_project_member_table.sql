-- Создание таблицы участников проектов
CREATE TABLE project_member (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    added_at TIMESTAMP WITHOUT TIME ZONE DEFAULT TIMEZONE('UTC', NOW())
);

-- Внешние ключи
ALTER TABLE project_member
    ADD CONSTRAINT fk_project_member_project
        FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE;

ALTER TABLE project_member
    ADD CONSTRAINT project_account
        UNIQUE (account_id, project_id);


CREATE INDEX idx_project_members_project_id ON project_member(project_id);
CREATE INDEX idx_project_members_account_id ON project_member(account_id);
CREATE UNIQUE INDEX idx_project_members_project_account ON project_member(project_id, account_id);

-- Комментарии
COMMENT ON TABLE project_member IS 'Участники проектов';
COMMENT ON COLUMN project_member.id IS 'Уникальный числовой ID записи';
COMMENT ON COLUMN project_member.project_id IS 'ID проекта (связь с projects.id)';
COMMENT ON COLUMN project_member.account_id IS 'ID аккаунта (из AccountMicroservice)';
COMMENT ON COLUMN project_member.role IS 'Роль участника';
COMMENT ON COLUMN project_member.added_at IS 'Дата добавления участника';