# StudentRepositoryテスト

## 概要

`StudentRepository`のMyBatisマッピングとSQLが正しく動作することを確認するRepositoryテストです。
本番のMySQLには接続せず、テスト実行時だけH2インメモリDBを使用します。

## このPRの対象範囲

このPRで追加するのは、`StudentRepository`を対象としたRepositoryテストと、その実行に必要なH2用SQL・依存関係・GitHub Actions設定のみです。Controller、Service、Converterの実装およびテストは変更対象に含みません。

`StudentConverterTest.java`はすでに`main`ブランチに存在し、今回のブランチでは変更していないため、PRの「Files changed」には表示されません。

- [mainブランチのStudentConverterTest.java](https://github.com/Tempest9174/Spring_Boot_Study_38/blob/main/src/test/java/raisetech/student/management2/controller/converter/StudentConverterTest.java)
- [mainブランチのStudentConverter.java](https://github.com/Tempest9174/Spring_Boot_Study_38/blob/main/src/main/java/raisetech/student/management2/controller/converter/StudentConverter.java)

## テスト構成

| ファイル | 役割 |
| --- | --- |
| `StudentRepositoryTest.java` | Repositoryテスト本体 |
| `src/test/resources/repository-schema.sql` | H2用のテーブル定義 |
| `src/test/resources/repository-data.sql` | 受講生10件・コース7件の初期データ |
| `build.gradle` | MyBatisテストとH2の依存関係 |
| `.github/workflows/repository-tests.yml` | GitHub ActionsでRepositoryテストを実行 |

各テストの前に`@Sql`でテーブルとデータを再作成します。そのため、登録・更新・論理削除を行うテストが、別のテスト結果へ影響しません。

## 正常系テスト

| No. | テスト内容 | 確認すること |
| --- | --- | --- |
| 1 | 受講生一覧検索 | 初期データ10件が取得され、各項目が正しくマッピングされること |
| 2 | 受講生の単一検索 | 存在するIDを指定すると、対象受講生のID・氏名・メールアドレスが取得できること |
| 3 | 受講生登録 | 新しい受講生を登録でき、IDが自動採番され、登録内容を再検索できること |
| 4 | コース一覧検索 | 初期データ7件がすべて取得できること |
| 5 | 受講生別コース検索 | 指定した受講生IDに紐づくコースだけが取得できること |
| 6 | コース登録 | 新しいコースを登録でき、IDが自動採番され、登録内容を再検索できること |
| 7 | 受講生情報更新 | 氏名とメールアドレスの更新内容が再検索結果へ反映されること |
| 8 | 論理削除 | 物理削除せず、`isDeleted=true`へ更新されること |

## 該当なし・異常系テスト

| No. | テスト内容 | 期待結果 |
| --- | --- | --- |
| 1 | 存在しない受講生IDを検索 | 例外ではなく`null`が返ること |
| 2 | 存在しない受講生IDのコースを検索 | 空リストが返ること |
| 3 | 名前が`null`の受講生を登録 | `DataIntegrityViolationException`が発生すること |
| 4 | コース名が`null`のコースを登録 | `DataIntegrityViolationException`が発生すること |
| 5 | 存在しない受講生IDへコースを登録 | 外部キー制約により`DataIntegrityViolationException`が発生すること |

### 1. 存在しない受講生IDを検索するテスト

テストデータに登録されている受講生IDは`1`から`10`までです。`999`はその中に存在しないIDとして、検索結果が0件になる場合を確認するために使用しています。`999`自体に特別な意味はなく、テストデータと重ならない番号であれば同じ目的を果たせます。

```java
assertNull(repository.searchStudent("999"));
```

この呼び出しでは、次のSQLが実行されます。

```sql
SELECT * FROM students WHERE id = 999;
```

`searchStudent`は受講生1人を`Student`オブジェクトとして返すメソッドです。該当する行が1件もない場合、MyBatisから返る値は`null`になります。そのため、このテストでは例外が発生することではなく、`null`が返ることを`assertNull`で確認しています。

### 2. 存在しない受講生IDのコースを検索するテスト

こちらも、存在しない受講生IDとして`999`を指定します。ただし、検索対象は受講生本人ではなく、その受講生に紐づくコースの一覧です。

```java
List<StudentsCourse> courses = repository.searchStudentCourse("999");
assertTrue(courses.isEmpty());
```

この呼び出しでは、次のSQLが実行されます。

```sql
SELECT * FROM students_courses WHERE student_id = 999;
```

`searchStudentCourse`は複数のコースを`List<StudentsCourse>`として返すメソッドです。該当する行が0件の場合は`null`ではなく、要素数0の空リスト`[]`が返ります。そのため、`courses.isEmpty()`が`true`になることを確認しています。

つまり、1番と2番の違いは次のとおりです。

| 検索内容 | 戻り値の型 | 該当データが0件の場合 |
| --- | --- | --- |
| 受講生を1人検索 | `Student` | `null` |
| コースを一覧検索 | `List<StudentsCourse>` | 空リスト`[]` |

## 実行方法

Windows:

```powershell
.\gradlew.bat test --tests raisetech.student.management2.repository.StudentRepositoryTest
```

macOS / Linux:

```bash
./gradlew test --tests raisetech.student.management2.repository.StudentRepositoryTest
```

実行後のHTMLレポートは`build/reports/tests/test/index.html`へ出力されます。

## 動作確認

- テスト数: 13件
- 失敗: 0件
- スキップ: 0件
- 成功率: **100%**
- GitHub Actions: [Repository tests 実行結果](https://github.com/Tempest9174/Spring_Boot_Study_38/actions/runs/40001888314)

![Repositoryテスト 13件・100%成功](../../../../../../../docs/images/repository-tests-100-percent.png)

## 削除の扱い

このアプリケーションは受講生レコードを物理的に削除しません。`Student.isDeleted`を`true`に更新する論理削除をテストしています。

