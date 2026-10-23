INSERT INTO students
  (id, name, kana_name, nickname, email, area, age, sex, remark, isDeleted)
VALUES
  (1, '山田太郎', 'ヤマダタロウ', 'たろう', 'taro@example.com', '東京', 25, '男性', '特になし', FALSE),
  (2, '佐藤花子', 'サトウハナコ', 'はな', 'hanako@example.com', '大阪', 30, '女性', NULL, FALSE),
  (3, '鈴木健', 'スズキケン', 'けん', 'ken@example.com', '福岡', 22, '男性', NULL, FALSE),
  (4, '高橋美咲', 'タカハシミサキ', 'みさき', 'misaki@example.com', '北海道', 27, '女性', 'リモート受講', FALSE),
  (5, '田中大輔', 'タナカダイスケ', 'だい', 'daisuke@example.com', '愛知', 35, '男性', NULL, FALSE),
  (6, '伊藤桜', 'イトウサクラ', 'さくら', 'sakura@example.com', '宮城', 24, '女性', NULL, FALSE),
  (7, '渡辺翔太', 'ワタナベショウタ', 'しょう', 'shota@example.com', '広島', 29, '男性', '転職活動中', FALSE),
  (8, '小林真由', 'コバヤシマユ', 'まゆ', 'mayu@example.com', '長野', 31, '女性', NULL, FALSE),
  (9, '加藤直樹', 'カトウナオキ', 'なお', 'naoki@example.com', '京都', 26, '男性', NULL, FALSE),
  (10, '吉田愛', 'ヨシダアイ', 'あい', 'ai@example.com', '沖縄', 33, '女性', '週末受講希望', FALSE);

INSERT INTO students_courses
  (id, student_id, course_name, course_start_at, course_end_at)
VALUES
  (1, 1, 'Java基礎', '2026-01-01', '2026-12-31'),
  (2, 1, 'Spring Boot', '2026-02-01', '2027-01-31'),
  (3, 2, 'AWS', '2026-03-01', '2027-02-28'),
  (4, 3, 'Docker', '2026-04-01', '2027-03-31'),
  (5, 4, 'React', '2026-05-01', '2027-04-30'),
  (6, 5, 'Python', '2026-06-01', '2027-05-31'),
  (7, 6, 'SQL', '2026-07-01', '2027-06-30');

