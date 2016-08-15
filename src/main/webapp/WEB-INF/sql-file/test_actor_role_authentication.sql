-- z为做用户权限管理插入数据
set SEARCH_PATH to public;
-- SCRIPTS
INSERT INTO public.Actor (name, password, enabled, firstName, lastName, email, created)
VALUES ('Hinsteny','welcome', TRUE, 'Hisoka', 'Hinsteny', 'hinsteny@gmail.com', now());

INSERT INTO public.Role (name, displayName, description)
VALUES ('admin','Administrator', 'user could control all things!');

INSERT INTO public.Permission (name, url, description)
VALUES ('AdminIndex','/admin', 'Visit admin index page!');

INSERT INTO public.ActorRole (actorId, roleId)
VALUES ('1','1');

INSERT INTO public.RolePermission (roleId, permissionId)
VALUES ('1','1');