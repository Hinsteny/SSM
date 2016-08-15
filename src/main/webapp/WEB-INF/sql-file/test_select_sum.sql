-- 做一个把行变列的SQL测试
set SEARCH_PATH to public;
-- SCRIPTS

CREATE TABLE public.tb_score
(
	id SERIAL,
	t_name character varying(24) NOT NULL,
	t_course character varying(64) NOT NULL,
	t_score smallint NOT NULL DEFAULT 0,

	CONSTRAINT tb_score_id PRIMARY KEY (id),
	CONSTRAINT tb_score_unq_name UNIQUE (t_name, t_course)
)
WITH (
OIDS=FALSE
);
-- SCRIP

INSERT INTO public.tb_score (t_name, t_course, t_score)
VALUES ('小明','语文',90), ('小明','数学',87), ('小明','英语',85),
	('小红','语文',92), ('小红','数学',89), ('小红','英语',95);

SELECT t_name as "姓名",
			 SUM(CASE t_course WHEN '数学' THEN t_score ELSE 0 END) AS "数学",
			 SUM(CASE t_course WHEN '英语' THEN t_score ELSE 0 END) AS 英语,
			 SUM(CASE t_course WHEN '语文' THEN t_score ELSE 0 END) AS 语文
FROM public.tb_score
GROUP BY t_name;

SELECT t_name as "姓名",
			 SUM(CASE t_course WHEN '数学' THEN t_score ELSE null END)  数学,
			 SUM(CASE t_course WHEN '英语' THEN t_score ELSE null END)  英语,
			 SUM(CASE t_course WHEN '语文' THEN t_score ELSE null END)  语文
FROM public.tb_score
GROUP BY t_name;